<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2019-02-13.
 */

class Denuncia {

    var $titulo;
    var $desccricao;
    var $data;
    var $local = [];
    var $classificacao;
    var $status;
    var $login;
    
    function get_login() {
    return $this->login;    
    }
    
    function get_titulo() {
        return $this->titulo;
    }

    function get_desccricao() {
        return $this->desccricao;
    }

    function get_data() {
        return $this->data;
    }

    function get_local() {
        return $this->local;
    }

    function get_classificacao() {
        return $this->classificacao;
    }

    function get_status() {
        return $this->status;
    }
    
    function set_login($login) {
        $this->login = $login;
    }
    
    function set_titulo($titulo) {
        $this->titulo = $titulo;
    }

    function set_desccricao($desccricao) {
        $this->desccricao = $desccricao;
    }

    function set_data($data) {
        $this->data = $data;
    }

    function set_local($local) {
        $this->local = $local;
    }

    function set_classificacao($classificacao) {
        $this->classificacao = $classificacao;
    }

    function set_status($status) {
        $this->status = $status;
    }

}
