<?php
require './Controller.php';
$Controller = new Controller();

$id_loc = 60;
$img = $_POST['file'];

$caminho = $Controller->salvarImagem($img);
$dados = array(
    'img' => $img,
    'id_loc' =>$id_loc,
    'caminho' => $caminho
);

$sql = $Controller->getSql()->insere("imagens", "id_loc, img_den", "$id_loc, '$caminho'");
if($sql){
    
}else{
    echo "NÃO FOI POSSÍVEL ADICIONAR";
}
echo json_encode($dados);