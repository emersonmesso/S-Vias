<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2018-12-21.
 */

class DenunciaDAO {

    var $cpf;
    var $titulo;
    var $desccricao;
    var $data;
    var $local;
    var $classificacao;
    var $status;
    var $conn;

    function __construct($conexao) {
        $this->conn = $conexao;
    }

    function set_denuncia(Denuncia $denuncia) {
        $this->cpf = $denuncia->get_cpf();
        $this->titulo = $denuncia->get_titulo();
        $this->descricao = $denuncia->get_descricao();
        $this->data = $denuncia->get_data();
        $this->local = $denuncia->get_local();
        $this->classificacao = $denuncia->get_classificacao();
        $this->status = $denuncia->get_status();
    }

    function insert_denuncia() {

        $query = "INSERT INTO denuncia(cpf, titulo, descricao, data, local, classificacao, status)"
                . " VALUES('$this->cpf', '$this->titulo', '$this->descricao', '$this->data', '$this->local', '$this->classificacao', '$this->status')";

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

    function get_denuncias() {
        $query = "SELECT * FROM denuncia";

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

    function delete() {

        $query = "DELETE FROM denuncia WHERE titulo = '$this->titulo'";

        mysqli_query($this->conn, $query) or die("erro na exclusão!");
    }

}
