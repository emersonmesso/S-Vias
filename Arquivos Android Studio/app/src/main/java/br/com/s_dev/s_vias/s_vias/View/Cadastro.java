package br.com.s_dev.s_vias.s_vias.View;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.s_dev.s_vias.s_vias.R;

import static android.widget.Toast.LENGTH_LONG;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);



        /*Ação Do Botão de cadastro*/
        Button btnCadastro = (Button) findViewById(R.id.btnCadastrar);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //Ação de verificação dos dados

            //trocando de tela
            Intent intent = new Intent(Cadastro.this, Mapa.class);
            startActivity(intent);
            }
        });
    }
}
