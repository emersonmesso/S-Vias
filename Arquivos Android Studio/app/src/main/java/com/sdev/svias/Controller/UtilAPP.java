package com.sdev.svias.Controller;

public class UtilAPP {

    //Criação do banco de dados
    public static String BANCO = "DADOS";
    public static int VERSAO = 1;
    public static String TABELA_FOTO = "DENUNCIA";


    //Conexão com o servidor
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public static String LINK_SITE_APP = "https://emersonmesso95.000webhostapp.com/";
    public static String LINK_SITE_EMPRESA = "https://s-development.000webhostapp.com/";
    public static String LINK_SERVIDOR = "https://emersonmesso95.000webhostapp.com/_api/";
    public static String LINK_SERVIDOR_EMAIL = "https://emersonmesso95.000webhostapp.com/_api/_apiVerificaLogin.php?email=";
    public static String LINK_SERVIDOR_CADASTRO = "https://emersonmesso95.000webhostapp.com/_api/cadastro.php?";

    public static String LINK_SERVIDOR_CADASTRO_DENUCIA = "https://emersonmesso95.000webhostapp.com/_api/cadastroDenuncia.php";
    public static String LINK_SERVIDOR_CADASTRO_IMG_DENUCIA = "https://emersonmesso95.000webhostapp.com/_api/salvaImagem.php";

}
