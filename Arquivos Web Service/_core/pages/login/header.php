<?php require "_core/_config/config.php";
$con->redirecionaSessao();
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>S-Vias</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../../_core/_css/login.css">
        
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
        <link rel="stylesheet" href="_styles/modals.css"/>
        <style>
            #logo {
                width: 25vw;
                padding-bottom: 5%;
            }
            #logo-img {
                width: 45px;
                height: 45px;
                float: left;
            }
            #logo>h1 {
                margin-top: 5%;
                font-size: 1em;
                float: left;
            }
            #item-search {
                float: right;
                margin-right: 10vw;
            }
            .item-p {
                display: inline-block;
            }
            main {
                background-color: rgb(245,245,245);
                margin-top: 2%;
                margin-bottom: 3%;
            }
            main>section {
                width: 85%;
                margin-left: 7%;
            }
            main>nav {
                margin-top: 5%;
                align-items: center;
                display: flex;
                flex-direction: row;
                flex-wrap: wrap;
                justify-content: center;
            }
            footer {
                top: 90vh;
                left: 0vw;
                position: fixed;
                display: block;
                background-color: darkgrey;
                padding-top: 1%;
                width: 100vw;
                height: 25vh;
            }
            footer>p {
                align-items: center;
                display: flex;
                flex-direction: row;
                flex-wrap: wrap;
                justify-content: center;
            }
            #modal-update, modal-confirm h1{
                padding-top: 5vh;
                color: rgb(140, 140, 140);
                font-size: 1.5em;
                font-weight: 700;
                text-align: center;
            }
            #form-atualizar, form-excluir {
                padding: 30px;
            }
        </style>

    </head>
    <body>
        <?php
        //Barra de navegação
        $con->navBarPage();