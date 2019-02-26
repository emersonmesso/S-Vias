$(document).ready(function(){
    //
    var cont = 2;
    
    
    
    //Adicionar novos campos de telefones
    $("#btnAddTelefone").on('click', function(){
        var telefones = $("#telefones");
        telefones.append('<div class="input-field col s4"><input id="telefone_'+cont+'" name="telefones[]" type="text" class="validate"><label for="telefone_'+cont+'">Telefone '+cont+'</label></div>');
        cont++;
    });
});