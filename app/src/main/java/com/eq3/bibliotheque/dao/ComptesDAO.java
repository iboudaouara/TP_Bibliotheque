package com.eq3.bibliotheque.dao;

import com.eq3.bibliotheque.modele.Utilisateur;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Classe responsable de la gestion des opérations liées aux comptes utilisateurs dans la base de données.
 * Utilise HttpJsonService pour interagir avec le service Web pour les opérations liées aux comptes utilisateurs.
 *
 */
public class ComptesDAO {

    private HttpJsonService httpJsonService;

    /**
     * Constructeur pour initialiser l'instance de HttpJsonService.
     * Crée un nouvel objet HttpJsonService pour effectuer les requêtes HTTP liées aux comptes utilisateurs.
     */
    public ComptesDAO() {

        this.httpJsonService = new HttpJsonService(); // Initialisation de HttpJsonService
    }

    /**
     * Récupère la liste des comptes utilisateurs en interrogeant le service Web.
     *
     * @return Liste des comptes utilisateurs.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la communication avec le service Web.
     * @throws JSONException Si une erreur se produit lors du traitement des données JSON.
     */
    public List<Utilisateur> getComptes() throws IOException, JSONException {

        return httpJsonService.getComptes();
    }

}
