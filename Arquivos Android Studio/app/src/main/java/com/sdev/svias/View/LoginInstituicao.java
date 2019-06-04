package com.sdev.svias.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sdev.svias.Controller.HTTPRequest;
import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.R;

public class LoginInstituicao extends AppCompatActivity {

    private ImageView imgLoad;
    private FrameLayout loadMapa;
    private Button btnLogin;
    private SharedPreferences preferences;
    private Button btnCadastro;

    //
    private EditText editEmail;
    private EditText editSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_instituicao);

        //Minhas alterações
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        imgLoad = (ImageView) findViewById(R.id.imgLoad);
        Glide.with(this).load(R.drawable.load).into(imgLoad);
        exibirProgress(false);

        //
        btnCadastro = (Button) findViewById(R.id.btnCadastroInstituicao);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UtilAPP.LINK_SITE_CADASTRO_INSTITUICAO;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //
        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        //
        editEmail = (EditText) findViewById(R.id.editEmail);
        editSenha = (EditText) findViewById(R.id.editSenha);

        //Botão Ação Login
        btnLogin = (Button) findViewById(R.id.btnLoginInstituicao);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //verificando os dados enviados
                ExecutaLogin login = new ExecutaLogin(editEmail.getText().toString(), editSenha.getText().toString());
                login.execute();
            }
        });

        if(!preferences.contains("loginInstituicao")){
            criaPreferences();
        }

        String login = preferences.getString("loginInstituicao", null);
        String email = preferences.getString("email", null);

        if (login.equals("sim")) {
            adicionarPreferenceEmail(email);
            //inicia a tela
            Intent intent = new Intent(this, Instituicao.class);
            startActivity(intent);
            finish();
        }
    }

    private void logout(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loginInstituicao", "nao");
        editor.putString("email", "");
        editor.commit();
    }

    private void adicionarPreferenceJaAbriu(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("loginInstituicao", "sim");
        editor.commit();
    }

    private void criaPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", "");
        editor.putString("loginInstituicao", "nao");
        editor.commit();
    }
    private void adicionarPreferenceEmail(String email) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.commit();
    }

    //Exibir Progress
    private void exibirProgress(boolean exibir) {
        loadMapa.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }



    /*Login Instituição*/
    private class ExecutaLogin extends AsyncTask<String, String, String> {
        private String email;
        private String senha;
        private boolean inicio = false;
        public ExecutaLogin (String email, String senha){
            this.email = email;
            this.senha = senha;
        }

        @Override
        protected void onPreExecute (){
            exibirProgress(true);

            //verifica os dados
            if(!email.isEmpty() || !senha.isEmpty()){
                inicio = true;
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {

            if(!inicio){
                return "1";
            }else{
                HTTPRequest processa = new HTTPRequest();
                String retorno = processa.getDados(UtilAPP.LINK_SERVIDOR + "loginInstituicao.php?e=" + this.email+"&p=" + UtilAPP.md5(this.senha), "");

                return retorno;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(result == "1"){
                Toast.makeText(getApplicationContext(),"INFORME TODOS OS DADOS!", Toast.LENGTH_LONG).show();
                exibirProgress(false);
            }else{

                if(result == ""){
                    //faz o login
                    adicionarPreferenceEmail(editEmail.getText().toString());
                    adicionarPreferenceJaAbriu(preferences);
                    Intent intent = new Intent(LoginInstituicao.this, Instituicao.class);
                    startActivity(intent);
                    finish();
                }else{
                    exibirProgress(false);
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
