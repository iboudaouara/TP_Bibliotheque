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
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpJsonService {

    private final String URL_POINT_ENTREE = "http://10.0.2.2:3000";
    //valeurs concstantes à utiliser pour générer un id aléatoire pour chaque livre
    private static final String CHARACTERS = "0123456789abcdef";
    private static final SecureRandom RANDOM = new SecureRandom(); // Création de l'instance SecureRandom

    public List<Livre> getLivres() throws IOException, JSONException {

        // Création client HTTP
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/livres")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
        String jsonStr = responseBody.string();
        List<Livre> livres = null;

        Log.d("HttpJsonService:getLivres", jsonStr);

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

    public boolean supprimerLivre(Livre livre) throws IOException, JSONException {

        // Création client HTTP
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/livres/" + livre.getId())
                .delete()
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.code() == 200;
    }

    public boolean ajouterLivre(Livre livre) throws IOException, JSONException {

        // Création client HTTP
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
        obj.put("nombre_appreciations", livre.getNbrAppreciations());

        // Type de contenu de la requête
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody corpsRequete = RequestBody.create(obj.toString(), JSON);

        Request request = new Request.Builder()
                .url(URL_POINT_ENTREE + "/livres")
                .post(corpsRequete)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.code() == 201; // Code HTTP pour création réussie
    }

    private String genereIdAleatoire(int tailleId) {

        StringBuilder id = new StringBuilder(tailleId);
        for (int i = 0; i < tailleId; i++) {
            id.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return id.toString();
    }

    public List<Utilisateur> getComptes() throws IOException, JSONException {

        // Création client HTTP
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
}
