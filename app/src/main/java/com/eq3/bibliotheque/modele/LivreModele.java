package com.eq3.bibliotheque.modele;

import com.eq3.bibliotheque.activities.ListeLivresActivity;
import com.eq3.bibliotheque.dao.HttpJsonService;
import android.util.Log;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe modèle pour gérer les opérations liées aux livres.
 */
public class LivreModele {
    private static final String TAG = "LivreModele"; // Tag pour les logs
    private HttpJsonService httpJsonService;

    public LivreModele() {

        this.httpJsonService = new HttpJsonService();
    }

    /**
     * Récupère la liste des livres à partir du service HTTP JSON et met à jour l'activité associée.
     *
     * @param activite l'activité de la liste des livres à mettre à jour
     */
    public void fetchBooks(final ListeLivresActivity activite) {
        new Thread(() -> {
            try {
                List<Livre> livres = httpJsonService.getLivres();
                if (livres != null) {
                    ArrayList<Livre> listeLivres = new ArrayList<>(livres);
                    activite.runOnUiThread(() -> activite.updateBookList(listeLivres));
                } else {
                    activite.runOnUiThread(() -> activite.showError("Aucun livre trouvé"));
                }
            } catch (IOException e) {
                Log.e(TAG, "Erreur de communication avec le serveur", e);
                activite.runOnUiThread(() -> activite.showError("Échec de la récupération des livres : " + e.getMessage()));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}