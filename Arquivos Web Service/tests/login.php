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
        </style>
    </head>
    <body>
        
        <div class="container">
        
        <div id="s-logo" style="">
            <a class="navbar-brand" href="index.php" style="">
                <span id="" style="font-size: 2em; color: #FEDC00;font-family: 'Arial Black', Gadget, sans-serif;">
                    S-Vias<img src = "_images/imagem2.png" class = "logo" />
                </span>
            </a>

        </div>
        <!-- Login S-Vias -->
        <div id="login-svias" class="content"> 
            <!-- Login S-Vias -->
            <div id="login2">
                <form method="post" action="active_login.php"> 
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
                            <input id="nome_login" name="nome_login_n" type="text" placeholder="Username or E-mail"/>
                        </p>
                        <br>
                        <p> 
                            <input id="pass_login" name="pass_login_n" type="password" placeholder="Password" /> 
                        </p>
                        <p> 
                            <input name="env" type="submit" value="Entrar" />
                        </p>
                    </div>
                    <div id="content-login-i">
                        <p> 
                            <input id="cnpj" name="cnpj_n" type="text" placeholder="cnpj for institute"/>
                        </p>
                        <br>
                        <p> 
                            <input id="pass_inst" name="pass_inst_n" type="password" placeholder="Password" /> 
                        </p>
                        <p> 
                            <input name="env" type="submit" value="Entrar" />
                        </p>
                    </div>
                </form>
            </div>
        </div>
        <!-- Select Login S-Vias | Google  -->
        <div class="content"> 
            <div id="login1">
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
        </div>
        <!-- footer site s-vias  -->
        <footer style="padding-top: 1%; margin-left: 70%; margin-top: 10%;">
            <p style="float: left; margin-right: 1%;">S-Vias 2018&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|</p>
            <ul style="list-style-type: none;">
                <li style="display: inline-block; margin-left: 1%;"><a href="index.php">Home</a></li>
                <li style="display: inline-block; margin-left: 1%;">Sobre</li>
                <li style="display: inline-block; margin-left: 1%;">Ajuda</li>
            </ul>
        </footer>
        
        </div>
        
    </body>
</html>
