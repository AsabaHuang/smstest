package com.deficientleaf.smstest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHelper extends SQLiteOpenHelper{
    private static MyDBHelper instance = null;
    public static MyDBHelper getInstance(Context ctx){
        if(instance==null){
            instance = new MyDBHelper(ctx,"HotlineDB",null,1);
        }
        return instance;
    }
    static final String DB_NAME = "HotlineDB";
    static final String TB_NAME = "hotlist" ;
    private MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE main.hotlist " +"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+ "name VARCHAR(32)," +"phone VARCHAR(16),"+"email VARCHAR(64))" ;
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
