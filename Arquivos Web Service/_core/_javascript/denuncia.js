$(document).ready(function(){
    var processos = "../../_core/_processos/";
    $("#btnAtivaModal").hide();
    $("#modalInfo").hide();
    $("#btnPermissao").hide();
    $("#btnInicio").hide();
    
    /*Ação botão permitir*/
    $("#btnPermissao").on('click', function(){
        navigator.permissions.query({name:'geolocation'})
        .then(function(permissionStatus) {
        console.log('geolocation permission state is ', permissionStatus.state);

        if(permissionStatus.state != "denied"){
            initMap();
        }else{
            
        }
        permissionStatus.onchange = function() {
            console.log('geolocation permission state has changed to ', this.state);
        };
    });
        
        
    });
    
    
});