<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2018-12-21.
 */

class Cidadao {

    var $nome;
    var $email;
    var $senha;
    var $cpf;

    function get_nome() {
        return $this->nome;
    }

    function get_email() {
        return $this->email;
    }

    function get_senha() {
        return $this->senha;
    }

    function get_cpf() {
        return $this->cpf;
    }

    function set_nome($nome) {
        $this->nome = $nome;
    }

    function set_email($email) {
        $this->email = $email;
    }

    function set_senha($senha) {
        $this->senha = $senha;
    }

    function set_cpf($cpf) {
        $this->cpf = $cpf;
    }

}
