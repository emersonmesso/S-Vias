package br.com.s_dev.s_vias.s_vias.View.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DataBase extends SQLiteOpenHelper {


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
}