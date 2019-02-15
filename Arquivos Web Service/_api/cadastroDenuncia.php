<?php
require './Controller.php';
$Controller = new Controller();

$email = $_POST['email'];
$nome = $_POST['nome'];
$desc = $_POST['desc'];
$lat = $_POST['lat'];
$lng = $_POST['lng'];
$cep = $_POST['cep'];
$img = $_POST['img'];

$dataHoje = time();

//adicionando os dados da denuncia no banco de dados
$sql = $Controller->getSql()->insere("denuncia", "email_cid, titulo, descricao, data, lati, lng, cep, type, id_class", "'$email', '$nome', '$desc', '$dataHoje', '$lat', '$lng', '$cep', 'denuncia', 1");

if($sql){
    //busacndo o id_loc do email
    $buscaID = $Controller->getSql()->select("denuncia", "*", "email_cid = '$email'", "id_loc DESC LIMIT 0,1");
    $retorno = mysqli_fetch_array($buscaID);
    $caminho = $Controller->salvarImagem($img);
    
    $sqll = $Controller->getSql()->insere("imagens", "id_loc, img_den", "'".$retorno['id_loc']."', '$caminho'");
    if($sqll){
        echo '';
    }else{
        echo "NÃO FOI POSSÍVEL ADICIONAR IMAGEM";
    }
    
}else{
    echo "NÃO FOI POSSÍVEL ADICIONAR DENÚNCIA";
}
