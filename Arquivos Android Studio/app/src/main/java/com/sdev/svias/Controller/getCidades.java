package com.sdev.svias.Controller;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import com.sdev.svias.Model.Cidades;


public class getCidades extends AsyncTask <String, String, ArrayList<Cidades>> {
    private URL url = null;
    private HttpURLConnection conn;
    private Uri.Builder builder;

    @Override
    protected void onPreExecute(){
        //Codigo
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected ArrayList doInBackground(String... strings) {
        ArrayList<Cidades> cidades = new ArrayList<>();
        try {
            url = new URL( strings[0] );

            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(UtilAPP.CONNECTION_TIMEOUT);
            conn.setReadTimeout(UtilAPP.READ_TIMEOUT);
            //
            conn.setRequestMethod("POST");

            conn.setRequestProperty("charset", "utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            //
            conn.connect();

            int response_cod = conn.getResponseCode();

            if(response_cod == HttpURLConnection.HTTP_OK){

                InputStream input = conn.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }


                //convertendo para array

                JSONArray jsonArray = new JSONArray(result);

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

            } else{

                Log.e("WebService", "Erro ao ler os dados oriundos do servidor");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cidades;
    }

    @Override
    protected void onPostExecute(ArrayList cidades){
        //Codigo
    }

}
