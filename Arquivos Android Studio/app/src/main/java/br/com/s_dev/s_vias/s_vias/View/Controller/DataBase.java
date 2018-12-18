package br.com.s_dev.s_vias.s_vias.View.Controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by allanromanato on 5/27/15.
 */
class DataBase extends SQLiteOpenHelper {


    public DataBase(Context context) {
        super(context, UtilAPP.BANCO, null, UtilAPP.VERSAO);
        SQLiteDatabase db = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}