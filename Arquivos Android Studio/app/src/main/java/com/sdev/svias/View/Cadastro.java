package com.sdev.svias.View;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.sdev.svias.Controller.CNP;
import com.sdev.svias.Controller.Mask;
import com.sdev.svias.R;
import com.sdev.svias.Controller.UtilAPP;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

public class Cadastro extends AppCompatActivity {

    //
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions gso;
    int RC_SIGN_IN;
    private ImageView imgLoad;
    FrameLayout loadMapa;
    private Button btnConcluir;
    private EditText campoCPF;
    private EditText campoSenha;

    private TextWatcher cpfMask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Minhas alterações
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        imgLoad = (ImageView) findViewById(R.id.imgLoad);
        Glide.with(this).load(R.drawable.load).into(imgLoad);

        //
        campoCPF = (EditText) findViewById(R.id.editCPF);
        cpfMask = Mask.insert("###.###.###-##", campoCPF);
        campoCPF.addTextChangedListener(cpfMask);

        campoSenha = (EditText) findViewById(R.id.editSenha);

        //
        btnConcluir = (Button) findViewById(R.id.btnCadastro);
        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn();
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        exibirProgress(false);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void exibirProgress(boolean exibir) {
        loadMapa.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }
    //Sing In
    public void singIn (){
        exibirProgress(true);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /*Envia para o servidor*/
    private String cadastraUsuario(String nome, String cpf, String senha, String email){
        String retorno = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(UtilAPP.LINK_SERVIDOR_CADASTRO);
        try {
            ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
            valores.add(new BasicNameValuePair("nome", nome));
            valores.add(new BasicNameValuePair("email", email));
            valores.add(new BasicNameValuePair("senha", senha));
            valores.add(new BasicNameValuePair("cpf", cpf));

            httpPost.setEntity(new UrlEncodedFormEntity(valores, "UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            retorno = EntityUtils.toString(response.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            exibirProgress(true);
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
            if(acc != null){
                String email = acc.getEmail();
                String nome = acc.getDisplayName();
                //recebe os dados do campo e verifica
                String senha = campoSenha.getText().toString();
                String CPF = campoCPF.getText().toString();

                if(CPF.isEmpty() || senha.isEmpty() || !CNP.isValidCPF(CPF)){
                    Toast.makeText(this, "VERIFIQUE OS DADOS INFORMADOS!", Toast.LENGTH_LONG).show();
                    //logout do sistema
                    signOut();
                }else{
                    signOut();

                    //testando a nova funcionalidade
                    ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
                    valores.add(new BasicNameValuePair("nome", nome));
                    valores.add(new BasicNameValuePair("email", email));
                    valores.add(new BasicNameValuePair("senha", md5(senha)));
                    valores.add(new BasicNameValuePair("cpf", CPF));

                    conexaoServer conexaoServer = new conexaoServer(UtilAPP.LINK_SERVIDOR_CADASTRO, valores);
                    conexaoServer.execute();
                }
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.i("falhas", "signInResult:failed code=" + e.getStatusCode());
            exibirProgress(false);
        }

    }

    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       //
                    }
                });
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    private class conexaoServer extends AsyncTask<String, String, JSONObject> {

        private ArrayList<NameValuePair> parametros;
        private String url;
        public conexaoServer(String url, ArrayList<NameValuePair> parametros) {
            this.parametros = parametros;
            this.url = url;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            final JSONObject raiz = new JSONObject();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            try {
                raiz.put("error", false);
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
            return raiz;
        }

        @Override
        protected void onPostExecute(JSONObject retorno) {

            try {
                if(retorno.getBoolean("error") != true){
                    if(retorno.getString("results").equals("1")){
                        //cadastrado!
                        Toast.makeText(getApplicationContext(), "Cadastrado Com Sucesso!\nAguarde...", Toast.LENGTH_LONG).show();
                        Handler handle = new Handler();
                        handle.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBackPressed();
                            }
                        }, 3000);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), retorno.getString("results"), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), retorno.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            exibirProgress(false);

        }
    }

}
