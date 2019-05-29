package com.sdev.svias.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import com.sdev.svias.R;
import com.sdev.svias.Controller.DownloadImageFromInternet;
import com.sdev.svias.Controller.HTTPRequest;
import com.sdev.svias.Controller.ScriptDLL;
import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.Model.Marcador;

public class BuscaDenuncia extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    static String query;
    static String cep;
    private GoogleMap mMap;
    FrameLayout loadMapa;
    List<Marcador> marcadores;
    LatLng mapa;
    SupportMapFragment mapFragment;
    ImageView imgLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(getIntent().hasExtra("query")){
            Bundle extras = getIntent().getExtras();
            query = extras.getString("query");
            cep = extras.getString("cep");
            mapa = new LatLng(extras.getDouble("lat"), extras.getDouble("lng"));
        }
        super.onCreate(savedInstanceState);
        super.setTitle("Buscando por " + query);
        setContentView(R.layout.activity_busca_denuncia);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgLoad = (ImageView) findViewById(R.id.imgLoad);
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        Glide.with(getApplicationContext()).load(R.drawable.load).into(imgLoad);

        exibirProgress(true);

        //Buscando os dados da busca
        BuscaDadosSistema sistema = new BuscaDadosSistema();
        sistema.execute();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    //Mostra que não encontrou a localização

    ;
    public void exibeAlert() {
        AlertDialog.Builder loca = new AlertDialog.Builder(this);
        loca.setMessage("Não encontramos nenhuma denúncia com o termo utilizado!");
        loca.setCancelable(false);
        loca.setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                onBackPressed();
            }
        });
        AlertDialog alert = loca.create();
        alert.show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng local = new LatLng(mapa.latitude, mapa.longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(local, 13);
        mMap.moveCamera(update);
        mMap.setOnMarkerClickListener(this);

        for (int i = 0; i < marcadores.size(); i++) {
            //
            Marker marcador;
            if(marcadores.get(i).getSituacao().equals("Pendente")){

                marcador = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marcadores.get(i).getLatlng().latitude, marcadores.get(i).getLatlng().longitude))
                        .title(marcadores.get(i).getNome())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                );
            }else if(marcadores.get(i).getSituacao().equals("Em Progresso" )){
                marcador = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marcadores.get(i).getLatlng().latitude, marcadores.get(i).getLatlng().longitude))
                        .title(marcadores.get(i).getNome())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                );
            }else{
                marcador = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marcadores.get(i).getLatlng().latitude, marcadores.get(i).getLatlng().longitude))
                        .title(marcadores.get(i).getNome())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                );
            }
            marcador.hideInfoWindow();
            marcador.setTag(0);

        }
    }

    private void exibirProgress(boolean exibir) {
        loadMapa.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final AlertDialog.Builder viewop = new AlertDialog.Builder(this);

        LatLng posisao = marker.getPosition();
        int pos = -1;

        LayoutInflater li = getLayoutInflater();
        //buscando os dados do marcador
        for (int i = 0; i < marcadores.size(); i++){
            if(marcadores.get(i).getLatlng().latitude == posisao.latitude && marcadores.get(i).getLatlng().longitude == posisao.longitude){
                pos = i;
            }
        }

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.click_marcador, null);
        ImageView imgDenuncia = (ImageView) view.findViewById(R.id.imgMarcador);

        Glide.with(view).load(R.drawable.load).into(imgDenuncia);
        TextView nomeDenuncia = (TextView) view.findViewById(R.id.nomeDenuncia);
        nomeDenuncia.setText(marcadores.get(pos).getNome());

        DownloadImageFromInternet img = new DownloadImageFromInternet(imgDenuncia);
        img.execute(marcadores.get(pos).getMidia().get(0));

        imgDenuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        TextView descDenuncia = (TextView) view.findViewById(R.id.descDenuncia);
        descDenuncia.setText(marcadores.get(pos).getDescricao());
        TextView cordenadasDenuncia = (TextView) view.findViewById(R.id.cordenadas);

        ImageView imgSituacao = (ImageView) view.findViewById(R.id.situacaoDenuncia);

        if(marcadores.get(pos).getSituacao().equals("Pendente" )){
            //troca a imagem aqui de pendente
            imgSituacao.setImageDrawable(getDrawable(R.drawable.vermelho));
        }else if(marcadores.get(pos).getSituacao().equals("Em Progresso" )){
            //Em andamento
            imgSituacao.setImageDrawable(getDrawable(R.drawable.amarelo));
        }else{
            //Concluido
            imgSituacao.setImageDrawable(getDrawable(R.drawable.verde));
        }
        cordenadasDenuncia.setText("Latitude: " + posisao.latitude + " Longitude: " + posisao.longitude);
        viewop.setView(view);
        viewop.setCancelable(false);
        viewop.setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                AlertDialog b = viewop.create();
                b.dismiss();
            }
        });
        AlertDialog alert = viewop.create();
        alert.show();
        return false;
    }

    //Mostra mensagem de nada encontrado
    public void BuscaNada() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Não Encontramos Nenhuma Denúncia Para Sua Busca!");
        builder.setCancelable(false);

        builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                onBackPressed();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void ErroSistema() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Houve Algum Erro!");
        builder.setCancelable(false);

        builder.setNegativeButton("Voltar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                onBackPressed();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }







    /*BUSCA DE DADOS*/
    private class BuscaDadosSistema extends AsyncTask<String, String, String> {
        private String retorno;

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
            //buscando os dados no servidor
            ScriptDLL func = new ScriptDLL();
            HTTPRequest httpMark = new HTTPRequest();
            try {
                marcadores = func.getMarcadores(httpMark.getDados(UtilAPP.LINK_SERVIDOR + "buscaMarcadores.php?cep=" + cep+"&busca=" + query, ""));
                if(marcadores.size() == 0){
                    this.retorno = "erro2";
                }else{
                    this.retorno = "ok";
                }

            } catch (JSONException e) {
                this.retorno = "erro1";
            }
            return this.retorno;
        }

        @Override
        protected void onPostExecute(String result) {

            if(result == "ok"){
                chamaMapa();
                Toast.makeText(getApplicationContext(), "Encontramos " + String.valueOf( marcadores.size() ) + " Denúncia(s)", Toast.LENGTH_LONG).show();
            }else if(result == "erro2"){
                BuscaNada();
            }else{
                ErroSistema();
            }

            exibirProgress(false);
        }
    }

    public void chamaMapa(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }


}
