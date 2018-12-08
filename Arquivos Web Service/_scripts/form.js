/* 
 * Autor: Michael Dydean
 * Data de criação: 2018-07-12.
 * Data de midificação: 2018-07-12.
 */

function gerarForm() {
    var cod = '<form enctype="multipart/form-data" action="upload.php" method="POST">'
            + '<input type="hidden" name="MAX_FILE_SIZE" value="30000" />'
            + '<label for="userfile">Enviar esse arquivo: </label>'
            + '<input name="userfile" type="file" />'
            + '<input type="submit" value="Enviar arquivo" />'
            + '</form>';

    document.getElementById('item-menu-form-upload').innerHTML += cod;
}

