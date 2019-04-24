package com.sdev.svias.Controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdev.svias.R;
import com.sdev.svias.View.OnboardingActivity;
import com.sdev.svias.View.PreProcessamento;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    Activity activity;

    public SliderAdapter(Context context, Activity ac){
        this.context = context;
        this.activity = ac;
    }

    //Arrays
    public String[] slide_heading = {

            "Cidadão S-Vias",
            "Visualizar Denúncias!",
            "Adicionar Denúncia",
            "Permissão GPS"
    };

    public String[] slide_desc = {
            "Realize seu cadastro utilizando sua conta Google para ter acesso ao sistema!",
            "Ao clicar em uma denúncia no mapa, você visualizará todas as informações da denúncia!",
            "Ao clicar no botão adicionar, você poderá adicionar as informações sobre a sua denúncia!",
            "Permita que utilizemos o GPS para localizar as denúncias."
    };

    public int[] slide_images = {
            R.drawable.icon_google,
            R.drawable.icon,
            R.drawable.icon_add,
            R.drawable.icon_google
    };

    @Override
    public int getCount() {
        return slide_desc.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (FrameLayout) o;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slideImageView);
        TextView slideTextHead = (TextView) view.findViewById(R.id.textHeading);
        TextView slideTextDesc = (TextView) view.findViewById(R.id.textDesc);
        slideImageView.setImageResource(slide_images[position]);
        slideTextHead.setText(slide_heading[position]);
        slideTextDesc.setText(slide_desc[position]);

        container.addView(view);

        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((FrameLayout) object);
    }
}
