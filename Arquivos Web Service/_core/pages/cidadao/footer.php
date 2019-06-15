<?php
    $Controller = new Controller();
?>
    <!-- jQuery CDN - Slim version (=without AJAX) -->
    <script type="text/javascript" src="<?php echo $Controller->gerLinkPastas() ?>_core/_javascript/jquery.js"></script>
    <!-- Popper.JS -->
    
    <!-- Bootstrap JS -->
    <script type="text/javascript" src="<?php echo $Controller->gerLinkPastas() ?>_core/_javascript/js/bootstrap.js"></script>
    <script type="text/javascript" src="<?php echo $Controller->gerLinkPastas() ?>_core/_javascript/_functions.js"></script>
    <script type="text/javascript" src="<?php echo $Controller->gerLinkPastas() ?>_core/_javascript/jquery.js"></script>
    <?php
    if(isset($Controller->url[1])){
        ?>
        <script type="text/javascript" src="<?php echo $Controller->gerLinkPastas() ?>_core/pages/cidadao/dadosCidadao.js"></script>
    <?php
    }else{
        ?>
        <script type="text/javascript" src="<?php echo $Controller->gerLinkPastas() ?>_core/_javascript/cidadao.js"></script>
    <?php
    }
    ?>
    
</body>

</html>