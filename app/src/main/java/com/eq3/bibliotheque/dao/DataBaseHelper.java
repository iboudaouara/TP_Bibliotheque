package com.eq3.bibliotheque.dao;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.eq3.bibliotheque.modele.Livre;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Helper pour gérer la base de données SQLite de l'application.
 * Crée et met à jour la base de données en fonction des versions.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bibliotheque.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_FAVORIS = "favoris";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_UTILISATEUR_ID = "utilisateur_id";
    public static final String COLUMN_LIVRE_ID = "livre_id";
    public static final String TABLE_EVALUATIONS = "evaluations";
    public static final String COLUMN_EVALUATION = "evaluation";

    private ExecutorService executorService;
    private HttpJsonService httpJsonService;
    private Activity activity;

    public DataBaseHelper(Activity activity) {
        super(activity, DATABASE_NAME, null, DATABASE_VERSION);
        this.activity = activity;
        this.executorService = Executors.newFixedThreadPool(4);
        this.httpJsonService = new HttpJsonService();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFavorisTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                TABLE_FAVORIS, COLUMN_ID, COLUMN_UTILISATEUR_ID, COLUMN_LIVRE_ID);
        db.execSQL(createFavorisTable);

        String createEvaluationsTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s FLOAT)",
                TABLE_EVALUATIONS, COLUMN_ID, COLUMN_UTILISATEUR_ID, COLUMN_LIVRE_ID, COLUMN_EVALUATION);
        db.execSQL(createEvaluationsTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATIONS);
        onCreate(db);
    }

    public void ajouterFavori(String livreId, String utilisateurId) {
        executorService.execute(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_LIVRE_ID, livreId);
            values.put(COLUMN_UTILISATEUR_ID, utilisateurId);
            db.insert(TABLE_FAVORIS, null, values);
            db.close();
        });
    }

    public void supprimerFavori(String livreId, String utilisateurId) {
        executorService.execute(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_FAVORIS, COLUMN_LIVRE_ID + " = ? AND " + COLUMN_UTILISATEUR_ID + " = ?",
                    new String[]{livreId, utilisateurId});
            db.close();
        });
    }

    public boolean estFavori(String livreId, String utilisateurId) {
        final boolean[] isFavori = {false};
        executorService.execute(() -> {
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_FAVORIS + " WHERE " + COLUMN_LIVRE_ID + " = ? AND " + COLUMN_UTILISATEUR_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{livreId, utilisateurId});
            isFavori[0] = cursor.getCount() > 0;
            cursor.close();
            db.close();
        });
        return isFavori[0];
    }

    public interface FavorisCallback {
        void onFavorisLoaded(List<Livre> favoris);
    }

    public void getFavoris(String utilisateurId, FavorisCallback callback) {

        executorService.execute(() -> {

            List<Livre> favoris = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT " + COLUMN_LIVRE_ID + " FROM " + TABLE_FAVORIS + " WHERE " + COLUMN_UTILISATEUR_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{utilisateurId});

            try {
                int livreIdIndex = cursor.getColumnIndexOrThrow(COLUMN_LIVRE_ID);

                if (cursor.moveToFirst()) {
                    do {
                        String livreId = cursor.getString(livreIdIndex);
                        Livre livre = getLivreDetails(livreId);
                        if (livre != null && getEvaluationUtilisateur(livreId, utilisateurId) > 4) {
                            favoris.add(livre);
                        }
                    } while (cursor.moveToNext());
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace(); // Traitez l'exception si la colonne est manquante
            } finally {
                cursor.close();
                db.close();
            }

            // Utilisation de runOnUiThread pour mettre à jour l'interface utilisateur
            activity.runOnUiThread(() -> callback.onFavorisLoaded(favoris));
        });
    }


    private Livre getLivreDetails(String livreId) {
        Future<Livre> future = executorService.submit(() -> {
            try {
                List<Livre> livres = httpJsonService.getLivres();
                if (livres != null) {
                    for (Livre livre : livres) {
                        if (livre.getId().equals(livreId)) {
                            return livre;
                        }
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public float getEvaluationUtilisateur(String livreId, String utilisateurId) {
        float evaluation = 0;
        SQLiteDatabase db = this.getReadableDatabase(); // Ouvre la base de données
        Cursor cursor = null;

        try {
            String query = "SELECT " + COLUMN_EVALUATION + " FROM " + TABLE_EVALUATIONS + " WHERE " + COLUMN_LIVRE_ID + " = ? AND " + COLUMN_UTILISATEUR_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{livreId, utilisateurId});
            if (cursor.moveToFirst()) {
                evaluation = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_EVALUATION));
            }
        } catch (Exception e) {
            Log.e("DataBaseHelper", "Erreur lors de l'accès à la base de données", e);
        } finally {
            if (cursor != null) {
                cursor.close(); // Assurez-vous de fermer le curseur
            }
            db.close(); // Ferme la base de données
        }

        return evaluation;
    }


    public boolean saveEvaluationUtilisateur(String livreId, String utilisateurId, float evaluation) {
        Future<Boolean> future = executorService.submit(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_LIVRE_ID, livreId);
            values.put(COLUMN_UTILISATEUR_ID, utilisateurId);
            values.put(COLUMN_EVALUATION, evaluation);

            long result = db.insertWithOnConflict(TABLE_EVALUATIONS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
            return result != -1;
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
