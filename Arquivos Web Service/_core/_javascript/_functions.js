var processos = "../../_core/_processos/";
var map;
var google;
var pos;
var denuncias;
var cep = "";
var markers = [];

var pagina = $("#paginaComp").attr("ativo");
$("#textInfo").html('');
/*INICIA O MAPA*/
function initMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) { // callback de sucesso
            //tenho a localização do usuário
            pos = position;

            $.ajax({
                url: "https://maps.googleapis.com/maps/api/geocode/json",
                data: {latlng: position.coords.latitude + ',' + position.coords.longitude, key: "AIzaSyDWbys2hSPGeSGDFJDROqCfz0y1k0hcZdc"},
                type: "GET",
                dataType: "JSON",
                success: function (data) {
                    $.each(data['results'], function (i, valor) {
                        
                        $.each(valor['types'], function(a, type){
                            if(type == "postal_code"){
                                cep = data['results'][i]['address_components'][0]['long_name'];
                                console.log("Indice:" + i);
                            }
                        });
                    });
                    console.log("CEP:" + cep);
                    if (cep == "") {
                        console.log("CEP não encontrado!");
                        //buscando o cep
                        var origem = data['results'][0]['formatted_address'];
                        console.log(origem);
                        var dados = origem.split(", ");
                        console.log(dados[1]);
                        if(pagina == "true"){
                            denunciaPagina();
                        }else{
                            getMarkets(dados[1]);
                        }
                        
                    } else {
                        if(pagina == "true"){
                            denunciaPagina();
                        }else{
                            getMarkets(cep);
                        }
                    }
                },
                error: function () {

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
        function (error) { // callback de erro
            //ativa o modal
            console.log("Não Permitido!");
            $("#carregandoInfo").hide();
            $("#btnPermissao").show();
            $("#textInfo").html('Precisamos de Sua Localizaçao para Mostrar as Denúncias!\nPor Favor, Ative seu GPS');
        });
    } else {
        console.log('Navegador não suporta Geolocalização!');
        alert("Navegador não suporta Geolocalização!");
    }
}

//

/*ALERTAS*/
function alerts(tipo, texto) {
    //verifica o tipo de alerta
    if (tipo == 0) {
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
        setTimeout(function () {
            $("#alertSuccess").fadeOut(300);
        }, 5000);
    } else {
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
        setTimeout(function () {
            $("#alertDanger").fadeOut(300);
        }, 5000);
    }
}

//Cria um carrousel
function carrousel(imagens, div){
    
    div.append('<h3>Antes</h3>');
    
    div.append('<div id="carouselDepois" class="carousel slide" data-ride="carousel">')
        div.append('<div class="carousel-inner">');
            //each
            $.each(imagens, function(i, img){
                if(i == 0){
                    div.append('<div class="carousel-item active"><img class="d-block w-100" src="'+img+'" alt="First slide"></div>');
                }else{
                    div.append('<div class="carousel-item"><img class="d-block w-100" src="'+img+'" alt="Second slide"></div>');
                }
            });
        div.append('</div>');
        div.append('<a class="carousel-control-prev" href="#carouselDepois" role="button" data-slide="prev">');
            div.append('<span class="carousel-control-prev-icon" aria-hidden="true"></span>');
            div.append('<span class="sr-only">Previous</span>');
        div.append('</a>');
        div.append('<a class="carousel-control-next" href="#carouselDepois" role="button" data-slide="next">')
            div.append('<span class="carousel-control-next-icon" aria-hidden="true"></span>');
            div.append('<span class="sr-only">Next</span>')
        div.append('</a>');
    div.append('</div>');
    
}

/*Mostra os dados dos Markets*/
function viewData(id) {
    //buscando as informações da denúncia
    progressbar(55);
    $.ajax({
        url: processos + "getDenuncia.php",
        data: {id: id},
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            progressbar(80);
            console.log(data);
            $("#modalName").html(data[0]['titulo']);
            
            //Criando a funcionalidade de ver mais imagens
            $("#imagesDenuncias").html('');
            $("#img").html('');
            if (data[0]['imagem'].length > 0) {
                //
                $("#tituloAntes").html('');
                $("#tituloAntes").prepend("<h3>Antes</h3>");

                $.each(data[0]['imagem'], function (i, img) {
                    //criando o carrousel
                    if (i == 0) {
                        $("#img").append('<div class="carousel-item active"><img class="d-block w-100" src="' + img + '"></div>');
                    } else {
                        $("#img").append('<div class="carousel-item"><img class="d-block w-100" src="' + img + '"></div>');
                    }
                });
            } else {
                $("#img").append('<div class="carousel-item active"><img class="d-block w-100" src="../../_core/_img/imageError.png"></div>');
            }
            
            //Imagens da prefeitura
            if(data[0]['img_pref'].length > 0){
                $("#divDepois").show();
                //mostra outro Carrousel
                $("#imgD").html('');
                $.each(data[0]['img_pref'], function (i, img) {
                    //criando o carrousel
                    if (i == 0) {
                        $("#imgD").append('<div class="carousel-item active"><img class="d-block w-100" src="' + img + '"></div>');
                    } else {
                        $("#imgD").append('<div class="carousel-item"><img class="d-block w-100" src="' + img + '"></div>');
                    }
                });
                
            }else{
                $("#divDepois").hide();
            }
            
            
            
            $("#infoDenuncia").html('');
            switch (data[0]['situacao']) {
                case 'Pendente':
                    $("#infoDenuncia").append('<div class="alert alert-danger text-center" role="alert"><i class="far fa-clock"></i> Ainda não foi Analisada pelas Autoridades Competentes!</div>');
                    break;
                case 'Concluído':
                    $("#infoDenuncia").append('<div class="alert alert-success text-center" role="alert"><i class="far fa-check-circle"></i> Problema Resolvido com Sucesso!</div>');
                    break;
                default :
                    $("#infoDenuncia").append('<div class="alert alert-warning text-center" role="alert"><i class="fas fa-tasks"></i> A denuncia foi analisada e está em processo de resolução!</div>');
            }

            $("#infoDenuncia").append('<h4>Descrição</h4>');
            $("#infoDenuncia").append('<p>' + data[0]['desc'] + '</p>');
            $("#infoDenuncia").append('<b>Endereço: </b><span class="text-muted">' + data[0]['rua'] + '</span><br />');
            $("#infoDenuncia").append('<b>Cidade: </b><span class="text-muted">' + data[0]['cidade'] + '</span><br />');
            $("#infoDenuncia").append('<b>CEP: </b><span class="text-muted">' + data[0]['cep'] + '</span><br />');
            $("#infoDenuncia").append('<b>Enviada em: </b><span class="text-muted">' + data[0]['data'] + '</span>');
            $("#infoDenuncia").append('<hr />');
            $("#infoDenuncia").append('<div class="text-center">');
                $("#infoDenuncia").append('<button class="btn btn-primary" id="btnCompFacebook" iden="'+data[0]['id']+'"><i class="fab fa-facebook-f"></i></button>');
                $("#infoDenuncia").append('<button class="btn btn-success ml-1" id="btnCompWhatsapp" iden="'+data[0]['id']+'"><i class="fab fa-whatsapp"></i></button>');
            $("#infoDenuncia").append('</div>');
            progressbar(90);
            $("#modalInfo").click();
            progressbar(100);
            progressbar(0);
            $("#telaCarregamento").hide();
        },
        error: function () {

        }
    });

}

function shareFacebook(id){
    var url = "https://emersonmesso95.000webhostapp.com/denuncias/" +id;
    var w = 630;
    var h = 360;
    var left = screen.width/2 - 630/2;
    var top = screen.height/2 - 360/2;
    window.open('http://www.facebook.com/sharer.php?u='+url, 'Compartilhar no facebook', 'toolbar=no, location=no, directories=no, status=no, ' + 'menubar=no, scrollbars=yes, resizable=no, copyhistory=no, width='+w+ ', height=' + h + ', top=' + top + ', left=' + left);
 }
 
function shareWhatsApp(id){
    var url = "https://emersonmesso95.000webhostapp.com/denuncias/" +id;
    //whatsapp://send?text=[CONTEÚDO
    if(detectar_mobile()){
        window.open("whatsapp://send?text=" + url);
    }else{
        window.open("https://api.whatsapp.com/send?text=" + url);
    }
}

function detectar_mobile() {
    if (navigator.userAgent.match(/Android/i)
            || navigator.userAgent.match(/webOS/i)
            || navigator.userAgent.match(/iPhone/i)
            || navigator.userAgent.match(/iPad/i)
            || navigator.userAgent.match(/iPod/i)
            || navigator.userAgent.match(/BlackBerry/i)
            || navigator.userAgent.match(/Windows Phone/i)
            ) {
        return true;
    } else {
        return false;
    }
}

function progressbar(progress) {
    $("#progressBar").css({
        'width': progress + '%'
    });
}

function getMarkets(cep) {
    //
    $.ajax({
        url: processos + "getMarkets.php",
        data: {cep: cep},
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            $("#carregandoInfo").hide();
            denuncias = data.denuncias;
            console.log(denuncias);
            if (data.erro == true) {
                $("#textInfo").html('');
                $("#textInfo").html(data.message);
                $("#btnInicio").show();
            } else {
                $("#totalMarkets").attr("lang", data.total);
                $.each(data.denuncias, function (key, valor) {
                    //valor['name']
                    var customLabel = {
                        "P": '../_core/_img/pendente.png',
                        "C": '../_core/_img/concluido.png',
                        "S": '../_core/_img/progresso.png'
                    };
                    var type = valor['type'];
                    var point = new google.maps.LatLng(valor['lat'], valor['lng']);

                    var icon = customLabel[type];

                    marker = new google.maps.Marker({
                        map: map,
                        draggable: false,
                        animation: google.maps.Animation.DROP,
                        position: point,
                        icon: icon
                    });
                    marker.addListener('click', function () {
                        progressbar(0);
                        viewData(valor['id']);
                    });
                    markers.push(marker);
                });
                $("#telaCarregamento").hide();

            }
        },
        error: function () {
            alerts(1, "Erro ao buscar posíções do mapa");
        }
    });
}

function denunciaPagina(){
    var id = $("#paginaComp").attr("lang");
    //buscando os dados da denuncia
    viewData(id);
    getMarkets(cep);
}


function formContato(nome, email, mensagem) {
    $.ajax({
        url: processos + "enviaFormContato.php",
        data: {nome: nome, email: email, mensagem: mensagem},
        type: "POST",
        success: function (data) {
            if (data == "") {
                $("#alertContato").html('');
                $("#alertContato").html('<div class="alert alert-success">Obrigado por nos contatar!</div>');
            } else {
                $("#alertContato").html('');
                $("#alertContato").html('<div class="alert alert-danger">' + data + '</div>');
            }
        },
        error: function () {

        }
    });
}
function setMapOnAll() {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
}

function atualizaMapa(tipo){
    setMapOnAll();
    markers = [];
    switch (tipo){
        case 1:
            console.log("C");
            $.each(denuncias, function(i, valor){
                
                if(valor['type'] == "C"){
                    //valor['name']
                    var customLabel = {
                        "P": '../_core/_img/pendente.png',
                        "C": '../_core/_img/concluido.png',
                        "S": '../_core/_img/progresso.png'
                    };
                    var type = valor['type'];
                    var point = new google.maps.LatLng(valor['lat'], valor['lng']);

                    var icon = customLabel[type];

                    marker = new google.maps.Marker({
                        map: map,
                        draggable: false,
                        position: point,
                        icon: icon
                    });
                    marker.addListener('click', function () {
                        progressbar(0);
                        viewData(valor['id']);
                    });
                    
                    markers.push(marker);
                }
            });
            break;
        case 2:
            console.log("S");
            $.each(denuncias, function(i, valor){
                
                if(valor['type'] == "S"){
                    //valor['name']
                    var customLabel = {
                        "P": '../_core/_img/pendente.png',
                        "C": '../_core/_img/concluido.png',
                        "S": '../_core/_img/progresso.png'
                    };
                    var type = valor['type'];
                    var point = new google.maps.LatLng(valor['lat'], valor['lng']);

                    var icon = customLabel[type];

                    marker = new google.maps.Marker({
                        map: map,
                        draggable: false,
                        position: point,
                        icon: icon
                    });
                    marker.addListener('click', function () {
                        progressbar(0);
                        viewData(valor['id']);
                    });
                    markers.push(marker);
                }
            });
            break;
        case 3:
            console.log("P");
            $.each(denuncias, function(i, valor){
                
                if(valor['type'] == "P"){
                    //valor['name']
                    var customLabel = {
                        "P": '../_core/_img/pendente.png',
                        "C": '../_core/_img/concluido.png',
                        "S": '../_core/_img/progresso.png'
                    };
                    var type = valor['type'];
                    var point = new google.maps.LatLng(valor['lat'], valor['lng']);

                    var icon = customLabel[type];

                    marker = new google.maps.Marker({
                        map: map,
                        draggable: false,
                        position: point,
                        icon: icon
                    });
                    marker.addListener('click', function () {
                        progressbar(0);
                        viewData(valor['id']);
                    });
                    markers.push(marker);
                }
            });
        break;
    }
}