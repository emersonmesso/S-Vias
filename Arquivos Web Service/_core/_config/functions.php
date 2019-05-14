<?php

require "_model/cidadao.php";
require "mysql.php";
require "_model/instituicao.php";
require "_model/denuncia.php";

class Controller {

    private $url;
    private $sql;

    function Controller() {
        $this->inicia();
        if (!isset($_SERVER['HTTPS']) || $_SERVER['HTTPS'] != 'on') {
            $redirect_url = "https://" . $_SERVER['HTTP_HOST'] . $_SERVER['REQUEST_URI'];
            header('HTTP/1.1 301 Moved Permanently');
            header("Location: $redirect_url");
            exit();
        }
    }

    function inicia() {
        $_SERVER['REQUEST_URI'];
        $parte1 = strrchr($_SERVER['REQUEST_URI'], "?");
        $parte2 = str_replace($parte1, "", $_SERVER['REQUEST_URI']);
        $this->url = explode("/", $parte2);
        array_shift($this->url);
        $this->conecta();
    }

    public function conecta() {
        //
        $mysql = new Mysql();
        return new mysqli($mysql->getHost(), $mysql->getUser(), $mysql->getPass(), $mysql->getBanco());
        //
    }

    //HEADER
    public function header() {
        //buscando o cabeçalho do arquivo
        if ($this->url[0] == "") {
            //inicial
            include "_core/view/header.php";
        } else {
            //verifica se existe a pasta
            if (file_exists("_core/pages/" . $this->url[0] . "/")) {
                include "_core/pages/" . $this->url[0] . "/header.php";
            } else {
                header("Location: ../");
            }
        }
    }

    //CONTEUDO
    public function conteudo() {
        if ($this->url[0] == "") {
            //inicial
            include "_core/view/cont.php";
        } else {
            include "_core/pages/" . $this->url[0] . "/cont.php";
        }
    }

    //FOOTER
    public function footer() {
        if ($this->url[0] == "") {
            //inicial
            include "_core/view/footer.php";
        } else {
            include "_core/pages/" . $this->url[0] . "/footer.php";
        }
    }

    //SELECT no banco de dados
    public function select($tabela, $todos = NULL, $where = NULL, $order = NULL) {
        //inicia a classe
        $mysql = $this->conecta();

        if ($todos == NULL) {
            $todos = "*";
        }
        if ($where != NULL) {
            $where = " WHERE " . $where;
        }
        if ($order != NULL) {
            $order = " ORDER BY " . $order;
        }
        $sql = "SELECT {$todos} FROM {$tabela}{$where}{$order}";
        $query = $mysql->query($sql);
        //fecha a coneção
        mysqli_close($mysql);
        return $query;
    }

    //DELETE no banco de dados
    public function delete($tabela, $where) {
        $mysql = $this->conecta();
        //
        if ($where != NULL) {
            $where = " WHERE " . $where;
        }
        //criando a consulta
        $sql = "DELETE FROM {$tabela}{$where}";
        $query = $mysql->query($sql);
        //fecha a coneção
        mysqli_close($mysql->sql);
        return $query;
    }

    //UPDATE no banco de dados
    public function update($tabela, $valores, $where) {
        $mysql = $this->conecta();
        //
        if ($where != NULL) {
            $where = " WHERE " . $where;
        }
        //
        $sql = "UPDATE {$tabela} SET {$valores} {$where}";
        //executa a Query
        $query = $mysql->query($sql);
        //fecha a coneção
        mysqli_close($mysql->sql);
        return $query;
    }

    //INSERE no banco de dados
    public function insere($tabela, $campos, $valores) {
        //inicia a classe
        $mysql = new mysql();
        //instancia a conexão
        $mysql->conecta();
        //
        $sql = "INSERT INTO {$tabela}({$campos}) VALUES({$valores})";
        //executa a Query
        $query = $mysql->sql->query($sql);
        //fecha a coneção
        mysqli_close($mysql->sql);
        return $query;
    }

    public function gerLinkPastas() {
        //string das url
        $resultado = "";
        //pega o tamanho dos links
        $total = count($this->url);

        for ($i = 0; $i < $total; $i++) {
            $resultado .= "../";
        }
        echo $resultado;
    }

    /* TELA DE INÍCIO DA PÁGINA */

    //Barra de navegação
    public function navBarPage() {
        echo '<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #FACC2E;" id="navBarInicio">';
        echo '<div class="container">';
        echo '<img src="./_core/_img/logoApp.png" class="navbar-brand" alt="Logo aplicativo" title="Logo Aplicativo" width="45px" style="border-radius: 50% !important;" />';
        echo '<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">';
        echo '<span class="navbar-toggler-icon"></span>';
        echo '</button>';
        echo '<div class="collapse navbar-collapse" id="navbarNavAltMarkup">';
        echo '<div class="navbar-nav">';
        echo '<a class="nav-item nav-link active" href="index.php">Inicio <span class="sr-only">(current)</span></a>';
        echo '<a class="nav-item nav-link" href="denuncias">Denúncias</a>';
        echo '<a class="nav-item nav-link" href="login">Login</a>';
        //echo '<a class="nav-item nav-link" href="#telaContatoLocal">Contato</a>';
        echo '</div>';
        echo '</div>';
        echo '</div>';
        echo '</nav>';
    }

    public function navBarPageIndex() {
        echo '<nav class="navbar navbar-expand-lg navbar-light" id="navBarInicio">';
        echo '<div class="container">';
        echo '<img src="./_core/_img/logoApp.png" class="navbar-brand" alt="Logo aplicativo" title="Logo Aplicativo" width="45px" style="border-radius: 50% !important;" />';
        echo '<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">';
        echo '<span class="navbar-toggler-icon"></span>';
        echo '</button>';
        echo '<div class="collapse navbar-collapse" id="navbarNavAltMarkup">';
        echo '<div class="navbar-nav">';
        echo '<a class="nav-item nav-link active" href="index.php">Inicio <span class="sr-only">(current)</span></a>';
        echo '<a class="nav-item nav-link" href="denuncias">Denúncias</a>';
        echo '<a class="nav-item nav-link" href="login">Login</a>';
        echo '<a class="nav-item nav-link" href="javascript:void(0)">Contato</a>';
        echo '<a class="nav-item nav-link" href="javascript:void(0)">Sobre</a>';
        echo '</div>';
        echo '</div>';
        echo '</div>';
        echo '</nav>';
    }

    //Imagens Do Aplicativo e descrição
    public function imgCarousel() {
        $array = array();
        $pasta = '_core/_img/carrousel/';

        $diretorio = dir($pasta);
        while (($arquivo = $diretorio->read()) !== false) {
            if (strlen($arquivo) > 2) {
                $array[] = $pasta . $arquivo;
            }
        }
        $diretorio->close();
        return $array;
    }

    public function Carrousel() {
        echo '<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">';
        for ($a = 0; $a < count($this->imgCarousel()); $a++) {
            if ($a == 0) {
                echo '<li data-target="#carouselExampleIndicators" data-slide-to="' . $a . '" class="active" style="z-index:1;"></li>';
            } else {
                echo '<li data-target="#carouselExampleIndicators" data-slide-to="' . $a . '" style="z-index:1;"></li>';
            }
        }
        echo '</ol>
                <div class="carousel-inner">
                ';

        $cont = 1;
        foreach ($this->imgCarousel() as $image) {
            if ($cont == 1) {
                echo '<div class="carousel-item active">
                                    <img class="d-block w-100" src="' . $image . '" alt="First slide">
                                </div>';
            } else {
                echo '<div class="carousel-item">
                                   <img class="d-block w-100" src="' . $image . '" alt="">
                                </div>';
            }
            $cont++;
        }
        echo '  </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                  <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  <span class="sr-only">Next</span>
                </a>
        </div>';
    }

    /* LOGIN E SESSÕES */

    public function criaSessao($email, $check) {
        if (session_status() !== PHP_SESSION_ACTIVE) {
            session_start();
        }
        $_SESSION['email'] = $email;
        $_SESSION['tipo'] = $check;
        return true;
    }

    public function verificaSessao() {
        if (session_status() !== PHP_SESSION_ACTIVE) {
            session_start();
        }
        if (isset($_SESSION['email'])) {
            return true;
        } else {
            return false;
        }
    }

    public function redirecionaSessao() {
        if (session_status() !== PHP_SESSION_ACTIVE) {
            session_start();
        }
        if (isset($_SESSION['email'])) {
            if ($_SESSION['tipo'] == 1) {
                //cidadao
                echo '<script>window.location.href="../cidadao";</script>';
            } else {
                //instituição
                echo '<script>window.location.href="../instituicao";</script>';
            }
        }
    }

    public function dadosCidadao() {
        $email = $_SESSION['email'];
        $sql = $this->select("cidadao", "*", "email = '$email'");
        $cid = new Cidadao();
        while ($cidadao = mysqli_fetch_array($sql)) {
            $cid->setCpf($cidadao['cpf']);
            $cid->setEmail($cidadao['email']);
            $cid->setNome($cidadao['nome']);
            $cid->setSenha($cidadao['senha']);
        }
        return $cid;
    }

    public function dadosInstituicao() {
        $sql = $this->select("instituicao", "*", "email = '" . $_SESSION['email'] . "'");
        $instituicao = new Instituicao();
        while ($dado = mysqli_fetch_array($sql)) {
            $instituicao->setBairro($dado['bairro']);
            $instituicao->setCep($dado['cep']);
            $instituicao->setEmail($dado['email']);
            $instituicao->setSenha($dado['senha']);
            $instituicao->setCidade($dado['cidade']);
            $instituicao->setCnpj($dado['cnpj']);
            $instituicao->setEndereco($dado['endereco']);
            $instituicao->setNum_ende($dado['num_ende']);
            $instituicao->setUf($dado['uf']);
            $instituicao->setTelefone($dado['telefone']);
            $instituicao->setRazao($dado['razao_social']);
        }
        return $instituicao;
    }

    public function dadosDenuncia($id) {
        $sql = $this->select("denuncia", "*", "id_loc = $id");
        $denuncia = new Denuncia();
        while ($row = mysqli_fetch_array($sql)) {
            $denuncia->setTitulo($row['titulo']);
            $denuncia->setCep($row['cep']);
            $denuncia->setCidade($row['cidade']);
            $denuncia->setImagem($this->imagemDenuncia($id));

            $data = new DateTime();
            $data->setTimestamp($row['data']);
            $denuncia->setData($data->format("d/m/Y"));
            if ($row['id_class'] == 1) {
                $denuncia->setClass("Pendente");
            } else if ($row['id_class'] == 2) {
                $denuncia->setClass("Em Progresso");
            } else {
                $denuncia->setClass("Concluído");
            }

            $denuncia->setEmail_cid($row['email_cid']);
            $denuncia->setLati($row['lati']);
            $denuncia->setLong($row['lng']);
            $denuncia->setRua($row['rua']);
            $denuncia->setDesc($row['descricao']);
        }
        return $denuncia;
    }

    public function imagemDenuncia($id) {
        $sql = $this->select("imagens", "*", "id_loc = $id");
        $imagem = "";
        if (mysqli_num_rows($sql) > 0) {
            while ($img = mysqli_fetch_array($sql)) {
                $imagem = "../../_api/images/" . $img['img_den'];
            }
            return $imagem;
        } else {
            return "";
        }
    }

    public function cidadeDenuncia($cep) {
        $sql = $this->select("cidades", "*", "cep = '$cep'");
        while ($cidade = mysqli_fetch_array($sql)) {
            //buscando as denuncias da cidade
            $sqlDenuncia = $this->select("denuncia", "*", "cep = '" . $cidade['cep'] . "'");

            if (mysqli_num_rows($sqlDenuncia) > 0) {
                //mostra o botão
                echo '<li>';
                echo '<a href="javascript:void(0)" id="btnCidade" lang="' . $cidade['nome'] . '" cep="' . $cidade['cep'] . '">' . $cidade['nome'] . '</a>';
                echo '</li>';
            }
        }
    }

    /**
     * O método recupera os dados da tabela denuncia do banco de dados.
     * 
     * @param type $status
     * @author Michael Dydean
     */
    public function statusDenuncia($status, $cep) {

        if ($status != NULL && $cep == NULL) {
            $sql = $this->select("denuncia", "*", "id_class = '" . $status . "'");
        } elseif ($status == NULL && $cep != NULL) {
            $sql = $this->select("denuncia", "*", "cep = '" . $cep . "'");
        } else {
            $sql = $this->select("denuncia", "*", "id_class = '" . $status . "' AND cep = '" . $cep . "'");
        }
        
        while ($denuncia = mysqli_fetch_array($sql)) {
            $denuncias[] = $denuncia;
        }

        return $denuncias;
    }

    /**
     * O método recupera os dados da tabela denuncia do banco de dados.
     * 
     * @param type $cep
     * @author Michael Dydean
     */
    public function cepDenuncia($cep) {
        $sql = $this->select("denuncia", "*", "cep = '" . $cep . "'");

        while ($denuncia = mysqli_fetch_array($sql)) {
            $denuncias[] = $denuncia;
        }

        return $denuncias;
    }

    /**
     * O método recupera os dados da tabela cidades do banco de dados.
     * 
     * @author Michael Dydean
     */
    public function cidades() {
        $sql = $this->select("cidades", "*");

        while ($cidade = mysqli_fetch_array($sql)) {
            $cidades[] = $cidade;
        }

        return $cidades;
    }

    /* FORMULÁRIO DE CONTATO DO SITE */

    public function enviaFormContato($dados) {
        $destino = "emersonmessoribeiro@gmail.com";
        $arquivo = ''
                . '<h1>Novo registro de contato</h1>'
                . 'Você recebeu um novo chamado de contato no site da empresa<br />'
                . '<b>Mensagem:</b> ' . $dados['mensagem'] . '<br />'
                . '<b>De:</b> ' . $dados['nome'] . '<br />'
                . '<b>E-mail:</b> ' . $dados['email']
                . '';


        $emailenviar = $dados['email'];
        $assunto = "Formulário De Contato S-Vias";

        // É necessário indicar que o formato do e-mail é html
        $headers = 'MIME-Version: 1.0' . "\r\n";
        $headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
        $headers .= 'From: <' . $emailenviar . '>';
        //$headers .= "Bcc: $EmailPadrao\r\n";
        if (!mail($destino, $assunto, $arquivo, $headers)) {
            echo "E-MAIL NÃO ENVIADO!";
        }
    }

    /* FORMULÁRIO DE CONTATO DO SITE */
}
