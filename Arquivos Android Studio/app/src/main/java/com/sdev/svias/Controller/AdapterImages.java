package com.sdev.svias.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import com.sdev.svias.R;
import java.util.List;

public class AdapterImages extends ArrayAdapter<Bitmap> {
    private List<Bitmap> imagens;
    private Context context;


    public AdapterImages(Context context, List<Bitmap> imagens) {
        super(context, R.layout.linha_image);
        this.imagens = imagens;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imagens.size();
    }

    @Override
    public Bitmap getItem(int position) {
        return imagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_image, parent, false);

        final ImageView imagem = (ImageView) rowView.findViewById(R.id.imagem);
        imagem.setImageBitmap(imagens.get(position));
        Button btnRemove = (Button) rowView.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imagens.remove(position);
                notifyDataSetChanged();
            }
        });
        return rowView;
    }

    public void updateDados(){
        notifyDataSetChanged();
    }
}
