package br.com.s_dev.s_vias.s_vias.View.Controller;

public class SQLUtil {
    private static String TABELA_MARKETS = "DENUNCIA";

    //Cria a tabela dos marcadores
    public static String getCreateTabelaDenuncia() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_MARKETS + " (" +
                "EMAIL_CID VARCHAR(100) NOT NULL," +
                "ID_LOC INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "TITULO TEXT," +
                "DESCRICAO TEXT," +
                "DATA VARCHAR(50)," +
                "LATI REAL NOT NULL," +
                "LNG REAL NOT NULL," +
                "CEP VARCHAR(50) NOT NULL," +
                "TYPE VARCHAR(30)," +
                "ID_CLASS INTEGER" +
                ");";
        return sql;
    }
}
