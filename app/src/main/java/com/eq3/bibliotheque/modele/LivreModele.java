package com.eq3.bibliotheque.modele;

import com.eq3.bibliotheque.activities.ListeLivresActivity;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Classe modèle pour gérer les opérations liées aux livres.
 */
public class LivreModele {

    private static final String TAG = "LivreModele"; // Tag pour les logs

    /**
     * Récupère la liste des livres à partir d'une API REST et met à jour l'activité associée.
     *
     * @param activite l'activité de la liste des livres à mettre à jour
     */
    public void fetchBooks(final ListeLivresActivity activite) {
        OkHttpClient client = new OkHttpClient(); // Création du client HTTP
        String url = "http://10.0.2.2:3000/livres"; // URL de l'API REST

        // Création de la requête HTTP
        Request request = new Request.Builder().url(url).build();

        // Exécution de la requête HTTP en arrière-plan
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "La requête réseau a échoué", e);
                activite.runOnUiThread(() -> activite.showError("Échec de la récupération des livres : " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonStr = response.body().string();
                        JSONArray livres = new JSONArray(jsonStr);
                        ArrayList<Livre> listeLivres = new ArrayList<>();

                        // Parcours du tableau JSON et création des objets Livre
                        for (int i = 0; i < livres.length(); i++) {
                            JSONObject livre = livres.getJSONObject(i);
                            String titre = livre.getString("titre");
                            String auteur = livre.getString("auteur");
                            String isbn = livre.getString("isbn");
                            String maisonEdition = livre.getString("maison_edition");
                            String datePublication = livre.getString("date_publication");
                            String description = livre.getString("description");
                            double appreciationMoyenne = livre.getDouble("appreciation_moyenne");
                            int nombreAppreciations = livre.getInt("nombre_appreciations");
                            String id = livre.getString("id");

                            // Création d'un objet Livre et ajout à la liste
                            Livre book = new Livre(titre, auteur, isbn, maisonEdition, datePublication, description, appreciationMoyenne, nombreAppreciations, id);
                            listeLivres.add(book);
                        }

                        // Mise à jour de l'interface utilisateur avec la liste des livres
                        final ArrayList<Livre> finalListeLivres = listeLivres;
                        activite.runOnUiThread(() -> activite.updateBookList(finalListeLivres));

                    } catch (JSONException e) {
                        Log.e(TAG, "Erreur d'analyse JSON", e);
                        activite.runOnUiThread(() -> activite.showError("Échec de l'analyse de la réponse du serveur"));
                    }
                } else {
                    activite.runOnUiThread(() -> activite.showError("Échec de la récupération des données du serveur"));
                }
            }
        });
    }
}
