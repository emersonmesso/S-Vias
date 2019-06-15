<?php require "_core/_config/config.php";
$Controller = new Controller();
$releases = array(
    'location' => "../../_core/releases/apk.apk",
    'name' => "S-Vias",
    'version' => "1.0",
    'tamanho' => "6.0 MB"
);
?>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="shortcut icon" type="imagem/x-icon" href="<?php echo $Controller->gerLinkPastas() ?>_core/_img/icon.ico"/>
        <title>Versões S-Vias</title>
        <link rel="stylesheet" type="text/css" href="<?php echo $Controller->gerLinkPastas() ?>_core/_css/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300" rel="stylesheet">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="<?php echo $Controller->gerLinkPastas() ?>_core/_css/_reset.css">
        <link rel="stylesheet" type="text/css" href="<?php echo $Controller->gerLinkPastas() ?>_core/_css/denuncias.css">
    </head>
    <body>
        <?php
        //Barra de navegação
        $Controller->navBarPage();
        ?>
        <div class="container mt-5 bg-white rounded p-2" style="font-size: 25px;">
            <h1>Versão Atual Do Aplicativo S-Vias(<?php echo $releases['version'] ?>)</h1>
            
            <a href="../../releases/download" class="btn btn-warning text-white">Baixar</a>
            <br />
            <hr />
            <h4>Requisitos Mínimos</h4>
            <b>Sistema Operacional:</b> Android
            <br />
            <b>Versão Do Android:</b> 5.0 ou Superior
            
            <br />
            <b>Tamanho: </b><?php echo $releases['tamanho'] ?>
            
        </div>
    </body>
</html>
