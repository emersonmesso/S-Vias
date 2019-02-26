package com.sdev.svias.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdev.svias.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Arrays
    public String[] slide_heading = {

            "Contas",
            "Visualizar Denúncia",
            "Adicionar Denúncia"
    };

    public String[] slide_desc = {
            "Você pode fazer o cadastro e entrar com a sua conta do Google, ou simplesmente entrar como visitante",
            "Ao clicar em uma denúncia no mapa, você terá todas as informações sobre a denúncia!",
            "Ao clicar no botão  de adicionar, você poderá adicionar as informações sobre a sua denúncia!"
    };

    public int[] slide_images = {
            R.drawable.icon,
            R.drawable.icon,
            R.drawable.icon_add
    };

    @Override
    public int getCount() {
        return slide_desc.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (FrameLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

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
