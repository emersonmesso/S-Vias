package com.sdev.svias.Controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        //background
        LinearLayout tela = (LinearLayout) rowView.findViewById(R.id.linhaComp);
        if(denuncias.get(position).getSituacao().equals("Pendente")){
            tela.setBackgroundColor(Color.RED);
        }else if(denuncias.get(position).getSituacao().equals("Em Progresso" )){
            tela.setBackgroundColor(Color.YELLOW);
        }else{
            tela.setBackgroundColor(Color.GREEN);
        }

        //nome da denuncia
        TextView nomeDenuncia = (TextView) rowView.findViewById(R.id.nomeDenuncia);
        nomeDenuncia.setText(denuncias.get(position).getNome());

        //descrição
        TextView descDenuncia = (TextView) rowView.findViewById(R.id.descDenuncia);
        if(denuncias.get(position).getDescricao().length() > 50){
            descDenuncia.setText(denuncias.get(position).getDescricao().substring(0, 50)+"...");
        }else{
            descDenuncia.setText(denuncias.get(position).getDescricao());
        }


        //Rua
        TextView rua = (TextView) rowView.findViewById(R.id.textRua);
        rua.setText(denuncias.get(position).getRua());


        return rowView;
    }
}
