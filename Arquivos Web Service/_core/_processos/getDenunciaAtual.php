<?php
require "../../_core/_config/functions.php";
$con = new Controller();

//retorno da página
$array = array();

//recebendo os dados
$email = $_POST['email'];
$cep = $_POST['cep'];

//buscando o nome da cidade
$sqCidade = $con->select("cidades", "*", "cep = '$cep'");
$cidade = mysqli_fetch_array($sqCidade);
$array['cidade'] = $cidade['nome'] . " - " . $cidade['uf'];

//buscando os dados
$sql = $con->select("denuncia", "*", "email_cid = '$email' AND cep = '$cep'");
if(mysqli_num_rows($sql) == 0){
    $array['total'] = 0;
}else{
    $array['total'] = mysqli_num_rows($sql);
    while ($denuncia = mysqli_fetch_array($sql)){
        $dados = $con->dadosDenuncia($denuncia['id_loc']);
        if($dados->getClass() == "Pendente"){
            $alert = "danger";
        }else if($dados->getClass() == "Concluído"){
            $alert = "success";
        }else{
            $alert = "warning";
        }
        $array['results'][] = array(
            'titulo' => utf8_decode ($dados->getTitulo()),
            'imagem' => $dados->getImagem(),
            'lati' => $dados->getLati(),
            'lng' => $dados->getLong(),
            'desc' => utf8_decode( $dados->getDesc() ),
            'cidade' => utf8_decode( $dados->getCidade() ),
            'cep' => $dados->getCep(),
            'rua' => utf8_decode( $dados->getRua() ),
            'data' => htmlspecialchars($dados->getData()),
            'situacao' => $dados->getClass(),
            'alert' => $alert,
            'id' => $denuncia['id_loc']
        );
    }
}
header('Content-type: application/json');
echo json_encode($array, JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);