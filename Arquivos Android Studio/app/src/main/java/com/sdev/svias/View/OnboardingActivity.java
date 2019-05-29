package com.sdev.svias.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdev.svias.Controller.SliderAdapter;
import com.sdev.svias.R;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager mSliderViewPager;
    private LinearLayout mDotslayout;

    private TextView[] mDots;
    private SliderAdapter sliderAdapter;

    private Button btnproximo, btnVolta;
    private int mCurrentPage;
    private boolean permitirGPS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);


        //
        mDotslayout = (LinearLayout) findViewById(R.id.dotsLayout);
        mSliderViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        sliderAdapter = new SliderAdapter(this, OnboardingActivity.this);
        mSliderViewPager.setAdapter(sliderAdapter);

        btnproximo = (Button) findViewById(R.id.nextBtn);
        btnVolta = (Button) findViewById(R.id.prevBtn);

        addDotsIndicator( 0);
        mSliderViewPager.addOnPageChangeListener(viewListener);

        btnproximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage == mDots.length - 1){
                    if(permitirGPS){
                        Intent intent = new Intent(OnboardingActivity.this, PreProcessamento.class);
                        startActivity(intent);
                        finish();
                    }else{
                        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(OnboardingActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            btnproximo.setText("Entrar");
                        }else{
                            permitirGPS = true;
                            btnproximo.setText("Entrar");
                        }
                    }
                }
                mSliderViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSliderViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    public void onClick(View v){
        Toast.makeText(getApplicationContext(), "Permissão", Toast.LENGTH_LONG).show();
    }


    public void addDotsIndicator(int position) {
        mDots = new TextView[4];

        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorBackSplash));
            mDotslayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorAppBar));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;

            if(i == 0){
                btnproximo.setEnabled(true);
                btnVolta.setEnabled(false);
                btnVolta.setVisibility(View.INVISIBLE);

                btnproximo.setText("Próximo");
                btnVolta.setText("");

            }else if(i == mDots.length - 1){
                btnproximo.setEnabled(true);
                btnVolta.setEnabled(true);
                btnVolta.setVisibility(View.VISIBLE);

                btnproximo.setText("Permitir");
                btnVolta.setText("Voltar");
            }else{

                btnproximo.setEnabled(true);
                btnVolta.setEnabled(true);
                btnVolta.setVisibility(View.VISIBLE);

                btnproximo.setText("Próximo");
                btnVolta.setText("Voltar");

            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}