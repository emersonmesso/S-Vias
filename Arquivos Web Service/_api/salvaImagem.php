<?php
require './Controller.php';
$Controller = new Controller();

$id_loc = intval($_POST['id_loc']);
$img = $_POST['img'];

$caminho = $Controller->salvarImagem($img);

$sql = $Controller->getSql()->insere("imagens", "id_loc, img_den", "$id_loc, '$caminho'");
if($sql){
    
}else{
    echo "NÃO FOI POSSÍVEL ADICIONAR";
}