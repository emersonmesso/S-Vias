<?php
require "../../_core/_config/functions.php";
$con = new Controller();

//recebe os dados
$email = $_POST['email'];
$senha = md5($_POST['senha']);
$check = intval($_POST['check']);

//verifica o tipo do login
if($check == 1){
    //login do cidadão
    $sqlLogin = $con->select("cidadao", "*", "email = '$email' AND senha = '$senha'");
    if(mysqli_num_rows($sqlLogin) > 0){
        //criando a sessão
        if(!$con->criaSessao($email, $check)){
            echo 'Erro';
        }
    }else{
        echo "Erro";
    }
}else{
    //login da instituição
    $sqlLogin = $con->select("instituicao", "*", "email = '$email' AND senha = '$senha'");
    if(mysqli_num_rows($sqlLogin) > 0){
        //criando a sessão
        if(!$con->criaSessao($email, $check)){
            echo 'Erro 1';
        }
    }else{
        echo "Erro 2";
    }
}