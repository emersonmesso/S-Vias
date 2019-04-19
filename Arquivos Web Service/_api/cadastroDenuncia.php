<?php
require './Controller.php';
$Controller = new Controller();

$email = $_POST['email'];
$nome = utf8_encode( $_POST['nome'] );
$desc = utf8_encode ($_POST['desc'] );
$lat = $_POST['lat'];
$lng = $_POST['lng'];
$cep = $_POST['cep'];
$rua = utf8_encode ($_POST['rua'] );
$cidade = utf8_encode ($_POST['cidade'] );
$dataHoje = time();

$rua = utf8_encode( $rua . " " . $_POST['bairro'] );


//verifica se a imagem foi enviada
if($_POST['img'] != "img"){
    //Envia com a imagem
    $img = $_POST['img'];
    
    //adicionando os dados da denuncia no banco de dados
    $sql = $Controller->getSql()->insere("denuncia", "email_cid, titulo, descricao, data, lati, lng, cep, rua, cidade, type, id_class", "'$email', '$nome', '$desc', '$dataHoje', '$lat', '$lng', '$cep', '$rua', '$cidade', 'denuncia', 1");
    
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

}else{
    //adicionando os dados da denuncia no banco de dados
    $sql = $Controller->getSql()->insere("denuncia", "email_cid, titulo, descricao, data, lati, lng, cep, rua, cidade, type, id_class", "'$email', '$nome', '$desc', '$dataHoje', '$lat', '$lng', '$cep', '$rua', '$cidade', 'denuncia', 1"); 
    if($sql){
        echo '';
    }else{
        echo "NÃO FOI POSSÍVEL ADICIONAR DENÚNCIA";
    }
}