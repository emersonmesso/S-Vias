<?php
require './Controller.php';
$Controller = new Controller();

$id = $_GET['id'];
$id_loc = $_GET['id_loc'];
$sql = $Controller->getSql()->update("denuncia", "id_class = $id", "id_loc = $id_loc");
if(!$sql){
    echo "NÃO FOI POSSÍVEL ATUALIZAR!";
}else{
    echo "";
}