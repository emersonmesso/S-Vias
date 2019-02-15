<?php
require './Controller.php';
$Controller = new Controller();
if(!isset($_GET['nome']) || !isset($_GET['email']) || !isset($_GET['cpf']) || !isset($_GET['senha'])){
    echo "DADOS NÃO ENVIADOS!";
    exit;
}
//armazena os dados em variáveis
$nome = $_GET['nome'];
$email = $_GET['email'];
$senha = $_GET['senha'];
$cpf = $_GET['cpf'];

//verifica se já tem algum cidadão com o mesmo email
$result = $Controller->getSql()->select("cidadao", "*", "email = '$email'");

if(mysqli_num_rows($result) > 0){
    //mostra a mensagem de erro que já existe um usuário cadastrado
    echo 'O EMAIL JÁ ESTA SENDO UTILIZADO!';
}else{
    //faz o cadastro do novo usuário
    $inserir = $Controller->getSql()->insere("cidadao" , "email, senha, cpf, nome", "'$email', '$senha', '$cpf', '$nome'");
    if($inserir){
        echo 1;
    }else{
        echo 'NÃO FOI POSSÍVEL CADASTRAR O CIDADÃO';
    }
}