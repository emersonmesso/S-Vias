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
            
            <form id="formCadastro" method="POST" action="">
                <div class = "form-row">
                    <div class="form-group col-sm-6">
                        <label for="">Nome: </label>
                        <input type="text" class="form-control" placeholder="Nome" id="campoNome" name="razao_social">
                    </div>
                    <div class="form-group col-sm-6">
                        <label for="">CNPJ: </label>
                        <input type="text" class="form-control" placeholder="CNPJ" id="campoNome" name="cnpj">
                    </div>
                </div>
                <div class = "form-row">
                    <div class="form-group col-md-6">
                        <label for="">Email</label>
                        <input type="email" class="form-control" placeholder="E-mail" id="campoEmail" name="email">
                    </div>
                
                    <div class="form-group col-md-6">
                        <label for="">Senha</label>
                        <input type="password" class="form-control" placeholder="Senha" id="campoSenha" name="password">
                    </div>
                </div>
                <div class="form-group">
                    <label for="">Endereço: </label>
                    <input type="text" class="form-control" placeholder="Endereço" id="campoEndereco" name="endereco">
                </div>
                <div class = "form-row">
                     <div class="form-group col-md-6">
                        <label for="">Cidade: </label>
                        <input type="text" class="form-control col-md-6" placeholder="Cidade" id="campoCidade" name="cidade">
                    </div>
                    <div class="form-group">
                        <label for="">CEP: </label>
                        <input type="text" class="form-control col-md-6" placeholder="CEP" id="campoCep" name="cep">
                    </div>
                </div>
                

                <div class="form-group mt-3">
                    <button type="submit" value="btnEntrar" class="btn btn-block btn-warning">Cadastrar</button>
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

