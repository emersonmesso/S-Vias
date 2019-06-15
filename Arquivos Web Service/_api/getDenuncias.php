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
    $imgpref = $Controller->getSql()->select("imagens_pref", "*", "id_loc = ".$rows['id_loc']);
    $image = array();
    $image_pref = array();
   
    if(mysqli_num_rows($imgDenuncia) > 0 || mysqli_num_rows($imgpref) > 0){
        while ($img = mysqli_fetch_array($imgDenuncia)){
            $image[] = array('img' => "https://emersonmesso95.000webhostapp.com/_api/images/".$img['img_den']);
        }
        while($pref = mysqli_fetch_array($imgpref)){
            $image_pref[] = array('img' => "https://emersonmesso95.000webhostapp.com/_api/images/".$pref['imagem']);
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
        'img_pref' => $image_pref
    );
}
echo json_encode($denuncias);