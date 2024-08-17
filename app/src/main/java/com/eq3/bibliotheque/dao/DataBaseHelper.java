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
 * Classe d'aide pour la gestion de la base de données SQLite de l'application.
 * Cette classe crée, met à jour et gère le schéma de la base de données et les opérations associées.
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

    /**
     * Constructeur de DataBaseHelper.
     * Initialise l'aide de base de données avec le contexte de l'activité donné.
     *
     * @param activity Le contexte de l'activité pour les mises à jour de l'interface utilisateur.
     */
    public DataBaseHelper(Activity activity) {
        super(activity, DATABASE_NAME, null, DATABASE_VERSION);
        this.activity = activity;
        this.executorService = Executors.newFixedThreadPool(4);
        this.httpJsonService = new HttpJsonService();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crée la table 'favoris' pour stocker les livres favoris des utilisateurs
        String createFavorisTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                TABLE_FAVORIS, COLUMN_ID, COLUMN_UTILISATEUR_ID, COLUMN_LIVRE_ID);
        db.execSQL(createFavorisTable);

        // Crée la table 'evaluations' pour stocker les évaluations des livres par les utilisateurs
        String createEvaluationsTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s FLOAT)",
                TABLE_EVALUATIONS, COLUMN_ID, COLUMN_UTILISATEUR_ID, COLUMN_LIVRE_ID, COLUMN_EVALUATION);
        db.execSQL(createEvaluationsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Supprime les tables existantes et les recrée pour gérer les mises à jour du schéma
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATIONS);
        onCreate(db);
    }

    /**
     * Ajoute un livre aux favoris de l'utilisateur.
     *
     * @param livreId      L'ID du livre à ajouter.
     * @param utilisateurId L'ID de l'utilisateur qui ajoute le livre.
     */
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

    /**
     * Supprime un livre des favoris de l'utilisateur.
     *
     * @param livreId      L'ID du livre à supprimer.
     * @param utilisateurId L'ID de l'utilisateur qui supprime le livre.
     */
    public void supprimerFavori(String livreId, String utilisateurId) {
        executorService.execute(() -> {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_FAVORIS, COLUMN_LIVRE_ID + " = ? AND " + COLUMN_UTILISATEUR_ID + " = ?",
                    new String[]{livreId, utilisateurId});
            db.close();
        });
    }

    /**
     * Vérifie si un livre est marqué comme favori par l'utilisateur.
     *
     * @param livreId      L'ID du livre à vérifier.
     * @param utilisateurId L'ID de l'utilisateur.
     * @return True si le livre est un favori, sinon false.
     */
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

    /**
     * Interface de rappel pour le chargement des livres favoris.
     */
    public interface FavorisCallback {
        void onFavorisLoaded(List<Livre> favoris);
    }

    /**
     * Récupère la liste des livres favoris d'un utilisateur.
     *
     * @param utilisateurId L'ID de l'utilisateur.
     * @param callback      Le rappel pour gérer les favoris chargés.
     */
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
                e.printStackTrace(); // Gère l'exception si la colonne est manquante
            } finally {
                cursor.close();
                db.close();
            }

            // Met à jour l'interface utilisateur avec les favoris chargés
            activity.runOnUiThread(() -> callback.onFavorisLoaded(favoris));
        });
    }

    /**
     * Récupère les détails d'un livre par son ID depuis le serveur distant.
     *
     * @param livreId L'ID du livre à récupérer.
     * @return Les détails du livre ou null si non trouvé.
     */
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

    /**
     * Récupère le score d'évaluation d'un livre par un utilisateur.
     *
     * @param livreId      L'ID du livre.
     * @param utilisateurId L'ID de l'utilisateur.
     * @return Le score d'évaluation du livre par l'utilisateur.
     */
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
                cursor.close(); // Assure la fermeture du curseur
            }
            db.close(); // Ferme la base de données
        }

        return evaluation;
    }

    /**
     * Sauvegarde ou met à jour l'évaluation d'un livre par un utilisateur.
     *
     * @param livreId      L'ID du livre.
     * @param utilisateurId L'ID de l'utilisateur.
     * @param evaluation   Le score d'évaluation à sauvegarder.
     * @return True si l'opération a réussi, sinon false.
     */
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
