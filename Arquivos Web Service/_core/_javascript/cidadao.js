
$(document).ready(function(){
    var processos = "../../_core/_processos/";
    var lat, lng;
    var cep = "";
    var cidade = "";
    var estado = "";
    var email = "";
    var dataDenuncias;
    $("#modalDen").hide();
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });
    
    //Logout User
    $("#btnLogout").on('click', function(){
        $.ajax({
            url : processos + "logoutUser.php",
            data: {},
            type: "POST",
            success: function(data){
                window.location.href = "../login";
            },
            error: function(){
                alert("Erro");
            }
        });
    });
    
    //geolocalização do usuário
    if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position){ // callback de sucesso
            //tenho a localização do usuário
            lat = position.coords.latitude;
            lng = position.coords.longitude;
            $("#lat").attr("lang", lat);
            $("#lng").attr("lang", lng);
            
            //busca o cep
            $.ajax({
                url: "https://maps.googleapis.com/maps/api/geocode/json",
                data: {latlng: position.coords.latitude +',' + position.coords.longitude, key : "AIzaSyDWbys2hSPGeSGDFJDROqCfz0y1k0hcZdc"},
                type: "GET",
                dataType: "JSON",
                success: function(data){
                    //alert(data['results'][0]['address_components'][6]['long_name']);
                    $.each(data['results'][0]['address_components'], function(i, valor){
                        if(valor['types'] == "postal_code"){
                            cep = valor['long_name'];
                        }
                        if(valor['types'][0] == "administrative_area_level_2"){
                            cidade = valor['long_name'];
                        }
                        if(valor['types'][0] == "administrative_area_level_1"){
                            estado = valor['short_name'];
                        }
                    });
                    if(cep == ""){
                        console.log("CEP não encontrado!");
                        //buscando o cep
                        var origem = data['results'][0]['formatted_address'];
                        var dados = origem.split(", ");
                        //console.log(dados[1]);
                        cep = dados[1];
                        cidade = dados[2];
                    }
                    
                    //buscar as denúncias
                    email = $("#emailUser").attr("lang");
                    mostraDenunciasCid(email, cep);
                    
                },
                error: function(){

                }
            });
        }, 
        function(error){ // callback de erro
           //ativa o modal
           console.log("Não Permitido!");
        });
    } else {
        console.log('Navegador não suporta Geolocalização!');
    }
    
    
    /*Botão cidades*/
    $('a[id^="btnCidade"]').on('click', function(){
        var cep = $(this).attr("cep");
        $("#denuncias").html('');
        $("#denuncias").html('<img src="../../_core/_img/load.gif" width="5%" />');
        
        //
        $("#sidebarCollapse").click();
        //
        mostraDenunciasCid(email, cep);
    });
    
    /*Botão filtros*/
    $("#btnPendentes").on('click', function(){
        filtroDenuncia(1);
    });
    $("#btnProgresso").on('click', function(){
        filtroDenuncia(2);
    });
    $("#btnConcluido").on('click', function(){
        filtroDenuncia(3);
    });
    
    
    //mostrando o Modal da denúncia
    $('#denuncias').delegate('a[id^="btnModalDenuncia"]', 'click', function(){
        var indice = $(this).attr("lang");
        console.log(dataDenuncias.results[indice]);
        $("#modalName").html(dataDenuncias.results[indice]['titulo']);
        if(dataDenuncias.results[indice]['imagem'] != ""){
            $("#imagemDenuncia").html('<img src="'+dataDenuncias.results[indice]['imagem']+'" class="w-75 img-fluid rounded" alt="Imagem de danúncia" />');
        }else{
            $("#imagemDenuncia").html('');
            $("#imagemDenuncia").append('<img src="../_core/_img/logoApp.png" class="w-75 img-fluid rounded" alt="Imagem de danúncia" />');
            $("#imagemDenuncia").append('<h4 class="mt-5">Sem Imagem!</h4>');
        }
        $("#infoDenuncia").html('');
        switch (dataDenuncias.results[indice]['situacao']){
            case 'Pendente':
                $("#infoDenuncia").append('<div class="alert alert-danger text-center" role="alert"><i class="fas fa-clock"></i> Pendente!</div>');
                break;
            case 'Concluído':
                $("#infoDenuncia").append('<div class="alert alert-success text-center" role="alert"><i class="fas fa-check-circle"></i> Resolvido!</div>');
                break;
            default :
                $("#infoDenuncia").append('<div class="alert alert-warning text-center" role="alert"><i class="fas fa-tasks"></i> Em Processo!</div>');
        }

        $("#infoDenuncia").append('<h4>Descrição</h4>');
        $("#infoDenuncia").append('<p>'+dataDenuncias.results[indice]['desc']+'</p>');
        $("#infoDenuncia").append('<b>Endereço: </b><span class="text-muted">'+ dataDenuncias.results[indice]['rua'] +'</span><br />');
        $("#infoDenuncia").append('<b>Cidade: </b><span class="text-muted">'+ dataDenuncias.results[indice]['cidade'] +'</span><br />');
        $("#infoDenuncia").append('<b>CEP: </b><span class="text-muted">'+ dataDenuncias.results[indice]['cep'] +'</span><br />');
        $("#infoDenuncia").append('<b>Enviada em: </b><span class="text-muted">'+ dataDenuncias.results[indice]['data'] +'</span>');
        $("#infoDenuncia").append('<hr />');
        $("#infoDenuncia").append('<button class="btn btn-block btn-primary">Compartilhar   <i class="fas fa-facebook-f"></i></button>');
        $("#infoDenuncia").append('<button class="btn btn-block btn-success">Compartilhar   <i class="fas fa-whatsapp"></i></button>');
        $("#infoDenuncia").append('');
        $("#modalDen").click();
    });
    
    
    /*FUNÇÕES*/
    
    /*Mostra os filtros*/
    function filtroDenuncia(op){
        $("#denuncias").html('');
        switch (op){
            case 1:
                
                $.each(dataDenuncias.results, function(i, denuncia){
                    
                    if(denuncia.alert == "danger"){
                        
                        $("#denuncias").append('<a href="javascript:void(0)" id="btnModalDenuncia" lang="'+i+'">\n\
                            <div class="card flex mr-2 mb-2 d-inline-flex" style="width: 20rem;">\n\
                                <img src="'+ denuncia.imagem + '" style="background-size: cover; width: 100%; height: 300px;" class="card-img-top" alt="" />\n\
                                <div class="card-body">\n\
                                    <div class="alert alert-danger"><i class="fas fa-clock"></i> ' +denuncia.situacao+'</div>\n\
                                    <p class="card-text">' + denuncia.titulo +'</p>\n\
                                </div>\n\
                            </div>\n\
                        </a>');
                        
                    }
                    
                });
                break;
            case 2: 
                
                $.each(dataDenuncias.results, function(i, denuncia){
                    
                    if(denuncia.alert == "warning"){
                        
                        $("#denuncias").append('<a href="javascript:void(0)" id="btnModalDenuncia" lang="'+i+'">\n\
                            <div class="card flex mr-2 mb-2 d-inline-flex" style="width: 20rem;">\n\
                                <img src="'+ denuncia.imagem + '" style="background-size: cover; width: 100%; height: 300px;" class="card-img-top" alt="" />\n\
                                <div class="card-body">\n\
                                    <div class="alert alert-warning"><i class="fas fa-tasks"></i> ' +denuncia.situacao+'</div>\n\
                                    <p class="card-text">' + denuncia.titulo +'</p>\n\
                                </div>\n\
                            </div>\n\
                        </a>');
                        
                    }
                    
                });
                
                break;
            default:
                $.each(dataDenuncias.results, function(i, denuncia){
                    
                    if(denuncia.alert == "success"){
                        
                        $("#denuncias").append('<a href="javascript:void(0)" id="btnModalDenuncia" lang="'+i+'">\n\
                            <div class="card flex mr-2 mb-2 d-inline-flex" style="width: 20rem;">\n\
                                <img src="'+ denuncia.imagem + '" style="background-size: cover; width: 100%; height: 300px;" class="card-img-top" alt="" />\n\
                                <div class="card-body">\n\
                                    <div class="alert alert-success"><i class="fas fa-check-circle"></i> ' +denuncia.situacao+'</div>\n\
                                    <p class="card-text">' + denuncia.titulo +'</p>\n\
                                </div>\n\
                            </div>\n\
                        </a>');
                        
                    }
                    
                });
                
        }
    }
    
    /*Mostra as denuncias do cidadão*/
    function mostraDenunciasCid(email, cep){
        $.ajax({
            url : processos + "getDenunciaAtual.php",
            data: {email : email , cep : cep},
            type: "POST",
            dataType: "JSON",
            success: function (data) {
                dataDenuncias = data;
                if(data.total != 0){
                    //mostra a denuncia
                    $("#denuncias").html('');

                    $.each(data.results, function(i, denuncia){
                        //altera o nome da cidade
                        $("#nomeCidade").html("Denúncias (" +data.cidade+")");
                        var string = "";
                        if(denuncia.alert == "danger"){
                            string = '<i class="fas fa-clock"></i>';
                        }else if(denuncia.alert == "warning"){
                            string = '<i class="fas fa-tasks"></i>';
                        }else{
                            string = '<i class="fas fa-check-circle"></i>';
                        }

                       $("#denuncias").append('<a href="javascript:void(0)" id="btnModalDenuncia" lang="'+i+'">\n\
                            <div class="card flex mr-2 mb-2 d-inline-flex" style="width: 20rem;">\n\
                                <img src="'+ denuncia.imagem + '" style="background-size: cover; width: 100%; height: 300px;" class="card-img-top" alt="" />\n\
                                <div class="card-body">\n\
                                    <div class="alert alert-'+denuncia.alert+'"> '+ string + ' ' +denuncia.situacao+'</div>\n\
                                    <p class="card-text">' + denuncia.titulo +'</p>\n\
                                </div>\n\
                            </div>\n\
                        </a>');
                    });
                }else{

                }
            },
            error: function () {

            }
        });
    }
    
});