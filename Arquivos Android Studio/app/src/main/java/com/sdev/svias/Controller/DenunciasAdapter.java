package com.sdev.svias.Controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sdev.svias.Model.Marcador;
import com.sdev.svias.R;
import java.util.List;

import static android.support.v7.content.res.AppCompatResources.getDrawable;


public class DenunciasAdapter extends ArrayAdapter<Marcador> {
    private final Context context;
    private final List<Marcador> denuncias;
    private int selected = -1;

    public DenunciasAdapter(Context context, List<Marcador> denuncias){
        super(context, R.layout.linha, denuncias);
        this.context = context;
        this.denuncias = denuncias;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha, parent, false);


        //editando os dados da tela

        if(selected != -1 && position == selected) {
            rowView.setBackgroundColor(Color.BLACK);
        }
        ImageView imgDenuncia = (ImageView) rowView.findViewById(R.id.imgDenuncia);
        if(denuncias.get(position).getMidia().equals("")){
            imgDenuncia.setImageResource(R.drawable.image_off);
        }else{
            Glide.with(rowView.getContext()).load(R.drawable.load).into(imgDenuncia);
            DownloadImageFromInternet img = new DownloadImageFromInternet(imgDenuncia);
            img.execute(denuncias.get(position).getMidia());
        }

        TextView nomeDenuncia = (TextView) rowView.findViewById(R.id.nomeDenuncia);
        nomeDenuncia.setText(denuncias.get(position).getNome());

        ImageView imgSituacao = (ImageView) rowView.findViewById(R.id.imgSituacao);

        if(denuncias.get(position).getSituacao().equals("Pendente")){
            imgSituacao.setImageResource(R.drawable.vermelho);
        }else if(denuncias.get(position).getSituacao().equals("Em Progresso" )){
            imgSituacao.setImageResource(R.drawable.amarelo);
        }else{
            imgSituacao.setImageResource(R.drawable.verde);
        }

        imgDenuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView descDenuncia = (TextView) rowView.findViewById(R.id.descDenuncia);
        descDenuncia.setText(denuncias.get(position).getDescricao());


        return rowView;
    }
}
