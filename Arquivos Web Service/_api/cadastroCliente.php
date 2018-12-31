<?php
require './Controller.php';
$sql = new SQL();

//requirindo os dados do aplicativo
if(!isset($_GET['email'])){
    echo 'EMAIL NÃO ENVIADO';
    exit;
}