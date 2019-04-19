var processos = "../../_core/_processos/";
var map;
var google;
var pos;
/*INICIA O MAPA*/
function initMap(){
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position){ // callback de sucesso
            //tenho a localização do usuário
            pos = position;
            
            $.ajax({
                url: "https://maps.googleapis.com/maps/api/geocode/json",
                data: {latlng: position.coords.latitude +',' + position.coords.longitude, key : "AIzaSyDWbys2hSPGeSGDFJDROqCfz0y1k0hcZdc"},
                type: "GET",
                dataType: "JSON",
                success: function(data){
                    console.log(data);
                    //alert(data['results'][0]['address_components'][6]['long_name']);
                    //busca os pontos
                    var cep = "";
                    $.each(data['results'][0]['address_components'], function(i, valor){
                        if(valor['types'] == "postal_code"){
                            cep = valor['long_name'];
                        }
                        
                    });
                    getMarkets(cep);
                },
                error: function(){

                }
            });
            
            map = new google.maps.Map(document.getElementById('map'), {
            //cebtraliza o mapa em salgueiro
            center: new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
            //define o ZOOM em 15
            zoom: 15,
            //Desabilita botões padrões do Google Maps
            disableDefaultUI: false,
            fullscreenControl: false,
            mapTypeControl: false,
            //remove dados do mapa
            styles: [
                {
                    "featureType": "poi.business",
                    "stylers": [
                        {"visibility": "off"}
                    ]
                },
                {
                    "featureType": "poi.government",
                    "stylers": [
                        {"visibility": "off"}
                    ]
                },
                {
                    "featureType": "poi.medical",
                    "stylers": [
                        {"visibility": "off"}
                    ]
                },
                {
                    "featureType": "poi.place_of_worship",
                    "stylers": [
                        {"visibility": "off"}
                    ]
                },
                {
                    "featureType": "poi.school",
                    "stylers": [
                        {"visibility": "off"}
                    ]
                },
                {
                    "featureType": "poi.sports_complex",
                    "stylers": [
                        {"visibility": "off"}
                    ]
                },
                {
                    "featureType": "poi.attraction",
                    "stylers": [
                        {"visibility": "off"}
                    ]
                }
            ]
        });
            
        }, 
        function(error){ // callback de erro
           //ativa o modal
        });
    } else {
        console.log('Navegador não suporta Geolocalização!');
    }
}

//

/*ALERTAS*/
function alerts(tipo, texto){
    //verifica o tipo de alerta
    if(tipo == 0){
        //ALERTA DE SUCESSO
        //apaga o último texto presente no alert
        $("#textAlertSuccess").html('');
        //escreve o novo texto no campo de texto
        $("#textAlertSuccess").html(texto);
        //mostra o alert na tela
        $("#alertSuccess").show();
        //esconde o alert de erro
        $("#alertDanger").hide();
        //esconde o alert com 5 segundos
        setTimeout(function(){
            $("#alertSuccess").fadeOut(300);
        },5000);
    }else{
        //ALERTA DE ERRO
        
        //apaga o último texto presente no alert
        $("#textAlerterror").html('');
        //escreve o texto no campo de texto
        $("#textAlerterror").html(texto);
        //mostra o alerta de erro
        $("#alertDanger").show();
        //esconde o alerta de sucesso
        $("#alertSuccess").hide();
        //esconde o alerta com 5 segundos
        setTimeout(function(){
            $("#alertDanger").fadeOut(300);
        },5000);
    }
}

/*Mostra os dados dos Markets*/
function viewData(id){
    //buscando as informações da denúncia
    $.ajax({
        url: processos + "getDenuncia.php",
        data: {id : id},
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            console.log(data);
            $("#modalName").html(data[0]['titulo']);
            if(data[0]['imagem'] != ""){
                $("#imagemDenuncia").html('<img src="'+data[0]['imagem']+'" class="w-75 img-fluid rounded" alt="Imagem de danúncia" />');
            }else{
                $("#imagemDenuncia").html('');
                $("#imagemDenuncia").append('<img src="../_core/_img/logoApp.png" class="w-75 img-fluid rounded" alt="Imagem de danúncia" />');
                $("#imagemDenuncia").append('<h4 class="mt-5">Sem Imagem!</h4>');
            }
            $("#infoDenuncia").html('');
            switch (data[0]['situacao']){
                case 'Pendente':
                    $("#infoDenuncia").append('<div class="alert alert-danger text-center" role="alert"><i class="far fa-clock"></i> Pendente!</div>');
                    break;
                case 'Concluído':
                    $("#infoDenuncia").append('<div class="alert alert-success text-center" role="alert"><i class="far fa-check-circle"></i> Resolvido!</div>');
                    break;
                default :
                    $("#infoDenuncia").append('<div class="alert alert-warning text-center" role="alert"><i class="fas fa-tasks"></i> Em Processo!</div>');
            }
            
            $("#infoDenuncia").append('<h4>Descrição</h4>');
            $("#infoDenuncia").append('<p>'+data[0]['desc']+'</p>');
            $("#infoDenuncia").append('<b>Endereço: </b><span class="text-muted">'+ data[0]['rua'] +'</span><br />');
            $("#infoDenuncia").append('<b>Cidade: </b><span class="text-muted">'+ data[0]['cidade'] +'</span><br />');
            $("#infoDenuncia").append('<b>CEP: </b><span class="text-muted">'+ data[0]['cep'] +'</span><br />');
            $("#infoDenuncia").append('<b>Enviada em: </b><span class="text-muted">'+ data[0]['data'] +'</span>');
            $("#infoDenuncia").append('<hr />');
            $("#infoDenuncia").append('<button class="btn btn-block btn-primary">Compartilhar   <i class="fab fa-facebook-f"></i></button>');
            $("#infoDenuncia").append('<button class="btn btn-block btn-success">Compartilhar   <i class="fab fa-whatsapp"></i></button>');
            $("#infoDenuncia").append('');
            
            
            $("#modalInfo").click();
        },
        error: function () {
            
        }
    });
    
}


function getMarkets(cep){
    //
    $.ajax({
        url: processos + "getMarkets.php",
        data: {cep : cep},
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            console.log(data);
            $("#totalMarkets").attr("lang", data.total);
            $.each(data, function(key, valor){
                //valor['name']
                var customLabel = {
                    P: 'https://png.pngtree.com/element_pic/16/12/02/51e6452ca365f618ab4b723c7aa18be9.jpg',
                    R: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png'
                };
                var type = valor['type'];
                var point = new google.maps.LatLng(valor['lat'],valor['lng']);
                
                var icon = customLabel[type];
                
                marker = new google.maps.Marker({
                    map: map,
                    draggable: false,
                    animation: google.maps.Animation.DROP,
                    position: point
                });
                marker.addListener('click', function() {
                    viewData(valor['id']);
                    //$("#telaDetalhe").fadeOut(100);
                });
            });
        },
        error: function () {
            alerts(1, "Erro ao buscar posíções do mapa");
        }
    });
}