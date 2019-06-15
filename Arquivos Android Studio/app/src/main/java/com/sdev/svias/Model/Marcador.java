package com.sdev.svias.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Marcador {
    private String descricao;
    private String data;
    private String nome;
    private LatLng latlng;
    private ArrayList<String> midia;
    private ArrayList<String> midia_pref;
    private Cidadao cidadao;
    private String situacao;
    private int id_loc;
    private String cidade;
    private String rua;
    private String bairro;
    private String cep;

    public ArrayList<String> getMidia_pref() {
        return midia_pref;
    }

    public void setMidia_pref(ArrayList<String> midia_pref) {
        this.midia_pref = midia_pref;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getId_loc() {
        return id_loc;
    }

    public void setId_loc(int id_loc) {
        this.id_loc = id_loc;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

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

    public ArrayList<String> getMidia() {
        return midia;
    }

    public void setMidia(ArrayList<String> midia) {
        this.midia = midia;
    }

    public Cidadao getCidadao() {
        return cidadao;
    }

    public void setCidadao(Cidadao cidadao) {
        this.cidadao = cidadao;
    }
}
