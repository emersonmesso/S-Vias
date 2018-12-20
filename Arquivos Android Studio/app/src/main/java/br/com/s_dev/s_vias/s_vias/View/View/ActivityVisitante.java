package br.com.s_dev.s_vias.s_vias.View.View;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TabHost;
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
import com.google.android.gms.maps.model.PointOfInterest;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import br.com.s_dev.s_vias.s_vias.R;
import br.com.s_dev.s_vias.s_vias.View.Controller.HTTPRequest;
import br.com.s_dev.s_vias.s_vias.View.Controller.ScriptDLL;
import br.com.s_dev.s_vias.s_vias.View.Controller.UtilAPP;
import br.com.s_dev.s_vias.s_vias.View.Model.Cidades;
import br.com.s_dev.s_vias.s_vias.View.Model.Marcador;

import static br.com.s_dev.s_vias.s_vias.View.View.PreProcessamento.REQUEST_LOCATION;

public class ActivityVisitante extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, SearchView.OnQueryTextListener, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    //Lista de marcadores
    List<Marcador> marcadores;
    //Lista de cidades
    List<Cidades> cidades;
    FrameLayout loadMapa;
    List<Address> lugares;
    //
    SearchView campoBusca;

    Location minhaLocalizacao;
    LatLng localMapaP = new LatLng(-12.4048291, -55.0187512);
    LatLng localMapaG;
    int zP = 5;
    int zG = 14;
    LocationManager locationManager;
    private boolean minhaCidade = false;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitante);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //
        campoBusca = (SearchView) findViewById(R.id.campoBusca);
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);

        campoBusca.setOnQueryTextListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        }

        //Buscando a minha localização
        if(getLocation()){
            exibirProgress(true);
            //buscando o meu CEP
            Geocoder geo = new Geocoder(getApplicationContext());
            try {

                lugares = geo.getFromLocation(minhaLocalizacao.getLatitude(), minhaLocalizacao.getLongitude(), 1);

                //Busca as cidades disponíveis
                HTTPRequest processa = new HTTPRequest();
                ScriptDLL func = new ScriptDLL();
                try {

                    cidades = func.getCidades(processa.getDados(UtilAPP.LINK_SERVIDOR + "getCidades.php", ""));

                    //verificando se as cidades disponíveis é iqual a minha cidade (Usando o Postal Code)
                    //percorrendo as cidades disponível
                    for(int i = 0; i < cidades.size(); i++){

                        if(cidades.get(i).getCEP().equals( lugares.get(0).getPostalCode() ) ){ //verifica se a minha cidada é permitida
                            minhaCidade = true;
                            Log.i("cidades", "Certo");
                        }
                    }
                    if(!minhaCidade){
                        exibirProgress(false);
                        CidadeErro();
                    }else{
                        //recebe o os dados da posição da cidade
                        localMapaG = new LatLng(lugares.get(0).getLatitude(), lugares.get(0).getLongitude());
                        //povoa o mapa
                        //buscando os dados no servidor
                        HTTPRequest httpMark = new HTTPRequest();
                        marcadores = func.getMarcadores(httpMark.getDados(UtilAPP.LINK_SERVIDOR + "getMarcadores.php?cep=" + lugares.get(0).getPostalCode(), ""));
                        Log.i("cidades", String.valueOf( marcadores.size() ));
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.mapa);
                        mapFragment.getMapAsync(this);
                        exibirProgress(false);
                    }

                } catch (JSONException e) {
                    Log.i("cidades", "não encontrado");
                    exibirProgress(false);
                    CidadeErro();
                }


            } catch (IOException e) {
                exibirProgress(false);
                CidadeErro();
            }

        }else{
            Toast.makeText(this, "Sem Localização!", Toast.LENGTH_LONG).show();
        }

    }


    private boolean getLocation(){
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(locationGPS != null){
            minhaLocalizacao = locationGPS;
            return true;
        }else{
            return false;
        }
    }

    /**/
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

    public void onLocationChanged(Location location) {
        minhaLocalizacao = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    //Mostra mensagem de cidade não permitida
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
                Intent intent = new Intent(ActivityVisitante.this,
                        PreProcessamento.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

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
        getMenuInflater().inflate(R.menu.activity_visitante, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            //Tela De Login
            Intent intent = new Intent(ActivityVisitante.this,
                    PreProcessamento.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_cadastro) {
            Intent intent = new Intent(ActivityVisitante.this,
                    Cadastro.class);
            startActivity(intent);
        } else if (id == R.id.nav_sobre) {

        } else if (id == R.id.nav_empresa) {
            String url = "https://emersonmesso95.000webhostapp.com/";
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
            Marker marcador = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(marcadores.get(i).getLatlng().latitude, marcadores.get(i).getLatlng().longitude))
                    .title(marcadores.get(i).getNome())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            );
            marcador.hideInfoWindow();
            marcador.setTag(0);

        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, query, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final AlertDialog.Builder viewop = new AlertDialog.Builder(this);

        LayoutInflater li = getLayoutInflater();

        //inflamos o layout alerta.xml na view
        View view = li.inflate(R.layout.click_marcador, null);

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
