package br.com.s_dev.s_vias.s_vias.View.View;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
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
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, SearchView.OnQueryTextListener {

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
    LocationManager locationManager;
    private boolean minhaCidade = false;

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


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        //
        campoBusca = (SearchView) findViewById(R.id.campoBusca);
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);

        campoBusca.setOnQueryTextListener(this);

        //Ativando localização via GPS
        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ActivityVisitante.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }

        //Busca as cidades disponíveis
        HTTPRequest processa = new HTTPRequest();
        ScriptDLL func = new ScriptDLL();
        try {
            cidades = func.getCidades(processa.getDados(UtilAPP.LINK_SERVIDOR+"_api/getCidades.php", ""));
            Log.i("cidades", cidades.get(0).getCEP());

            //Buscando a minha localização
            minhaLocalizacao = getLocation();

            //buscando o meu CEP
            Geocoder geo = new Geocoder(getApplicationContext());
            try {
                lugares = geo.getFromLocation(minhaLocalizacao.getLatitude(), minhaLocalizacao.getLongitude(), 1);
            } catch (IOException e) {
                Log.i("erroE", e.getMessage());
            }

            //verificando se as cidades disponíveis é iqual a minha cidade (Usando o Postal Code)
            //percorrendo as cidades disponível
            for(int i = 0; i < cidades.size(); i++){
                if(cidades.get(i).getCEP() == lugares.get(0).getPostalCode() ){ //verifica se a minha cidada é permitida
                    minhaCidade = true;
                }
            }

            if(minhaCidade){
                //Povoando a tela com os marcadores
                HTTPRequest processa1 = new HTTPRequest();
                try {
                    marcadores = func.getMarcadores(processa1.getDados(UtilAPP.LINK_SERVIDOR+"_api/getMarcadores.php", ""));
                    Log.i("marcadores", String.valueOf( marcadores.size()));
                } catch (JSONException e) {
                    Log.i("erroE", e.getMessage());
                }
            }else{
                //CidadeErro();
            }

        } catch (JSONException e) {
            Log.i("erroE", e.getMessage());
        }

    }

    //
    public Location getLocation(){
        Location location = null;
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

            if( location != null){
                //

            } else {
                Toast.makeText(this, "Erro ao buscar sua Localização!", Toast.LENGTH_LONG).show();
            }
        }
        return location;
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
        exibirProgress(true);
        mMap = googleMap;

        LatLng local = new LatLng(-8.069222,-39.126781);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(local, 14);
        mMap.moveCamera(update);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }
        mMap.setMyLocationEnabled(false);
        //verificando se a cidade é permitida

        if(minhaCidade){
            //Povoando a tela com os marcadores
            HTTPRequest processa1 = new HTTPRequest();
            try {
                ScriptDLL func = new ScriptDLL();
                marcadores = func.getMarcadores(processa1.getDados(UtilAPP.LINK_SERVIDOR+"_api/getMarcadores.php", ""));
                Log.i("marcadores", String.valueOf( marcadores.size()));
                addMarcadores(googleMap);
            } catch (JSONException e) {
                Log.i("erroE", e.getMessage());
            }

        }else{
            CidadeErro();
        }

        exibirProgress(false);
    }

    private void addMarcadores (GoogleMap googleMap){
        mMap = googleMap;
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
}
