<?php

include '../../_config/functions.php';

$con = new Controller();

//update($tabela, $valores, $where)

$cod = (isset($_GET['cod'])) ? $_GET['cod'] : '';
$status = (isset($_GET['status'])) ? $_GET['status'] : -1;
$foto = (isset($_GET['status'])) ? $_GET['status'] : null;

//UPDATE `denuncia` SET `titulo` = 'lembrança voam' WHERE `denuncia`.`id_loc` = 87;

$con->update("denuncia", '`id_class`=' . $status , 'id_loc = ' . $cod);

echo 'Modificado!';