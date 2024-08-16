package com.eq3.bibliotheque.presentateur;

import android.content.Context;
import android.util.Log;

import com.eq3.bibliotheque.activities.activity_login;
import com.eq3.bibliotheque.modele.Utilisateur;
import com.eq3.bibliotheque.modele.UtilisateurModele;

import java.util.List;

public class ConnexionPresentateur {

    private UtilisateurModele modele;
    private activity_login vue;

    public ConnexionPresentateur(UtilisateurModele modele, activity_login vue) {
        this.modele = modele;
        this.vue = vue;
    }

    public void loadUsers(Context context) {
        modele.loadUsers(context, new UtilisateurModele.LoadUsersCallback() {
            @Override
            public void onSuccess(List<Utilisateur> users) {
                vue.updateUserList(users);
            }

            @Override
            public void onError(String message) {
                vue.showLoadError(message);
            }
        });
    }

    public void login(String email, String password) {
        Log.d("ConnexionPresentateur", "Tentative de connexion avec email: " + email);

        if (email.equals("Admin@example.com") && password.equals("tch057")) {
            vue.showLoginSuccess(true);
        }

        else {
            Log.d("ConnexionPresentateur", "Tentative de connexion avec email: " + email);
            Utilisateur user = modele.findUserByEmail(email);
            Log.d("ConnexionPresentateur", "Utilisateur trouvé: " + (user != null));

            if (user != null && password.equals("mdp123")) {
                vue.showLoginSuccess(false);
            }

            else {
                vue.showLoginError();
            }
        }
    }

}
