package com.sdev.svias.Controller;

public class SQLUtil {
    private static String TABELA_MARKETS = "DENUNCIA";

    //Cria a tabela dos marcadores
    public static String getCreateTabelaDenuncia() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_MARKETS + " (" +
                "ID INTEGER PRIMARY KEY NOT NULL," +
                "NOME VARCHAR(100) NOT NULL," +
                "DESC VARCHAR(200) NOT NULL," +
                "EMAIL VARCHAR(100) NOT NULL," +
                "LAT REAL NOT NULL," +
                "LNG REAL NOT NULL," +
                "CEP VARCHAR(10) NOT NULL," +
                "FOTO BLOB NOT NULL" +
                ");";
        return sql;
    }

    //Apaga a tabela da imagem
    public static String deleteTable (){
        String sql = "DROP TABLE " + TABELA_MARKETS;
        return sql;
    }

    //Apaga registros da tabela de fotos
    public static String deleteImg (){
        String sql = "DELETE FROM " + TABELA_MARKETS;
        return sql;
    }

    //Busca o market
    public static String getImage (){
        String sql = "SELECT * FROM "+TABELA_MARKETS;
        return sql;
    }
}
