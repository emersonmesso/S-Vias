<?php
/*
 * Autor: Michael Dydean
 * Data de criação: 2018-12-07.
 * Data de modificação: 2019-02-12.
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
                <div class="col col-3"></div>
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
                    <a class="link" href="create_profile.php">Não tenho conta</a>
                </div>
                <div class="col col-3"></div>
            </div>

            <!-- Login S-Vias -->
            <div id="login-svias" class="row justify-content-center"> 
                <!-- Login S-Vias -->
                <div class="col col-12">
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
                        <div class="group-link">
                            <ul>
                                <li>
                                    <a class="link" style="width: 40%; margin-left: 5%; float: left; border-right: 1px solid #9e9e9e;" href="create_profile.php">Não tenho conta</a>
                                    <a class="link" style="width: 40%; margin-left: 0%; margin-right: 5%; float: left; border-left: 1px solid rgba(100, 100, 100, 0.8);" href="recover_profile.php">Recuperar acesso</a>
                                </li>
                            </ul>
                        </div>
                    </form>
                </div>
            </div>
            <!-- footer site s-vias  -->
            <footer class="row" style="position: absolute; bottom: 0vh; width: 75%;">
                <hr/>
                <div class="col col-4"></div>
                <div class="col col-4">
                    <p style="font-size: 1vw; font-family: 'Arial', Gadget, sans-serif;">S-Vias 2018 | SDea Development</p>
                </div>
                <hr/>
                <div class="col col-4"></div>
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
