package com.sdev.svias.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.sdev.svias.Controller.DownloadImageFromInternet;
import com.sdev.svias.Controller.HTTPRequest;
import com.sdev.svias.Controller.ScriptDLL;
import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class ViewDenunciaInstituicao extends AppCompatActivity {
    private String nomeDenuncia;
    private int id_loc;
    private String situacao = "";
    private String itemSelect;
    private int IDSituacao;
    private ArrayList<String> foto;
    private String desc;

    //
    private LinearLayout telaSituacao;
    //
    private TextView descDenuncia;
    private ImageView imgDenuncia;
    private TextView situacaoDenuncia;

    //table
    private String endereco;
    private String cidade;
    private String cep;
    private String data;

    private TextView dataDen;
    private TextView cidadeDen;
    private TextView cepDen;
    private TextView enderecoDen;


    //LoadMapa
    FrameLayout loadMapa;
    private ImageView imgLoad;

    //
    private Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(getIntent().hasExtra("nome")){
            Bundle extras = getIntent().getExtras();
            nomeDenuncia = extras.getString("nome");
            id_loc = extras.getInt("id_loc");
            situacao = extras.getString("sit");
            itemSelect = situacao;
            foto = extras.getStringArrayList("foto");
            desc = extras.getString("desc");
            endereco = extras.getString("endereco");
            cidade = extras.getString("cidade");
            data = extras.getString("data");
            cep = extras.getString("cep");
        }
        super.onCreate(savedInstanceState);
        super.setTitle("Denúncia - " + nomeDenuncia);
        setContentView(R.layout.activity_view_denuncia_instituicao);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Interação do o XML*/
        alteraSituacao();

        btnAdd = (Button) findViewById(R.id.btnAddImage);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ViewDenunciaInstituicao.this, ViewImages.class);
                intent.putExtra("imagens", foto);
                startActivity(intent);
                onPause();
            }
        });


        //Tela Load
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        imgLoad = (ImageView) findViewById(R.id.imgLoad);
        Glide.with(this).load(R.drawable.load).into(imgLoad);

        telaSituacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder viewop = new AlertDialog.Builder(ViewDenunciaInstituicao.this);
                LayoutInflater li = getLayoutInflater();
                final View view = li.inflate(R.layout.spinner, null);

                Spinner spinner = (Spinner) view.findViewById(R.id.planets_spinner);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.planets_array, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        itemSelect = String.valueOf(parent.getSelectedItem());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                viewop.setTitle("Selecione a Opção");

                viewop.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        situacao = itemSelect;
                        alteraSituacao();
                        //atualiza
                        AtualizaSituacao sistema = new AtualizaSituacao();
                        sistema.execute();
                    }
                });


                viewop.setView(view);
                viewop.setCancelable(true);
                AlertDialog alert = viewop.create();
                alert.show();
            }
        });

        //Descrição
        descDenuncia = (TextView) findViewById(R.id.descDenuncia);
        descDenuncia.setText(desc);

        //Imagem
        imgDenuncia = (ImageView) findViewById(R.id.imgDenuncia);

        if(foto.equals("")){
            imgDenuncia.setImageResource(R.drawable.image_off);
        }else{
            DownloadImageFromInternet img = new DownloadImageFromInternet(imgDenuncia);
            img.execute(foto.get(0));
        }
        imgDenuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //table
        dataDen = (TextView) findViewById(R.id.data);
        dataDen.setText(data);
        cidadeDen = (TextView) findViewById(R.id.cidade);
        cidadeDen.setText(cidade);
        cepDen = (TextView) findViewById(R.id.cep);
        cepDen.setText(cep);
        enderecoDen = (TextView) findViewById(R.id.endereco);
        enderecoDen.setText(endereco);
        exibirProgress(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void exibirProgress(boolean exibir) {
        loadMapa.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }
    private void alteraSituacao(){
        Log.d("Script", situacao);
        telaSituacao = (LinearLayout) findViewById(R.id.telaSituacao);
        if(situacao.equals("Pendente")){
            telaSituacao.setBackgroundResource(R.color.colorPendente);
            IDSituacao = 1;
        }else if(situacao.equals("Em Progresso")){
            telaSituacao.setBackgroundResource(R.color.colorAppBar);
            IDSituacao = 2;
        }else{
            telaSituacao.setBackgroundResource(R.color.colorConcluido);
            IDSituacao = 3;
        }
        situacaoDenuncia = (TextView) findViewById(R.id.nomeSituacao);
        situacaoDenuncia.setText(situacao);
    }



    /*CLASES QUE ATUALIZAM*/
    private class AtualizaSituacao extends AsyncTask<String, String, String> {
        private String retorno;
        public AtualizaSituacao (){

        }

        @Override
        protected void onPreExecute (){
            exibirProgress(true);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {

            HTTPRequest processa = new HTTPRequest();
            String dados = processa.getDados(UtilAPP.LINK_SERVIDOR + "atualizaSituacao.php?id=" + IDSituacao+"&id_loc=" + id_loc, "");
            this.retorno = dados;
            return this.retorno;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("Script", "Resultado:" + result);
            if(!result.equals("")){
                Toast.makeText(ViewDenunciaInstituicao.this, result, Toast.LENGTH_LONG).show();
            }
            exibirProgress(false);
        }
    }
}
