<?php

include '../../_config/functions.php';

$con = new Controller();

//verifica a página atual caso seja informada na URL, senão atribui como 1ª página 
//$total = $con->countStatusDenuncia('1', '' . $instituicao->getCep() . '');
//
////seta a quantidade de itens por página, neste caso, 2 itens 
//$registros = 2;
//
////calcula o número de páginas arredondando o resultado para cima 
//$numPaginas = ceil($total / $registros);
//
////variavel para calcular o início da visualização com base na página atual 
//$inicio = ($registros * $pagina) - $registros;
//
//$denuncias = $con->statusDenunciaLimit('1', '' . $instituicao->getCep() . '', $inicio, $registros);
//
//
//echo PHP_EOL . '
//                 <table id="tableview" class="table">
//                   <thead class="thead-dark">
//                     <tr>
//                      <th scope="col">#</th>
//                      <th scope="col">Título</th>
//                      <th scope="col">Descrição</th>
//                      <th scope="col">Status</th>
//                      <th scope="col">Cep</th>
//                      <th scope="col">Editar</th>
//                      <th scope="col">Excluir</th>
//                     </tr>
//                   </thead>';
//
//$total = 0;
//
//foreach ($denuncias as $denuncia) {
//    echo PHP_EOL . '<tr>';
//    echo PHP_EOL . '<th scope="row">' . $denuncia["id_loc"] . '</th>';
//    echo PHP_EOL . '<td id="title-tt">' . $denuncia["titulo"] . '</td>';
//    echo PHP_EOL . '<td>' . utf8_encode($denuncia["descricao"]) . '</td>';
//
//    switch ($denuncia["id_class"]) {
//        case '1':
//            echo PHP_EOL . '<td>pendente</td>';
//            break;
//        case '2':
//            echo PHP_EOL . '<td>em progresso</td>';
//            break;
//        case '3':
//            echo PHP_EOL . '<td>concluído</td>';
//            break;
//        default :
//            echo PHP_EOL . '<td>erro!</td>';
//            break;
//    }
//
//    echo PHP_EOL . '<td>' . $denuncia["cep"] . '</td>';
//    echo PHP_EOL . '<td><button onclick="" name="btn-edit" style="cursor: pointer;" value=""><span class="glyphicon" onclick="">&#x270f;</span></button></td>';
//    echo PHP_EOL . '<td><button onclick="" name="btn" style="cursor: pointer;" value="">x</button></td>';
//    // <td><form method="post"><button name="exc" style="cursor: pointer;" value="$isbn" formaction="excluir_dados.php">x</button></form></td>"
//    echo PHP_EOL . '</tr>';
//
//    $total += 1;
//}
//echo PHP_EOL . '</table>';
//
//
//
////exibe a paginação
//if($pagina > 1) {
//    echo "<a href='../../../_core/pages/instituicao/cont.php?pagina=".($pagina - 1)."' class='controle'>&laquo; anterior</a>";
//}
// 
//for($i = 1; $i < $numPaginas + 1; $i++) {
//    $ativo = ($i == $pagina) ? 'numativo' : '';
//    echo "<a href='../../../_core/pages/instituicao/cont.php?pagina=".$i."' class='numero ".$ativo."'> ".$i." </a>";
//}
//     
//if($pagina < $numPaginas) {
//    echo "<a href='../../../_core/pages/instituicao/cont.php?pagina=".($pagina + 1)."' class='controle'>proximo &raquo;</a>";
//}    

$pagina = 1;

$status = (isset($_GET['status'])) ? $_GET['status'] : '-1';
$cep = $_GET['cep'] . '-000';

$total = $con->countStatusDenuncia($status, $cep);

//seta a quantidade de itens por página, neste caso, 2 itens 
$registros = 5;

//calcula o número de páginas arredondando o resultado para cima 
$numPaginas = ceil($total / $registros);

//variavel para calcular o início da visualização com base na página atual 
$inicio = ($registros * $pagina) - $registros;

$denuncias = $con->statusDenunciaLimit($status, $cep, $inicio, $registros);

$codd = '<nav style="margin-top: 5%; align-items: center; display: flex; flex-direction: row;  flex-wrap: wrap; justify-content: center;">'
        . PHP_EOL . '<ul class="pagination">'
        . PHP_EOL . '<li class="page-item disabled">'
        . PHP_EOL . '<a class="page-link" href="#" aria-label="Previous">'
        . PHP_EOL . '<span aria-hidden="true">&laquo;</span>'
        . PHP_EOL . '</a>'
        . PHP_EOL . '</li>';

for ($i = 1; $i < $numPaginas + 1; $i++) {
    $ativo = ($i == $pagina) ? 'numativo' : '';

    if ($pagina == $i) {
        $codd .= PHP_EOL . '<li class="page-item active">';
        $codd .= PHP_EOL . '<a class="page-link" href="?pagina=' . $i . '&status=' . $status . '">' . $i . '<span class="sr-only">(current)</span> </a>';
        $codd .= PHP_EOL . '</li>';
    } else {
        $codd .= PHP_EOL . '<li class="page-item">';
        $codd .= PHP_EOL . '<a class="page-link" href="?pagina=' . $i . '&status=' . $status . '">' . $i . '</a>';
        $codd .= PHP_EOL . '</li>';
    }
}
$codd .= PHP_EOL . '<li class="page-item">'
        . PHP_EOL . '<a class="page-link" href="#" aria-label="Next">'
        . PHP_EOL . '<span aria-hidden="true">&raquo;</span>'
        . PHP_EOL . '</a>'
        . PHP_EOL . '</li>'
        . PHP_EOL . '</ul>'
        . PHP_EOL . '</nav>';

echo $codd;
