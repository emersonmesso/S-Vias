$(document).ready(function(){
    var processos = "../../_core/_processos/";
    $("#formContato").submit(function(){
        
        //recebendo os dados
        var nome = $("#formNome").val();
        var email = $("#formEmail").val();
        var mensagem = $("#formMensagem").val();
        $.ajax({
            url: processos + "enviaFormContato.php",
            data: {nome : nome, email : email, mensagem : mensagem},
            type: "POST",
            success: function(data){
                if(data == ""){
                    $("#formNome").val('');
                    $("#formEmail").val('');
                    $("#formMensagem").val('');
                    $("#alertContato").html('');
                    $("#alertContato").html('<div class="alert alert-success">Obrigado por nos contatar!<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');
                }else{
                    $("#alertContato").html('');
                    $("#alertContato").html('<div class="alert alert-success">' + data + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');
                }
            },
            error: function(){
                $("#alertContato").html('');
                $("#alertContato").html('<div class="alert alert-success">NÃO FOI POSSÍVEL ENVIAR!<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');
            }
        });
        
        return false;
    });
});