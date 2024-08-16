package com.eq3.bibliotheque.presentateur;

import android.util.Log;

import com.eq3.bibliotheque.activities.ConnexionActivity;
import com.eq3.bibliotheque.activities.ConnexionActivity;
import com.eq3.bibliotheque.modele.Utilisateur;
import com.eq3.bibliotheque.modele.UtilisateurModele;
import java.util.List;

public class ConnexionPresentateur {
    private static final String TAG = "ConnexionPresentateur";
    private UtilisateurModele modele;
    private ConnexionActivity vue;
    private List<Utilisateur> users;

    public ConnexionPresentateur(UtilisateurModele modele, ConnexionActivity vue) {
        this.modele = modele;
        this.vue = vue;
    }

    public void loadUsers() {
        modele.loadUsers(new UtilisateurModele.LoadUsersCallback() {
            @Override
            public void onSuccess(List<Utilisateur> loadedUsers) {
                users = loadedUsers;
                vue.runOnUiThread(() -> vue.updateUserList(users));
            }

            @Override
            public void onError(String message) {
                vue.runOnUiThread(() -> vue.showLoadError(message));
            }
        });
    }

    public void login(String email, String password) {
        Log.d(TAG, "Tentative de connexion avec email: " + email + ", password: " + password);

        if (email.equals("Admin@example.com") && password.equals("tch057")) {
            Log.d(TAG, "Connexion admin réussie");
            vue.showLoginSuccess(true);
        } else {
            Utilisateur user = modele.findUserByEmail(users, email);
            Log.d(TAG, "Utilisateur trouvé: " + (user != null));

            if (user != null) {
                Log.d(TAG, "Mot de passe attendu: " + Utilisateur.MDP_UTILISATEUR);
                Log.d(TAG, "Mot de passe entré: " + password);
                if (password.equals(Utilisateur.MDP_UTILISATEUR)) {
                    Log.d(TAG, "Connexion utilisateur réussie");
                    vue.showLoginSuccess(false);
                } else {
                    Log.d(TAG, "Mot de passe incorrect");
                    vue.showLoginError();
                }
            } else {
                Log.d(TAG, "Utilisateur non trouvé");
                vue.showLoginError();
            }
        }
    }
}