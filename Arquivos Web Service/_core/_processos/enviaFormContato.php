<?php
require "../../_core/_config/functions.php";
$con = new Controller();

$nome = $_POST['nome'];
$email = $_POST['email'];
$mensagem = $_POST['mensagem'];
$dados = array(
    'nome' => $nome,
    'email' => $email,
    'mensagem' => $mensagem
);

$con->enviaFormContato($dados);