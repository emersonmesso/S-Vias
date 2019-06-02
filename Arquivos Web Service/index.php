<?php
require_once './_core/_config/functions.php';
$con = new Controller();
$con->inicia();
 
//Mostrando todos os erro do PHP
//ini_set('display_errors',1);
//ini_set('display_startup_erros',1);
//error_reporting(E_ALL);

//Header
$con->header();

//Conteudo
$con->conteudo();

//Footer
$con->footer();