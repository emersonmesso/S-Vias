<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2018-12-21.
 */

class Instituicao {

    var $r_social;
    var $telefone;
    var $email;
    var $end;
    var $cnpj;

    function get_r_social() {
        return $this->r_social;
    }

    function get_telefone() {
        return $this->telefone;
    }

    function get_email() {
        return $this->email;
    }

    function get_cnpj() {
        return $this->cnpj;
    }

    function set_r_social($r_social) {
        $this->r_social = $r_social;
    }

    function set_telefone($telefone) {
        $this->telefone = $telefone;
    }

    function set_email($email) {
        $this->email = $email;
    }

    function set_cnpj($cnpj) {
        $this->cnpj = $cnpj;
    }

}
