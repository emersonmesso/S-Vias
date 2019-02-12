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
