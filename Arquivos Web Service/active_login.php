<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-21.
 * Data de modificação: 2019-02-13.
 */

session_start(); //inicia a sessão;

function invalido() {

    session_destroy(); //elimina a sessão criada

    echo '<script>'
    . 'alert("Dados inválido!"); '
    . 'window.location.assign("login.php");'
    . '</script>';
}

if (isset($_POST['env'])) {//verificar código de autenticação incluso
    include './Conn.php';
    include './Cidadao.php';
    include './CidadaoDAO.php';

    $type = (!empty(isset($_POST['select-l']))) ? $_POST['select-l'] : "";

    if ($type === "Cid") {

        $email_n = (!empty($_POST["email_n1"])) ? htmlspecialchars($_POST["email_n1"]) : "";
        $pass_n = (!empty($_POST["pass_n1"])) ? htmlspecialchars($_POST["pass_n1"]) : "";

        if (strlen($email_n) < 3) {
            echo "email inválido!";
            // header('Location: create_email.php');
        }

        if (strlen($pass_n) < 3) {
            echo "senha inválida!";
            //header('Location: create_email.php');
        }

        $cidadao = new Cidadao();
        $cDao = new CidadaoDAO($conn);

        $cidadao->set_email($email_n);
        $cidadao->set_senha($pass_n);

        $cDao->set_cidadao($cidadao);

        if ($cDao->is_cidadao()) {
            $_SESSION['active'] = true; //ativa a sessão do usuário
            $_SESSION['type'] = $type; //tipo do usuário
            $_SESSION['login'] = $email_n; //tipo do usuário

            header("location: newEmptyPHP.php"); //página do usuário
        } else {
            session_destroy(); //elimina a sessão criada
            invalido();
        }
    } else if ($type === "Inst") {

        $email_n = (!empty($_POST["email_n2"])) ? htmlspecialchars($_POST["email_n2"]) : "";
        $pass_n = (!empty($_POST["pass_n2"])) ? htmlspecialchars($_POST["pass_n2"]) : "";

        if (strlen($email_n) < 3) {
            echo "email inválido!";
            // header('Location: create_email.php');
        }

        if (strlen($pass_n) < 3) {
            echo "senha inválida!";
            //header('Location: create_email.php');
        }

        $instituicao = new Instituicao();
        $iDao = new InstituicaoDAO($conn);

        $instituicao->set_email($email_n);
        $instituicao->set_senha($pass_n);

        $iDao->set_instituicao($instituicao);

        if ($iDao->is_instituicao()) {
            $_SESSION['active'] = true; //ativa a sessão do usuário
            $_SESSION['type'] = $type; //tipo do usuário
            $_SESSION['login'] = $email_n; //tipo do usuário

            header("location: newEmptyPHP.php"); //página do usuário
        } else {
            invalido();
        }
    } else {
        invalido();
    }
} else {
    invalido();
}
