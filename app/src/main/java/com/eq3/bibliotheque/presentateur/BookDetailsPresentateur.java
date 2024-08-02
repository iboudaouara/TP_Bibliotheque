package com.eq3.bibliotheque.presentateur;

import com.eq3.bibliotheque.dao.BibliothequeDAO;
import com.eq3.bibliotheque.modele.Livre;

public class BookDetailsPresentateur {
    private BookDetailsView view;
    private BibliothequeDAO dao;

    public BookDetailsPresentateur(BookDetailsView view, BibliothequeDAO dao) {

        this.view = view;
        this.dao = dao;
    }

    public void loadBookDetails(String isbn) {
        // Charger les détails du livre depuis le DAO et les afficher dans la vue
    }

    public void submitRating(String isbn, float rating) {
        // Mettre à jour la note du livre et rafraîchir la vue
    }

    public interface BookDetailsView {

        void showBookDetails(Livre livre);
        void updateRating(double newAverage, int newCount);
        void showError(String message);
    }

}
