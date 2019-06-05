<?php require "_core/_config/config.php";

?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cadastro S-Vias</title>
        <meta name="description" content="Cadastre a sua instituição no sistema S-Vias agora mesmo!">
        <meta name="keywords" content="S-vias, Buraco, IF, SDea,adastro, Instituição">
        <meta name="author" content="S Dea">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" type="imagem/x-icon" href="../../_core/_img/icon.ico"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../../_core/_css/login.css">
    </head>
    <body>
        <?php
        //Barra de navegação
        $con->navBarPage();

