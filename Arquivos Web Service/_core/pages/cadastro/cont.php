<div style="height: 90vh; padding-top: 5vh;" class="container">
    <div class="row justify-content-center">
        <div class="col col-auto">
            <a class="navbar-brand" href="index.php" style="">
                <span id="" style="font-size: 2em; color: #FEDC00;font-family: 'Arial Black', Gadget, sans-serif;">
                    S-Vias<img src = "../../_core/_img/logoApp.png" class="logo" />
                </span>
            </a>
        </div>
    </div>

    <div class="row" style="padding-top: 5vh;">
        <div class="col-sm-2"></div>
        <div id="cad" class="col-sm-8">
            <div>
                <?php
                if(isset($_POST['btnEnviar'])){
                    require "_core/_config/config.php";
                    //recebendo os dados do formulário
                    $razao = $_POST['razao_social'];
                    $cnpj = $_POST['cnpj'];
                    $email = $_POST['email'];
                    $password = md5($_POST['password']);
                    $endereco = $_POST['endereco'];
                    $cidade = $_POST['cidade'];
                    $uf = $_POST['uf'];
                    $cep = $_POST['cep'];
                    $bairro = $_POST['bairro'];
                    
                    if($con->validaCNPJ($cnpj)){
                        
                        //verifica se já existe o cadastro
                        $sqlVerifica = $con->select("instituicao", "*", "cnpj = '$cnpj' AND email = '$email'");
                        if(mysqli_num_rows($sqlVerifica) == 0){
                            $sqlCadastro = $con->insere("instituicao", "email, razao_social, cnpj, endereco, bairro, cidade, uf, cep, senha", "'$email','$razao','$cnpj','$endereco','$bairro','$cidade','$uf','$cep','$password'");
                            if($sqlCadastro){
                                echo '<div class="alert alert-success">';
                                    echo 'CADASTRO EFETUADO COM SUCESSO!';
                                echo '</div>';
                            }else{
                                echo '<div class="alert alert-danger">';
                                    echo 'NÃO FOI POSSÍVEL CADASTRAR!';
                                echo '</div>';
                            }
                            
                        }else{
                            
                            echo '<div class="alert alert-danger">';
                                echo 'INSTITUIÇÃO JÁ CADASTRADA!';
                            echo '</div>';
                            
                        }
                        
                    }else{
                        echo '<div class="alert alert-danger">';
                            echo 'CNPJ INVÁLIDO!';
                        echo '</div>';
                    }
                }

                ?>
            </div>
            
            <form id="formCadastro" method="POST" action="">
                <i class="text-danger">Todos os campos são obrigatórios!</i>
                <div class = "form-row">
                    <div class="form-group col-sm-6">
                        <label for="">Nome: </label>
                        <input type="text" class="form-control" required placeholder="Nome" id="campoNome" name="razao_social">
                    </div>
                    <div class="form-group col-sm-6">
                        <label for="">CNPJ: </label>
                        <input type="text" class="form-control" required placeholder="CNPJ" id="campoNome" name="cnpj">
                    </div>
                </div>
                <div class = "form-row">
                    <div class="form-group col-md-6">
                        <label for="">Email</label>
                        <input type="email" class="form-control" required placeholder="E-mail" id="campoEmail" name="email">
                    </div>
                
                    <div class="form-group col-md-6">
                        <label for="">Senha</label>
                        <input type="password" class="form-control" required placeholder="Senha" id="campoSenha" name="password">
                    </div>
                </div>
                <div class="form-group">
                    <label for="">Endereço: </label>
                    <input type="text" class="form-control" required placeholder="Endereço" id="campoEndereco" name="endereco">
                </div>
                
                <div class="form-group">
                    <label for="">Bairro: </label>
                    <input type="text" class="form-control col-md-6" required placeholder="Bairro" id="campoEndereco" name="bairro">
                </div>
                
                <div class = "form-row">
                    <div class="form-group col-md-4">
                        <label for="">Cidade: </label>
                        <input type="text" class="form-control" required placeholder="Cidade" id="campoCidade" name="cidade">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="">UF: </label>
                        <input type="text" class="form-control col-md-6" required placeholder="UF" size="2" minlength="2" maxlength="2" id="campoCidade" name="uf">
                    </div>
                    
                    
                    <div class="form-group col-md-4">
                        <label for="">CEP: </label>
                        <input type="text" class="form-control col-md-12" required placeholder="CEP" minlength="9" maxlength="9" id="campoCep" name="cep" onkeypress="mascara(this, '#####-###')">
                    </div>
                </div>
                

                <div class="form-group mt-3">
                    <button type="submit" value="btnEntrar" name="btnEnviar" class="btn btn-block btn-warning">Cadastrar</button>
                </div>
            </form>
            
            <!---->
            
            
            <!---->
            
        </div>
        <div class="col-sm-2"></div>
    </div>
</div>
<!-- footer site s-vias  -->
<footer>
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <div class="col border p-2">
                <p>S-Vias <?php echo date("Y") ?> | S-Dea</p>
            </div>
        </div>
        <hr/>
        <div class="col-sm-2"></div>
    </div>

</footer>

