/*
 * Autor: Michael Dydean
 * Data de criação: 2019-02-13.
 * Data de modificação: 2019-02-13.
 */


session_start(); //inicia a sessão
?>

<?php if (isset($_SESSION['active'])): ?> 
    <h1>Logado com sucesso!</h1>
    <?php
        echo $_SESSION['type'] . "<br/>";
        echo $_SESSION['login'] . "<br/>";
    ?>
    <a href="logout.php">logout</a>
  
<?php else: ?>
    <?php header("location: logout.php"); //redireciona o logout ?>
<?php endif; ?>
