<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

include '../../_config/functions.php';

$con = new Controller();

$text = $_GET['text'];

$cep = $_GET['cep'] . '-000';

$pagina = 1;
$total = $con->countStatusDenuncia($text, $cep);

//seta a quantidade de itens por página, neste caso, 2 itens 
$registros = 5;

//calcula o número de páginas arredondando o resultado para cima 
$numPaginas = ceil($total / $registros);

//variavel para calcular o início da visualização com base na página atual 
$inicio = ($registros * $pagina) - $registros;

if ($con->statusDenunciaLimit($text, $cep, $inicio, $registros) != NULL) {
    $denuncias = $con->statusDenunciaLimit($text, $cep, $inicio, $registros);

    $cod = '<table class="table"><thead class="thead-dark"><tr><th scope="col">#</th><th scope="col">Título</th><th scope="col">Descrição</th><th scope="col">Status</th><th scope="col">Cep</th><th scope="col">Editar</th></tr></thead>';


    foreach ($denuncias as $denuncia) {
        $cod .= PHP_EOL . '<tr>';
        $cod .= PHP_EOL . '<th scope="row">' . $denuncia["id_loc"] . '</th>';
        $cod .= PHP_EOL . '<td id="title-tt">' . $denuncia["titulo"] . '</td>';
        $cod .= PHP_EOL . '<td>' . utf8_encode($denuncia["descricao"]) . '</td>';

        $attr = '';

        switch ($denuncia["id_class"]) {
            case '1':
                $cod .= PHP_EOL . '<td>pendente</td>';
                $cod .= PHP_EOL . '<td>' . $denuncia["cep"] . '</td>';
                $cod .= PHP_EOL . '<td><button onclick="ativar();" name="btn-edit" style="cursor: pointer;" value=""><span class="glyphicon">&#x270f;</span></button></td>';
                $cod .= PHP_EOL . '</tr>';
                break;
            case '2':
                $cod .= PHP_EOL . '<td>em andamento</td>';
                $cod .= PHP_EOL . '<td>' . $denuncia["cep"] . '</td>';
                $cod .= PHP_EOL . '<td><button onclick="ativar();" name="btn-edit" style="cursor: pointer;" value=""><span class="glyphicon">&#x270f;</span></button></td>';
                $cod .= PHP_EOL . '</tr>';
                break;
            case '3':
                $cod .= PHP_EOL . '<td>concluído</td>';
                $cod .= PHP_EOL . '<td>' . $denuncia["cep"] . '</td>';
                $cod .= PHP_EOL . '<td><button onclick="ativar();" name="btn-edit" style="cursor: pointer;" disabled value=""><span class="glyphicon">&#10003;</span></button></td>';
                $cod .= PHP_EOL . '</tr>';
                break;
            default :
                $cod .= PHP_EOL . '<td>erro!</td>';
                break;
        }
    }
    $cod .= PHP_EOL . '</table>';

    echo $cod;
} else {
    echo 'nenhum dados encontrado!';
}