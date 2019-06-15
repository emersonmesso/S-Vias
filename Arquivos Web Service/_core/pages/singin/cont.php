<div style="height: 90vh; padding-top: 5vh;" class="container">
    <!-- Select Login S-Vias | Google  -->
    <div class="row" style="padding-top: 5vh;">
        <div class="col-sm-2"></div>
        <div id="login1" class="col-sm-8">
            <form id="formAdmin" method="POST" action="">
                <div class="form-group">
                    <label for="">Usuário</label>
                    <input type="email" class="form-control-lg w-100" id="campoUser" name="email">
                </div>
                
                <div class="form-group">
                    <label for="">Senha</label>
                    <input type="password" class="form-control-lg w-100" placeholder="Senha" id="campoSenha" name="password">
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