package com.eq3.bibliotheque.presentateur;

import com.eq3.bibliotheque.dao.BibliothequeDAO;
import com.eq3.bibliotheque.modele.Livre;

import java.util.List;

public class BookListPresentateur {
    private BookListView view;
    private BibliothequeDAO dao;

    public BookListPresentateur(BookListView view, BibliothequeDAO dao) {

        this.view = view;
        this.dao = dao;
    }

    public void loadBooks() {
        // Charger les livres depuis le DAO et les afficher dans la vue
    }

    public interface BookListView {

        void showBooks(List<Livre> livres);
        void showError(String message);
    }

}
