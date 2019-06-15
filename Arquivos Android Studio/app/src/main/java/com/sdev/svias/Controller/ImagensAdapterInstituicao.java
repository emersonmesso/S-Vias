package com.sdev.svias.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.sdev.svias.R;

import java.util.ArrayList;

public class ImagensAdapterInstituicao extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> imagens;

    public ImagensAdapterInstituicao(Context context, ArrayList<String> imagens) {
        super(context, R.layout.linha_image, imagens);
        this.context = context;
        this.imagens = imagens;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_image, parent, false);

        //Adicionando a imagem
        ImageView image = (ImageView) rowView.findViewById(R.id.imagem);
        DownloadImageFromInternet downloadImageFromInternet = new DownloadImageFromInternet(image, context);
        downloadImageFromInternet.execute(imagens.get(position));

        //Botão
        Button btn = (Button) rowView.findViewById(R.id.btnRemove);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chama método
                imagens.remove(position);
                notifyDataSetChanged();
            }
        });


        return rowView;
    }
}
