package com.sdev.svias.Controller;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sdev.svias.R;

import java.util.ArrayList;

public class SlideImageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    Activity activity;
    private ArrayList<String> imagens;

    public SlideImageAdapter(Context context, Activity ac, ArrayList<String> imagens) {
        this.context = context;
        this.activity = ac;
        this.imagens = imagens;
    }

    @Override
    public int getCount() {
        return imagens.size();
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

        View view = layoutInflater.inflate(R.layout.slide_image, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.imgSlide);
        DownloadImageFromInternet downloadImageFromInternet = new DownloadImageFromInternet(slideImageView,this.context);
        downloadImageFromInternet.execute(imagens.get(position));
        container.addView(view);

        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((FrameLayout) object);
    }

}
