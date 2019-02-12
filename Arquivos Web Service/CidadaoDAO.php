<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2018-12-21.
 */

class CidadaoDAO {

    var $nome;
    var $email;
    var $senha;
    var $cpf;
    var $conn;

    function __construct($conexao) {
        $this->conn = $conexao;
    }

    function set_cidadao(Cidadao $cidadao) {
        $this->nome = $cidadao->get_nome();
        $this->email = $cidadao->get_email();
        $this->senha = $cidadao->get_senha();
        $this->cpf = $cidadao->get_cpf();
    }

    function insert_cidadao() {

        $query = "INSERT INTO cidadao(nome, email, senha, cpf)"
                . " VALUES('$this->nome', '$this->email', '$this->senha', '$this->cpf')";

        mysqli_refresh($this->conn, mysqli_query($this->conn, $query) or die("erro ao inserir os dados na tabela endereco!"));
    }

    function update() {

        $query = "UPDATE cidadao(email, senha)"
                . " VALUES('$this->email', '$this->senha')";

        mysqli_refresh($this->conn, mysqli_query($this->conn, $query) or die("erro ao inserir os dados na tabela endereco!"));
    }

    function get_cidadao() {
       $query = "SELECT * FROM cidadao WHERE email = '$this->email'";

        $result = mysqli_query($this->conn, $query) or die("erro na pesquisa de posts!");

        $row = mysqli_num_rows($result);

        if ($row > 0) {
            $array = [];
            while ($linha = mysqli_fetch_array($result)) {
                
                echo $linha;
               echo count($linha);
                
                foreach($linha as $item) {
                    $array[] = $item;
                }
            }
             var_dump($array);
            echo count($array);
            return $array;
        } else {
           return NULL;
        }
    }

    function is_cidadao() {
        return (count($this->get_cidadao()) > 4);
    }

    function delete() {

        $query = "DELETE FROM cidadao WHERE cpf = '$this->cpf'";

        mysqli_query($this->conn, $query) or die("erro na exclusão!");
    }

}
