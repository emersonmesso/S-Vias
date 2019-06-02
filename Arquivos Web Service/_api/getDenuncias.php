<?php
require './Controller.php';
$Controller = new Controller();
$denuncias = array();
//recebe o cep e o E-mail

if(!isset($_GET['email'])){
    echo 'DADOS NÃO ENVIADOS!!';
    exit;
}
$email = $_GET['email'];

//buscando as denuncias pelo cep e E-mail
$result = $Controller->getSql()->select("denuncia", "*", "email_cid = '$email'", "data DESC");

while($rows = mysqli_fetch_array($result)){
    //buscando a imagem
    $imgDenuncia = $Controller->getSql()->select("imagens", "*", "id_loc = ".$rows['id_loc']);
    $image = "";
    while ($img = mysqli_fetch_array($imgDenuncia)){
        $image = "https://emersonmesso95.000webhostapp.com/_api/images/".$img['img_den'];
    }
    //buscando o tipo de situação
    $sqlSituacao = $Controller->getSql()->select("classificacao", "*", "id_class = " . $rows['id_class']);
    $dadosClassificacao = mysqli_fetch_array($sqlSituacao);
    
    $data = date("d/m/Y H:i:s", $rows['data']);
    $denuncias[] = array(
        'titulo' => utf8_decode ($rows['titulo'] ),
        'data' => $data,
        'desc' => utf8_decode( $rows['descricao'] ),
        'lat' => $rows['lati'],
        'lng' => $rows['lng'],
        'cidadao' => 1,
        'cep' => $rows['cep'],
        'sit' => $dadosClassificacao['classificacao'],
        'img' => $image
    );
}
echo json_encode($denuncias);