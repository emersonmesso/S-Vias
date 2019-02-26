<?php
require './Controller.php';
$Controller = new Controller();

//verifica se o email foi enviado
if(!isset($_GET['email'])){
    echo 'EMAIL NÂO INFORMADO!';
    exit;
}

//pega o email enviado
$email = $_GET['email'];

//faz a consulta
$result = $Controller->getSql()->select("cidadao", "*", "email = '$email'");

//mostra o valor encontrado
echo mysqli_num_rows($result);