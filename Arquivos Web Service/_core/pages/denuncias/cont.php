<?php
$Controller = new Controller();
//verificando se existe uma denuncia no GET da página
$pagina = false;
if(isset($Controller->url[1])){
    echo '<b id="paginaComp" ativo="true" lang="'.$Controller->url[1].'"></b>';
}else{
    echo '<b id="paginaComp" ativo="false" lang=""></b>';
}
?>


<div class="progress" style="height: 3px;">
    <div class="progress-bar progress-bar-striped progress-bar-animated" id="progressBar" role="progressbar" style="width: 0%;" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
</div>

<!-- Tela Carregamento -->
<div class="" id="telaCarregamento">
    <div class="container text-center p-5" id="infoCarregamento">
        
        <div id="carregandoInfo">
            <img src="../../_core/_img/load.gif" alt="load mapa" title="Carregando mapa..." />
        </div>
        
        <!--<button type="button" class="btn btn-block btn-primary" id="btnPermissao">Permitir</button>-->
        <h2 class="text-center" id="textInfo">
            
        </h2>
        <!--<a href="../index.php" class="btn btn-block btn-warning" id="btnInicio">Início Do Site</a>-->
        
    </div>
</div>

<div id="" class=" text-center">
    <button type="button" class="btn btn-success" id="btnFiltroConcluido">Problemas Resolvidos</button>
    <button type="button" class="btn btn-warning" id="btnFiltroEmProcesso">Em Processo de resolução</button>
    <button type="button" class="btn btn-danger" id="btnFiltroPendente">Sem Análise das Autoridades</button>
</div>


<!-- MAPA -->
<div class="container-fluid mt-2" id="mapa">
    <!-- MAPA -->
    <div id="map">
        
        <div class="text-center">
            <h1>Imagem</h1>
            <!--<button type="button" class="btn btn-block btn-primary" id="btnPermissao">Permitir</button>-->
            
        </div>
        
    </div>
    <!-- MAPA -->
</div>
<!-- MAPA -->

<button id="btnAtivaModal" type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalPermissao">
  Launch demo modal
</button>

<!--MODAL INFORMAÇÕES-->
<button type="button" id="modalInfo" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-lg">Large modal</button>
<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
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
                    
                    <div class="col-lg-5 p-5 text-center" id="imagemDenuncia">
                        <div id="tituloAntes"></div>
                        
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
                        
                        <div id="divDepois" class="container-fluid">
                            <h3 id="tituloDepois">Depois</h3>
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
                    
                    <div class="col-lg-6" id="infoDenuncia">
                        
                    </div>
                    <div class="col-lg-1">
                        
                    </div>
                    
                </div>
                
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-block btn-light" data-dismiss="modal"><i class="fas fa-external-link text-danger"></i>Fechar</button>
            </div>
        </div>
    </div>
</div>
<!--MODAL INFORMAÇÕES-->