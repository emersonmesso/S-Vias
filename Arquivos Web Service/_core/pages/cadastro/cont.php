<div style="height: 90vh; padding-top: 5vh;" class="container">
    <div class="container">
        <h3>Entre em contato para saber como cadastrar a cidade que você administra!</h3>
        <form method="post" action="" id="formCadastro">
            <div class="form-group">
                <input type="email" name="emailIns" class="form-control col-sm-8" placeholder="Digite o seu E-mail">
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn btn-success">Entrar Em Contato</button>
            </div>
        </form>
        <?php
        $Controller = new Controller();
        if(isset($_POST['emailIns'])){
            $email = $_POST['emailIns'];
            $Controller->emailContatoIns($email);
        }
        ?>
        
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

