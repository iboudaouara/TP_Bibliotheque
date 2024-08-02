package com.eq3.bibliotheque.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bibliotheque.db";
    private static final int DATABASE_VERISON = 1;


    //nom table et colonnes pour table des favoris
    public static final String TABLE_NAME = "favoris";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_UTILISATEUR_ID = "utilisateur_id";
    public static final String COLUMN_LIVRE_ID = "livre_id";




    public DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERISON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //requete de creation de table a executé
        String requeteCreation = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER)",
                TABLE_NAME,
                COLUMN_ID,
                COLUMN_UTILISATEUR_ID,
                COLUMN_LIVRE_ID);

        db.execSQL(requeteCreation);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
