<button id="btnAtivaModal" type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalPermissao">
  Launch demo modal
</button>
<!-- MAPA -->
<div class="container-fluid mt-2" id="mapa">
    <!-- MAPA -->
    <div id="map">
        
        <div class="text-center">
            <h1>Imagem</h1>
            <button type="button" class="btn btn-block btn-primary" id="btnPermissao">Permitir</button>
            
        </div>
        
    </div>
    <!-- MAPA -->
</div>
<!-- MAPA -->

<!--MODAL INFORMAÇÕES-->
<button type="button" id="modalInfo" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-lg">Large modal</button>
<div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
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
                        
                    </div>
                    
                    <div class="col-lg-6" id="infoDenuncia">
                        
                    </div>
                    <div class="col-lg-1">
                        
                    </div>
                    
                </div>
                
                
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger btn-block" data-dismiss="modal">Fechar</button>
            </div>
        </div>
    </div>
</div>
<!--MODAL INFORMAÇÕES-->