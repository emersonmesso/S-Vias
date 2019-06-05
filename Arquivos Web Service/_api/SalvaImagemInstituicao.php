<?php
require './Controller.php';
$Controller = new Controller();

$id_loc = intval($_POST['id_loc']);
$img = $_POST['img'];

$caminho = $Controller->salvarImagem($img);

$insere = $Controller->getSql()->insere("imagens_pref", "id_loc, imagem", "'$id_loc', '$caminho'");
if($insere){
    
}else{
    echo "NÃO FOI POSSÍVEL ADICIONAR";
}