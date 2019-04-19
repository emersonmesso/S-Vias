$(document).ready(function(){
    var processos = "../../_core/_processos/";
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
});