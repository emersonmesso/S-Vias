package com.sdev.svias.Controller;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.sdev.svias.Model.Cidades;
import com.sdev.svias.Model.Marcador;

public class ScriptDLL {



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
                denuncia.setSituacao(jsonObject.getString("sit"));
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
