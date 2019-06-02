<?php
require "../../_core/_config/functions.php";
$con = new Controller();
$array = array();
$data_img = "";
$cep = $_POST['cep'];

//verificando se o CEp está cadastrado
$sqlCep = $con->select("cidades", "*", "cep = '$cep'");
if(mysqli_num_rows($sqlCep) == 1){
    $array['erro'] = FALSE;
    $array['message'] = "";
    //mostra
    // Select all the rows in the markers table
    $sql_markets = $con->select("denuncia", "*", "cep = '$cep'");
    $array['total'] = mysqli_num_rows($sql_markets);
    while ($row = mysqli_fetch_array($sql_markets)) {

        if ($row['id_class'] == 1) {
            $type = "P";
        } else if ($row['id_class'] == 2) {
            $type = "S";
        } else {
            $type = "C";
        }

        $array['denuncias'][] = array(
            "id" => $row['id_loc'],
            "lat" => $row['lati'],
            "lng" => $row['lng'],
            "name" => $row['titulo'],
            'desc' => "Descrição",
            'type' => "P"
        );
    }
}else{
    $array['erro'] = TRUE;
    $array['message'] = "Cidade não cadastrada!";
}
echo json_encode($array, JSON_UNESCAPED_UNICODE);