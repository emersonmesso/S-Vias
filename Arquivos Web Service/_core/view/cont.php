<?php
require "_core/_config/config.php";
$Controller = new Controller();
?>
<div class="container-fluid mt-5 pt-5" style="min-height: 100%; background-color: #FACC2E;">
    
    <div class="container">
        <div class="row">

            <div class="col-md-4">

                <div class="container-fluid w-100 text-center" id="carrousel">
                    <?php  echo $Controller->Carrousel()?>
                </div>
            </div>

            <!---->
            <div class="col-md-8 p-5">
                <h1 style="font-size: 50px">Sua denúncia é importante para nós!</h1>
                <br />
                <p class="" style="font-size: 35px;" id="">Começe a utilizar o <strong>S-Vias</strong> agora mesmo!</p>
                <div class="row mb-5">
                    
                    <div class="col-md-6 text-center">
                        <a href="<?php echo $Controller->gerLinkPastas() ?>releases"><img src="../../_core/_img/playStore.png" width="90%" /></a>
                        <br />
                        <a class="text-dark" href="<?php echo $Controller->gerLinkPastas() ?>releases"><em><strong>Download .APK</strong></em></a>  
                    </div>
                    <div class="col-md-6">
                        
                    </div>
                    
                </div>
                <p class="" style="font-size: 30px;" id="">Se você é pessoa jurídica, cadastre-se agora mesmo!</p>
                <a href="../cadastro" class="btn btn-dark btn-lg"> Cadastre-se</a>
                
            </div>
        </div>
    </div>
</div>

<!---->
<div class="container-fluid mt-5 pt-5">
    <div class="container p-2">
        <h2 class="text-center"><img src="_core/_img/logoApp.png" class="rounded" width="40" alt="Logo Aplicativo" title="Logo aplicativo SVias" />Sobre S-Vias</h2>

        <p>S-vias não é nada mais do que um aplicativo que visa fiscalizar os serviços da prefeitura em relação às vias públicas 
            da sua cidade. É muito fácil de usar. É só ligar o seu GPS, marcar o local que está com problemas, tirar 
            uma foto e enviar sua denúncia com nome e descrição. A prefeitura será notificada e quando resolver o problema indicara
            no aplicativo que tudo foi resolvido, podendo postar, inclusive, fotos da obra concluída e promover suas atividades.</p>

        <p><b>Observação: aplicativo só funciona para cidades cadastradas.</b></p>

        <p>Utilizar o S-vias é um serviço de cidadania para com sua cidade. Cobre da sua prefeitura para contratar o serviço já e baixe nosso aplicativo.</p>
    </div>


    <div class="container text-center mt-5 pt-5">
        <h2 class="">Como Funciona?</h2>

        <div class="card d-inline-flex mr-2" style="width: 20em;">
            <div class="card-body">
                <img src="_core/_img/cards/img7.jpeg" alt="" title="" class="card-img-top" />
                <h4 class="card-title p-4">Cadastro</h5>
                    <div class="card-text p-2">
                        <p>Realize o seu cadastro como cidadão pelo aplicativo S-vias!</p>
                    </div>
                <div class="card-footer">
                    <a href="" class="btn btn-block btn-dark">Baixar Aplicativo</a>
                </div>
            </div>
        </div>

        <!---->
        <div class="card d-inline-flex mr-2" style="width: 20em;">
            <div class="card-body">
                <img src="_core/_img/cards/img2.jpeg" alt="" title="" class="card-img-top" />
                <h4 class="card-title p-4">Adicionar Denúncia</h5>
                    <div class="card-text p-2">
                        <p>Adicione uma denúncia utilizando o botão inferir da tela!</p>
                    </div>
            </div>
        </div>

        <!---->
        <div class="card d-inline-flex mr-2" style="width: 20em;">
            <div class="card-body">
                <img src="_core/_img/carrousel/img10.jpeg" alt="" title="" class="card-img-top" />
                <h4 class="card-title p-4">Visualizar Denúncia</h5>
                    <div class="card-text p-2">
                        <p>Clique nos ícones do mapa e visualize a denúncia!</p>
                    </div>
            </div>
        </div>
        
    </div>

</div>

<!---->
<div class="container-fluid mt-4 p-3 mb-5 bg-white" id="telaContatoLocal">
    <div class="row p-3">
        
        <div class="col-md-6">
            <h2>Fale Conosco!</h2>
            
            <form method="post" action="" id="formContato">
                <div class="form-group">
                    <label>Seu Nome: <b style="color: red;">*</b></label>
                    <input type="text" placeholder="Nome" id="formNome" required class="form-control">
                    <br />
                    <label>Seu E-mail: <b style="color: red;">*</b></label>
                    <input type="email" required class="form-control" id="formEmail" placeholder="Email">
                </div>
                
                <div class="form-group">
                    <label>Sua Mensagem: <b style="color: red;">*</b></label>
                    <textarea class="form-control" rows="3" required id="formMensagem" placeholder="Sua Mensagem!"></textarea>
                </div>
                
                <div class="form-group">
                    <button type="submit" class="btn btn-block btn-lg" style="background-color: #FACC2E;">Enviar</button>
                </div>
            </form>
            
            <div class="container-fluid w-100" id="alertContato">
                
                
            </div>
            
            
        </div>
        
        <div class="col-md-6">
            <h2 class="text-center">Onde Estamos!</h2>
            <div id="mapa">
                <iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d15801.26875863661!2d-39.1179405!3d-8.0690944!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x7a093c32746f1cb%3A0x472d7fced615961c!2sInstituto+Federal+de+Educa%C3%A7%C3%A3o%2C+Ci%C3%AAncia+e+Tecnologia+do+Sert%C3%A3o+de+Pernambuco.!5e0!3m2!1spt-BR!2sbr!4v1549931386983" width="100%" height="500" frameborder="0" style="border:0" allowfullscreen></iframe>
            </div>
        </div>
        
    </div>
    
    <div class="container-fluid w-100 bg-white text-center">
        &copy; <?php echo date("Y") ?> | Desenvolvido por <a href="" class="badge badge-light">S-Dea</a>
    </div>
</div>