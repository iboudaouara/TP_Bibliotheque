package com.eq3.bibliotheque.dao;

import android.util.Log;

import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.modele.Utilisateur;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Service pour interagir avec le serveur JSON via des requêtes HTTP.
 * Permet de récupérer, ajouter et supprimer des livres et obtenir des informations sur les comptes utilisateurs.
 */
public class HttpJsonService {

    private final String URL_POINT_ENTREE = "http://10.0.2.2:3000"; // URL du serveur local
    private static final String CHARACTERS = "0123456789abcdef"; // Caractères pour générer un ID aléatoire
    private static final SecureRandom RANDOM = new SecureRandom(); // Générateur aléatoire sécurisé

    /**
     * Récupère la liste des livres depuis le serveur JSON.
     *
     * @return Une liste d'objets Livre.
     * @throws IOException En cas d'erreur de communication avec le serveur.
     * @throws JSONException En cas d'erreur de traitement JSON.
     */
    public List<Livre> getLivres() throws IOException, JSONException {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/livres")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
        String jsonStr = responseBody.string();
        List<Livre> livres = null;

        Log.d("HttpJsonService", "Response code: " + response.code());
        Log.d("HttpJsonService", "Response body: " + jsonStr);

        if (jsonStr.length() > 0) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                livres = Arrays.asList(mapper.readValue(jsonStr, Livre[].class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return livres;
        }

        return null;
    }

    /**
     * Supprime un livre du serveur JSON en utilisant son ID.
     *
     * @param livre L'objet Livre à supprimer.
     * @return true si la suppression est réussie, false sinon.
     * @throws IOException En cas d'erreur de communication avec le serveur.
     * @throws JSONException En cas d'erreur de traitement JSON.
     */
    public boolean supprimerLivre(Livre livre) throws IOException, JSONException {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/livres/" + livre.getId())
                .delete()
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.code() == 200;
    }

    /**
     * Ajoute un livre au serveur JSON.
     *
     * @param livre L'objet Livre à ajouter.
     * @return true si l'ajout est réussi, false sinon.
     * @throws IOException En cas d'erreur de communication avec le serveur.
     * @throws JSONException En cas d'erreur de traitement JSON.
     */
    public boolean ajouterLivre(Livre livre) throws IOException, JSONException {

        OkHttpClient okHttpClient = new OkHttpClient();

        String id = genereIdAleatoire(4);

        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("titre", livre.getTitre());
        obj.put("auteur", livre.getAuteur());
        obj.put("isbn", livre.getIsbn());
        obj.put("maison_edition", livre.getMaisonEdition());
        obj.put("date_publication", livre.getDatePublication());
        obj.put("description", livre.getDescription());
        obj.put("appreciation_moyenne", livre.getAppreciationMoyenne());
        obj.put("nombre_appreciations", livre.getNombreAppreciations());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody corpsRequete = RequestBody.create(obj.toString(), JSON);

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/livres")
                .post(corpsRequete)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.code() == 201; // Code HTTP pour création réussie
    }

    /**
     * Génère un ID aléatoire pour un livre.
     *
     * @param tailleId La taille de l'ID à générer.
     * @return L'ID généré.
     */
    private String genereIdAleatoire(int tailleId) {

        StringBuilder id = new StringBuilder(tailleId);
        for (int i = 0; i < tailleId; i++) {
            id.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return id.toString();
    }

    /**
     * Récupère la liste des comptes utilisateurs depuis le serveur JSON.
     *
     * @return Une liste d'objets Utilisateur.
     * @throws IOException En cas d'erreur de communication avec le serveur.
     * @throws JSONException En cas d'erreur de traitement JSON.
     */
    public List<Utilisateur> getComptes() throws IOException, JSONException {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/comptes")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
        String jsonStr = responseBody.string();
        List<Utilisateur> comptes = null;

        if (jsonStr.length() > 0) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                comptes = Arrays.asList(mapper.readValue(jsonStr, Utilisateur[].class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return comptes;
        }

        return null;
    }

    /**
     * Met à jour un livre sur le serveur JSON.
     *
     * @param livre L'objet Livre à mettre à jour.
     * @return true si la mise à jour est réussie, false sinon.
     * @throws IOException En cas d'erreur de communication avec le serveur.
     * @throws JSONException En cas d'erreur de traitement JSON.
     */
    public boolean updateLivre(Livre livre) throws IOException, JSONException {
        String url = URL_POINT_ENTREE + "/livres/" + livre.getId();

        JSONObject livreJson = new JSONObject();
        livreJson.put("titre", livre.getTitre());
        livreJson.put("auteur", livre.getAuteur());
        livreJson.put("isbn", livre.getIsbn());
        livreJson.put("maison_edition", livre.getMaisonEdition());
        livreJson.put("date_publication", livre.getDatePublication());
        livreJson.put("description", livre.getDescription());
        livreJson.put("appreciation_moyenne", livre.getAppreciationMoyenne());
        livreJson.put("nombre_appreciations", livre.getNombreAppreciations());

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(livreJson.toString(), JSON);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }

    /**
     * Récupère la liste des livres dont la note est supérieure à 4 depuis le serveur JSON.
     *
     * @return Une liste d'objets Livre avec une note supérieure à 4.
     * @throws IOException En cas d'erreur de communication avec le serveur.
     * @throws JSONException En cas d'erreur de traitement JSON.
     */
    public List<Livre> getLivresFavoris() throws IOException, JSONException {

        List<Livre> tousLesLivres = getLivres();
        if (tousLesLivres == null) {
            return null;
        }

        List<Livre> livresFavoris = new ArrayList<>();
        for (Livre livre : tousLesLivres) {
            if (livre.getAppreciationMoyenne() > 4) {
                livresFavoris.add(livre);
            }
        }

        return livresFavoris;
    }

}
