package com.sdev.svias.View;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.sdev.svias.R;

public class Instituicao extends AppCompatActivity {
    private String Email;
    private FrameLayout loadMapa;
    private ImageView imgLoad;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicao);

        preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);

        //Loading
        loadMapa = (FrameLayout) findViewById(R.id.telaLoad);
        imgLoad = (ImageView) findViewById(R.id.imgLoad);
        Glide.with(this).load(R.drawable.load).into(imgLoad);
        exibirProgress(false);

        Email = preferences.getString("email" ,null);

        Toast.makeText(this, Email, Toast.LENGTH_LONG).show();
    }
    private void exibirProgress(boolean exibir) {
        loadMapa.setVisibility(exibir ? View.VISIBLE : View.GONE);
    }
}
