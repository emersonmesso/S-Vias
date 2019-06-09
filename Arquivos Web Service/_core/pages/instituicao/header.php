<?php require "_core/_config/config.php"; ?>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Instituição - S Vias</title>
        <!-- Bootstrap CSS CDN -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <!-- Our Custom CSS -->
        <link rel="stylesheet" href="../../_core/pages/cidadao/style.css">
        
        
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
       
        <!-- Font Awesome JS -->
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
        <script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
        <style>
            #welcome {
                font-weight: bold;
                color: black;
                padding-top: 3vh;
                padding-left: 5vh;
            }
            #welcome > span {
                font-size: 0.7em;

            }
            .logo{
                width:50px;
                height: 50px;
            }
            
               #content-op {
                width: 100vw;
                height: 100vh;
                background-color: rgba(0, 0, 0, 0.5);
                z-index: 2;
                position: absolute;
                top: 0px;
                left: 0px;
            }
            #modal {
                width: 40vw;
                background-color: #ddd;
                z-index: 3;
                position: absolute;
                top: 35vh;
                left: 28vw;
                padding: 2vw 4vh;
                box-shadow: 4px 4px 4px #616161;
            }
            input[type=file] {
            }
            #content-btn {
            }
            .btn-modal {
                float: right;
                margin-left: 1vw;
                padding: 0.5vh 1vw;
                color: white;
                border: none;
                box-shadow: 2px 2px 2px #616161;
            }
            #btn-modal-cancel {
              background-color: red;  
            }
            #btn-modal-confirm {
               background-color: green;
            }
        </style>
    </head>
    <body>