package com.eq3.bibliotheque.modele;

import android.util.Log;
import com.eq3.bibliotheque.dao.HttpJsonService;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;

public class UtilisateurModele {
    private static final String TAG = "UtilisateurModele";
    private HttpJsonService httpJsonService;

    public UtilisateurModele() {
        this.httpJsonService = new HttpJsonService();
    }

    public interface LoadUsersCallback {
        void onSuccess(List<Utilisateur> users);
        void onError(String message);
    }

    public void loadUsers(LoadUsersCallback callback) {
        new Thread(() -> {
            try {
                List<Utilisateur> users = httpJsonService.getComptes();
                if (users != null && !users.isEmpty()) {
                    callback.onSuccess(users);
                } else {
                    callback.onError("Aucun utilisateur trouvé");
                }
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Erreur lors du chargement des utilisateurs", e);
                callback.onError("Échec du chargement des utilisateurs : " + e.getMessage());
            }
        }).start();
    }

    public Utilisateur findUserByEmail(List<Utilisateur> users, String email) {
        if (users != null) {
            for (Utilisateur user : users) {
                if (user.getCompte().equals(email)) {
                    return user;
                }
            }
        }
        return null;
    }
}