package com.sdev.svias.View;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdev.svias.Controller.SlideImageAdapter;
import com.sdev.svias.Controller.SliderAdapter;
import com.sdev.svias.R;

import java.util.ArrayList;

public class ViewImages extends AppCompatActivity {
    private ViewPager mSliderViewPager;
    private SlideImageAdapter slideImageAdapter;
    private ArrayList<String> imagens;
    private int mCurrentPage;
    private LinearLayout mDotslayout;

    private TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().hasExtra("imagens")) {
            Bundle extras = getIntent().getExtras();
            imagens = extras.getStringArrayList("imagens");
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);
        mDotslayout = (LinearLayout) findViewById(R.id.dotsLayout);
        mSliderViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        slideImageAdapter = new SlideImageAdapter(this, ViewImages.this, imagens);
        mSliderViewPager.setAdapter(slideImageAdapter);

        addDotsIndicator(0);
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[imagens.size()];

        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorAppBar));

            mDotslayout.addView(mDots[i]);
        }

        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorAppBar));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
