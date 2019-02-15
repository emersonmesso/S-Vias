<?php require "./Conn.php"; ?>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cadastro - S-Vias</title>
        <link type = "image/png" rel="icon" href="_images/Imagem2.png" id="icone" />
        <link rel="stylesheet" type="text/css" href="_styles/login.css" />
        <style>
            html, body{
                height: 100%;
                width: 100%;
            }
        </style>
        <!-- Compiled and minified CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    </head>
    <body>
        
        
        <div class="container center-text">
            <div class="row">
                
                <div class="col s12 offset-s0">
                    <div class="card grey lighten-5 darken-1 z-depth-5">
                        <form method="post" action="">
                        <div class="card-content white-text">
                            <span class="card-title center">
                                Cadastro
                            </span>
                            
                            <!--Cadastro Instituiçao-->
                            
                                <div class="row">
                                    
                                    
                                    <div class="col s12">
                                        <!--Raxão Social e CNPJ-->
                                        <div class="row">
                                            <div class="input-field col s8">
                                                <input id="razao_social" type="text" name="razao" class="validate" required="required">
                                                <label for="razao_social">Razão Social</label>
                                            </div>

                                            <div class="input-field col s4">
                                                <input id="cnpj" type="text" name="cnpj" class="validate" required="required">
                                                <label for="cnpj">CNPJ</label>
                                            </div>
                                        </div>

                                        <!--RDados De Endereço-->
                                        <div class="row">
                                            <div class="input-field col s6">
                                                <input id="endereco" type="text" name="endereco" class="validate" required="required">
                                                <label for="endereco">Endereço</label>
                                            </div>

                                            <div class="input-field col s2">
                                                <input id="numero" type="number" name="numero" class="validate" required="required">
                                                <label for="numero">Nº</label>
                                            </div>
                                            <div class="input-field col s4">
                                                <input id="bairro" type="text" name="bairro" class="validate" required="required">
                                                <label for="bairro">Bairro</label>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="input-field col s6">
                                                <input id="cidade" type="text" name="cidade" class="validate" required="required">
                                                <label for="cidade">Cidade</label>
                                            </div>

                                            <div class="input-field col s2">
                                                <input id="cep" type="text" name="cep" class="validate" required="required">
                                                <label for="cep">CEP</label>
                                            </div>
                                            <div class="input-field col s4">
                                                <input name="uf" id="uf" type="text" class="validate" data-length="2" maxlength="2" required="required">
                                                <label for="uf">UF</label>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="" id="telefones">
                                        <div class="input-field col s4">
                                            <input id="telefone_1" name="telefone" type="text" class="validate" required="required">
                                            <label for="telefone_1">Telefone 1</label>
                                        </div>
                                    </div>
                                    <div class="col s12 b2">
                                        <!--<button type="button" id="btnAddTelefone" class="waves-effect waves-light btn-small">Adicionar <i class="material-icons right">add</i></button>-->
                                    </div>

                                    <div class="input-field col s12">
                                        <input name="email" id="email" type="email" class="validate" required="required">
                                        <label for="email">E-mail</label>
                                    </div>

                                    <div class="input-field col s12">
                                        <input name="senha" id="senha" type="password" class="validate" required="required">
                                        <label for="senha">Senha</label>
                                    </div>

                                </div>
                            
                            
                            <div class="container">
                                <!--Código PHP-->
                                <?php
                                require "./InstituicaoDAO.php";
                                require "./Instituicao.php";
                                if(isset($_POST['btnSubmitCadastro'])){
                                    //recebendo os dados
                                    $razao = htmlspecialchars($_POST['razao']);
                                    $cnpj = htmlspecialchars($_POST['cnpj']);
                                    $endereco = htmlspecialchars($_POST['endereco']);
                                    $numero = htmlspecialchars($_POST['numero']);
                                    $bairro = htmlspecialchars($_POST['bairro']);
                                    $cidade = htmlspecialchars($_POST['cidade']);
                                    $cep = htmlspecialchars($_POST['cep']);
                                    $uf = htmlspecialchars($_POST['uf']);
                                    $telefone = htmlspecialchars($_POST['telefone']);
                                    $email = htmlspecialchars($_POST['email']);
                                    $senha = htmlspecialchars($_POST['senha']);
                                    
                                    //inicia um objeto de instituição
                                    $instituicao = new Instituicao();
                                    $instituicao->set_email($email);
                                    $instituicao->set_r_social($razao);
                                    $instituicao->set_senha($senha);
                                    $instituicao->set_telefone($telefone);
                                    $instituicao->setEnd($endereco);
                                    $instituicao->setBairro($bairro);
                                    $instituicao->setCep($cep);
                                    $instituicao->setCidade($cidade);
                                    $instituicao->setCnpj($cnpj);
                                    $instituicao->setNum_ende($numero);
                                    $instituicao->setUf($uf);
                                    
                                    $processo = new InstituicaoDAO($conn);
                                    $processo->set_instituicao($instituicao);
                                    if($processo->insert_instituicao() != ""){
                                        
                                    }else{
                                        echo "Cadastro Efetuado Com Sucesso!";
                                    }
                                    
                                }
                                ?>
                                <!--Código PHP-->
                            </div>
                            
                        </div>
                        <div class="card-action center">
                            <button type="submit" name="btnSubmitCadastro" class="waves-effect waves-light btn"><i class="material-icons right">save</i>Concluir Cadastro</button>
                            <button type="reset" class="waves-effect red darken-4 btn"><i class="material-icons right">cancel</i>Cancelar</button>
                        </div>
                        </form>
                    </div>
                </div>
                
            </div>
        </div>
        
        
        
        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <script type="text/javascript" src="./_scripts/jquery.js"></script>
        <script type="text/javascript" src="./_scripts/form_cadastro.js"></script>
    </body>
</html>
