<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2019-02-15.
 * Data de modificação: 2019-02-15.
 */

function invalido() {

    session_destroy(); //elimina a sessão criada

    echo '<script>'
    . 'alert("Dados inválido!"); '
    . 'window.location.assign("login.php");'
    . '</script>';
}

if (isset($_POST[' '])) {//verificar código de autenticação incluso
    include './Conn.php';
    include './CidadaoDAO.php';
    
     $termo = (!empty($_POST["search"])) ? htmlspecialchars($_POST["search"]) : "";
    
        if (strlen($termo) < 3) {
            echo "pesquisa inválido!";
            // header('Location: create_email.php');
        } else {
            invalido();
        }

       
        $dDao = new DenunciaDAO($conn);

       $result = $dDao->get_denuncia($termo);
       
        
}