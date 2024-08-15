package com.eq3.bibliotheque.presentateur;

import com.eq3.bibliotheque.activities.ListeLivresActivity;
import com.eq3.bibliotheque.modele.LivreModele;

/**
 * Le présentateur pour gérer la logique de la liste des livres.
 */
public class ListeLivresPresentateur {

    private LivreModele livreModele;
    private ListeLivresActivity listeLivresActivite;

    /**
     * Constructeur pour initialiser le présentateur avec le modèle et l'activité correspondante.
     *
     * @param livreModele        Le modèle de livre utilisé pour interagir avec les données.
     * @param listeLivresActivite L'activité qui affiche la liste des livres.
     */
    public ListeLivresPresentateur(LivreModele livreModele, ListeLivresActivity listeLivresActivite) {
        this.livreModele = livreModele;
        this.listeLivresActivite = listeLivresActivite;
    }

    /**
     * Méthode pour récupérer les livres via le modèle et mettre à jour l'activité.
     * Vérifie que le modèle et l'activité ne sont pas nulls avant de procéder.
     */
    public void fetchBooks() {
        if (livreModele != null && listeLivresActivite != null) {
            livreModele.fetchBooks(listeLivresActivite);
        }
    }
}
