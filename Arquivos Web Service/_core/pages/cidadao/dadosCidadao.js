$(document).ready(function(){
    var processos = "../../_core/_processos/";
    $("#modalDen").hide();
    $('#sidebar').toggleClass('active');
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });
});