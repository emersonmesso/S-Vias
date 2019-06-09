<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

include '../../_config/functions.php';

$con = new Controller();

$termo = $_GET['termo'];

if ($con->searchDenuncia($termo) != NULL) {
    $denuncias = $con->searchDenuncia($termo);

    $cod = '<table id="tableview" class="table"><thead class="thead-dark"><tr><th scope="col">#</th><th scope="col">Título</th><th scope="col">Descrição</th><th scope="col">Status</th><th scope="col">Cep</th><th scope="col">Editar</th><th scope="col">Excluir</th></tr></thead>';


    foreach ($denuncias as $denuncia) {
        $cod .= PHP_EOL . '<tr>';
        $cod .= PHP_EOL . '<th scope="row">' . $denuncia["id_loc"] . '</th>';
        $cod .= PHP_EOL . '<td id="title-tt">' . $denuncia["titulo"] . '</td>';
        $cod .= PHP_EOL . '<td>' . utf8_encode($denuncia["descricao"]) . '</td>';

        switch ($denuncia["id_class"]) {
            case '1':
                $cod .= PHP_EOL . '<td>pendente</td>';
                break;
            case '2':
                $cod .= PHP_EOL . '<td>em andamento</td>';
                break;
            case '3':
                $cod .= PHP_EOL . '<td>concluído</td>';
                break;
            default :
                $cod .= PHP_EOL . '<td>erro!</td>';
                break;
        }

        $cod .= PHP_EOL . '<td>' . $denuncia["cep"] . '</td>';
        $cod .= PHP_EOL . '<td><button onclick="ativar();" name="btn-edit" style="cursor: pointer;" value=""><span class="glyphicon">&#x270f;</span></button></td>';
        $cod .= PHP_EOL . '</tr>';
    }
    $cod .= PHP_EOL . '</table>';

    echo $cod;
} else {
    echo 'nenhum dados encontrado!';
}

