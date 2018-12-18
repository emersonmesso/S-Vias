package br.com.s_dev.s_vias.s_vias.View.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import br.com.s_dev.s_vias.s_vias.R;
import br.com.s_dev.s_vias.s_vias.View.Controller.HTTPRequest;

public class PreProcessamento extends AppCompatActivity {

    LocationManager locationManager;
    boolean ativoGps = false;
    boolean ativoInt = false;
    static final int REQUEST_LOCATION = 1;

    SignInButton buttonLogin;
    LinearLayout ligin;
    LinearLayout carregando;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions gso;
    int RC_SIGN_IN;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_processamento);


        ligin = (LinearLayout) findViewById(R.id.login);
        carregando = (LinearLayout) findViewById(R.id.carregando);
        ligin.setVisibility(View.GONE);
        buttonLogin = (SignInButton) findViewById(R.id.sign_in_button);
        buttonLogin.setSize(SignInButton.SIZE_STANDARD);
        mProgressBar = (ProgressBar) findViewById(R.id.note_list_progress);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
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
        onPreExecute();
    }

    private void exibirProgress(boolean exibir) {
        mProgressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }

    //verifica se tem internet
    public void verificaConexao() {
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            ativoInt = true;
        }
    }


    //verifica se o usuário está logado


    //Verifica se existe permissão do GPS
    private void perGPS(){
        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ativoGps = false;
            ActivityCompat.requestPermissions(PreProcessamento.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }else {
            ativoGps = true;
        }
    }

    //Sing In
    public void singIn (){
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
                verificaCadastro(email);
                exibirProgress(false);
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.i("falhas", "signInResult:failed code=" + e.getStatusCode());
        }

    }

    protected void onPreExecute() {
        //verifica se existe o gps
        perGPS();
        //verifica se existe internet
        verificaConexao();

        if(ativoInt && ativoGps){
            //verifica se está cadastrado
            Log.i("Permissao", "Tudo Permitido!");

            //verifica se o login foi feito
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if(account == null){
                //Não logado
                ligin.setVisibility(View.VISIBLE);
                carregando.setVisibility(View.GONE);
            }else{
                //Logado
                //troca de tela
                Intent intent = new Intent(PreProcessamento.this,
                        MainActivity.class);
                startActivity(intent);
                finish();

            }

        }else{
            //mostra os botões de permissões
        }

    }

    private void verificaCadastro (String email){
        ligin.setVisibility(View.INVISIBLE);
        HTTPRequest request = new HTTPRequest();

        String dados = request.getDados("https://emersonmesso95.000webhostapp.com/_api/_apiVerificaLogin.php?email=" + email, "");

        Log.i("dados", dados);

        if(dados.equals("1")){
            Log.i("dados", "ok");
            Intent intent = new Intent(PreProcessamento.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Log.i("dados", "Erro");
            Intent intent = new Intent(PreProcessamento.this,
                    Cadastro.class);
            startActivity(intent);
            finish();
        }


    }

    public void visitante (View v){
        Intent intent = new Intent(PreProcessamento.this,
                ActivityVisitante.class);
        startActivity(intent);
        finish();
    }
}
