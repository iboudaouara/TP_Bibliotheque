package com.eq3.bibliotheque.dao;

import com.eq3.bibliotheque.modele.Livre;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class LivreDao {

    private HttpJsonService httpJsonService;

    // Constructeur
    public LivreDao() {
        this.httpJsonService = new HttpJsonService(); // Initialisation de HttpJsonService
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
}
