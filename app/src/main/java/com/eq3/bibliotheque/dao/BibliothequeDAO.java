package com.eq3.bibliotheque.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.eq3.bibliotheque.modele.Bibliotheque;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class BibliothequeDAO {
    private static final String SERVER_URL = "http://votre-serveur-json.com/bibliotheque.json";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Bibliotheque getBibliotheque() throws IOException {
        Request request = new Request.Builder()
                .url(SERVER_URL)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            String jsonData = response.body().string();
            return objectMapper.readValue(jsonData, Bibliotheque.class);
        }
    }

    public void updateBibliotheque(Bibliotheque bibliotheque) throws IOException {
        // Implémenter la logique pour mettre à jour le fichier JSON sur le serveur
    }
}
