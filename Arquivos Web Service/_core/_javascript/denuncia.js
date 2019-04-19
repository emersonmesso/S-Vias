$(document).ready(function(){
    var processos = "../../_core/_processos/";
    $("#btnAtivaModal").hide();
    $("#modalInfo").hide();
    
    /*Ação botão permitir*/
    $("#btnPermissao").on('click', function(){
        navigator.permissions.query({name:'geolocation'})
        .then(function(permissionStatus) {
        console.log('geolocation permission state is ', permissionStatus.state);

        if(permissionStatus.state != "denied"){
            initMap();
        }
        permissionStatus.onchange = function() {
            console.log('geolocation permission state has changed to ', this.state);
        };
    });
        
        
    });
    
    
});