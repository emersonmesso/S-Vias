<?php
require "../../_core/_config/functions.php";
$con = new Controller();
$id = $_POST['id'];
$dados = $con->dadosDenuncia($id);
$array = array();
$array[] = array(
    'titulo' => utf8_decode ($dados->getTitulo()),
    'imagem' => $dados->getImagem(),
    'lati' => $dados->getLati(),
    'lng' => $dados->getLong(),
    'desc' => utf8_decode( $dados->getDesc() ),
    'cidade' => utf8_decode( $dados->getCidade() ),
    'cep' => $dados->getCep(),
    'rua' => utf8_decode( $dados->getRua() ),
    'data' => htmlspecialchars($dados->getData()),
    'situacao' => $dados->getClass()
);
header('Content-type: application/json');
echo json_encode($array, JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE);