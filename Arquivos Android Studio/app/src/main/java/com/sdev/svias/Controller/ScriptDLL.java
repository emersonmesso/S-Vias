package com.sdev.svias.Controller;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.sdev.svias.Model.Cidades;
import com.sdev.svias.Model.Marcador;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class ScriptDLL {

    /*Conexões com o servidor*/
    public static JSONObject conexaoServer(final String url, final ArrayList<NameValuePair> parametros) throws JSONException {
        final JSONObject raiz = new JSONObject();
        final JSONArray retorno = new JSONArray();

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                try {
                    raiz.put("error", false);
                    raiz.put("results", "");
                    //valores.add(new BasicNameValuePair("cpf", cpf));
                    httpPost.setEntity(new UrlEncodedFormEntity(parametros, "UTF-8"));
                    HttpResponse response = httpClient.execute(httpPost);
                    //adiciona o retorno do servidor
                    raiz.put("results", EntityUtils.toString(response.getEntity()));
                } catch (UnsupportedEncodingException e) {
                    try {
                        raiz.put("error", true);
                        raiz.put("message", e.getMessage());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } catch (ClientProtocolException e) {
                    try {
                        raiz.put("error", true);
                        raiz.put("message", e.getMessage());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e) {
                    try {
                        raiz.put("error", true);
                        raiz.put("message", e.getMessage());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                } catch (JSONException e) {
                    try {
                        raiz.put("error", true);
                        raiz.put("message", e.getMessage());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                }
                Log.d("Script 1:", raiz.toString());
            }
        }).start();
        for(;;){
            if(raiz.getString("results") != ""){
                break;
            }
        }
        return raiz;
    }



    //Busca os dados das cidades
    public List<Cidades> getCidades (String dados) throws JSONException {
        List<Cidades> cidades = new ArrayList<>();
        Log.i("cidades", dados);
        JSONArray jsonArray = new JSONArray(dados);

        if(jsonArray.length() != 0){

            //recebi alguma coisa
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //cria um nonvo objeto de cidades
                Cidades cidade = new Cidades();
                cidade.setCEP(jsonObject.getString("cep"));
                cidade.setNome(jsonObject.getString("nome"));
                cidade.setUf(jsonObject.getString("uf"));
                cidades.add(cidade);
            }

        }

        return cidades;
    }


    //Buscando as denúmcias
    public List<Marcador> getMarcadores(String dados) throws JSONException {
        Log.i("marcadores", "Processando: " + dados);
        List<Marcador> denuncias = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(dados);

        if(jsonArray.length() != 0){

            //recebi alguma coisa
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //cria um nonvo objeto de cidades
                Marcador denuncia = new Marcador();
                denuncia.setNome(jsonObject.getString("titulo"));
                denuncia.setData(jsonObject.getString("data"));
                denuncia.setDescricao(jsonObject.getString("desc"));
                denuncia.setId_loc(jsonObject.getInt("id_loc"));
                denuncia.setBairro(jsonObject.getString("bairro"));
                denuncia.setRua(jsonObject.getString("rua"));
                denuncia.setCep(jsonObject.getString("cep"));
                denuncia.setCidade(jsonObject.getString("cidade"));

                denuncia.setSituacao(jsonObject.getString("sit"));

                //buscando as imagens
                JSONArray jsonImg = new JSONArray(jsonObject.getString("img"));
                ArrayList<String> imagens = new ArrayList<String>();

                if(jsonImg.length() != 0){
                    for (int a = 0; a < jsonImg.length(); a++){
                        JSONObject jsonImage = jsonImg.getJSONObject(a);
                        Log.d("Script", jsonImage.getString("img"));
                        imagens.add(jsonImage.getString("img"));
                    }

                }
                denuncia.setMidia(imagens);

                /*Imagens da denuncia enviadas pela instituição*/
                JSONArray jsonImgPref = new JSONArray(jsonObject.getString("img_pref"));
                ArrayList<String> imagensPref = new ArrayList<String>();
                if(jsonImgPref.length() != 0){
                    for(int a = 0; a < jsonImgPref.length(); a++){
                        JSONObject jsonPref = jsonImgPref.getJSONObject(a);
                        imagensPref.add(jsonPref.getString("img"));
                    }
                }
                denuncia.setMidia_pref(imagensPref);
                /*Imagens da denuncia enviadas pela instituição*/

                //Localização
                LatLng latLng = new LatLng(jsonObject.getDouble("lat"), jsonObject.getDouble("lng"));
                denuncia.setLatlng(latLng);
                denuncias.add(denuncia);
            }

        }

        return denuncias;
    }

    //Buscando as denúmcias
    public List<Marcador> getMarcadoresInstituicao(String dados) throws JSONException {
        Log.i("marcadores", "Processando: " + dados);
        List<Marcador> denuncias = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(dados);

        if(jsonArray.length() != 0){

            //recebi alguma coisa
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //cria um nonvo objeto de cidades
                Marcador denuncia = new Marcador();
                denuncia.setNome(jsonObject.getString("titulo"));
                denuncia.setData(jsonObject.getString("data"));
                denuncia.setDescricao(jsonObject.getString("desc"));

                /*Imagens Da Denuncia enviadas pelo cidadão*/
                JSONArray jsonImg = new JSONArray(jsonObject.getString("img"));
                ArrayList<String> imagens = new ArrayList<String>();

                if(jsonImg.length() != 0){
                    for (int a = 0; a < jsonImg.length(); a++){
                        JSONObject jsonImage = jsonImg.getJSONObject(a);
                        Log.d("Script", jsonImage.getString("img"));
                        imagens.add(jsonImage.getString("img"));
                    }
                }
                denuncia.setMidia(imagens);
                /*Imagens Da Denuncia enviadas pelo cidadão*/


                /*Imagens da denuncia enviadas pela instituição*/
                JSONArray jsonImgPref = new JSONArray(jsonObject.getString("img_pref"));
                ArrayList<String> imagensPref = new ArrayList<String>();
                if(jsonImgPref.length() != 0){
                    for(int a = 0; a < jsonImgPref.length(); a++){
                        JSONObject jsonPref = jsonImgPref.getJSONObject(a);
                        imagensPref.add(jsonPref.getString("img"));
                    }
                }
                denuncia.setMidia_pref(imagensPref);
                /*Imagens da denuncia enviadas pela instituição*/

                denuncia.setSituacao(jsonObject.getString("sit"));
                LatLng latLng = new LatLng(jsonObject.getDouble("lat"), jsonObject.getDouble("lng"));
                denuncia.setLatlng(latLng);
                denuncia.setId_loc(jsonObject.getInt("id_loc"));
                denuncia.setBairro(jsonObject.getString("bairro"));
                denuncia.setRua(jsonObject.getString("rua"));
                denuncia.setCep(jsonObject.getString("cep"));
                denuncia.setCidade(jsonObject.getString("cidade"));
                denuncias.add(denuncia);
            }

        }

        return denuncias;
    }


}
