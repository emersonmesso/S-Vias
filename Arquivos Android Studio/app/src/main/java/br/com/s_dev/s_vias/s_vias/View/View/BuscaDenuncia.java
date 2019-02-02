package br.com.s_dev.s_vias.s_vias.View.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.List;

import br.com.s_dev.s_vias.s_vias.R;
import br.com.s_dev.s_vias.s_vias.View.Controller.DownloadImageFromInternet;
import br.com.s_dev.s_vias.s_vias.View.Controller.HTTPRequest;
import br.com.s_dev.s_vias.s_vias.View.Controller.ScriptDLL;
import br.com.s_dev.s_vias.s_vias.View.Controller.UtilAPP;
import br.com.s_dev.s_vias.s_vias.View.Model.Marcador;

public class BuscaDenuncia extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    static String query;
    static String cep;
    private GoogleMap mMap;
    FrameLayout loadMapa;
    List<Marcador> marcadores;
    LatLng mapa;

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


        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        exibirProgress(true);

        //buscando os dados no servidor
        ScriptDLL func = new ScriptDLL();
        HTTPRequest httpMark = new HTTPRequest();
        try {
            marcadores = func.getMarcadores(httpMark.getDados(UtilAPP.LINK_SERVIDOR + "buscaMarcadores.php?cep=" + cep+"&busca=" + query, ""));
            if(marcadores.size() == 0){
                exibeAlert();
            }
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapa);
            mapFragment.getMapAsync(this);
            exibirProgress(false);

        } catch (JSONException e) {
            Toast.makeText(this, "Não foi possível buscar denúncia!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    //Mostra que não encontrou a localização
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
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(local, 14);
        mMap.moveCamera(update);
        mMap.setOnMarkerClickListener(this);

        for (int i = 0; i < marcadores.size(); i++) {

            //
            Marker marcador = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(marcadores.get(i).getLatlng().latitude, marcadores.get(i).getLatlng().longitude))
                    .title(marcadores.get(i).getNome())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            );
            marcador.hideInfoWindow();
            marcador.setTag(0);
        }
        exibirProgress(false);
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

        Log.i("posicao", "Posição do marcador no mapa: " + posisao.latitude + " | " + posisao.longitude);
        Log.i("posicao", "Posição do marcador: " + marcadores.get(pos).getLatlng().latitude + " | " + marcadores.get(pos).getLatlng().longitude);

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.click_marcador, null);
        ImageView imgDenuncia = (ImageView) view.findViewById(R.id.imgMarcador);

        imgDenuncia.setImageDrawable(getDrawable(R.drawable.load));
        TextView nomeDenuncia = (TextView) view.findViewById(R.id.nomeDenuncia);
        nomeDenuncia.setText(marcadores.get(pos).getNome());

        DownloadImageFromInternet img = new DownloadImageFromInternet(imgDenuncia);
        img.execute(marcadores.get(pos).getMidia());
        TextView descDenuncia = (TextView) view.findViewById(R.id.descDenuncia);
        descDenuncia.setText(marcadores.get(pos).getDescricao());
        TextView cordenadasDenuncia = (TextView) view.findViewById(R.id.cordenadas);

        ImageView imgSituacao = (ImageView) view.findViewById(R.id.situacaoDenuncia);

        if(marcadores.get(pos).getSituacao() == "pendente"){
            //troca a imagem aqui de pendente
            imgSituacao.setImageDrawable(getDrawable(R.drawable.verde));
        }else if(marcadores.get(pos).getSituacao() == "andamento"){
            //Em andamento
            imgSituacao.setImageDrawable(getDrawable(R.drawable.verde));
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
}
