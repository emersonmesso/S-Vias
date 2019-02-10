<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2018-12-21.
 */

if (isset($_POST['env'])) {//verificar código de autenticação incluso
    $user = (!empty($_POST["nome_login_n"])) ? htmlspecialchars($_POST["nome_login_n"]) : "";
    $pass = (!empty($_POST["pass_login_n"])) ? htmlspecialchars($_POST["pass_login_n"]) : "";

    if (strlen($user) < 3) {
        header('Location: create_login.php');
    }

    if (strlen($pass) < 3) {
        header('Location: create_login.php');
    }
}

include './Conn.php';

if (isset($_POST['select-l']) === "Cid") {

    $cidadao = new Cidadao();

    $cidadao->set_login($login);
    $cidadao->set_senha($senha);

    $cDao = new CidadaoDAO($conn);

    if ($cDao->is_cidadao()) {
        //acesso
    } else {
        //não acesso
    }
} else if (isset($_POST['select-l']) === "Inst") {
    
    $instituicao = new Instituicao();

    $instituicao->set_login($login);
    $instituicao->set_senha($senha);

    $cDao = new InstituicaoDAO($conn);

    if ($cDao->is_instituicao()) {
        //acesso
    } else {
        //não acesso
    }
    
} else {
    header('Location: create_login.php');
}





