<?php
require './Controller.php';
$Controller = new Controller();

$denuncias = array();
//recebe o cep

if(!isset($_GET['cep'])){
    echo 'CEP NÂO INFORMADO!';
    exit;
}
$cep = $_GET['cep'];

//buscando as denuncias pelo cep
$result = $Controller->getSql()->select("denuncia", "*", "cep = $cep");

while($rows = mysqli_fetch_array($result)){
    //buscando a imagem
    $imgDenuncia = $Controller->getSql()->select("imagens", "id_loc=" . $rows['id_loc']);
    $dadosImg = mysqli_fetch_array($imgDenuncia);
    
    //
    $data = date("d/m/Y H:i:s", $rows['data']);
    $denuncias[] = array(
        'titulo' => $rows['titulo'],
        'data' => $data,
        'desc' => $rows['descricao'],
        'lat' => $rows['lati'],
        'lng' => $rows['lng'],
        'cidadao' => 1,
        'cep' => $rows['cep'],
        'sit' => "pendente",
        'img' => $dadosImg['img_den']
    );
    
}

echo json_encode($denuncias);