<?php
require "../../_core/_config/functions.php";
$con = new Controller();

//recebe os dados
$id = $_POST['id'];
$desc = utf8_encode($_POST['desc']);
$sql = $con->update("denuncia", "descricao = '$desc'", "id_loc = $id");
if(!$sql){
    echo "Não Foi Possível Editar!";
}