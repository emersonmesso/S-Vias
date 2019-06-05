<?php
$releases = array(
    'location' => "../../_core/releases/s-vias.0.1.apk",
    'name' => "S-Vias",
    'version' => 0.1
);

// Define o tempo máximo de execução em 0 para as conexões lentas
set_time_limit(0);
// Aqui você pode aumentar o contador de downloads
// Configuramos os headers que serão enviados para o browser
header('Content-Description: File Transfer');
header('Content-Disposition: attachment; filename="'.$releases['name'].'"');
header('Content-Type: application/octet-stream');
header('Content-Transfer-Encoding: binary');
header('Content-Length: ' . filesize($releases['location']));
header('Cache-Control: must-revalidate, post-check=0, pre-check=0');
header('Pragma: public');
header('Expires: 0');
// Envia o arquivo para o cliente
readfile($releases['location']);