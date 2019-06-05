<?php require "_core/_config/config.php";
$Controller = new Controller();
$pagina = false;
$desc = "Visualize todas as denúncias da sua cidade!";
$descPagina = "";
$img = "";
if(isset($Controller->url[1])){
    $pagina = true;
    //buscando os dados da denuncia
    $denuncia = $Controller->dadosDenuncia($Controller->url[1]);
    $descPagina = utf8_decode( $denuncia->getDesc());
    if(count( $denuncia->getImagem()) > 0){
        $im = $denuncia->getImagem();
        $img = "https://emersonmesso95.000webhostapp.com/" . $im[0];
        $img = str_replace("../", "", $img);
    }
}
?>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="author" content="S-Vias">
        <meta property="og:description" content="<?php echo ($pagina) ? $descPagina : $desc; ?>"/>
        <meta name="description" content="<?php echo ($pagina) ? $descPagina : $desc; ?>">
        <meta property="og:image" content="<?php echo $img ?>"/>
        <meta name="keywords" content="S-vias, S Vias">
        <link rel="shortcut icon" type="imagem/x-icon" href="<?php echo $Controller->gerLinkPastas() ?>_core/_img/icon.ico"/>
        <title><?php echo ($pagina)? utf8_decode($denuncia->getTitulo()) . " - S Vias" : "Denuncias - S Vias"; ?></title>
        <link rel="stylesheet" type="text/css" href="<?php echo $Controller->gerLinkPastas() ?>_core/_css/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans+Condensed:300" rel="stylesheet">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" integrity="sha384-50oBUHEmvpQ+1lW4y57PTFmhCaXp0ML5d60M1M7uH2+nqUivzIebhndOJK28anvf" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="<?php echo $Controller->gerLinkPastas() ?>_core/_css/_reset.css">
        <link rel="stylesheet" type="text/css" href="<?php echo $Controller->gerLinkPastas() ?>_core/_css/denuncias.css">
    </head>
    <body>
        <?php
        //Barra de navegação
        $con->navBarPage();