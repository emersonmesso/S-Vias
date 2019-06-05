$(document).ready(function () {
    var processos = "../../_core/_processos/";
    $("#btnAtivaModal").hide();
    $("#modalInfo").hide();
    $("#btnPermissao").hide();
    $("#btnInicio").hide();

    /*Ação botão permitir*/
    $("#btnPermissao").on('click', function () {
        navigator.permissions.query({name: 'geolocation'})
                .then(function (permissionStatus) {
                    console.log('geolocation permission state is ', permissionStatus.state);

                    if (permissionStatus.state != "denied") {
                        initMap();
                    } else {

                    }
                    permissionStatus.onchange = function () {
                        console.log('geolocation permission state has changed to ', this.state);
                    };
                });
    });
    
    
    //Filtros mapa
    $("#btnFiltroConcluido").on('click', function(){
        console.log("Mostrando os concluídos!");
        atualizaMapa(1);
    });
    $("#btnFiltroEmProcesso").on('click', function(){
        atualizaMapa(2);
    });
    $("#btnFiltroPendente").on('click', function(){
        console.log("Mostrando os concluídos!");
        atualizaMapa(3);
    });
    
    
    /*Compartilhar*/
    $("#infoDenuncia").delegate("#btnCompFacebook", 'click', function(){
        var id = $(this).attr("iden");
        shareFacebook(id);
    });
    $("#infoDenuncia").delegate("#btnCompWhatsapp", 'click', function(){
        var id = $(this).attr("iden");
        shareWhatsApp(id); 
    });
    
    
});