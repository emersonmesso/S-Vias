package com.sdev.svias.Model;

public class Parametro {

    private String imagem;
    private String desc;
    private String email;
    private String cep;
    private String nome;
    private double lat;
    private double lng;
    private String url;


    public Parametro(String img, String nome, String cep, String desc, String email, double lat, double lng, String url){
        this.imagem = img;
        this.cep = cep;
        this.nome = nome;
        this.desc = desc;
        this.lat = lat;
        this.lng = lng;
        this.email = email;
        this.url = url;
    }


    public String getImagem() {
        return imagem;
    }

    public String getDesc() {
        return desc;
    }

    public String getEmail() {
        return email;
    }

    public String getCep() {
        return cep;
    }

    public String getNome() {
        return nome;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getUrl() {
        return url;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
