<html>
    <head>
        <meta charset="utf-8"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
        <style>
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
                height: 40vh;
                background-color: #ddd;
                z-index: 3;
                position: absolute;
                top: 35vh;
                left: 28vw; 
            }
            button {
                 z-index: 3;
                 position: absolute;
                top: 5vh;
                left: 8vw; 
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div id="content-op" class="row" hidden="true">
                <div id="modal" class="col align-self-center">
                    One of three columns
                </div>
            </div>
        </div>
        <button onclick="ativar();">Clique</button>
        
        <script>
        function ativar() {
            var content = document.getElementById("content-op");
            
            if(content.hidden) {
            content.hidden = false;
            modal.hidden = false;
        } else {
             content.hidden = true;
            modal.hidden = true;
        }
        }
        </script>
    </body>
</html>