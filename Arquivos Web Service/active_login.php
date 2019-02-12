<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2019-02-11.
 */

if (isset($_POST['env'])) {//verificar código de autenticação incluso
    $email_n = (!empty($_POST["email_n"])) ? htmlspecialchars($_POST["email_n"]) : "";
    $pass_n = (!empty($_POST["pass_n"])) ? htmlspecialchars($_POST["pass_n"]) : "";

    if (strlen($email_n) < 3) {
        echo "email inválido!";
        // header('Location: create_email.php');
    }

    if (strlen($pass_n) < 3) {
        echo "senha inválida!";
        //header('Location: create_email.php');
    }
}

include './Conn.php';
include './Cidadao.php';
include './CidadaoDAO.php';

if ($_POST['select-l'] === "Cid") {

    $cidadao = new Cidadao();
    $cDao = new CidadaoDAO($conn);

   $cidadao->set_email($email_n);
    $cidadao->set_senha($pass_n);

    $cDao->set_cidadao($cidadao);
    
    if($cDao->get_cidadao() == null) {
        echo "null";
    }

    if ($cDao->is_cidadao()) {
       header('Location: newEmptyPHP.php');
    } else {
        header('Location: login.php');
    }
    
} /*else if (isset($_POST['select-l']) === "Inst") {

    $instituicao = new Instituicao();

    $cidadao->set_email($email_n);
    $cidadao->set_senha($pass_n);

    $cDao = new InstituicaoDAO($conn);

    if ($cDao->is_instituicao()) {
        //acesso
    } else {
        //não acesso
    }
} else {
    echo "erro!<br/>";
    //header('Location: create_email.php');
}
*/




