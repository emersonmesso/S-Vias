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
<div class="wrapper">
    <!-- Sidebar  -->
    <nav id="sidebar">
        <div class="sidebar-header">
            <h3><a href="../index.php"><i class="fas fa-arrow-left"></i> S-Vias</a></h3>
        </div>

        <ul class="list-unstyled components">
            <p>Bem Vindo!
                <br />
                <?php echo $instituicao->getRazao() ?></p>

            <li>
                <a href="#">Minhas Denúncias</a>
            </li>
            <!--
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
            -->

        </ul>

        <ul class="list-unstyled CTAs">
            <li>
                <a href="javascript:void(0)" class="download">Download source</a>
            </li>
        </ul>
    </nav>

    <!-- Page Content  -->
    <div id="content">

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">

                <button type="button" id="sidebarCollapse" class="btn btn-info">
                    <i class="fas fa-align-left"></i>
                </button>
                <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <i class="fas fa-align-justify"></i>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="nav navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="javascript:void(0)" id="btnLogout">Sair</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <!--**
           * Nesta parte do código é implementado o campo de busca e filtros para a denuncia, além da implementação de uma tabela para apresentação dos dados coletados no banco de dados.
           * @author Michael Dydean
           * @since 2019-05-12
           *-->

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
                            data: {text: document.getElementById('select-status-id').value},
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
            </script>

            <button style="float:right;">ir</button>
            <input style="float:right;" type="search" />
        </p>

   <?php
        $denuncias = $con->statusDenuncia('1', '' . $instituicao->getCep() . '');

        $cont = 1;

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
                      <th scope="col">Excluir</th>
                     </tr>
                   </thead>';

        foreach ($denuncias as $denuncia) {
            echo PHP_EOL . '<tr>';
            echo PHP_EOL . '<th scope="row">' . $cont++ . '</th>';
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
            echo PHP_EOL . '<td><button onclick="" name="btn" style="cursor: pointer;" value="">x</button></td>';
            // <td><form method="post"><button name="exc" style="cursor: pointer;" value="$isbn" formaction="excluir_dados.php">x</button></form></td>"
            echo PHP_EOL . '</tr>';
        }
          echo PHP_EOL . '</table>';
        ?>
     
        </table>
        <!-- https://getbootstrap.com/docs/4.2/components/pagination/ -->
        <nav style="
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
                </li>
                <li class="page-item active"><a class="page-link" href="#">
                        1<span class="sr-only">(current)</span>
                    </a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>

    </div>
</div>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
