package com.sdev.svias.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.Model.Parametro;
import com.sdev.svias.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class AddImages extends AppCompatActivity {

    /*INFORMAÇÕES DA DENUNCIA*/
    private int id_loc;
    private int sit;

    /*VARIÁVEIS GLOBAIS*/
    private static final int OPEN_REQUEST_CODE = 41;
    private static final int TIRAR_FOTO = 3;


    /*BOTÕES ADD*/
    private ImageView btnSelect;
    private ImageView btnPick;

    /*Botão Concluir*/
    private Button brnConcluir;

    /*IIMAGEM SELECIONADA*/
    private Bitmap imgSelected;

    /*TEXTVIEWS*/
    private TextView textoSituacao;
    private TextView textoDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent().hasExtra("id_loc")){
            Bundle extras = getIntent().getExtras();
            id_loc = extras.getInt("id_loc");
            sit = extras.getInt("sit");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_images);

        /*CRIANDO OS OBJETOS XML*/
        btnSelect = (ImageView) findViewById(R.id.btnSelect);
        btnPick = (ImageView) findViewById(R.id.btnPick);
        textoDesc = (TextView) findViewById(R.id.textoDesc);
        textoSituacao = (TextView) findViewById(R.id.textoSituacao);
        brnConcluir = (Button) findViewById(R.id.brnConcluir);
        /*CRIANDO OS OBJETOS XML*/

        /*Alterando o texto exibido*/
        if(sit == 2) {
            textoSituacao.setText("O Status da Denúncia foi Alterada para \"Em Processo de Resolução\"!");
            textoDesc.setText("Caso queira adicionar imagens da situação atual, envie selecionando-as abaixo!");
        }if(sit == 0){
            textoSituacao.setText("Adicione Novas Fotos!");
            textoDesc.setText("Selecione da sua galeria ou capture com a camera!");
        }else{
            textoSituacao.setText("Você Alterou o Status da Denúncia Para Concluído!");
            textoDesc.setText("Envie Imagens do Problema Já Resolvido!");
        }


        /*AÇÕES DOS BOTÕES*/
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Selecione As Imagens"), OPEN_REQUEST_CODE);
            }
        });

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TIRAR_FOTO);
            }
        });

        brnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public void onBackPressed(){
        super.onBackPressed();
        this.finish();
    }




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if (resultCode == Activity.RESULT_OK)
        {

            if (requestCode == OPEN_REQUEST_CODE) {

                //verificando se selecionou só uma imagems
                if(resultData.getData() != null){

                    Uri uri = resultData.getData();
                    try {
                        Bitmap img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        imgSelected = img;
                        alertaImagem();
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
                            imgSelected = image;
                            alertaImagem();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }

            if(requestCode == TIRAR_FOTO){
                Bundle extras = resultData.getExtras();
                Bitmap img = (Bitmap) extras.get("data");
                imgSelected = img;
                alertaImagem();
            }

        }
    }

    private String salvaImagens(String img, int id){
        String retorno = "";

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(UtilAPP.LINK_SERVIDOR_CADASTRO_IMG_INSTITUICAO);
        String answer = null;
        Log.d("Script", "Enviando a imagem: " + img);
        Log.d("Script", String.valueOf( id ));
        try {
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("id_loc", String.valueOf( id )));
            valores.add(new BasicNameValuePair("img", img));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            answer = EntityUtils.toString(response.getEntity());
            if(!answer.equals("")){
                retorno = answer;
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


    /*AlertDialog que mostra a imagem*/
    private void alertaImagem (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater li = getLayoutInflater();
        final View view = li.inflate(R.layout.imagem_selected, null);
        ImageView img = (ImageView) view.findViewById(R.id.imagem);
        img.setImageBitmap(imgSelected);


        builder.setView(view);
        builder.setTitle("Imagem Selecionada");
        builder.setCancelable(false);
        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
                EnviaImage enviaImage = new EnviaImage(imgSelected);
                enviaImage.execute();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    /*CLASSE QUE ENVIA AS IMAGENS PARA O SERVIDOR*/
    private class EnviaImage extends AsyncTask<String, String, String> {
        private Bitmap imagem;


        public EnviaImage (Bitmap img){
            this.imagem = img;
        }

        @Override
        protected void onPreExecute (){
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            String retorno = "";
            //convertendo a imagem em String Baase64
            ByteArrayOutputStream saida = new ByteArrayOutputStream();
            imagem.compress( Bitmap.CompressFormat.JPEG, 50, saida );
            byte[] img = saida.toByteArray();
            String imgArray = Base64.encodeToString( img, Base64.DEFAULT );

            //enviando a imagem
            retorno = salvaImagens(imgArray, id_loc);

            return retorno;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("")){
                Toast.makeText(getApplicationContext(), "Imagem Adicionada!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }
    }
}
