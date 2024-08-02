package com.eq3.bibliotheque.presentateur;

import com.eq3.bibliotheque.dao.BibliothequeDAO;
import com.eq3.bibliotheque.modele.Bibliotheque;

public class LoginPresentateur {
    private LoginView view;
    private BibliothequeDAO dao;

    public LoginPresentateur(LoginView view, BibliothequeDAO dao) {

        this.view = view;
        this.dao = dao;
    }

    public void verifyLogin(String email, String password) {
        // Implémenter la logique de vérification
    }

    public interface LoginView {

        void showError(String message);
        void navigateToBookList();
        void navigateToAdminPanel();
    }

}
