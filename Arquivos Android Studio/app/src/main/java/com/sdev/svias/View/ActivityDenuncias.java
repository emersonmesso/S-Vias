package com.sdev.svias.View;

import android.content.Intent;
import android.location.Criteria;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.sdev.svias.Controller.DenunciasAdapter;
import com.sdev.svias.Controller.HTTPRequest;
import com.sdev.svias.Controller.ScriptDLL;
import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.Model.Marcador;
import com.sdev.svias.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ActivityDenuncias extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private String email;
    private String cep;
    private LatLng mapa;
    private FrameLayout loadMapa;
    private List<Marcador> marcadores;
    private ListView denuncias;
    private ArrayAdapter adapter;
    private ImageView imgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(getIntent().hasExtra("email")){
            Bundle extras = getIntent().getExtras();
            email = extras.getString("email");
            cep = extras.getString("cep");
            mapa = new LatLng(extras.getDouble("lat"), extras.getDouble("lng"));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncias);

        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        denuncias = (ListView) findViewById(R.id.listview);
        denuncias.setOnItemClickListener(this);
        denuncias.setDividerHeight(20);
        imgLoad = (ImageView) findViewById(R.id.imgLoad);
        Glide.with(this).load(R.drawable.load).into(imgLoad);
        exibirProgress(false);

        BuscaDadosSistema sistema = new BuscaDadosSistema();
        sistema.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void exibirProgress(boolean exibir) {
        loadMapa.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    private void init() {
        //Povoando a ListView
        adapter = new DenunciasAdapter(this, marcadores);
        denuncias.setAdapter(adapter);
        exibirProgress(false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /*BUSCA AS DENÚNCIAS*/
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
            try {
                marcadores = func.getMarcadores(httpMark.getDados(UtilAPP.LINK_SERVIDOR + "getDenuncias.php?email=" + email, ""));
            } catch (JSONException e) {
                this.retorno = "erro1";
            }
            this.retorno = "ok";

            return this.retorno;
        }

        @Override
        protected void onPostExecute(String result) {

            if(result == "ok"){

                init();

            }else if(result == "erro1"){

            }

            exibirProgress(false);
        }
    }
}
