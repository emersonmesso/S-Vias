$(document).ready(function(){
    var processos = "../../_core/_processos/";
    //ação do botão de login
    $("#formLogin").submit(function(){
        
        //recebendo os dados
        var email = $("#campoEmail").val();
        var senha = $("#campoSenha").val();
        var check = $("input[name=acesso]:checked").val();
        
        if(email == "" || senha == ""){
            //erro
            $("#infoLogin").html('<div class="alert alert-danger"><h4>Dados Não Informados!</h4></div>');
        }else{
            //faz a consulta
            $.ajax({
                url : processos + "loginUser.php",
                data: {email : email, senha : senha, check : check},
                type: "POST",
                success: function (data){
                    console.log(data);
                    if(data == ""){
                        //redireciona
                        if(check == 1){
                            window.location.href = "../cidadao";
                        }else{
                            window.location.href = "../instituicao";
                        }
                    }else{
                        $("#infoLogin").html('<div class="alert alert-danger"><h4>Verifique os dados informados!</h4></div>');
                    }
                },
                error: function (){
                    
                }
            }); 
        }
        return false;
    });
    
});