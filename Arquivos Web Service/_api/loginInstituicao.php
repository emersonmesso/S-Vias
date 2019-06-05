<?php
require './Controller.php';
$Controller = new Controller();

if(!isset($_GET['p']) || !isset($_GET['e'])){
    echo 'E-MAIL OU SENHA NÃO INFORMADO!';
    exit;
}
$pass = $_GET['p'];
$email = $_GET['e'];

$result = $Controller->getSql()->select("instituicao", "*", "email = '$email' AND senha = '$pass'");
if(mysqli_num_rows($result) > 0){
    
}else{
    echo "VERIFIQUE OS DADOS INFORMADOS!";
}