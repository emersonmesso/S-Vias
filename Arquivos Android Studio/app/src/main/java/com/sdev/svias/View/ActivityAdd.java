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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sdev.svias.Controller.AdapterImages;
import com.sdev.svias.Controller.HTTPRequest;
import com.sdev.svias.Controller.ScriptDLL;
import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.Model.Parametro;
import com.sdev.svias.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ActivityAdd extends AppCompatActivity{
    ImageView imgSelect;
    ImageView imgFoto;
    private static final int OPEN_REQUEST_CODE = 41;
    private static final int TIRAR_FOTO = 3;
    Bitmap imageBitmap = null;
    private ListView imagensSelecionadas;
    private ArrayAdapter<Bitmap> adapter;
    private int totalImage = 10;

    //
    private ArrayList<Bitmap> imagensSelect;
    //
    FrameLayout loadMapa;
    ScrollView telaConteudo;
    Button btnConcluir;
    EditText nomeDenuncia;
    EditText descDenuncia;
    private ImageView imgLoad;
    private TextView textView;

    //Conta Google
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions gso;
    private String personName;
    private String personEmail;

    //Localização
    private String CEP;
    List<Address> lugares;
    private LatLng pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle Extras = intent.getExtras();
        pos = new LatLng(Extras.getDouble("lat"), Extras.getDouble("lng"));

        Log.d("Local:", String.valueOf(pos));

        super.onCreate(savedInstanceState);
        super.setTitle("Fazer Denuncia");
        setContentView(R.layout.activity_add);

        imgLoad = (ImageView) findViewById(R.id.imgLoad);
        Glide.with(this).load(R.drawable.load).into(imgLoad);
        textView = (TextView) findViewById(R.id.textView);

        imagensSelecionadas = (ListView) findViewById(R.id.listImagens);
        imagensSelecionadas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

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
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        telaConteudo = (ScrollView) findViewById(R.id.telaConteudo);
        nomeDenuncia = (EditText) findViewById(R.id.editNomeDenuncia);
        descDenuncia = (EditText) findViewById(R.id.editDescDenuncia);

        imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrindo a intent de arquivos
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Selecione As Imagens"), OPEN_REQUEST_CODE);
            }
        });

        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TIRAR_FOTO);
            }
        });

        /*PEGA O CEP*/
        Geocoder geo = new Geocoder(getApplicationContext());
        try {
            lugares = geo.getFromLocation(pos.latitude, pos.longitude, 1);

            for(int i = 0; i < lugares.size(); i++){
                Log.i("lugares", lugares.get(i).getPostalCode());
                Log.i("lugares", String.valueOf( lugares.get(i).getAddressLine(0) ));
                Log.i("lugares", String.valueOf( lugares.get(i).getSubLocality() ));
            }

            CEP = lugares.get(0).getPostalCode();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Ação do Botão
        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome = String.valueOf(nomeDenuncia.getText());
                final String desc = String.valueOf(descDenuncia.getText());


                if(nome.equals("") || desc.equals("")){

                    Toast.makeText(getApplicationContext(), "Adicione Todas As Informações!", Toast.LENGTH_LONG).show();
                    exibirProgress(false);

                }else{
                    //verifica a imagem selecionada
                    if(imageBitmap != null){
                        Log.i("ENVIANDO", "Enviando os dados");
                        //
                        Parametro p = new Parametro("",nome, CEP,desc,personEmail, pos.latitude, pos.longitude, UtilAPP.LINK_SERVIDOR_CADASTRO_DENUCIA);
                        CadastraInfo envia = new CadastraInfo(p);
                        envia.execute();
                    }else{
                        //Não tem Imagem
                        Parametro p = new Parametro("",nome, CEP,desc,personEmail, pos.latitude, pos.longitude, UtilAPP.LINK_SERVIDOR_CADASTRO_DENUCIA);
                        CadastraInfo envia = new CadastraInfo(p);
                        envia.execute();
                    }
                }

            }
        });
        imagensSelect = new ArrayList<>();
        adapter = new AdapterImages(this, imagensSelect);
        imagensSelecionadas.setAdapter(adapter);
        exibirProgress(false);
    }

    private void mostraImagens (){
        for(int a = 0; a < imagensSelect.size(); a++){
            Log.d("Imagens" , "Imagem: " + String.valueOf(imagensSelect.get(a).getGenerationId()));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ActivityAdd.this, MainActivity.class);
        finish();
        startActivity(i);
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

                //verificando se selecionou só uma imagems
                if(resultData.getData() != null){

                    Uri uri = resultData.getData();
                    try {
                        Bitmap img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        adapter.add(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                //verifica se tem mais de uma imagem
                if(resultData.getClipData() != null){
                    for(int i = 0; i < resultData.getClipData().getItemCount(); i++) {
                        Uri uri = resultData.getClipData().getItemAt(i).getUri();
                        try {
                            Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            imagensSelect.add(image);
                            adapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }

            if(requestCode == TIRAR_FOTO){
                Bundle extras = resultData.getExtras();
                imagensSelect.add( (Bitmap) extras.get("data"));
                adapter.notifyDataSetChanged();
                mostraImagens();
            }

        }
        exibirProgress(false);
    }


    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
    }


    private String salvaImagens(String img, String id){
        String retorno = "";

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(UtilAPP.LINK_SERVIDOR_CADASTRO_IMG_DENUCIA);
        String answer = null;
        Log.d("Script", "Enviando a imagem: " + img);
        try {
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("id_loc", id));
            valores.add(new BasicNameValuePair("img", img));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            answer = EntityUtils.toString(response.getEntity());
            if(!answer.equals("")){
                retorno = "IMAGEM NÃO ENVIADA!";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    private String salvaDenuncia(Parametro parametro){
        String retorno = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(UtilAPP.LINK_SERVIDOR_CADASTRO_DENUCIA);
        String id = null;
        try {
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("nome", parametro.getNome()));
            valores.add(new BasicNameValuePair("desc", parametro.getDesc()));
            valores.add(new BasicNameValuePair("email", parametro.getEmail()));
            valores.add(new BasicNameValuePair("cep", parametro.getCep()));
            valores.add(new BasicNameValuePair("rua", String.valueOf( lugares.get(0).getAddressLine(0) )));
            valores.add(new BasicNameValuePair("lat", String.valueOf( parametro.getLat() )));
            valores.add(new BasicNameValuePair("lng", String.valueOf( parametro.getLng() )));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            id = EntityUtils.toString(response.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        retorno = id;
        return retorno;
    }


    /**/
    private class CadastraInfo extends AsyncTask<String, String, String> {
        Bitmap imageBitmap;
        Parametro parametro;

        private ArrayList<String> imagensPro = new ArrayList<String>();

        public CadastraInfo (Parametro parametro){
            this.parametro = parametro;
        }

        @Override
        protected void onPreExecute (){
            exibirProgress(true);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textView.setText(values[0] + " de " + String.valueOf( imagensSelect.size() ) + " imagens enviadas!");
        }

        @Override
        protected String doInBackground(String... strings) {
            String retorno = "";
            if(imagensSelect.size() > 0){

                for(int a = 0; a < imagensSelect.size(); a++){

                    ByteArrayOutputStream saida = new ByteArrayOutputStream();
                    imagensSelect.get(a).compress( Bitmap.CompressFormat.JPEG, 50, saida );
                    byte[] img = saida.toByteArray();
                    String imgArray = Base64.encodeToString( img, Base64.DEFAULT );
                    this.imagensPro.add(imgArray);
                    Log.d("Script", imgArray);
                }


                //Enviando a denuncia
                String retornoDenuncia = salvaDenuncia(this.parametro);
                if(!retornoDenuncia.equals("")){
                    Log.d("Script", "ID: " + retornoDenuncia);
                    //enviando as imagens
                    for(int a = 0; a < imagensPro.size(); a++){

                        String retornoImagens = salvaImagens(imagensPro.get(a), retornoDenuncia);
                        retorno = retornoImagens;
                        //atualiza a situação para o usuario
                        publishProgress(String.valueOf( (a+1) ));
                    }

                }else{
                    //Não foi possível enviar a denúncia
                    retorno = "NÃO FOI POSSÍVEL ADICIONAR A DENÚNCIA!";
                }

            }else{
                //Sem imagens
                retorno = "DENÚNCIA SEM IMAGENS!";
            }

            return retorno;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("")){
                exibirProgress(false);
                if(this.imageBitmap == null){
                    addSucesso(false);
                }else{
                    addSucesso(true);
                }

            }else{
                exibirProgress(false);
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }

        }
    }

    //Mostra que não encontrou a localização
    public void addSucesso(boolean img) {
        AlertDialog.Builder loca = new AlertDialog.Builder(this);

        if(img){
            loca.setMessage("Denúncia Adicionada Com Sucesso!");
        }else{
            loca.setMessage("Denúncia Adicionada Com Sucesso!\nDenúncias Com Imagem Tem Prioridades!");
        }

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
