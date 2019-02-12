<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2019-02-12.
 */

class InstituicaoDAO {

    var $r_social;
    var $telefone;
    var $senha;
    var $email;
    var $end;
    var $conn;

    function __construct($conexao) {
        $this->conn = $conexao;
    }

    function set_instituicao(Instituicao $instituicao) {
        $this->r_social = $instituicao->get_r_social();
        $this->telefone = $instituicao->get_telefone();
        $this->senha = $instituicao->get_senha();
        $this->end = $instituicao->get_end();
        $this->email = $instituicao->get_email();
    }

    //falta adicionar o campo endereco
    function insert_instituicao() {

        $query = "INSERT INTO instituicao(razao_social, senha, telefone, email)"
                . " VALUES('$this->r_social', '$this->senha', '$this->telefone', '$this->email')";

        mysqli_refresh($this->conn, mysqli_query($this->conn, $query) or die("erro ao inserir os dados na tabela endereco!"));
    }

    //falta adicionar o campo endereco
    function update() {

        $query = "UPDATE instituicao()"
                . " VALUES()";

        mysqli_refresh($this->conn, mysqli_query($this->conn, $query) or die("erro ao inserir os dados na tabela endereco!"));
    }

    function get_instituicao() {
        $query = "SELECT * FROM instituicao WHERE email = '$this->email'";

        $result = mysqli_query($this->conn, $query) or die("erro na pesquisa de posts!");

        $row = mysqli_num_rows($result);

        return $row;
    }

    function is_instituicao() {
        return $this->get_cidadao() === 4;
    }

    function delete() {

        $query = "DELETE FROM instituicao WHERE email = '$this->email'";

        mysqli_query($this->conn, $query) or die("erro na exclusão!");
    }

}
