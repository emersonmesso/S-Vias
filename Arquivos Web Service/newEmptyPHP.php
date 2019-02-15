<!DOCTYPE html>
<?php
/*
 * Autor: Michael Dydean
 * Data de criação: 2019-02-13.
 * Data de modificação: 2019-02-14.
 */


session_start(); //inicia a sessão
?>

<?php if (isset($_SESSION['active'])): ?> 
    <!-- <h1>Logado com sucesso!</h1> -->
    <?php
    //  echo $_SESSION['type'] . "<br/>";
    //echo $_SESSION['login'] . "<br/>";
    ?>
    <html>
        <head>
            <title>TODO supply a title</title>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="_styles/bootstrap.css">
            <style>
                  #map {
                    height: 100%;
                }
                /* Optional: Makes the sample page fill the window. */
                html, body {
                    height: 100%;
                    margin: 0;
                    padding: 0;
                }
                .bg-menu {
                    background: #FEDC00 linear-gradient(180deg, #FACC2E, #FEDC00) repeat-x !important;
                }    #menu-itens {
                    width: 50px;
                    height: 50px;
                    display: block;
                    background: #FACC2E;
                    border: 1px solid #828a91;
                    border-radius: 8px;
                    box-shadow: 2px 2px 3px #3a3a3a;
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
                /*            li {
                                list-style: none;
                                display: inline-block;
                            }
                            #menu > li {
                                padding-left: 5%;
                                color: #FACC2E;
                                background-color: black;
                            }*/
                a, a:hover, a:active, a:link {
                    text-decoration: none;
                }
                .marker {
                    width: 15vw;
                    display: block;
                    border: 1px solid black;
                    padding: 1vw;
                }
                .marker h1 {
                    text-align: center;
                    font-size: 2em;
                    font-weight: bold;
                    border-bottom: 1px solid #dedede;
                }
                .marker p {
                    padding-top: 2%;
                }

                /* ---------------------  */

                a { 
                    color: #FACC2E;;
                    text-decoration: none;/*retira o sublinhado do link*/
                }
                a:hover {
                    color: rgba(10, 10, 245, 0.5);
                    cursor: pointer;/*coloca o ponteiro de mão*/
                }
                li {
                    color: #FACC2E;;

                    text-align: center;
                    list-style: none;
                    font-family: sans-serif;
                }
                img {
                    /*remove selecão de texto*/
                    -moz-user-select: none;
                    -webkit-user-select: none;
                    -ms-user-select: none;  
                    -user-select: none;/*outros navegadores*/
                }
                nav-btn {
                    top: 7vh;
                }
                #nav1 > div {
                    /*                    height: 0.3vh;*/
                }
                #nav-slide {
                    top: 13%;
                    height: 0px;
                }
                #sessao_links {
                    height: 100vh;
                    padding-top: 14vh;
                }
                #sessao_links > ul > li {
                    padding-top: 3vh;
                }
                .nav-btn {
                    left: 85%;
                    position: absolute;
                    border: 1px solid #bababa;
                    border-radius: 2px;
                    cursor: pointer;
                    background: #FACC2E;
                    transition: all 0.4s linear;
                    z-index: 11;
                    display: block;
                }
                #nav1 {
                                        padding: 5px 11px 5px 15px;
                }
                #nav2 {
                    padding: 5px 11px 5px 15px;
                    display: none;
                }
                #nav1:hover {
                    border: #0061f2 1px solid;
                }
                #nav2:hover {
                    border: #0061f2 1px solid;
                }
                #nav1 > div {
                    /*                    width: 90%;
                                        background: #dcdcdc;
                                        margin: 6px 0px;*/
                    transition: all 0.4s linear;
                }
                #nav1:hover div {
                    background: #828282;
                    -webkit-box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.2), 0 1px 1px 0 rgba(0, 0, 0, 0.2);
                    -moz-box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.2), 0 1px 1px 0 rgba(0, 0, 0, 0.2);
                    box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.2), 0 1px 1px 0 rgba(0, 0, 0, 0.2);
                }
                #nav2 > img {
                    transition: all 0.4s linear;
                }
                #nav-slide {
                    top: 0px;
                    position: absolute;
                    right:-25vw;
                    width: 0px;
                    background-color: rgba(0, 0, 0, 0.3);
                    transition: right 0.9s linear;
                    z-index: 7;
                    display: none;
                }
                #quadro_nav_slide {
                    display: none;
                }
                #sessao_links {
                    margin-left: 75vw;
                    width: 35vw;
                    padding: 0%;
                    padding-right: 1.5%;
                    opacity: 1;
                    background-color: black;
                    -webkit-user-select: none;
                    -moz-user-select: none;
                    -ms-user-select: none;
                    -user-select: none;
                }
                #sessao_links > ul {
                    top: 20vh;
                    position: absolute;
                    padding: 0%;
                    margin: 0%;
                    text-transform: uppercase;
                }
                #sessao_links > ul > li {
                    width: 18vw;
                    padding-bottom: 3vh;
                    margin: 5vh;
                    border: 2px solid #ddd;
                    font-size: 1.3em;
                    cursor: pointer;
                }
                #sessao_links > ul > li:hover {
                    background-color: #ddd;
                    border: 2px solid #ddd;
                    color: black;
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
            </style>
        </head>
        <body>
            <nav style="z-index: 8;" class="navbar navbar-expand-lg bg-menu">
                <!-- Logomarca -->
                <a class="navbar-brand" href="index.php" style="padding: 2%;">
                    <p style="color: #212529; position: absolute; top: 10%; left: 1%; z-index: 2; font-size: 1.3em; font-weight: 700;">S-Vias</p>
                    <image src="_images/Logo.png" id="logotipo"/>
                    <br/>
                    <!--<span class="lead" style="font-family: 'Libre Baskerville', serif; font-size: 0.9em;color:#888; font-weight:bold; margin-left: 0%; margin-top: 5px;">
                        Centro de Convenções | Natal - RN| 25 e 26 de Julho&nbsp;2018 
                    </span> -->
                </a>
                <input type="search" placeholder="Pesquisar" style="position: fixed; width: 30%; height: 5vh; margin-left: 25%; border-radius: 10px; border: none; padding-left: 3%;" />
                <!-- Barra de navegação recolhível -->
                <!--                <nav id='nav1' class='nav-btn' title="Abrir" onclick='slidetoggle()'>
                                    <div></div>
                                    <div></div>
                                    <div></div>
                                </nav>-->
                <button id="nav1" class="nav-btn border border-secondary" type="button" title="Abrir" onclick='slidetoggle()'>
                    <div class="content-menu"></div>
                    <div class="content-menu"></div>
                    <div class="content-menu"></div>
                    <div class="content-menu"></div>
                    <div style="background: none; margin-top: 4px;" class="content-menu"></div>
                </button>
                <!--                <nav id='nav2' class='nav-btn' onclick='slidetoggle()'>
                                    <img id='icone_fechar' src='_images/close.png' alt="Fechar" title='Fechar' draggable="false"/>
                                </nav>-->
                <button id="nav2" class="nav-btn border border-secondary" type="button" title="Abrir" onclick='slidetoggle()'>
                    <img id='icone_fechar' src='_images/close.png' alt="Fechar" title='Fechar' draggable="false"/>
                </button>
            </nav>

            <aside id='nav-slide'>
                <div id='quadro_nav_slide'>
                    <section id="sessao_links">
                        <br/>
                        <br/>
                        <ul>
                            <li>pesquisa</li>
                            <li>gerenciar denuncias</li>
                            <li>conta</li>
                            <li><a href="logout.php">logout</a></li>
                        </ul>
                    </section>
                </div>
            </aside>
            <script src="_scripts/globaldoc_js.js"></script>
        </body>
    </html>
<?php else: ?>
    <?php header("location: logout.php"); //redireciona o logout ?>
<?php endif; ?>
