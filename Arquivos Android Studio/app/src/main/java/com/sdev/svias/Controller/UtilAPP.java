package com.sdev.svias.Controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    public static String LINK_SITE_CADASTRO_INSTITUICAO = LINK_SITE_APP + "cadastro";
    public static String LINK_SERVIDOR = "https://emersonmesso95.000webhostapp.com/_api/";
    public static String LINK_SERVIDOR_EMAIL = "https://emersonmesso95.000webhostapp.com/_api/_apiVerificaLogin.php?email=";
    public static String LINK_SERVIDOR_CADASTRO = "https://emersonmesso95.000webhostapp.com/_api/cadastro.php";

    public static String LINK_SERVIDOR_CADASTRO_DENUCIA = "https://emersonmesso95.000webhostapp.com/_api/cadastroDenuncia.php";
    public static String LINK_SERVIDOR_CADASTRO_IMG_DENUCIA = "https://emersonmesso95.000webhostapp.com/_api/salvaImagem.php";
    public static String LINK_SERVIDOR_CADASTRO_IMG_INSTITUICAO = "https://emersonmesso95.000webhostapp.com/_api/SalvaImagemInstituicao.php";


    //
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
