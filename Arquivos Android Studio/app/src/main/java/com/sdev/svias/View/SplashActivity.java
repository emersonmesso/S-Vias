package com.sdev.svias.View;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.sdev.svias.R;

public class SplashActivity extends Activity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferences.contains("ja_abriu_app")) {
                    //adicionarPreferenceJaAbriu(preferences);
                    mostrarLogin();
                } else {
                    mostraOnBoarding();
                }
            }
        }, 4000);

    }

    private void mostrarLogin(){
        Intent intent = new Intent(SplashActivity.this,
                PreProcessamento.class);
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
        editor.putBoolean("ja_abriu_app", true);
        editor.commit();
    }
}
