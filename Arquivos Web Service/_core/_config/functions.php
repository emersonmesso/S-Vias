<?php
require "_model/cidadao.php";
require "mysql.php";
require "_model/instituicao.php";
require "_model/denuncia.php";
class Controller{
    private $url;
    private $sql;
    
    function __construct(){
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
        $parte1 = strrchr($_SERVER['REQUEST_URI'],"?");
        $parte2 = str_replace($parte1,"",$_SERVER['REQUEST_URI']);
        $this->url = explode("/",$parte2);
        array_shift($this->url);
        $this->conecta();
    }
    
    public function conecta(){
        //
        $mysql = new Mysql();
        return new mysqli($mysql->getHost(), $mysql->getUser(), $mysql->getPass(), $mysql->getBanco());
        //
    }
    
    //HEADER
    public function header(){
        //buscando o cabeçalho do arquivo
        if($this->url[0] == ""){
            //inicial
            include "_core/view/header.php";
        }else{
            //verifica se existe a pasta
            if(file_exists("_core/pages/".$this->url[0]."/")){
                include "_core/pages/".$this->url[0]."/header.php";
            }else{
                header("Location: ../");
            }
        }
    }
    //CONTEUDO
    public function conteudo (){
        if($this->url[0] == ""){
            //inicial
            include "_core/view/cont.php";
        }else{
            include "_core/pages/".$this->url[0]."/cont.php";
        }
    }
    //FOOTER
    public function footer(){
        if($this->url[0] == ""){
            //inicial
            include "_core/view/footer.php";
        }else{
            include "_core/pages/".$this->url[0]."/footer.php";
        }
    }

    //SELECT no banco de dados
    public function select($tabela,$todos=NULL,$where=NULL,$order=NULL){
        //inicia a classe
        $mysql = $this->conecta();
        
        if($todos == NULL){
            $todos = "*";
        }
        if($where != NULL){
            $where = " WHERE ".$where;
        }
        if($order != NULL){
            $order = " ORDER BY ".$order;
        }
        $sql = "SELECT {$todos} FROM {$tabela}{$where}{$order}";
        $query = $mysql->query($sql);
        //fecha a coneção
        mysqli_close($mysql);
        return $query;
    }
    
    //DELETE no banco de dados
    public function delete($tabela, $where){
        $mysql = $this->conecta();
        //
        if($where != NULL){
            $where = " WHERE ".$where;
        }
        //criando a consulta
        $sql = "DELETE FROM {$tabela}{$where}";
        $query = $mysql->query($sql);
        //fecha a coneção
        mysqli_close($mysql);
        return $query;
    }
    
    //UPDATE no banco de dados
    public function update($tabela,$valores,$where){
        $mysql = $this->conecta();
        //
        if($where != NULL){
            $where = " WHERE ".$where;
        }
        //
        $sql = "UPDATE {$tabela} SET {$valores} {$where}";
        //executa a Query
        $query = $mysql->query($sql);
        //fecha a coneção
        mysqli_close($mysql);
        return $query;
    }
    
    //INSERE no banco de dados
    public function insere($tabela,$campos,$valores){
        $mysql = $this->conecta();
        //
        $sql = "INSERT INTO {$tabela}({$campos}) VALUES({$valores})";
        //executa a Query
        $query = $mysql->query($sql);
        //fecha a coneção
        mysqli_close($mysql);
        return $query;
    }
    
    public function gerLinkPastas(){
        //string das url
        $resultado = "";
        //pega o tamanho dos links
        $total = count($this->url);
        
        for($i = 0; $i < $total; $i++){
            $resultado .= "../";
        }
        return $resultado;
    }
    
    /*TELA DE INÍCIO DA PÁGINA*/
    
    //Barra de navegação
    public function navBarPage() {
        echo '<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #FACC2E;" id="navBarInicio">';
            echo '<div class="container">';
                echo '<img src="'.$this->gerLinkPastas().'_core/_img/logoApp.png" class="navbar-brand" alt="Logo aplicativo" title="Logo Aplicativo" width="45px" style="border-radius: 50% !important;" />';
                echo '<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">';
                    echo '<span class="navbar-toggler-icon"></span>';
                echo '</button>';
                echo '<div class="collapse navbar-collapse" id="navbarNavAltMarkup">';
                    echo '<div class="navbar-nav">';
                        echo '<a class="nav-item nav-link active" href="'.$this->gerLinkPastas().'index.php">Inicio <span class="sr-only">(current)</span></a>';
                        echo '<a class="nav-item nav-link" href="'.$this->gerLinkPastas().'denuncias">Denúncias</a>';
                        echo '<a class="nav-item nav-link" href="'.$this->gerLinkPastas().'login">Entrar</a>';
                        echo '<a class="nav-item nav-link" href="'.$this->gerLinkPastas().'cadastro">Cadastro</a>';
                        echo '</div>';
                echo '</div>';
            echo '</div>';
        echo '</nav>';
    }
    public function navBarPageIndex() {
        echo '<nav class="navbar navbar-expand-lg navbar-light" id="navBarInicio">';
            echo '<div class="container">';
                echo '<img src="'.$this->gerLinkPastas().'/_core/_img/logoApp.png" class="navbar-brand" alt="Logo aplicativo" title="Logo Aplicativo" width="45px" style="border-radius: 50% !important;" />';
                echo '<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">';
                    echo '<span class="navbar-toggler-icon"></span>';
                echo '</button>';
                echo '<div class="collapse navbar-collapse" id="navbarNavAltMarkup">';
                    echo '<div class="navbar-nav">';
                        echo '<a class="nav-item nav-link active" href="'.$this->gerLinkPastas().'index.php">Inicio <span class="sr-only">(current)</span></a>';
                        echo '<a class="nav-item nav-link" href="'.$this->gerLinkPastas().'denuncias">Denúncias</a>';
                        echo '<a class="nav-item nav-link" href="'.$this->gerLinkPastas().'login">Login</a>';
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
        while(($arquivo = $diretorio->read()) !== false){
            if(strlen($arquivo) > 2){
                $array[] = $pasta . $arquivo;
            }
        }
        $diretorio->close();
        return $array;
    }
    
    public function Carrousel() {
        echo '<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">';
                for ($a = 0; $a < count($this->imgCarousel()); $a++){
                    if($a == 0){
                        echo '<li data-target="#carouselExampleIndicators" data-slide-to="'.$a.'" class="active" style="z-index:1;"></li>';
                    }else{
                        echo '<li data-target="#carouselExampleIndicators" data-slide-to="'.$a.'" style="z-index:1;"></li>';
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
    
    
    /*LOGIN E SESSÕES*/
    public function criaSessao($email, $check) {
        if ( session_status() !== PHP_SESSION_ACTIVE ){
            session_start();
        }
        $_SESSION['email'] = $email;
        $_SESSION['tipo'] = $check;
        return true;
    }
    
    public function verificaSessao() {
        if ( session_status() !== PHP_SESSION_ACTIVE ){
            session_start();
        }
        if(isset($_SESSION['email'])){
            return true;
        }else{
            return false;
        }
    }
    
    public function verificaSessaoAdmin() {
        if ( session_status() !== PHP_SESSION_ACTIVE ){
            session_start();
        }
        if(isset($_SESSION['admin'])){
            return true;
        }else{
            return false;
        }
    }
    
    public function redirecionaSessao() {
        if ( session_status() !== PHP_SESSION_ACTIVE ){
            session_start();
        }
        if(isset($_SESSION['email'])){
            if($_SESSION['tipo'] == 1){
                //cidadao
                echo '<script>window.location.href="../cidadao";</script>';
            }else{
                //instituição
                echo '<script>window.location.href="../instituicao";</script>';
            }
        }
    }
    
    /*
     * Redireciona a sessão do admin caso ela exista
     * Criado por Emerson Santos
     */
    public function redirecionaAdmin() {
        if ( session_status() !== PHP_SESSION_ACTIVE ){
            session_start();
        }
        if(isset($_SESSION['admin'])){
            echo '<script>window.location.href="../admin";</script>';            
        }
    }
    
    public function dadosCidadao() {
        $email = $_SESSION['email'];
        $sql = $this->select("cidadao", "*", "email = '$email'");
        $cid = new Cidadao();
        while($cidadao = mysqli_fetch_array($sql)){
            $cid->setCpf($cidadao['cpf']);
            $cid->setEmail($cidadao['email']);
            $cid->setNome($cidadao['nome']);
            $cid->setSenha($cidadao['senha']);
        }
        return $cid;
    }
    
    public function dadosInstituicao(){
        $sql = $this->select("instituicao", "*", "email = '". $_SESSION['email']."'");
        $instituicao = new Instituicao();
        while ($dado = mysqli_fetch_array($sql)){
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
    
    public function dadosDenuncia($id){
        $sql = $this->select("denuncia", "*", "id_loc = $id");
        $denuncia = new Denuncia();
        while($row = mysqli_fetch_array($sql)){
            $denuncia->setTitulo($row['titulo']);
            $denuncia->setCep($row['cep']);
            $denuncia->setCidade($row['cidade']);
            $denuncia->setImagem($this->imagemDenuncia($id));
            $denuncia->setImagens_pref($this->imagemDenuncia_pref($id));
            
            $data = new DateTime();
            $data->setTimestamp($row['data']);
            $denuncia->setData($data->format("d/m/Y"));
            if($row['id_class'] == 1){
                $denuncia->setClass("Pendente");
            }else if($row['id_class'] == 2){
                $denuncia->setClass("Em Progresso");
            }else{
                $denuncia->setClass("Concluído");
            }
            
            $denuncia->setEmail_cid($row['email_cid']);
            $denuncia->setLati($row['lati']);
            $denuncia->setLong($row['lng']);
            $denuncia->setRua($row['rua']);
            $denuncia->setId_loc($row['id_loc']);
            $denuncia->setDesc($row['descricao']);
        }
        return $denuncia;
    }
    
    
    //retorna as imagens feitas pela prefeitura
    public function imagemDenuncia_pref($id) {
        $sql = $this->select("imagens_pref", "*", "id_loc = $id");
        $imagem = array();
        if(mysqli_num_rows($sql) > 0){
            while($img = mysqli_fetch_array($sql)){
                $imagem[] = "../../_api/images/" . $img['imagem'];
            }
            
        }
        return $imagem;
    }
    
    //retorna as imagens da denuncia
    public function imagemDenuncia($id) {
        $sql = $this->select("imagens", "*", "id_loc = $id");
        $imagem = array();
        if(mysqli_num_rows($sql) > 0){
            while($img = mysqli_fetch_array($sql)){
                $imagem[] = "../../_api/images/" . $img['img_den'];
            }
            
        }
        return $imagem;
    }
    
    public function cidadeDenuncia($cep) {
        $sql = $this->select("cidades", "*", "cep = '$cep'");
        $array = array();
        while ($row = mysqli_fetch_array($sql)){
            
        }
        
    }
    
    
    /*MENU DAS CIDADES DA DENUNCIA DO USUARIO*/
    public function cidadesDenuncia($email) {
        $sql = $this->select("cidades", "*");
        while($cidade = mysqli_fetch_array($sql)){
            
            //buscando as denuncias da cidade
            $sqlDenuncia = $this->select("denuncia", "*", "cep = '".$cidade['cep']."' AND email_cid = '$email'");
            
            if(mysqli_num_rows($sqlDenuncia) > 0){
                //mostra o botão
                echo '<li>';
                    echo '<a href="javascript:void(0)" id="btnCidade" lang="'.$cidade['nome'].'" cep="'.$cidade['cep'].'">'.$cidade['nome'].'</a>';
                echo '</li>';
            }
        }
        
    }
    
    /**
     * O método recupera os dados da tabela denuncia do banco de dados.
     * 
     * @param type $status
     * @param type $cep
     * @author Michael Dydean
     */
    public function statusDenuncia($status, $cep) {
        $denuncias = [];
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
    
    public function countStatusDenuncia($status, $cep) {
        if ($status != NULL && $cep == NULL) {
            $sql = $this->select("denuncia", "*", "id_class = '" . $status . "'");
        } elseif ($status == NULL && $cep != NULL) {
            $sql = $this->select("denuncia", "*", "cep = '" . $cep . "'");
        } else {
            $sql = $this->select("denuncia", "*", "id_class = '" . $status . "' AND cep = '" . $cep . "'");
        }

        return mysqli_num_rows($sql);
    }
    
       /**
     * O método recupera os dados da tabela denuncia do banco de dados.
     * 
     * @param type $status
     * @param type $cep
     * @author Michael Dydean
     */
    public function statusDenunciaLimit($status, $cep, $i, $r) {
        $denuncias = [];
        if ($status != NULL && $cep == NULL) {
            $sql = $this->select("denuncia", "*", "id_class = '$status' Limit $i, $r");
        } elseif ($status == NULL && $cep != NULL) {
            $sql = $this->select("denuncia", "*", "cep = '$cep'  Limit $i, $r");
        } else {
            $sql = $this->select("denuncia", "*", "id_class = '$status' AND cep = '$cep' Limit $i, $r");
        }

        while ($denuncia = mysqli_fetch_array($sql)) {
            $denuncias[] = $denuncia;
        }

        return $denuncias;
    }
    
     /**
     * O método recupera os dados da tabela denuncia do banco de dados.
     * 
     * @param type $termo
     * @author Michael Dydean
     */
    public function searchDenuncia($termo) {
        $denuncias = [];
        if ($termo != NULL) {
            $sql = $this->select("denuncia", "*", "titulo LIKE '%$termo%' or descricao LIKE '%$termo%' or cep LIKE '%$termo%'");
        } else {
            return NULL;
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
    
    //Valida CNPJ
    function validaCNPJ($cnpj = null) {

        // Verifica se um número foi informado
        if (empty($cnpj)) {
            return false;
        }

        // Elimina possivel mascara
        $cnpj = preg_replace("/[^0-9]/", "", $cnpj);
        $cnpj = str_pad($cnpj, 14, '0', STR_PAD_LEFT);

        // Verifica se o numero de digitos informados é igual a 11 
        if (strlen($cnpj) != 14) {
            return false;
        }

        // Verifica se nenhuma das sequências invalidas abaixo 
        // foi digitada. Caso afirmativo, retorna falso
        else if ($cnpj == '00000000000000' ||
                $cnpj == '11111111111111' ||
                $cnpj == '22222222222222' ||
                $cnpj == '33333333333333' ||
                $cnpj == '44444444444444' ||
                $cnpj == '55555555555555' ||
                $cnpj == '66666666666666' ||
                $cnpj == '77777777777777' ||
                $cnpj == '88888888888888' ||
                $cnpj == '99999999999999') {
            return false;

            // Calcula os digitos verificadores para verificar se o
            // CPF é válido
        } else {

            $j = 5;
            $k = 6;
            $soma1 = "";
            $soma2 = "";

            for ($i = 0; $i < 13; $i++) {

                $j = $j == 1 ? 9 : $j;
                $k = $k == 1 ? 9 : $k;

                $soma2 += ($cnpj{$i} * $k);

                if ($i < 12) {
                    $soma1 += ($cnpj{$i} * $j);
                }

                $k--;
                $j--;
            }

            $digito1 = $soma1 % 11 < 2 ? 0 : 11 - $soma1 % 11;
            $digito2 = $soma2 % 11 < 2 ? 0 : 11 - $soma2 % 11;

            return (($cnpj{12} == $digito1) and ( $cnpj{13} == $digito2));
        }
    }

    /*FORMULÁRIO DE CONTATO DO SITE*/
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
        $headers  = 'MIME-Version: 1.0' . "\r\n";
        $headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
        $headers .= 'From: <'.$emailenviar.'>';
        //$headers .= "Bcc: $EmailPadrao\r\n";
        if(!mail($destino, $assunto, $arquivo, $headers)){
            echo "E-MAIL NÃO ENVIADO!";
        }
        
    }
    
    /*FORMULÁRIO DE CONTATO DO SITE*/
    
    /*Download release php*/
    public function release(){
        $releases = array(
            'location' => $this->gerLinkPastas() . "_core/releases/apk.apk",
            'name' => "S-Vias",
            'version' => 0.1
        );

        // Define o tempo máximo de execução em 0 para as conexões lentas
        set_time_limit(0);
        // Aqui você pode aumentar o contador de downloads
        // Configuramos os headers que serão enviados para o browser
        header('Content-Description: File Transfer');
        header('Content-Disposition: attachment; filename="' . $releases['name'] . '"');
        header('Content-Type: application/octet-stream');
        header('Content-Transfer-Encoding: binary');
        header('Content-Length: ' . filesize($releases['location']));
        header('Cache-Control: must-revalidate, post-check=0, pre-check=0');
        header('Pragma: public');
        header('Expires: 0');
        // Envia o arquivo para o cliente
        readfile($releases['location']);
    }
    
    /*
     * Envia email para a administração para que seja respondida sobre a adição da instituição no sistema
     * 
     */
    public function emailContatoIns($email){
        $arquivo = ''
                . 'Você recebeu este E-mail porque uma instituição quer utilizar o sistema S Vias em sua cidade!<br />'
                . 'Você pode conversar com a mesma a fim de sanar todas as dúvidas da instituição.<br />';
        
        
        $emailenviar = $email;
        $destino = "emersonmessoribeiro@gmail.com";
        $assunto = "Nova Instituição (S-Vias)";

        // É necessário indicar que o formato do e-mail é html
        $headers  = 'MIME-Version: 1.0' . "\r\n";
        $headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";
        $headers .= 'From: <'.$emailenviar.'>';
        //$headers .= "Bcc: $EmailPadrao\r\n";
        if(mail($destino, $assunto, $arquivo, $headers)){
            echo '<div class="alert alert-success text-center">';
                echo 'Aguarde nossa resposta em seu E-mail. Não vamos demorar para responder!';
            echo '</div>';
        }else{
            echo '<div class="alert alert-danger text-center">';
                echo 'Não foi possível enviar o E-mail!';
            echo '</div>';
        }
    }
}