package com.eq3.bibliotheque.modele;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurModele {

    private List<Utilisateur> users;

    public interface LoadUsersCallback {
        void onSuccess(List<Utilisateur> users);
        void onError(String message);
    }

    public void loadUsers(Context context, LoadUsersCallback callback) {
        try {
            InputStream is = context.getAssets().open("bibliotheque.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Utilisateur>>(){}.getType();
            users = gson.fromJson(json, listType);

            callback.onSuccess(users);
        } catch (IOException ex) {
            ex.printStackTrace();
            callback.onError("Erreur lors du chargement du fichier JSON");
        }
    }

    public Utilisateur findUserByEmail(String email) {
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