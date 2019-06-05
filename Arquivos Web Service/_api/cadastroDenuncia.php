<?php
require './Controller.php';
$Controller = new Controller();

$email = $_POST['email'];
$nome = utf8_encode( $_POST['nome'] );
$desc = utf8_encode ($_POST['desc'] );
$lat = $_POST['lat'];
$lng = $_POST['lng'];
$cep = $_POST['cep'];
$dadosRua = explode("," , $_POST['rua']);

$rua = utf8_encode( $dadosRua[0] . $dadosRua[1] );
$cidade = utf8_encode($dadosRua[2]);
$dataHoje = time();

//verifica se a imagem foi enviada
//as imagens são enviadas em array de strings

//adicionando os dados da denuncia no banco de dados
$sql = $Controller->getSql()->insere("denuncia", "email_cid, titulo, descricao, data, lati, lng, cep, rua, cidade, type, id_class", "'$email', '$nome', '$desc', '$dataHoje', '$lat', '$lng', '$cep', '$rua', '$cidade', 'denuncia', 1");
$buscaID = $Controller->getSql()->select("denuncia", "*", "email_cid = '$email'", "id_loc DESC LIMIT 0,1");
$retorno = mysqli_fetch_array($buscaID);
echo $retorno['id_loc'];