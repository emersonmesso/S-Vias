package com.sdev.svias.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.sdev.svias.R;
import com.sdev.svias.Controller.HTTPRequest;
import com.sdev.svias.Controller.UtilAPP;

public class Cadastro extends AppCompatActivity {

    //
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions gso;
    ProgressBar mProgressBar;
    int RC_SIGN_IN;
    EditText campoCPF;
    EditText campoSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //Minhas alterações
        mProgressBar = (ProgressBar) findViewById(R.id.note_list_progress);
        campoCPF = (EditText)findViewById(R.id.campoCPF);
        campoSenha = (EditText) findViewById(R.id.campoSenha);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        /*Ação Do Botão de cadastro*/
        Button btnCadastro = (Button) findViewById(R.id.btnCadastrar);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //pega a conta do usuário
                singIn();
            }
        });

        exibirProgress(false);

    }

    private void exibirProgress(boolean exibir) {
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
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

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            exibirProgress(true);
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
            if(acc != null){
                String email = acc.getEmail();
                String nome = acc.getDisplayName();
                //recebe os dados do campo e verifica
                String senha = String.valueOf( campoSenha.getText() );
                String CPF = String.valueOf( campoCPF.getText() );

                if(CPF.isEmpty() || senha.isEmpty()){
                    Toast.makeText(this, "CPF ou Senha não informada!", Toast.LENGTH_LONG).show();
                }else{
                    //faz o cadastro no servidor
                    HTTPRequest rest = new HTTPRequest();
                    String retorno = rest.getDados(UtilAPP.LINK_SERVIDOR_CADASTRO + "nome=" + nome +"&email=" + email + "&senha=" + md5(senha) + "&cpf=" + CPF, "");
                    if(retorno.equals("1")){

                        Intent intent = new Intent(Cadastro.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(this, retorno, Toast.LENGTH_LONG).show();
                        signOut();

                    }
                }


            }
            exibirProgress(false);
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

}
