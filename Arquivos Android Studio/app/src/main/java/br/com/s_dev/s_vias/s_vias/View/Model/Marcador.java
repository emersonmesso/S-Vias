package br.com.s_dev.s_vias.s_vias.View.Model;

import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;
import java.util.Date;

public class Marcador {
    private String descricao;
    private Date data;
    private String nome;
    private LatLng latlng;
    private Bitmap midia;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }

    public Bitmap getMidia() {
        return midia;
    }

    public void setMidia(Bitmap midia) {
        this.midia = midia;
    }
}
