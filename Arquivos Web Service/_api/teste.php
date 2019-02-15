<?php
require "./Controller.php";
$Controller = new Controller();

$dados = $Controller->getSql()->select("cidades");

echo mysqli_num_rows($dados);