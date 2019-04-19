<?php
require "../../_core/_config/functions.php";
$con = new Controller();
$array = array();
$data_img = "";
$cep = $_POST['cep'];

// Select all the rows in the markers table
$sql_markets = $con->select("denuncia", "*", "cep = '$cep'");
while ($row = mysqli_fetch_array($sql_markets)){

    if($row['id_class'] == 1){
        $type = "P";
    }else if($row['id_class'] == 2){
        $type = "S";
    }else{
        $type = "C";
    }
    
    $array[] = array(
        "id" => $row['id_loc'],
        "lat"=> $row['lati'],
        "lng" => $row['lng'],
        "name" => $row['titulo'],
        'desc' => "Descrição",
        'type' => "P"
    );
}
echo json_encode($array, JSON_UNESCAPED_UNICODE);