package br.com.s_dev.s_vias.s_vias.View.Controller;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.s_dev.s_vias.s_vias.View.Model.Cidades;
import br.com.s_dev.s_vias.s_vias.View.Model.Marcador;

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
                denuncia.setMidia(jsonObject.getString("img"));
                denuncia.setSituacao(jsonObject.getString("sit"));
                LatLng latLng = new LatLng(jsonObject.getDouble("lat"), jsonObject.getDouble("lng"));
                denuncia.setLatlng(latLng);
                denuncias.add(denuncia);
            }

        }

        return denuncias;
    }


}