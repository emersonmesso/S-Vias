package com.sdev.svias.View;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sdev.svias.Controller.HTTPRequest;
import com.sdev.svias.Controller.ScriptDLL;
import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.R;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends Activity {

    SharedPreferences preferences;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions gso;
    private boolean isLogado = false;
    private FrameLayout FrameLogin;
    private TextView nomeUser;
    private CircleImageView imgUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FrameLogin = (FrameLayout) findViewById(R.id.FrameLogin) ;
        FrameLogin.setVisibility(View.GONE);
        nomeUser = (TextView) findViewById(R.id.nomeuser);
        imgUser = (CircleImageView) findViewById(R.id.imgUser);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        //signOut();
        Splash init = new Splash();
        init.execute();

    }
    public Bitmap DownloadImage(String pURL){
        StrictMode.ThreadPolicy vPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(vPolicy);

        InputStream inStream = null;
        Bitmap vBitmap = null;

        try{

            URL url = new URL(pURL);
            HttpURLConnection pConnection = (HttpURLConnection)url.openConnection();
            pConnection.setDoInput(true);
            pConnection.connect();

            if(pConnection.getResponseCode() == HttpURLConnection.HTTP_OK){

                inStream = pConnection.getInputStream();
                vBitmap = BitmapFactory.decodeStream(inStream);
                inStream.close();

                return vBitmap;
            }

        }
        catch(Exception ex){
            Log.e("Exception",ex.toString());
        }

        return null;

    }

    private void mostrarLogin(){
        Intent intent = new Intent(SplashActivity.this,
                PreProcessamento.class);
        startActivity(intent);
        finish();
    }

    public void mostraMapa(){
        Intent intent = new Intent(SplashActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void mostraOnBoarding (){
        Intent intent = new Intent(SplashActivity.this,
                OnboardingActivity.class);
        startActivity(intent);
        finish();
    }

    private void adicionarPreferenceJaAbriu(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("onboarding", true);
        editor.commit();
    }

    private void signOut() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private class Splash extends AsyncTask<String, String, String> {

        private String nome;
        private Uri imagem;

        @Override
        protected void onPreExecute (){

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if(account != null){
                //tem o login
                this.nome = account.getDisplayName();
                this.imagem = account.getPhotoUrl();
                isLogado = true;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            if(isLogado){

                if(this.imagem != null){
                    imgUser.setImageBitmap(DownloadImage(this.imagem.toString()));
                    nomeUser.setText(this.nome);
                    FrameLogin.setVisibility(View.VISIBLE);
                }
            }
            Handler handle = new Handler();
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                if (preferences.contains("onboarding")) {

                    if(isLogado){
                        mostraMapa();
                    }else {
                        mostrarLogin();
                    }
                } else {
                    adicionarPreferenceJaAbriu(preferences);
                    mostraOnBoarding();
                }
                }
            }, 4000);
        }
    }


}
