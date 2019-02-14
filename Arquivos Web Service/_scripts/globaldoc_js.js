/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 Created on : 11/01/2017.
 Changer on : 14/02/2019.
 Author     : Michael Dydean
 */

/*Funções da página*/
/*Função que redericiona link*/
function redirecionamento(url) {
    return window.location.replace(url);
}

function redirecionamentoNovaAba(url) {
    return window.open(url, '_blank');
}
/*Função que abre janela*/
function abreJan(theURL, winName, features) {
    return window.open(theURL, winName, features);
}
/*Função que capta a data ea hora atual*/
function dataHora() {

    var data = new Date();

    //dia do mês
    var dia = data.getDate();
    //mês atual 
    var mes = data.getMonth();
    //ano atual
    var ano = data.getFullYear();

    return ano + "-" + mes + "-" + dia;
}
/*Funções e código que manipula o scroll e slideToggle*/
function habilitarScroll(estado) {
    if (estado === true) {
        return window.onscroll = function () {};
    } else {
        window.onscroll = function () {
            return window.scrollTo(0, 0);
        };
    }
}

function slidetoggle() {

    var botaoA = document.getElementById("nav1");
    var botaoF = document.getElementById("nav2");
    var slider = document.getElementById("nav-slide");
    var qd_slide = document.getElementById("quadro_nav_slide");

    slider.style.height = window.innerHeight + "px";

    if (slider.style.right === "0vw") {
        habilitarScroll(true);
        botaoA.style.display = "block";
        botaoF.style.display = "none";
        qd_slide.style.display = "none";

        slider.style.right = "-25vw";
        slider.style.width = "0px";
        slider.style.height = "0px";
        slider.style.transition = "all 2s ease-in-out";
        slider.style.display = "none";
    } else {

        botaoA.style.display = "none";
        botaoF.style.display = "block";
        qd_slide.style.display = "block";

        slider.style.display = "block";
        slider.style.right = "0vw";
        slider.style.width = "100%";
        slider.style.height = "100%";
        slider.style.transition = "all 2s ease-in-out";
        habilitarScroll(false);
    }
}

function noSlideToggle() {

    var botaoA = document.getElementById("nav1");
    var botaoF = document.getElementById("nav2");
    var slider = document.getElementById("nav-slide");
    var qd_slide = document.getElementById("quadro_nav_slide");

    slider.style.height = window.innerHeight + "px";
    if (slider.style.width === "100%") {
        botaoA.style.display = "block";
        botaoF.style.display = "none";
        qd_slide.style.display = "none";

        slider.style.right = "-25vw";
        slider.style.width = "0px";
        slider.style.height = "0px";
        slider.style.transition = "all 2s ease-in-out";

        habilitarScroll(true);

        clearInterval(id);
        location.reload();
    }
}
/*Código da página*/
var id;
window.onload = function () {
    id = setInterval(noSlideToggle, 8000);

};
document.body.onresize = function () {
    noSlideToggle();
//    location.reload(true);//Reloads the current page from the server
};
/*Código que manipula a apresentação slide down teste*/
var cont = 0;
var sAgent = window.navigator.userAgent;
/*
 if (sAgent.toLowerCase().indexOf('firefox') > -1) {
 alert("firefox");
 }
 
 // If IE, return version number.
 if(sAgent.indexOf("MSIE") < 9) {
 alert("Atualize: O site não está adaptado para versões pré-históricas o MSEI!");
 }
 // If IE 11 then look for Updated user agent string.
 /*else if (!!navigator.userAgent.match(/Trident\/7\./)){
 alert("IE 11");
 }*/

