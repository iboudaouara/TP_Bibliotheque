package com.eq3.bibliotheque.dao;

import com.eq3.bibliotheque.modele.Livre;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Classe responsable de la gestion des opérations liées aux livres dans la base de données.
 * Utilise HttpJsonService pour communiquer avec le service Web pour les opérations CRUD sur les livres.
 */
public class LivreDAO {

    private HttpJsonService httpJsonService;

    /**
     * Constructeur pour initialiser l'instance de HttpJsonService.
     * Crée un nouvel objet HttpJsonService pour effectuer les requêtes HTTP.
     */
    public LivreDAO() {

        this.httpJsonService = new HttpJsonService(); // Initialisation de HttpJsonService
    }

    /**
     * Récupère la liste des livres en interrogeant le service Web.
     *
     * @return Liste des livres.
     * @throws IOException   Si une erreur d'entrée/sortie se produit lors de la communication avec le service Web.
     * @throws JSONException Si une erreur se produit lors du traitement des données JSON.
     */
    public List<Livre> getLivres() throws IOException, JSONException {

        return httpJsonService.getLivres();
    }



    /**
     * Ajoute un nouveau livre en envoyant les détails au service Web.
     *
     * @param livre L'objet Livre contenant les informations à ajouter.
     * @return true si l'ajout est réussi, false sinon.
     * @throws IOException   Si une erreur d'entrée/sortie se produit lors de la communication avec le service Web.
     * @throws JSONException Si une erreur se produit lors du traitement des données JSON.
     */
    public boolean ajouterLivre(Livre livre) throws IOException, JSONException {

        return httpJsonService.ajouterLivre(livre);
    }

    /**
     * Supprime un livre en envoyant une demande au service Web pour le supprimer.
     *
     * @param livre L'objet Livre à supprimer.
     * @return true si la suppression est réussie, false sinon.
     * @throws IOException   Si une erreur d'entrée/sortie se produit lors de la communication avec le service Web.
     * @throws JSONException Si une erreur se produit lors du traitement des données JSON.
     */
    public boolean supprimerLivre(Livre livre) throws IOException, JSONException {

        return httpJsonService.supprimerLivre(livre);
    }

    public boolean updateLivre(Livre livre) throws IOException, JSONException {

        return httpJsonService.updateLivre(livre);
    }
}
