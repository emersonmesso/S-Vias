<?php
require './Controller.php';
$Controller = new Controller();
$denuncias = array();
//recebe o cep e o E-mail

if(!isset($_GET['email'])){
    exit;
}
$email = $_GET['email'];

//buscando os dados da instituição
$sql = $Controller->getSql()->select("instituicao", "*", "email = '$email'");
$instituicao = mysqli_fetch_array($sql);
$cep = $instituicao['cep'];

//buscando todas as denuncias do cep
$sqlDenuncia = $Controller->getSql()->select("denuncia", "*", "cep = '$cep'", "id_loc DESC");
while($rows = mysqli_fetch_array($sqlDenuncia)){
    //buscando a imagem
    $imgDenuncia = $Controller->getSql()->select("imagens", "*", "id_loc = ".$rows['id_loc']);
    $image = array();
   
    if(mysqli_num_rows($imgDenuncia) > 0){
        while ($img = mysqli_fetch_array($imgDenuncia)){
            $image[] = array(
                'img' => "https://emersonmesso95.000webhostapp.com/_api/images/".$img['img_den']
                );
        }
    }else{
        $image[] = array('img' => "https://emersonmesso95.000webhostapp.com/_core/_img/imageError.png");
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
        'img' => $image,
        'id_loc' => $rows['id_loc'],
        'rua' => utf8_decode($rows['rua']),
        'cidade' => utf8_decode($rows['cidade']),
        'bairro' => $rows['bairro'],
        'cep' => $rows['cep']
    );
}
echo json_encode($denuncias);