package com.sdev.svias.Controller;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CriaDataBase {

    private SQLiteDatabase conexao;
    private DataBase dadosOpenHelper;

    //Cria a conexão com o banco de dados
    public boolean criaConexao (Context context){
        try {

            dadosOpenHelper = new DataBase(context);
            conexao = dadosOpenHelper.getWritableDatabase();

            Log.i("BancoDados", "Banco De Dados Criado Com Sucesso !");
            return true;

        } catch (SQLException e){
            Log.i("BancoDados", e.getMessage());
            return false;
        }
    }

    public boolean fechaConexao (){
        this.conexao.close();
        return true;
    }


}
