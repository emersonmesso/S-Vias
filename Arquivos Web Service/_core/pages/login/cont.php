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

    <!-- Select Login S-Vias | Google  -->
    <div class="row" style="padding-top: 5vh;">
        <div class="col-sm-2"></div>
        <div id="login1" class="col-sm-8">
            <form id="formLogin" method="POST" action="">
                <div class="form-group">
                    <label for="">Email</label>
                    <input type="email" class="form-control-lg w-100" placeholder="E-mail" id="campoEmail" name="email">
                </div>
                
                <div class="form-group">
                    <label for="">Senha</label>
                    <input type="password" class="form-control-lg w-100" placeholder="Senha" id="campoSenha" name="password">
                </div>
                
                <div class="form-check">
                    <label for="">Acessar como:</label>
                    <br />
                    <input type="radio" id="checkin" id="chek" name="acesso" value="2">
                    <label for="checkin">Instituição</label>
                    <br />
                    <input type="radio" id="checkci" id="chek" name="acesso" value="1">
                    <label for="checkci">Cidadão</label>
                </div>
                <div class="form-group mt-3">
                    <button type="submit" value="btnEntrar" class="btn btn-block btn-warning">Entrar</button>
                </div>
            </form>
            
            <!---->
            <div id="infoLogin">
                
            </div>
            
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