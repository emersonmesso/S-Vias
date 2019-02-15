<?php
require './Controller.php';
$Controller = new Controller();
$result = $Controller->getSql()->select("cidades");
while($row = mysqli_fetch_array($result)){
    $cidades[] = array(
        'nome' => $row['nome'],
        'uf' => $row['uf'],
        'cep' => $row['cep']
    );
}
echo json_encode($cidades);