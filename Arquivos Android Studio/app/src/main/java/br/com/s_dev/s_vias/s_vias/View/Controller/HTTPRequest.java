package br.com.s_dev.s_vias.s_vias.View.Controller;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPRequest {

    private HttpURLConnection conn;
    private URL url = null;
    private String urlServer;
    private Uri.Builder builder;
    private String argumentos;
    private String dados = "null";
    private void getDataServidor (String UrlServer, String argumentos){

        //
        this.urlServer = UrlServer;
        this.argumentos = argumentos;
        this.builder = new Uri.Builder();
        builder.appendQueryParameter("api", "s-vias");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //
                //Montar a URL com o endereço do Script PHP
                try{

                    url = new URL( urlServer );

                } catch (MalformedURLException e){
                    Log.e("Webservice", "Url mal formada - " +e.getMessage());

                } catch (Exception erro){
                    Log.e("Webservice", "Url mal formada - " +erro.getMessage());

                }


                try {

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

                } catch ( IOException e){

                    Log.e("webService", "IOException Erro - " +e.getMessage());

                }

                try {
                    //recebe o codigo http da resposta
                    int response_cod = conn.getResponseCode();

                    if(response_cod == HttpURLConnection.HTTP_OK){

                        InputStream input = conn.getInputStream();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();

                        String line;

                        while ((line = reader.readLine()) != null){

                            result.append(line);

                        }

                        salvaDados(result.toString());

                    } else{

                        Log.e("WebService", "Erro ao ler os dados oriundos do servidor");

                    }

                } catch (IOException e){

                    Log.e("webService", "IOException Erro - " +e.getMessage());

                } finally {
                    conn.disconnect();
                }
                //
            }
        }).start();

    }

    private void salvaDados (String dados){
        this.dados = dados;
    }

    //
    public String getDados(String Url, String argumentos){

        //
        getDataServidor(Url, argumentos);

        while (dados == "null"){
            System.out.println("");
        }
        return dados;
    }

    public String getData(){
        return this.dados;
    }
}
