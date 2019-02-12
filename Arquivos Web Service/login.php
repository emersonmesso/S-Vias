<?php
/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-07.
 * Data de modificação: 2019-02-11.
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
        <title>Login</title>
        <link rel="stylesheet" href="_styles/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="_styles/login.css" />

        <style>
            li {
                list-style-type: none;
            }
            #login-svias {
                display: none;
            }
            #content-login-i {
                display: none;
            }
            #s-logo {
                width: 40%; 
                /*                align-items: center;
                                flex-direction: row;
                                flex-wrap: wrap;
                                justify-content: center;
                                display: flex;
                */
                width: 10%; 
                position:absolute;
                top:10%;
                left:45%;
                margin-top:-50px;
                margin-left:-50px;
                z-index: 3;
            }
            #login2{
                background: #f7f7f7;
                border: 1px solid rgba(147, 184, 189,0.8);
                -webkit-box-shadow: 5px;
                border-radius: 5px;
                width: 50%;
                margin-left: 25%;
                padding: 5%;
            }
            footer {
                padding-top: 5vh;
                padding-bottom: 5vh;
            }
        </style>
    </head>
    <body>
        <div style="height: 100vh; padding-top: 5vh;" class="container">
            <div class="row justify-content-center">
                <div class="col col-auto">
                    <a class="navbar-brand" href="index.php" style="">
                        <span id="" style="font-size: 2em; color: #FEDC00;font-family: 'Arial Black', Gadget, sans-serif;">
                            S-Vias<img src = "_images/imagem2.png" class = "logo" />
                        </span>
                    </a>
                </div>
            </div>

            <!-- Select Login S-Vias | Google  -->
            <div class="row" style="padding-top: 5vh;">
                <div class="col col-3 border"></div>
                <div id="login1" class="col col-6">
                    <ul> 
                        <li id="l-svias" style="width: 100%; height: 8vh; margin-bottom: 5%; z-index: 2" class="border border-secondary py-2 rounded" 
                            onclick="document.getElementById('login1').style.display = 'none';
                                    window.location.assign('#login-svias');
                                    document
                                            .getElementById('login-svias').style.display = 'block';">
                            <p style="text-align: center;"><a href="#login-svias">Login S-Vias</a></p>
                        </li>
                        <li id="l-google" style="width: 100%; height: 8vh;  z-index: 2" class="border border-secondary py-2 rounded"> <!--onclick="document
                                        .getElementById('login1').style.display = 'none';
                                window.location.assign('#');
                                document
                                        .getElementById('login-svias').style.display = 'block';">-->
                            <p style="text-align: center;"><a href="#">Login Google</a></p>
                        </li>
                    </ul>
                </div>
                <div class="col col-3 border"></div>
            </div>

            <!-- Login S-Vias -->
            <div id="login-svias" class="row justify-content-center"> 
                <!-- Login S-Vias -->
                <div class="col col-12 border">
                    <form id="login2" method="post" action="active_login.php"> 
                        <script>
                            function changeSelect() {
                                var obj = document.getElementById('select-login');
                                if (obj.value === 'Cid') {
                                    document.getElementById('content-login-i').style.display = 'none';
                                    document.getElementById('content-login-c').style.display = 'block';
                                } else if (obj.value === 'Inst') {
                                    document.getElementById('content-login-c').style.display = 'none';
                                    document.getElementById('content-login-i').style.display = 'block';
                                }
                            }
                        </script>
                        <label>Selecione :
                            <select id="select-login" class="select" name="select-l" onchange="changeSelect();">
                                <optgroup>
                                    <option value="Cid" selected>Cidadão</option>
                                    <option value="Inst">Instituição</option>
                                </optgroup>
                            </select>
                        </label>
                        <div id="content-login-c">
                            <p> 
                                <input id="nome_login" name="email_n" type="text" placeholder="Username or E-mail" autocomplete/>
                            </p>
                            <br>
                            <p> 
                                <input id="pass_login" name="pass_n" type="password" placeholder="Password" /> 
                            </p>
                            <p> 
                                <input name="env" type="submit" value="Entrar" />
                            </p>
                        </div>
                        <div id="content-login-i">
                            <p> 
                                <input id="nome_login_i" name="email_n" type="text" placeholder="Username or E-mail"/>
                            </p>
                            <br>
                            <p> 
                                <input id="pass_login_i" name="pass_n" type="password" placeholder="Password" /> 
                            </p>
                            <p> 
                                <input name="env" type="submit" value="Entrar" />
                            </p>
                        </div>
                    </form>
                </div>
            </div>
            <!-- footer site s-vias  -->
            <footer class="row" style="position: absolute; bottom: 0vh; width: 75%;">
                <hr/>
                <div class="col col-4 border"></div>
                <div class="col col-4 border">
                    <p style="font-size: 1vw; font-family: 'Arial', Gadget, sans-serif;">S-Vias 2018 | SDea Development</p>
                </div>
                <hr/>
                <div class="col col-4 border"></div>
                <div class="col border">
                    <ul style="list-style-type: none;">
                        <li style="display: inline-block; margin-left: 1%;"><a href="index.php">Home</a></li>
                        <li style="display: inline-block; margin-left: 1%;">Sobre</li>
                        <li style="display: inline-block; margin-left: 1%;">Ajuda</li>
                    </ul>
                </div>
            </footer>
        </div>
    </body>
</html>
