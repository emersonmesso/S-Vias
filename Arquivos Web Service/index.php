<?php

/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-07.
 * Data de modificação: 2018-12-21.
 */
?>
<!DOCTYPE html>
<html>
    <head>
         <meta name="viewport" content="initial-scale=1.0">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="description" content="">
        <link type = "image/png" rel = "icon" href = "_images/Imagem2.png" id = "icone" />
       <title>S-Vias</title>
        <link rel="stylesheet" href="_styles/bootstrap.css">
        <link rel="stylesheet" href="_styles/modals.css"/>
        <link rel="stylesheet" href="_styles/main.css"/>
        <style>
            .info-marker h1 {
                text-align: center;
                font-size: 2em;
                font-weight: bold;
            }
            .info-marker ul > li {
                list-style: none;
                font-size: 1em;
            }
            .info-marker ul > li > span {
                font-weight: bold;
            }

            .info-aloc-marker h2 {
                font-size: 1.2em;    
            }
            .info-aloc-marker ul > li {
                list-style: none;
                font-size: 1em;
            }
            .info-aloc-marker ul > li > span {
                font-weight: normal;
            }
            #menu-itens {
                width: 50px;
                height: 50px;
                display: block;
                background: #FACC2E;
                border: 1px solid #828a91;
                border-radius: 8px;
                box-shadow: 2px 2px 3px #3a3a3a;
            }
            .content-menu {
                width: 40px;
                height: 2px;
                display: block;
                background: #828a91;
                margin-top: 6px;
                margin-left: auto;
                margin-right: auto;
            }
            #logotipo {
                width: 130px;
                height: 70px;
                display: block;
                z-index: 1;
                position: absolute; 
                top: 1px;
                left: 12px;
            }
            li {
                list-style: none;
                display: inline-block;
            }
            #menu > li {
                padding-left: 5%;
                color: #FACC2E;
                background-color: black;
            }
            a, a:hover, a:active, a:link {
               text-decoration: none;
            }
        </style>
    </head>
    <body>

        <nav class="navbar navbar-expand-lg bg-menu">
            <!-- Logomarca -->
            <a class="navbar-brand" href="index.php" style="padding: 2%;">
                <p style="position: absolute; top: 10%; left: 1%; z-index: 2; font-size: 1.3em; font-weight: 700;">S-Vias</p>
                <image src="_images/Logo.png" id="logotipo"/>
                <br/>
                <!--<span class="lead" style="font-family: 'Libre Baskerville', serif; font-size: 0.9em;color:#888; font-weight:bold; margin-left: 0%; margin-top: 5px;">
                    Centro de Convenções | Natal - RN| 25 e 26 de Julho&nbsp;2018 
                </span> -->
            </a>
            <input type="search" placeholder="Pesquisar" style="width: 30%; height: 45px; margin-left: 20%; border-radius: 10px; border: none; padding-left: 3%;" />
            <!-- Barra de navegação recolhível -->
            <button class="navbar-toggler border border-secondary" type="button" data-toggle="collapse" data-target="#navbar_site">
                <div class="content-menu"></div>
                <div class="content-menu"></div>
                <div class="content-menu"></div>
                <div class="content-menu"></div>
                <div style="background: none; margin-top: 4px;" class="content-menu"></div>

            </button>
            <!-- menu do site -->
            <div class="collapse navbar-collapse" id="navbar_site">
                <ul class="menu" style="margin-left: 35%;"> 
                    <li style="width: 7vw;" class="border border-secondary py-2 rounded" onclick="window.location.assign('login.php');"> <!-- <li class="nav-item text-right"> -->
                        <p style="text-align: center;" ><a href="login.php">Login</a></p>
                    </li>
                    <li style="width: 7vw;" class="border border-secondary py-2 rounded" onclick="window.location.assign('#');">
                        <p style="text-align: center;"><a href="#">Sobre</a></p>
                    </li>
                    <li style="width: 7vw" class="border border-secondary py-2 rounded" onclick="window.location.assign('#');">
                        <p style="text-align: center;"><a href="#">Help</a></p>
                    </li>
                </ul>

            </div>
        </nav>


        <div id="add"></div>
        <div id="map"></div>

        <script>
            setInterval(function () {
                window.location.reload();//reload em 5 minutos
            }, 300000);

            var map;
            var coords;
            var contClick = 1;
            var coord1, coord2;
            var center = {
            }

            function initMap() {
                //opções
                var options = {
                    zoom: 13,
                    center: {
                        lat: -8.0725, lng: -39.1268 //posição inicial no mapa
                    }
                }

                //mapa
                var map = new google.maps.Map(document.getElementById('map'), options);

                /**
                 * O código abaixo é uma função que adiciona os marcadores no, por meio do evento click.
                 
                 google.maps.event.addListener(map, 'click', function (event) {
                 
                 var coords = event.latLng;
                 var lat = coords.lat();
                 var lng = coords.lng();
                 
                 alert('posso adicionar informações do servidor nesse momento!!!');
                 addMarker(coords);
                 enviarCoords("lat:" + lat + ",lng:" + lng);
                 //capturar as cordenadas a partir do 'event.latLng'
                 
                 window.location.assign('index.php');
                 
                 /*código para recarregar a página
                 
                 // location.reload(true);//false - pagina em cache, true - pagina do servidor
<?php
/* código para recarregar a página */

//header("Refresh: 0");
//header("Refresh: 0; url=index.php");
//header('Location: index.php');
?>
                 
                 });
                 **/

                //função adicionar marcadores
                function addMarker(coords, info) {
                    this.coords = coords;
                    //marcadores
                    var marker = new google.maps.Marker(
                            {
                                position: coords,
                                map: map,
                                icon: '_images/server.png'
                            });
                    //janelas de informações
                    var infoWindow = new google.maps.InfoWindow({
                        content: info
                    });
                    //eventos
                    marker.addListener('click', function () {
                        addLink(marker.getPosition());

                    });
                    marker.addListener('mouseover', function () {
                        infoWindow.open(map, marker)
                    });
                    marker.addListener('mouseout', function () {
                        infoWindow.close()
                    });
                    /*
                     * A função de arrastar o marcador será desativada, pois se faz
                     * necessário implementar um código para atualizar a nova coordenada
                     * do marcador no 'arquivo de coordenadas'.
                     
                     marker.setDraggable(true);
                     */
                }

            }

        </script>

        <script asyn defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDDaFKMowME4EWRAPFmRUwp-kTu1ndQ3JM&callback=initMap" async defer></script>

        <!-- scripts embarcados -->
        <script src="_scripts/jquery.js"></script>
        <script src="_scripts/popper.js"></script>
        <script src="_scripts/bootstrap.js"></script>

        <footer style="padding-top: 1%; margin-left: 65%;">
            <p style="float: left;margin-right: 1%;">S-Vias 2018&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</p>
            <ul style="list-style-type: none;">
                <li style="display: inline-block; margin-left: 1%;"><a href="login.php">Login</a></li>
                <li style="display: inline-block; margin-left: 1%;">Sobre</li>
                <li style="display: inline-block; margin-left: 1%;">Ajuda</li>
            </ul>
        </footer>

    </body>
</html>