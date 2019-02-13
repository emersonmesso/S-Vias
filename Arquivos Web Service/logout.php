<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2019-02-13.
 * Data de modificação: 2019-02-13.
 */

session_start(); //inicia a sessão

unset($_SESSION['active']); //elimina a variável da sessão
unset($_SESSION['type']); //elimina a variável da sessão
unset($_SESSION['login']); //elimina a variável da sessão

session_destroy(); //elimina a sessão do usuário

header("location: index.php"); //redireciona para a index do site
