<?php require "_core/_config/config.php";
$Controller = new Controller();
?>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" type="imagem/x-icon" href="<?php echo $Controller->gerLinkPastas() ?>_core/_img/icon.ico"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Cidadão - S Vias</title>
        <!-- Bootstrap CSS CDN -->
        <link rel="stylesheet" href="<?php echo $Controller->gerLinkPastas() ?>_core/_css/css/bootstrap.css">
        <!-- Our Custom CSS -->
        <link rel="stylesheet" href="<?php echo $Controller->gerLinkPastas() ?>_core/pages/cidadao/style.css">
        <link rel="stylesheet" href="<?php echo $Controller->gerLinkPastas() ?>_core/_css/bootstrap-social.css">
        <!-- Font Awesome JS -->
       <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
    </head>
    <body>