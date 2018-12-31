<?php

/*CONEXÃO COM O BANCO DE DADOS DA API*/
class Banco {
    private $sql = null;
    private $banco = "id7145571_svias";
    private $host = "localhost";
    private $pass = "sistemassvias";
    private $user = "id7145571_svias";
    
    public function conectaBanco (){
        //iniciando a comunicação com o banco de dados
        $sql = new mysqli($this->host, $this->user, $this->pass, $this->banco);
        
        //verificando se conectou
        if(mysqli_errno($sql)){
            echo "NÃO CONECTADO AO BANCO DE DADOS!";
            return $sql;
        }else{
            return $sql;
        }
    }
    
    public function fechaConexao(){
        if($this->sql != null){
            mysqli_close($this->sql);
        }
    }
    
}

/*MANIPULAÇÃO DE BANCO DE DADOS DA API*/
class SQL {
    
    //Função de Select do banco de dados
    public function select($tabela, $todos=NULL, $where=NULL, $order=NULL){
        
        $bancoDados = new Banco();
        $db = $bancoDados->conectaBanco();
        if($db == null){
            echo "NÃO FOI POSSÍVEL CONECTAR AO BANCO DE DADOS!";
            exit;
        }
        
        if($todos == NULL){
            $todos = "*";
        }
        if($where != NULL){
            $where = " WHERE ".$where;
        }
        if($order != NULL){
            $order = " ORDER BY ".$order;
        }
        $sql = "SELECT {$todos} FROM {$tabela}{$where}{$order}";
        
        //
        $query = $db->query($sql);
        $bancoDados->fechaConexao(); 
        
        return $query;
    }
    
    //DELETE no banco de dados
    public function delete($tabela, $where){
        $bancoDados = new Banco();
        $db = $bancoDados->conectaBanco();
        if($db == null){
            echo "NÃO FOI POSSÍVEL CONECTAR AO BANCO DE DADOS!";
            exit;
        }
        if($where != NULL){
            $where = " WHERE ".$where;
        }
        //criando a consulta
        $sql = "DELETE FROM {$tabela}{$where}";
        $query = $db->query($sql);
        //fecha a coneção
        $bancoDados->fechaConexao();
        return $query;
    }
    
    //UPDATE no banco de dados
    public function update($tabela,$valores,$where){
        $bancoDados = new Banco();
        $db = $bancoDados->conectaBanco();
        if($db == null){
            echo "NÃO FOI POSSÍVEL CONECTAR AO BANCO DE DADOS!";
            exit;
        }
        //
        if($where != NULL){
            $where = " WHERE ".$where;
        }
        //
        $sql = "UPDATE {$tabela} SET {$valores} {$where}";
        //executa a Query
        $query = $db->query($sql);
        //fecha a coneção
        $bancoDados->fechaConexao();
        return $query;
    }
    
    //INSERE no banco de dados
    public function insere($tabela,$campos,$valores){
        $bancoDados = new Banco();
        $db = $bancoDados->conectaBanco();
        if($db == null){
            echo "NÃO FOI POSSÍVEL CONECTAR AO BANCO DE DADOS!";
            exit;
        }
        //
        $sql = "INSERT INTO {$tabela}({$campos}) VALUES ({$valores})";
        //executa a Query
        $query = $db->query($sql);
        //fecha a coneção
        $bancoDados->fechaConexao();
        //
        return $query;
    }
    
}

/*FUNÇÕES DA API*/
class Controller{
    private $sql;
    
    public function __construct() {
        $this->sql = new SQL();
    }
    
    public function getSql (){
        return $this->sql;
    }
    
}