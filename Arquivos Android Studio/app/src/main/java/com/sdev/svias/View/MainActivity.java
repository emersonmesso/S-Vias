package com.sdev.svias.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.sdev.svias.Controller.CriaDataBase;
import com.sdev.svias.Controller.DownloadImageFromInternet;
import com.sdev.svias.Controller.HTTPRequest;
import com.sdev.svias.Controller.ScriptDLL;
import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.Model.Cidades;
import com.sdev.svias.Model.Marcador;
import com.sdev.svias.Model.Parametro;
import com.sdev.svias.R;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, SearchView.OnQueryTextListener, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions gso;
    private String personName;
    private String personGivenName;
    private String personFamilyName;
    private String personEmail;
    private String personId;
    private Uri personPhoto;
    TextView nomeUser;
    TextView emailUser;
    ImageView imageUser;
    Bitmap imagem;
    private AdView mAdView;
    SearchView campoBusca;
    FrameLayout loadMapa;
    List<Address> lugares;
    LocationManager locationManager;
    private String provider;
    Location minhaLocalizacao;
    boolean ativoGPS = false;
    //Lista de marcadores
    List<Marcador> marcadores;
    //Lista de cidades
    List<Cidades> cidades;
    private boolean minhaCidade = false;
    LatLng localMapaP = new LatLng(-12.4048291, -55.0187512);
    LatLng localMapaG;
    int zP = 5;
    int zG = 14;
    private static final int OPEN_REQUEST_CODE = 41;
    Bitmap imageSelect;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityAdd.class);
                finish();
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);



        if(acc != null){
            personName = acc.getDisplayName();
            personEmail = acc.getEmail();
            personPhoto = acc.getPhotoUrl();
        }
        //



        /*ANUNCIOS*/

        //AdMob
        MobileAds.initialize(this, "ca-app-pub-1846233707943905~6483000765");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Toast.makeText(getApplicationContext(), "Anúncio Atualizado !", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        /*ANÚNCIOS*/


        /*CAMPO DE BUSCA*/
        campoBusca = (SearchView) findViewById(R.id.campoBusca);
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);

        campoBusca.setOnQueryTextListener(this);
        /*CAMPO DE BUSCA*/


        /*LOCALIZAÇÃO E DADOS DA TELA*/
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        BuscaDadosSistema sistema = new BuscaDadosSistema();
        sistema.execute();

    }

    public void CidadeErro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sua cidade não é compatível com o nosso sistema!");
        builder.setCancelable(false);
        builder.setPositiveButton("Quero Na Cidade", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        builder.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                Intent intent = new Intent(MainActivity.this,
                        PreProcessamento.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    //Mostra que não encontrou a localização
    public void erroLocalizacao() {
        AlertDialog.Builder loca = new AlertDialog.Builder(this);
        loca.setMessage("Não encontramos a sua localização. Ative seu GPS!\nAguarde até carpturarmos a sua licalização.\nPressione OK quando a localização for encontrada!");
        loca.setCancelable(false);
        loca.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        loca.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //volta para tela de login
                signOut();
                Intent i = new Intent(MainActivity.this, PreProcessamento.class);
                finish();
                startActivity(i);
            }
        });
        AlertDialog alert = loca.create();
        alert.show();

    }

    private boolean getLocation(){
        Location locationGPS = locationManager.getLastKnownLocation(provider);

        if(locationGPS != null){
            ativoGPS = true;
            minhaLocalizacao = locationGPS;
            return true;
        }else{
            return false;
        }
    }

    private void exibirProgress(boolean exibir) {
        loadMapa.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        nomeUser = (TextView) findViewById(R.id.nomeUser);
        emailUser = (TextView) findViewById(R.id.emailUser);
        imageUser = (ImageView) findViewById(R.id.imageView);

        //
        nomeUser.setText(personName);
        emailUser.setText(personEmail);
        imagem = DownloadImage(personPhoto.toString());
        imageUser.setImageBitmap(imagem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            signOut();
        }
        if (id == R.id.action_stop) {
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cadastro) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_sobre) {
            Intent i = new Intent(MainActivity.this, ActivityContato.class);
            startActivity(i);

        } else if (id == R.id.nav_empresa) {
            String url = UtilAPP.LINK_SITE_EMPRESA;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(minhaCidade){
            LatLng local = new LatLng(localMapaG.latitude, localMapaG.longitude);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(local, zG);
            mMap.moveCamera(update);
            mMap.setOnMarkerClickListener(this);
        }else{
            LatLng local = new LatLng(localMapaP.latitude, localMapaP.longitude);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(local, zP);
            mMap.moveCamera(update);
        }

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


    /*MINHAS FUNÇÕES*/
    private void signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(MainActivity.this,
                            PreProcessamento.class);
                    startActivity(intent);
                    finish();
                }
            });
    }


    //
    public Bitmap DownloadImage(String pURL){

        StrictMode.ThreadPolicy vPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(vPolicy);

        InputStream inStream = null;
        Bitmap vBitmap = null;

        try{

            URL url = new URL(pURL);
            HttpURLConnection pConnection = (HttpURLConnection)url.openConnection();
            pConnection.setDoInput(true);
            pConnection.connect();

            if(pConnection.getResponseCode() == HttpURLConnection.HTTP_OK){

                inStream = pConnection.getInputStream();
                vBitmap = BitmapFactory.decodeStream(inStream);
                inStream.close();

                return vBitmap;
            }

        }
        catch(Exception ex){
            Log.e("Exception",ex.toString());
        }

        return null;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(this, BuscaDenuncia.class);
        intent.putExtra("query", query);
        intent.putExtra("cep", lugares.get(0).getPostalCode());
        intent.putExtra("lat", lugares.get(0).getLatitude());
        intent.putExtra("lng", lugares.get(0).getLongitude());
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        minhaLocalizacao = locationManager.getLastKnownLocation(provider);
        if(!ativoGPS){
            ativoGPS = true;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "GPS Ativado " + provider,
                Toast.LENGTH_SHORT).show();
        onLocationChanged(minhaLocalizacao);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "GPS Desativado " + provider,
                Toast.LENGTH_SHORT).show();
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

        imgDenuncia.setImageDrawable(getDrawable(R.drawable.load));
        TextView nomeDenuncia = (TextView) view.findViewById(R.id.nomeDenuncia);
        nomeDenuncia.setText(marcadores.get(pos).getNome());

        DownloadImageFromInternet img = new DownloadImageFromInternet(imgDenuncia);
        img.execute(marcadores.get(pos).getMidia());
        TextView descDenuncia = (TextView) view.findViewById(R.id.descDenuncia);
        descDenuncia.setText(marcadores.get(pos).getDescricao());
        TextView cordenadasDenuncia = (TextView) view.findViewById(R.id.cordenadas);

        ImageView imgSituacao = (ImageView) view.findViewById(R.id.situacaoDenuncia);

        if(marcadores.get(pos).getSituacao().equals("Pendente" )){
            //troca a imagem aqui de pendente
            imgSituacao.setImageDrawable(getDrawable(R.drawable.verde));
        }else if(marcadores.get(pos).getSituacao().equals("Em Progresso" )){
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
    /**/
    private class BuscaDadosSistema extends AsyncTask<String, String, String> {
        private String retorno;
        public BuscaDadosSistema (){

        }

        @Override
        protected void onPreExecute (){
            exibirProgress(true);
            //Buscando os dados de GPS
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            minhaLocalizacao = locationManager.getLastKnownLocation(provider);
            if (minhaLocalizacao != null) {
                onResume();
                onLocationChanged(minhaLocalizacao);
            }
            minhaLocalizacao = locationManager.getLastKnownLocation(provider);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            //Buscando a minha localização
            if(getLocation()){
                //buscando o meu CEP
                Geocoder geo = new Geocoder(getApplicationContext());
                try {

                    lugares = geo.getFromLocation(minhaLocalizacao.getLatitude(), minhaLocalizacao.getLongitude(), 1);

                    //Busca as cidades disponíveis
                    HTTPRequest processa = new HTTPRequest();
                    ScriptDLL func = new ScriptDLL();
                    try {
                        cidades = func.getCidades(processa.getDados(UtilAPP.LINK_SERVIDOR + "getCidades.php", ""));
                        for(int i = 0; i < cidades.size(); i++){
                            if(cidades.get(i).getCEP().equals( lugares.get(0).getPostalCode() ) ){ //verifica se a minha cidada é permitida
                                minhaCidade = true;
                            }
                        }
                        if(!minhaCidade){
                            this.retorno = "erro1";
                        }else{
                            //recebe o os dados da posição da cidade
                            localMapaG = new LatLng(lugares.get(0).getLatitude(), lugares.get(0).getLongitude());
                            //povoa o mapa
                            //buscando os dados no servidor
                            HTTPRequest httpMark = new HTTPRequest();
                            marcadores = func.getMarcadores(httpMark.getDados(UtilAPP.LINK_SERVIDOR + "getMarcadores.php?cep=" + lugares.get(0).getPostalCode(), ""));
                            Log.i("cidades", String.valueOf( marcadores.size() ));
                            this.retorno = "ok";
                        }

                    } catch (JSONException e) {
                        Log.i("cidades", "não encontrado");
                        this.retorno = "erro1";
                    }


                } catch (IOException e) {
                    this.retorno = "erro1";
                }

            }else{
                this.retorno = "erro2";
                ativoGPS = false;

            }


            return this.retorno;
        }

        @Override
        protected void onPostExecute(String result) {

            if(result == "ok"){
                chamaMapa();
            }else if(result == "erro1"){
                CidadeErro();
            }else{
                erroLocalizacao();
                onResume();
                onLocationChanged(minhaLocalizacao);
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