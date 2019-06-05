
$(document).ready(function(){
    var processos = "../../_core/_processos/";
    var lat, lng;
    var cep = "";
    var cidade = "";
    var estado = "";
    var email = "";
    var dataDenuncias;
    $("#modalDen").hide();
    $("#infoDenuncia div[id^='telaEdit'").hide();
    
    
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
    
    
    /*Compartilhar*/
    $("#infoDenuncia").delegate("#btnCompFacebook", 'click', function(){
        var id = $(this).attr("iden");
        shareFacebook(id);
    });
    $("#infoDenuncia").delegate("#btnCompWhatsapp", 'click', function(){
        var id = $(this).attr("iden");
        shareWhatsApp(id); 
    });
    
    
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
    
    /*Botão Editar*/
    var editando = false;
    $("#infoDenuncia").delegate('#btnEdit','click', function(){
        var indice = $("#infoDenuncia b#indiceDenuncia").attr("lang");
        if(!editando){
            editando = true;    
            $("#infoDenuncia div#addEdit").html('');
            $("#infoDenuncia div#addEdit").append('<textarea row="3" id="campoDesc" class="form-control">' + dataDenuncias.results[indice]['desc'] + '</textarea>');
            $("#infoDenuncia div#addEdit").append('<button type="button" class="btn btn-success btn-blck mt-2" id="btnSalvaEdit">Salvar</button>');
        }else{
            editando = false;
            $("#infoDenuncia div#addEdit").html('');
            $("#infoDenuncia div#addEdit").html(dataDenuncias.results[indice]['desc']);
        }
    });
    
    
    /*REDE SOCIAIS*/
    $("#infoDenuncia").delegate('#btnWhatsApp','click', function(){
        var conteudo = encodeURIComponent(document.title + " " + window.location.href);
        window.open("https://api.whatsapp.com/send?text=" + conteudo);
    });
    
    //Leia Mais
    $("#infoDenuncia").delegate('#btnLeiaMais','click', function(){
        $(this).hide();
        var indice = $("#infoDenuncia b#indiceDenuncia").attr("lang");
        $("#infoDenuncia").append('<b>Endereço: </b><span class="text-muted">'+ dataDenuncias.results[indice]['rua'] +'</span><br />');
        $("#infoDenuncia").append('<b>Cidade: </b><span class="text-muted">'+ dataDenuncias.results[indice]['cidade'] +'</span><br />');
        $("#infoDenuncia").append('<b>CEP: </b><span class="text-muted">'+ dataDenuncias.results[indice]['cep'] +'</span><br />');
        $("#infoDenuncia").append('<b>Enviada em: </b><span class="text-muted">'+ dataDenuncias.results[indice]['data'] +'</span>');
        $("#infoDenuncia").append('<hr />');
        $("#infoDenuncia").append('<button class="btn btn-primary" id="btnCompFacebook" iden="'+dataDenuncias.results[indice]['id']+'"><i class="fab fa-facebook-f"></i></button>');
        $("#infoDenuncia").append('<button class="btn btn-success ml-1" id="btnCompWhatsapp" iden="'+dataDenuncias.results[indice]['id']+'"><i class="fab fa-whatsapp"></i></button>');
        $("#infoDenuncia").append('');
    });
    
    /*Salvar Edição*/
    $("#infoDenuncia").delegate('div#addEdit button','click', function(){
        $(this).html('');
        $(this).addClass("disabled");
        $(this).html('Salvando...');
        var indice = $("#infoDenuncia b#indiceDenuncia").attr("lang");
        var desc = $("#infoDenuncia #campoDesc").val();
        var id = dataDenuncias.results[indice]['id'];
        console.log("Descrição: " + desc + "\nID:" + id);
        
        //envia os dados
        
        $.ajax({
            url: processos + "editDenunciaCid.php",
            data: {id : id, desc : desc},
            type: "POST",
            success: function(data){
                console.log(data);
                if(data != ""){
                    alert(data);
                }else{
                    window.location.reload();
                }
            },
            error: function(){
                
            }
        });
        
    });
    
    
    //mostrando o Modal da denúncia
    $('#denuncias').delegate('a[id^="btnModalDenuncia"]', 'click', function(){
        var indice = $(this).attr("lang");
        
        $("#telaEdit").hide();
        $("#btnEdit").fadeIn(100);
        
        $("#modalName").html(dataDenuncias.results[indice]['titulo']);
        
        /*IMAGENS*/
        $("#img").html('');
        
        if(dataDenuncias.results[indice]['imagem'].length > 0){
            
            $.each(dataDenuncias.results[indice]['imagem'], function(i, img){
                //criando o carrousel
                if(i == 0){
                    $("#img").append('<div class="carousel-item active"><img class="d-block w-100" src="'+ img +'"></div>');
                }else{
                    $("#img").append('<div class="carousel-item"><img class="d-block w-100" src="'+ img +'"></div>');
                }
            });
            
        }else{
            $("#img").append('<div class="carousel-item active"><img class="d-block w-100" src="../../_core/_img/imageError.png"></div>');
        }
        
        //depois
        if(dataDenuncias.results[indice]['img_pref'].length > 0){
            $("#anterior").show();
            $.each(dataDenuncias.results[indice]['img_pref'], function(i, img){
                //criando o carrousel
                if(i == 0){
                    $("#imgD").append('<div class="carousel-item active"><img class="d-block w-100" src="'+ img +'"></div>');
                }else{
                    $("#imgD").append('<div class="carousel-item"><img class="d-block w-100" src="'+ img +'"></div>');
                }
            });
            
        }else{
            $("#anterior").hide();
        }
        
        
        /*IMAGENS*/
        
        $("#infoDenuncia").html('');
        

        $("#infoDenuncia").append('<h4>Descrição<a href="javascript:void(0)" id="btnEdit" class="badge badge-secundary"><i class="fas fa-edit"></i></a></h4>');
        $("#infoDenuncia").append('<div id="addEdit" class="mb-4"><p>'+dataDenuncias.results[indice]['desc']+'</p></div>');
        $("#infoDenuncia").append('<button class="btn btn-block btn-warning" id="btnLeiaMais" type="button">Leia Mais</button>');
        
        //edit
        $("#infoDenuncia").append('<b id="indiceDenuncia" lang="'+indice+'"></b>');
        $("#situacao").html('');
        switch (dataDenuncias.results[indice]['situacao']){
            case 'Pendente':
                $("#situacao").append('<div class="alert alert-danger text-center" role="alert"><i class="fas fa-clock"></i> Ainda não foi Analisada pelas Autoridades Competentes!</div>');
                break;
            case 'Concluído':
                $("#situacao").append('<div class="alert alert-success text-center" role="alert"><i class="fas fa-check-circle"></i> Problema Resolvido com Sucesso!</div>');
                break;
            default :
                $("#situacao").append('<div class="alert alert-warning text-center" role="alert"><i class="fas fa-tasks"></i> A denuncia foi analisada e está em processo de resolução!</div>');
        }
        $("#modalDen").click();
    });
    
    
    /*FUNÇÕES*/
    
    /*Cria o registro*/
    function log(mensagem, user){
        
        $.ajax({
            url : processos + "geraLogs.php",
            data: {},
            type: "POST",
            suceess: function(data){
                if(data != ""){
                    alert(data);
                }
            },
            error: function(){
                
            }
        });
        
    }
    
    /*Mostra os filtros*/
    function filtroDenuncia(op){
        $("#denuncias").html('');
        switch (op){
            case 1:
                
                $.each(dataDenuncias.results, function(i, denuncia){
                    
                    if(denuncia.alert == "danger"){
                        var img;
                        if(denuncia.imagem[0] == ""){
                            img = "../../_core/_img/imageError.png";
                        }else{
                            img = denuncia.imagem[0];
                        }
                        
                        $("#denuncias").append('<a href="javascript:void(0)" id="btnModalDenuncia" lang="'+i+'">\n\
                            <div class="card flex mr-2 mb-2 d-inline-flex" style="width: 20rem;">\n\
                                <img src="'+ img + '" style="background-size: cover; width: 100%; height: 300px;" class="card-img-top" alt="" />\n\
                                <div class="card-body">\n\
                                    <div class="alert alert-danger"><i class="fas fa-clock"></i> Ainda não foi Analisada pelas Autoridades Competentes!</div>\n\
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
                        var img;
                        if(denuncia.imagem[0] == ""){
                            img = "../../_core/_img/imageError.png";
                        }else{
                            img = denuncia.imagem[0];
                        }
                        $("#denuncias").append('<a href="javascript:void(0)" id="btnModalDenuncia" lang="'+i+'">\n\
                            <div class="card flex mr-2 mb-2 d-inline-flex" style="width: 20rem;">\n\
                                <img src="'+ img + '" style="background-size: cover; width: 100%; height: 300px;" class="card-img-top" alt="" />\n\
                                <div class="card-body">\n\
                                    <div class="alert alert-warning"><i class="fas fa-tasks"></i> A denuncia foi analisada e está em processo de resolução!</div>\n\
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
                        var img;
                        if(denuncia.imagem[0] == ""){
                            img = "../../_core/_img/imageError.png";
                        }else{
                            img = denuncia.imagem[0];
                        }
                        $("#denuncias").append('<a href="javascript:void(0)" id="btnModalDenuncia" lang="'+i+'">\n\
                            <div class="card flex mr-2 mb-2 d-inline-flex" style="width: 20rem;">\n\
                                <img src="'+ img + '" style="background-size: cover; width: 100%; height: 300px;" class="card-img-top" alt="" />\n\
                                <div class="card-body">\n\
                                    <div class="alert alert-success"><i class="fas fa-check-circle"></i> Problema Resolvido com Sucesso!</div>\n\
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
                console.log(data);
                if(data.total != 0){
                    //mostra a denuncia
                    $("#denuncias").html('');

                    $.each(data.results, function(i, denuncia){
                        //altera o nome da cidade
                        $("#nomeCidade").html("Denúncias (" +data.cidade+")");
                        var string = "";
                        if(denuncia.alert == "danger"){
                            string = '<i class="fas fa-clock"></i> Ainda não foi Analisada pelas Autoridades Competentes!';
                        }else if(denuncia.alert == "warning"){
                            string = '<i class="fas fa-tasks"></i> A denuncia foi analisada e está em processo de resolução!';
                        }else{
                            string = '<i class="fas fa-check-circle"></i> Problema Resolvido com Sucesso!';
                        }
                        var img;
                        if(denuncia.imagem.length == 0){
                            img = "../../_core/_img/imageError.png";
                        }else{
                            img = denuncia.imagem[0];
                        }
                       $("#denuncias").append('<a href="javascript:void(0)" id="btnModalDenuncia" lang="'+i+'">\n\
                            <div class="card flex mr-2 mb-2 d-inline-flex" style="width: 20rem;">\n\
                                <img src="'+ img + '" style="background-size: cover; width: 100%; height: 300px;" class="card-img-top" alt="" />\n\
                                <div class="card-body">\n\
                                    <div class="alert alert-'+denuncia.alert+'"> '+ string + '</div>\n\
                                    <p class="card-text">' + denuncia.titulo +'</p>\n\
                                </div>\n\
                            </div>\n\
                        </a>');
                    });
                }else{
                    $("#denuncias").html('');
                    $("#denuncias").html("Nenhuma Denúncia!");
                }
            },
            error: function () {

            }
        });
    }
    
});