<?php
    require "../../_core/_config/functions.php";
    $con = new Controller();
    
    $email = $_POST['email'];
    $nome = $_POST['razao_social'];
    $cnpj = $_POST['cnpj'];
    $endereco = $_POST['endereco'];
    $cidade = $_POST['cidade'];
    $cep = $_POST['cep'];
    $senha = $_POST['password'];

    $mysql = new mysql();
    $mysql ->conecta();
    
    $res = "INSERT INTO instituicao ('email','razao_social','cnpj','endereco','cidade','cep','senha') VALUES ('$email','$nome','$cnpj','$endereco','$cidade','$cep','$senha')";
    
    $query = $mysql->sql->query($res);
    
    
  
    
    
    if(mysqli_affected_rows($con) != 0){
                echo "Usuario cadastrado com Sucesso";    
            }else{
                echo "O Usuario não foi cadastrado com Sucesso.";
             
            }
    mysqli_close($mysql->sql);
?>



