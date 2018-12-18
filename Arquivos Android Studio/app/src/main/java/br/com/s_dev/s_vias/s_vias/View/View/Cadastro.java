package br.com.s_dev.s_vias.s_vias.View.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.s_dev.s_vias.s_vias.R;

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
            }
        });
    }
}
