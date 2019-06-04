package com.sdev.svias.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.ListPreloader;
import com.google.android.gms.maps.model.LatLng;
import com.sdev.svias.Controller.DenunciasAdapter;
import com.sdev.svias.Controller.HTTPRequest;
import com.sdev.svias.Controller.ScriptDLL;
import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.Model.Marcador;
import com.sdev.svias.R;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;

public class Instituicao extends AppCompatActivity {
    private String Email;
    private FrameLayout loadMapa;
    private ImageView imgLoad;
    private List<Marcador> marcadores;
    private ArrayAdapter adapter;
    private ListView denuncias;
    private Button btnSair;
    private SwipeRefreshLayout refresh;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicao);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        //Loading
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        imgLoad = (ImageView) findViewById(R.id.imgLoad);
        Glide.with(this).load(R.drawable.load).into(imgLoad);
        exibirProgress(false);

        //Botão Sair
        btnSair = (Button) findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //ListView
        refresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BuscaDadosSistema busca = new BuscaDadosSistema();
                busca.execute();
            }
        });
        denuncias = (ListView) findViewById(R.id.lista);
        denuncias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //envia os dados para uma nova tela
                Intent intent = new Intent(Instituicao.this, ViewDenunciaInstituicao.class);
                intent.putExtra("id_loc", marcadores.get(position).getId_loc());
                intent.putExtra("nome", marcadores.get(position).getNome());
                intent.putExtra("foto", marcadores.get(position).getMidia());
                intent.putExtra("sit", marcadores.get(position).getSituacao());
                intent.putExtra("desc", marcadores.get(position).getDescricao());
                intent.putExtra("endereco", marcadores.get(position).getRua());
                intent.putExtra("data", marcadores.get(position).getData());
                intent.putExtra("cidade", marcadores.get(position).getCidade());
                intent.putExtra("cep", marcadores.get(position).getCep());
                startActivity(intent);
                onPause();
            }
        });

        Email = preferences.getString("email" ,null);

        BuscaDadosSistema sistema = new BuscaDadosSistema();
        sistema.execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void logout(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loginInstituicao", "nao");
        editor.putString("email", "");
        editor.commit();
        Intent intent = new Intent(this, LoginInstituicao.class);
        startActivity(intent);
        finish();
    }
    private void exibirProgress(boolean exibir) {
        loadMapa.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    private void init() {
        //Povoando a ListView
        adapter = new DenunciasAdapter(this, marcadores);
        denuncias.setAdapter(adapter);
        exibirProgress(false);
        refresh.setRefreshing(false);
    }

    private class BuscaDadosSistema extends AsyncTask<String, String, String> {
        private String retorno;
        public BuscaDadosSistema (){

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
            this.retorno = "ok";
            ScriptDLL func = new ScriptDLL();
            //buscando os dados no servidor
            HTTPRequest httpMark = new HTTPRequest();
            String dados = httpMark.getDados(UtilAPP.LINK_SERVIDOR + "getDenunciasInstituicao.php?email=" + Email, "");

            if(dados == ""){
                this.retorno = "erro1";
            }else{
                try {
                    marcadores = func.getMarcadoresInstituicao(dados);
                    this.retorno = "ok";
                } catch (JSONException e) {
                    this.retorno = "erro1";
                }
            }
            return this.retorno;
        }

        @Override
        protected void onPostExecute(String result) {

            if(result == "ok"){

                init();

            }else if(result == "erro1"){
                Toast.makeText(getApplicationContext(), "HOUVE ALGUM ERRO!", Toast.LENGTH_LONG).show();
            }

            exibirProgress(false);
        }
    }

}
