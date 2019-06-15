package com.sdev.svias.View;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdev.svias.Controller.UtilAPP;
import com.sdev.svias.R;

public class ActivityContato extends AppCompatActivity {

    private Button btnSiteApp;
    private Button btnSiteEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        btnSiteApp = (Button) findViewById(R.id.btnSiteApp);
        btnSiteEmp= (Button) findViewById(R.id.btnSiteEmp);


        //click
        btnSiteApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UtilAPP.LINK_SITE_APP;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnSiteEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = UtilAPP.LINK_SITE_EMPRESA;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
