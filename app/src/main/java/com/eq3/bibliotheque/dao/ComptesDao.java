package com.eq3.bibliotheque.dao;

import com.eq3.bibliotheque.modele.Utilisateur;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ComptesDao {

    private HttpJsonService httpJsonService;

    // Constructeur
    public ComptesDao() {
        this.httpJsonService = new HttpJsonService(); // Initialisation de HttpJsonService
    }

    // Méthode pour obtenir la liste des comptes utilisateurs
    public List<Utilisateur> getComptes() throws IOException, JSONException {
        return httpJsonService.getComptes();
    }

}
