package com.sdev.svias.View;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import com.sdev.svias.Controller.UtilAPP;
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

public class ActivityAdd extends AppCompatActivity implements LocationListener {
    ImageView imgSelect;
    ImageView imgFoto;
    ImageView imgDenuncia;
    private static final int OPEN_REQUEST_CODE = 41;
    private static final int TIRAR_FOTO = 3;
    Uri currentUri = null;
    Bitmap imageBitmap = null;
    String imgArray;
    FrameLayout loadMapa;
    ScrollView telaConteudo;
    Button btnConcluir;
    EditText nomeDenuncia;
    EditText descDenuncia;
    String ID_LOC;


    //Conta Google
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions gso;
    private String personName;
    private String personEmail;

    //Localização
    LocationManager locationManager;
    private String provider;
    Location minhaLocalizacao;
    boolean ativoGPS = false;
    private String CEP;
    List<Address> lugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Fazer Denuncia");
        setContentView(R.layout.activity_add);

        //Recebendo os dados da conta do google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);

        if(acc != null){
            personName = acc.getDisplayName();
            personEmail = acc.getEmail();
        }


        //Carregando as imagens e botões
        btnConcluir = (Button) findViewById(R.id.btnConcluir);
        imgSelect = (ImageView) findViewById(R.id.imgArquivos);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        imgDenuncia = (ImageView) findViewById(R.id.imgDenuncia);
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        telaConteudo = (ScrollView) findViewById(R.id.telaConteudo);
        nomeDenuncia = (EditText) findViewById(R.id.editNomeDenuncia);
        descDenuncia = (EditText) findViewById(R.id.editDescDenuncia);

        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrindo a intent de arquivos
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, OPEN_REQUEST_CODE);
            }
        });

        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TIRAR_FOTO);
            }
        });

        //Localização
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        minhaLocalizacao = locationManager.getLastKnownLocation(provider);
        if (minhaLocalizacao != null) {
            onResume();
            onLocationChanged(minhaLocalizacao);
        }

        //pega a localização
        getLocation();

        /*PEGA O CEP*/
        Geocoder geo = new Geocoder(getApplicationContext());
        try {
            lugares = geo.getFromLocation(minhaLocalizacao.getLatitude(), minhaLocalizacao.getLongitude(), 1);

            for(int i = 0; i < lugares.size(); i++){
                Log.i("lugares", lugares.get(i).getPostalCode());
            }

            CEP = lugares.get(0).getPostalCode();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Ação do Botão
        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            exibirProgress(true);
            final String nome = String.valueOf(nomeDenuncia.getText());
            final String desc = String.valueOf(descDenuncia.getText());
            //verifica a imagem selecionada
            if(imageBitmap != null){
                Log.i("ENVIANDO", "Enviando os dados");
                //
                Parametro p = new Parametro("",nome, CEP,desc,personEmail, minhaLocalizacao.getLatitude(), minhaLocalizacao.getLongitude(), UtilAPP.LINK_SERVIDOR_CADASTRO_DENUCIA);
                CadastraInfo envia = new CadastraInfo(p, imageBitmap);
                envia.execute();
            }else{
                //Não tem Imagem
                Toast.makeText(getApplicationContext(), "Selecione Uma Imagem", Toast.LENGTH_LONG).show();
                exibirProgress(true);
            }
            }
        });

        exibirProgress(false);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(ActivityAdd.this, MainActivity.class);
        finish();
        startActivity(i);
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
        telaConteudo.setVisibility(exibir? View.GONE : View.VISIBLE);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        exibirProgress(true);

        if (resultCode == Activity.RESULT_OK)
        {

            if (requestCode == OPEN_REQUEST_CODE) {

                if (resultData != null) {
                    currentUri = resultData.getData();
                    try {
                        imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), currentUri);
                        Log.i("img", "Imagem codificada");
                        exibirProgress(false);
                    } catch (IOException e) {
                        Log.i("img", "Erro ao codificar imagem");
                    }
                    imgDenuncia.setImageBitmap(imageBitmap);
                }
            }

            if(requestCode == TIRAR_FOTO){

                Bundle extras = resultData.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                imgDenuncia.setImageBitmap(imageBitmap);
            }

        }

        exibirProgress(false);

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
        minhaLocalizacao = location;
        if(!ativoGPS){
            ativoGPS = true;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /**/
    private class CadastraInfo extends AsyncTask<String, String, String> {
        Bitmap imageBitmap;
        Parametro parametro;

        public CadastraInfo (Parametro parametro, Bitmap imageBitmap){
            this.parametro = parametro;
            this.imageBitmap = imageBitmap;
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

            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            this.imageBitmap.compress( Bitmap.CompressFormat.JPEG, 100, saida );
            byte[] img = saida.toByteArray();
            String imgArray = Base64.encodeToString( img, Base64.DEFAULT );
            this.parametro.setImagem(imgArray);

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(UtilAPP.LINK_SERVIDOR_CADASTRO_DENUCIA);
            String answer = null;
            try {
                ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
                valores.add(new BasicNameValuePair("nome", this.parametro.getNome()));
                valores.add(new BasicNameValuePair("desc", this.parametro.getDesc()));
                valores.add(new BasicNameValuePair("email", this.parametro.getEmail()));
                valores.add(new BasicNameValuePair("cep", this.parametro.getCep()));
                valores.add(new BasicNameValuePair("img", this.parametro.getImagem()));
                valores.add(new BasicNameValuePair("lat", String.valueOf( this.parametro.getLat() )));
                valores.add(new BasicNameValuePair("lng", String.valueOf( this.parametro.getLng() )));

                httpPost.setEntity(new UrlEncodedFormEntity(valores));
                HttpResponse response = httpClient.execute(httpPost);
                answer = EntityUtils.toString(response.getEntity());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return answer;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("RETORNO", result);

            if(result.equals("")){
                exibirProgress(false);
                addSucesso();
            }else{
                exibirProgress(false);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }

        }
    }

    //Mostra que não encontrou a localização
    public void addSucesso() {
        AlertDialog.Builder loca = new AlertDialog.Builder(this);
        loca.setMessage("Denúncia Adicionada Com Sucesso!");
        loca.setCancelable(false);
        loca.setPositiveButton("Ver No Mapa", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                Intent intent = new Intent(ActivityAdd.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });
        AlertDialog alert = loca.create();
        alert.show();

    }

}
