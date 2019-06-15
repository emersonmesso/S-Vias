<?php
$con = new Controller();
if(!$con->verificaSessao()){
    echo "Sair";
    header("Location: ../login");
    echo '<script>window.location.href="../login";</script>';
}
//busca as informações do usuário
$cidadao = $con->dadosCidadao();
?>
<div class="wrapper">
    <!-- Sidebar  -->
    <nav id="sidebar">
        <div class="sidebar-header">
            <h3><a href="../index.php"><i class="fas fa-arrow-left"></i> S-Vias</a></h3>
        </div>

        <ul class="list-unstyled components">
            <p>Bem Vindo!
            <br />
            <?php echo $cidadao->getNome() ?></p>
            <li>
                <a href="<?php echo $con->gerLinkPastas() ?>cidadao/me">Meus Dados</a>
            </li>
            <li>
                <a href="../cidadao">Minhas Denúncias</a>
            </li>

            <li class="active">
                <a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Cidades</a>
                <ul class="collapse list-unstyled" id="homeSubmenu">
                    <?php $con->cidadesDenuncia($cidadao->getEmail()); ?>

                </ul>
            </li>
            <li>
                <a href="<?php echo $con->gerLinkPastas() ?>denuncias">Mapa Denúncias</a>
            </li>
            
        </ul>
    </nav>

    <!-- Page Content  -->
    <div id="content">

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">

                <button type="button" id="sidebarCollapse" style="background-color: #FACC2E;" class="btn btn-warning">
                    <i class="fas fa-align-left"></i>
                </button>
                
                <button class="btn btn-warning" id="btnLogout" type="button">
                    <i class="fas fa-times-circle" style="font-size: 20px;"></i>
                </button>
                
            </div>
        </nav>
        <!--Variáveis do Jquery-->
        <b id="lat" lang=""></b>
        <b id="lng" lang=""></b>
        <b id="emailUser" lang="<?php echo $cidadao->getEmail(); ?>"></b>
        <?php
        if(isset($con->url[1]) && $con->url[1] == "me"){
        ?>
        <!--MOSTRANDO OS DADOS CADASTRAIS DO CIDADÃO-->
        <h1>Meus Dados</h1>
        <p>Os seus dados pessoais foram capturados utilizando o aplicativo S-Vias</p>
        <form method="post" action="">
            <div class="form-group">
                <label for="">E-mail:</label>
                <input type="email" disabled class="form-control col-sm-5" value="<?php echo $cidadao->getEmail(); ?>">
            </div>
            <div class="form-group">
                <label for="">CPF:</label>
                <input type="text" disabled class="form-control col-sm-3" value="<?php echo $cidadao->getCpf(); ?>">
            </div>
            <div class="form-group">
                <label for="">Nome:</label>
                <input type="text" class="form-control col-sm-6" value="<?php echo $cidadao->getNome(); ?>">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-success" title="Salvar Informações"><i class="fas fa-save"></i> Salvar</button>
            </div>
        </form>
        
        <form method="post" action="">
            <div class="form-group">
                <h3>Alterar Senha</h3>
                <label for="">Senha Atual:</label>
                <input type="password" class="form-control col-sm-4">
                <label for="">Nova Senha:</label>
                <input type="password" class="form-control col-sm-4">
                <label for="">Confirmação Nova Senha:</label>
                <input type="password" class="form-control col-sm-4">
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-success" title="Salvar Informações"><i class="fas fa-save"></i> Salvar</button>
            </div>
            
            
        </form>
        
        
        <!--MOSTRANDO OS DADOS CADASTRAIS DO CIDADÃO-->
        <?php
            
        }else{
        ?>
        <!--Mostra as denúncias-->
        <div class="container-fluid bg-light navbar">
            <h1><i id="nomeCidade"></i></h1>
            <div class="w-100 text-center border border-light rounded">
                <button class="btn btn-danger" type="button" id="btnPendentes"><i class="fas fa-clock"></i> Pendente</button>
                <button class="btn btn-warning text-white" type="button" id="btnProgresso"><i class="fas fa-tasks"></i> Em Progresso</button>
                <button class="btn btn-success" type="button" id="btnConcluido"><i class="fas fa-check-circle"></i> Concluído</button>
            </div>
            <div class="w-100 p-2 rounded text-center" id="denuncias">
                <img src="../../_core/_img/load.gif" width="5%" />
            </div>

        </div>
        
        <?php } ?>

    </div>
</div>

<!--MODAL INFO-->
<button type="button" class="btn btn-primary" id="modalDen" data-toggle="modal" data-target=".bd-example-modal-info">Large modal</button>
<div class="modal fade bd-example-modal-info" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center" id="modalName"></h3>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row">
                    
                    <div class="col-lg-5 p-3 text-center" id="imagemDenuncia">
                        <h3>Antes</h3>
                        
                        <div id="carouselControls" class="carousel slide" data-ride="carousel">
                            <div class="carousel-inner" id="img">
                                
                            </div>
                            <a class="carousel-control-prev" href="#carouselControls" role="button" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carouselControls" role="button" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                        
                        <div class="container-fluid" id="anterior">
                            <h3>Depois</h3>
                            <div id="carouselControlsD" class="carousel slide" data-ride="carousel">
                                <div class="carousel-inner" id="imgD">

                                </div>
                                <a class="carousel-control-prev" href="#carouselControlsD" role="button" data-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="carousel-control-next" href="#carouselControlsD" role="button" data-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>
                        </div>
                        
                        
                    </div>
                    
                    <div class="col-lg-6">
                        <div id="infoDenuncia">
                            
                        </div>
                        <div id="situacao" class="mt-5">
                            
                        </div>
                    </div>
                    <div class="col-lg-1">
                        
                    </div>
                    
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-block btn-light" data-dismiss="modal"><i class="fas fa-times"></i> Fechar</button>
            </div>
        </div>
    </div>
    
</div>