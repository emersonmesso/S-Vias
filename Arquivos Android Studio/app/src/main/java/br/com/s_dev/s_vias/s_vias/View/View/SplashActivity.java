package br.com.s_dev.s_vias.s_vias.View.View;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.s_dev.s_vias.s_vias.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        }, 4000);

    }

    private void mostrarLogin(){
        Intent intent = new Intent(SplashActivity.this,
                PreProcessamento.class);
        startActivity(intent);
        finish();
    }
}
