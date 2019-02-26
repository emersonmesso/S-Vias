package com.sdev.svias.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sdev.svias.Model.Parametro;

public class DataBase extends SQLiteOpenHelper {


    public DataBase(Context context) {
        super(context, UtilAPP.BANCO, null, UtilAPP.VERSAO);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL( SQLUtil.getCreateTabelaDenuncia() );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( SQLUtil.getCreateTabelaDenuncia() );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Adiciona os dados do marcador para alterar tela
    public boolean setImage (String img, String nome, String cep, double lat, double lng, String email, String desc){

        SQLiteDatabase db = getReadableDatabase();
        db.execSQL( SQLUtil.deleteImg() );
        db.execSQL( SQLUtil.getCreateTabelaDenuncia() );


        Log.i("BANCO", "Tabela Criada!");

        ContentValues values = new ContentValues();
        values.put( "ID" , 1 );
        values.put( "FOTO" , img );
        values.put( "NOME" , nome );
        values.put( "CEP" , cep );
        values.put( "LAT" , lat );
        values.put( "LNG" , lng );
        values.put( "DESC" , desc );
        values.put( "EMAIL" , email );

        db.insert( UtilAPP.TABELA_FOTO, null, values);

        return true;
    }

}