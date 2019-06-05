<?php
class Denuncia {
    private $id_loc;
    private $email_cid;
    private $titulo;
    private $data;
    private $lati;
    private $long;
    private $rua;
    private $cep;
    private $cidade;
    private $class;
    private $desc;
    private $imagem;
    private $imagens_pref;
    
    function getImagens_pref() {
        return $this->imagens_pref;
    }

    function setImagens_pref($imagens_pref) {
        $this->imagens_pref = $imagens_pref;
    }
    
    function getImagem() {
        return $this->imagem;
    }

    function setImagem($imagem) {
        $this->imagem = $imagem;
    }
    
    function getDesc() {
        return $this->desc;
    }

    function setDesc($desc) {
        $this->desc = $desc;
    }
            
    function getId_loc() {
        return $this->id_loc;
    }

    function getEmail_cid() {
        return $this->email_cid;
    }

    function getTitulo() {
        return $this->titulo;
    }

    function getData() {
        return $this->data;
    }

    function getLati() {
        return $this->lati;
    }

    function getLong() {
        return $this->long;
    }

    function getRua() {
        return $this->rua;
    }

    function getCep() {
        return $this->cep;
    }

    function getCidade() {
        return $this->cidade;
    }

    function getClass() {
        return $this->class;
    }

    function setId_loc($id_loc) {
        $this->id_loc = $id_loc;
    }

    function setEmail_cid($email_cid) {
        $this->email_cid = $email_cid;
    }

    function setTitulo($titulo) {
        $this->titulo = $titulo;
    }

    function setData($data) {
        $this->data = $data;
    }

    function setLati($lati) {
        $this->lati = $lati;
    }

    function setLong($long) {
        $this->long = $long;
    }

    function setRua($rua) {
        $this->rua = $rua;
    }

    function setCep($cep) {
        $this->cep = $cep;
    }

    function setCidade($cidade) {
        $this->cidade = $cidade;
    }

    function setClass($class) {
        $this->class = $class;
    }
}