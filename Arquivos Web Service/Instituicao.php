<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2019-02-12.
 */

class Instituicao {

    var $r_social;
    var $telefone;
    var $senha;
    var $end;
    var $email;
    var $cnpj;
    var $num_ende;
    var $cidade;
    var $cep;
    var $bairro;
    var $uf;
    
    
    function getCnpj() {
        return $this->cnpj;
    }

    function getNum_ende() {
        return $this->num_ende;
    }

    function getCidade() {
        return $this->cidade;
    }

    function getCep() {
        return $this->cep;
    }

    function getBairro() {
        return $this->bairro;
    }

    function getUf() {
        return $this->uf;
    }

    function setCnpj($cnpj) {
        $this->cnpj = $cnpj;
    }

    function setNum_ende($num_ende) {
        $this->num_ende = $num_ende;
    }

    function setCidade($cidade) {
        $this->cidade = $cidade;
    }

    function setCep($cep) {
        $this->cep = $cep;
    }

    function setBairro($bairro) {
        $this->bairro = $bairro;
    }

    function setUf($uf) {
        $this->uf = $uf;
    }
    
    function getEnd() {
        return $this->end;
    }

    function setEnd($end) {
        $this->end = $end;
    }

    
    function get_r_social() {
        return $this->r_social;
    }

    function get_telefone() {
        return $this->telefone;
    }

    function get_senha() {
        return $this->senha;
    }

    function get_email() {
        return $this->email;
    }

    function set_r_social($r_social) {
        $this->r_social = $r_social;
    }

    function set_telefone($telefone) {
        $this->telefone = $telefone;
    }

    function set_senha($senha) {
        $this->senha = $senha;
    }

    function set_email($email) {
        $this->email = $email;
    }

}
