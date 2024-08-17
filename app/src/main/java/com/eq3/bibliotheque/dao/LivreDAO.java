package com.eq3.bibliotheque.dao;

import android.app.Activity;
import android.content.Context;

import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.modele.Utilisateur;

import org.json.JSONException;
import java.io.IOException;
import java.util.List;

public class LivreDAO {

    private HttpJsonService httpJsonService;
    private DataBaseHelper dbHelper;

    public LivreDAO(Activity activity) {
        this.httpJsonService = new HttpJsonService();
        this.dbHelper = new DataBaseHelper(activity);
    }

    public List<Livre> getLivres() throws IOException, JSONException {
        return httpJsonService.getLivres();
    }

    public boolean ajouterLivre(Livre livre) throws IOException, JSONException {
        return httpJsonService.ajouterLivre(livre);
    }

    public boolean supprimerLivre(Livre livre) throws IOException, JSONException {
        return httpJsonService.supprimerLivre(livre);
    }

    public boolean updateLivre(Livre livre) throws IOException, JSONException {
        return httpJsonService.updateLivre(livre);
    }

    public float getEvaluationUtilisateur(String livreId, String utilisateurId) {
        return dbHelper.getEvaluationUtilisateur(livreId, utilisateurId);
    }

    public boolean saveEvaluationUtilisateur(String livreId, String utilisateurId, float evaluation) {
        return dbHelper.saveEvaluationUtilisateur(livreId, utilisateurId, evaluation);
    }
}