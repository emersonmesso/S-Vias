<?php
$Controller = new Controller();

if(isset( $Controller->url[1] )){
    if($Controller->url[1] == "download"){
        $releases = array(
            'location' => "../../_core/releases/apk.apk",
            'name' => "S-Vias",
            'version' => 1.0
        );
        header("Location: " . $releases['location']);
        
    }
    if($Controller->url[1] == "chat"){
        $releases = array(
            'location' => "../../_core/releases/app-debug.apk",
            'name' => "S-Vias",
            'version' => 1.0
        );
        header("Location: " . $releases['location']);
        
    }
}else{
    //mostra todos os releases
    include 'todoReleases.php';
}