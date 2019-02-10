<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2018-12-21.
 */

class InstituicaoDAO {

    var $r_social;
    var $telefone;
    var $email;
    var $cnpj;
    var $end;
    var $conn;

    function __construct($conexao) {
        $this->conn = $conexao;
    }

    function set_instituicao(Instituicao $instituicao) {
        $this->r_social = $instituicao->get_r_social();
        $this->telefone = $instituicao->get_telefone();
        $this->email = $instituicao->get_email();
        $this->end = $instituicao->get_end();
        $this->cnpj = $instituicao->get_cnpj();
    }

    //falta adicionar o campo endereco
    function insert_instituicao() {

        $query = "INSERT INTO instituicao(razao_social, email, telefone, cnpj)"
                . " VALUES('$this->r_social', '$this->email', '$this->telefone', '$this->cnpj')";

        mysqli_refresh($this->conn, mysqli_query($this->conn, $query) or die("erro ao inserir os dados na tabela endereco!"));
    }

    //falta adicionar o campo endereco
    function update() {

        $query = "UPDATE instituicao()"
                . " VALUES()";

        mysqli_refresh($this->conn, mysqli_query($this->conn, $query) or die("erro ao inserir os dados na tabela endereco!"));
    }

    function get_instituicao() {
        $query = "SELECT * FROM instituicao WHERE cnpj = '$this->cnpj'";

        $result = mysqli_query($this->conn, $query) or die("erro na pesquisa de posts!");

        $row = mysqli_num_rows($result);

        if ($row > 0) {
            $array = array();
            $cont = 0;

            while ($linha = mysqli_fetch_array($result)) {
                $array[$cont++] = $linha;
            }
            return $array;
        } else {
            return NULL;
        }
    }

    function is_instituicao() {
        return (cont(get_cidadao()) === 4);
    }

    function delete() {

        $query = "DELETE FROM instituicao WHERE cnpj = '$this->cnpj'";

        mysqli_query($this->conn, $query) or die("erro na exclusão!");
    }

}
