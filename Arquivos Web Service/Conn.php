<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2018-12-21.
 */

$servidor = '';
$user = '';
$pass = '';
$database = '';

global $conn;

$conn = mysqli_connect($servidor, $user, $pass, $database);

if (mysqli_connect_errno()) {
    echo "Falha ao conectar com o MySQL: " . mysqli_connect_error();
}