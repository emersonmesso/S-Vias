<?php
$con = new Controller();
if (!$con->verificaSessao()) {
    echo "Sair";
    header("Location: ../login");
    echo '<script>window.location.href="../login";</script>';
}
//busca as informações do usuário
$instituicao = $con->dadosInstituicao();
?>

<!--
https://bootstrapious.com/p/bootstrap-sidebar
-->



<div class="wrapper" style="z-index: 1;">
    <!-- Sidebar  -->
    <nav id="sidebar" style="z-index: 3;">

        <div>
            <div class="row justify-content-center">
                <div class="col col-auto">
                    <a class="navbar-brand" href="index.php" style="">
                        <span id="" style="font-size: 1.5em; font-family: 'Arial Black', Gadget, sans-serif;">
                            S-Vias&nbsp;&nbsp;<img src = "../../../_core/_img/logoApp.png" class="logo"/>
                        </span>
                    </a>
                </div>
            </div>
            <p id="welcome"><span>Bem Vindo</span> <?php echo $instituicao->getRazao() ?></p>
        </div>

        <hr>


        <!--        <div class="sidebar-header">
                    <h3><a href="../index.php"><i class="fas fa-arrow-left"></i> S-Vias</a></h3>
                </div>-->

        <ul class="list-unstyled components">
            <li>
                <a href="#">Minhas conta</a>
                <a class="nav-link" href="javascript:void(0)" id="btnLogout">Sair</a>
            </li>

            <li class="active">
                <a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Home</a>
                <ul class="collapse list-unstyled" id="homeSubmenu">
                    <li>
                        <a href="#">Home 1</a>
                    </li>
                    <li>
                        <a href="#">Home 2</a>
                    </li>
                    <li>
                        <a href="#">Home 3</a>
                    </li>
                </ul>
            </li>


        </ul>

        <!--        <ul class="list-unstyled CTAs">
                    <li>
                        <a href="javascript:void(0)" class="download">Download source</a>
                    </li>
                </ul>-->
    </nav>

    <!-- Page Content  -->
    <div id="content">

        <!--      <nav class="navbar navbar-expand-lg navbar-light bg-light">
                 <div class="container-fluid">-->

        <script>
           
            function ddd() {
                 if(document.getElementById('content-op').style.visibility === 'hidden') {
                    document.getElementById('content-op').style.visibility = 'visible';
                } else if(document.getElementById('content-op').style.visibility === 'visible') {
                    document.getElementById('content-op').style.visibility = 'hidden';
                }
                
                return 0;
            }
            
            </script>
        <div style="box-sizing: border-box; height: 10vh; width: 100%;">         
            <button type="button" id="sidebarCollapse" class="btn btn-info" style="float: left; z-index: 3; position: absolute;" onclick="javascript:
              // ddd();
            ">
                <i class="fas fa-align-left"></i>
            </button>
        </div>


        <!--                <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                            <i class="fas fa-align-justify"></i>
                        </button>-->

        <!-- <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="nav navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="javascript:void(0)" id="btnLogout">Sair</a>
                </li>
            </ul>
        </div>
    </div>
</nav> -->

        <!--**
           * Nesta parte do código é implementado o campo de busca e filtros para a denuncia, além da implementação de uma tabela para apresentação dos dados coletados no banco de dados.
           * @author Michael Dydean
           * @since 2019-05-12
           *-->
        <div id="content-op" style="background-color: rgba(0,0,0,0.5); z-index: 2; width: 100%; height: 100%; position: absolute; top: 0px; left: 0px; visibility: hidden;">
        </div>
        <p style="color: black;">
            <span style="font-weight: 700; color: black;">Filtros :</span>

            <label>status
                <select id="select-status-id" class="select" name="select-status" onchange='send();'>
                    <optgroup>
                        <option value="1">pendente</option>
                        <option value="2">em andamento</option>
                        <option value="3">concluídos</option>
                    </optgroup>
                </select>
            </label>

            <script src="../../_javascript/jquery.js"></script>
            <script>
                    function send() {
                        $.ajax({
                            url: "../../../_core/pages/instituicao/requisicao_instituicao_home.php",
                            data: {text: document.getElementById('select-status-id').value, cep: <?php echo $instituicao->getCep(); ?>},
                            type: "GET",
                            success: function (data) {
                                modifyTable(data);
                            },
                            error: function () {
                                alert("Erro ao buscar posíções do mapa");
                            }
                        });

                    }
                    function sendSearch() {
                        $.ajax({
                            url: "../../../_core/pages/instituicao/requisicao_instituicao_home_1.php",
                            data: {termo: document.getElementById('search-termo').value},
                            type: "GET",
                            success: function (data) {
                                modifyTable(data);
                            },
                            error: function () {
                                alert("Erro ao buscar posíções do mapa");
                            }
                        });

                    }
                    function modifyTable(data) {
                        document.getElementById('tableview').innerHTML = data;
                    }
                    function deleteItem() {

                    }

                    function modifyItem() {

                    }
            </script>

            <button style="float:right;" onclick="sendSearch()">ir</button>
            <input id='search-termo' style="float:right;" type="search" />
        </p>

        <?php
        $pagina = (isset($_GET['pagina'])) ? $_GET['pagina'] : 1;


        $total = $con->countStatusDenuncia('1', '' . $instituicao->getCep() . '');

//seta a quantidade de itens por página, neste caso, 2 itens 
        $registros = 5;

//calcula o número de páginas arredondando o resultado para cima 
        $numPaginas = ceil($total / $registros);

//variavel para calcular o início da visualização com base na página atual 
        $inicio = ($registros * $pagina) - $registros;

        $denuncias = $con->statusDenunciaLimit('1', '' . $instituicao->getCep() . '', $inicio, $registros);


        echo PHP_EOL . '
                 <table id="tableview" class="table">
                   <thead class="thead-dark">
                     <tr>
                      <th scope="col">#</th>
                      <th scope="col">Título</th>
                      <th scope="col">Descrição</th>
                      <th scope="col">Status</th>
                      <th scope="col">Cep</th>
                      <th scope="col">Editar</th>
                      <th scope="col">Fotos</th>
                     </tr>
                   </thead>';

        $total = 0;

        foreach ($denuncias as $denuncia) {
            echo PHP_EOL . '<tr>';
            echo PHP_EOL . '<th scope="row">' . $denuncia["id_loc"] . '</th>';
            echo PHP_EOL . '<td id="title-tt">' . $denuncia["titulo"] . '</td>';
            echo PHP_EOL . '<td>' . utf8_encode($denuncia["descricao"]) . '</td>';

            switch ($denuncia["id_class"]) {
                case '1':
                    echo PHP_EOL . '<td>pendente</td>';
                    break;
                case '2':
                    echo PHP_EOL . '<td>em progresso</td>';
                    break;
                case '3':
                    echo PHP_EOL . '<td>concluído</td>';
                    break;
                default :
                    echo PHP_EOL . '<td>erro!</td>';
                    break;
            }

            echo PHP_EOL . '<td>' . $denuncia["cep"] . '</td>';
            echo PHP_EOL . '<td><button onclick="" name="btn-edit" style="cursor: pointer;" value=""><span class="glyphicon" onclick="">&#x270f;</span></button></td>';
            echo PHP_EOL . '<td><button onclick="" name="btn" style="cursor: pointer;" value="">---</button></td>';
            // <td><form method="post"><button name="exc" style="cursor: pointer;" value="$isbn" formaction="excluir_dados.php">x</button></form></td>"
            echo PHP_EOL . '</tr>';

            $total += 1;
        }
        echo PHP_EOL . '</table>';


// <!-- https://getbootstrap.com/docs/4.2/components/pagination/ -->
        echo '<nav style="
             margin-top: 5%;
             align-items: center;
             display: flex;
             flex-direction: row;
             flex-wrap: wrap;
             justify-content: center;
             ">
            <ul class="pagination">
                <li class="page-item disabled">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>';

        for ($i = 1; $i < $numPaginas + 1; $i++) {
            $ativo = ($i == $pagina) ? 'numativo' : '';

            if ($pagina == $i) {
                echo "<li class='page-item active'> "
                . "<a class='page-link' href='?pagina=" . $i . "'> " . $i . " <span class='sr-only'>(current)</span> </a> "
                . "</li>";
            } else {
                echo "<li class='page-item'> "
                . "<a class='page-link' href='?pagina=" . $i . "'> " . $i . "</a> "
                . "</li>";
            }
        }
        echo '
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>';

        /////var_dump($pagina);
////exibe a paginação
//        if ($pagina > 1) {
//            echo "<a href='instituicao/cont.php?pagina=" . ($pagina - 1) . "' class='controle'>&laquo; anterior</a>";
//        }
//
//        for ($i = 1; $i < $numPaginas + 1; $i++) {
//            $ativo = ($i == $pagina) ? 'numativo' : '';
//            echo "<a href='instituicao/cont.php?pagina=" . $i . "' class='numero " . $ativo . "'> " . $i . " </a>";
//        }
//
//        if ($pagina < $numPaginas) {
//            echo "<a href='instituicao/cont.php?pagina=" . ($pagina + 1) . "' class='controle'>proximo &raquo;</a>";
//        }
//        $denuncias = $con->statusDenuncia('1', '' . $instituicao->getCep() . '');
//
//        echo PHP_EOL . '
//                 <table id="tableview" class="table">
//                   <thead class="thead-dark">
//                     <tr>
//                      <th scope="col">#</th>
//                      <th scope="col">Título</th>
//                      <th scope="col">Descrição</th>
//                      <th scope="col">Status</th>
//                      <th scope="col">Cep</th>
//                      <th scope="col">Editar</th>
//                      <th scope="col">Excluir</th>
//                     </tr>
//                   </thead>';
//
//        $total = 0;
//        
//        foreach ($denuncias as $denuncia) {
//            echo PHP_EOL . '<tr>';
//            echo PHP_EOL . '<th scope="row">' . $denuncia["id_loc"] . '</th>';
//            echo PHP_EOL . '<td id="title-tt">' . $denuncia["titulo"] . '</td>';
//            echo PHP_EOL . '<td>' . utf8_encode($denuncia["descricao"]) . '</td>';
//
//            switch ($denuncia["id_class"]) {
//                case '1':
//                    echo PHP_EOL . '<td>pendente</td>';
//                    break;
//                case '2':
//                    echo PHP_EOL . '<td>em progresso</td>';
//                    break;
//                case '3':
//                    echo PHP_EOL . '<td>concluído</td>';
//                    break;
//                default :
//                    echo PHP_EOL . '<td>erro!</td>';
//                    break;
//            }
//
//            echo PHP_EOL . '<td>' . $denuncia["cep"] . '</td>';
//            echo PHP_EOL . '<td><button onclick="" name="btn-edit" style="cursor: pointer;" value=""><span class="glyphicon" onclick="">&#x270f;</span></button></td>';
//            echo PHP_EOL . '<td><button onclick="" name="btn" style="cursor: pointer;" value="">x</button></td>';
//            // <td><form method="post"><button name="exc" style="cursor: pointer;" value="$isbn" formaction="excluir_dados.php">x</button></form></td>"
//            echo PHP_EOL . '</tr>';
//            
//            $total += 1;
//        }
//        echo PHP_EOL . '</table>';
//        
//      
        ?>


    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>