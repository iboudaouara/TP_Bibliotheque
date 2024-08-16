package com.eq3.bibliotheque.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eq3.bibliotheque.modele.Livre;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper pour gérer la base de données SQLite de l'application.
 * Crée et met à jour la base de données en fonction des versions.
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bibliotheque.db";
    private static final int DATABASE_VERISON = 1;

    // Nom de la table et colonnes pour la table des favoris
    public static final String TABLE_NAME = "favoris";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_UTILISATEUR_ID = "utilisateur_id";
    public static final String COLUMN_LIVRE_ID = "livre_id";

    /**
     * Constructeur pour la classe DataBaseHelper.
     *
     * @param context Le contexte de l'application.
     */
    public DataBaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERISON);
    }

    /**
     * Crée la base de données et la table des favoris si elle n'existe pas.
     *
     * @param db L'instance de SQLiteDatabase utilisée pour exécuter la requête de création.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Requête de création de table à exécuter
        String requeteCreation = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER)",
                TABLE_NAME,
                COLUMN_ID,
                COLUMN_UTILISATEUR_ID,
                COLUMN_LIVRE_ID);

        db.execSQL(requeteCreation);
    }

    /**
     * Met à jour la base de données en cas de changement de version.
     * Supprime la table existante et la recrée avec la nouvelle version.
     *
     * @param db L'instance de SQLiteDatabase utilisée pour exécuter les commandes de mise à jour.
     * @param oldVersion La version précédente de la base de données.
     * @param newVersion La nouvelle version de la base de données.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Supprime la table existante si elle existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Crée une nouvelle table
        onCreate(db);
    }

    public void ajouterFavori(Livre livre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", livre.getId());
        values.put("titre", livre.getTitre());
        values.put("auteur", livre.getAuteur());
        values.put("note", livre.getAppreciationMoyenne());
        db.insert("favoris", null, values);
        db.close();
    }

    public List<Livre> getFavoris() {
        List<Livre> favoris = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM favoris WHERE note >= 4", null);
        if (cursor.moveToFirst()) {
            do {
                Livre livre = new Livre(
                        cursor.getString(1), // titre
                        cursor.getString(2), // auteur
                        "", // isbn
                        "", // maison d'édition
                        "", // date de publication
                        "", // description
                        cursor.getDouble(3), // note
                        0, // nombre d'appréciations
                        cursor.getString(0) // id
                );
                favoris.add(livre);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoris;
    }
}
