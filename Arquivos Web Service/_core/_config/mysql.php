<?php
class Mysql{
    private $host = "localhost";
    private $user = "root";
    private $pass = "";
    private $banco = "s-vias";
    
    function getHost() {
        return $this->host;
    }

    function getUser() {
        return $this->user;
    }

    function getPass() {
        return $this->pass;
    }

    function getBanco() {
        return $this->banco;
    }

    function setHost($host) {
        $this->host = $host;
    }

    function setUser($user) {
        $this->user = $user;
    }

    function setPass($pass) {
        $this->pass = $pass;
    }

    function setBanco($banco) {
        $this->banco = $banco;
    }


}