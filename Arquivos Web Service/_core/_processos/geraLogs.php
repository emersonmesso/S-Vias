<?php
require "../../_core/_config/functions.php";
$con = new Controller();

$mensagem = $_POST['mensagem'];
$usuario = $_POST['user'];

//
$data = time();

$sql = $con->insere("registros", "user, mensagem, data", "'$usuario', '$mensagem', $data");
if(!$sql){
    echo "";
}