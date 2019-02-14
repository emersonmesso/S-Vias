<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2019-02-13.
 */

class DenunciaDAO {

    var $titulo;
    var $desccricao;
    var $data;
    var $local = [];
    var $classificacao;
    var $status;
    var $conn;
    var $login;

    function __construct($conexao) {
        $this->conn = $conexao;
    }

    function set_denuncia(Denuncia $denuncia) {
        $this->titulo = $denuncia->get_titulo();
        $this->descricao = $denuncia->get_descricao();
        $this->data = $denuncia->get_data();
        $this->local = $denuncia->get_local();
        $this->classificacao = $denuncia->get_classificacao();
        $this->status = $denuncia->get_status();
        $this->login = $denuncia->get_login();
    }

    function insert_denuncia() {

        $query = "INSERT INTO denuncia(data, descricao, email_cid, lati, lng, titulo)"
                . " VALUES('$this->data', '$this->descricao', '$this->login', '" . $this->local['lat'] . "', '" . $this->local['lng'] . "', '$this->titulo')";

        mysqli_refresh($this->conn, mysqli_query($this->conn, $query) or die("erro ao inserir os dados na tabela endereco!"));
    }

    function update() {

        $query = "UPDATE denuncia(descricao, status)"
                . " VALUES('$this->descricao', '$this->status')";

        mysqli_refresh($this->conn, mysqli_query($this->conn, $query) or die("erro ao inserir os dados na tabela endereco!"));
    }

    function get_denuncia() {
        $query = "SELECT * FROM denuncia WHERE titulo = '$this->titulo'";

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
    
    function get_denuncias_tipo($type) {
        $query = "SELECT * FROM denuncia WHERE type = '" . $type . "'";

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

    function get_denuncias() {
        $query = "SELECT * FROM denuncia";

        $result = mysqli_query($this->conn, $query) or die("erro na pesquisa de posts!");

        $row = mysqli_num_rows($result);

        if ($row > 0) {
            $array = [];

            while ($linha = mysqli_fetch_array($result)) {
                $array[] = $linha;
            }
            return $array;
        } else {
            return NULL;
        }
    }

    function delete() {

        $query = "DELETE FROM denuncia WHERE titulo = '$this->titulo'";

        mysqli_query($this->conn, $query) or die("erro na exclusão!");
    }

}
