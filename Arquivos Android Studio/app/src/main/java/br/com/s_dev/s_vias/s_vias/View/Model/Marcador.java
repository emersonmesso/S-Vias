package br.com.s_dev.s_vias.s_vias.View.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import java.util.Date;

public class Marcador {
    private String descricao;
    private String data;
    private String nome;
    private LatLng latlng;
    private String midia;
    private Cidadao cidadao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public String getMidia() {
        return midia;
    }

    public void setMidia(String midia) {
        this.midia = midia;
    }

    public Cidadao getCidadao() {
        return cidadao;
    }

    public void setCidadao(Cidadao cidadao) {
        this.cidadao = cidadao;
    }
}
