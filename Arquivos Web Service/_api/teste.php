<?php
require "./Controller.php";
$Controller = new Controller();
$dados = $Controller->getSql()->insere("denuncia", "email_cid, titulo, descricao, data, lati, lng, cep, type, id_class", "'emersonmessoribeiro@gmail.com', 'Ação', "
        . "'Descrição De Ação', '1550527031', '-8.073533', '-39.137722', '56000-000', 'denuncia', 1");

if($dados){
    echo 'Ok';
}else{
    echo "Erro";
}