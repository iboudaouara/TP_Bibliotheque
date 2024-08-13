package com.eq3.bibliotheque.modele;

import com.eq3.bibliotheque.activities.BookListActivity;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookModel {
    private static final String TAG = "BookModel";
    public void fetchBooks(final BookListActivity activity) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://10.0.2.2:3000/livres";
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "La requête Network a échoué", e);
                activity.runOnUiThread(() -> activity.showError("Échec de la récupération des livres : " + e.getMessage()));
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonStr = response.body().string();
                        JSONArray livres = new JSONArray(jsonStr);
                        ArrayList<Livre> bookList = new ArrayList<>();
                        for (int i = 0; i < livres.length(); i++) {
                            JSONObject livre = livres.getJSONObject(i);
                            String titre = livre.getString("titre");
                            String auteur = livre.getString("auteur");
                            String isbn = livre.getString("isbn");
                            String maisonEdition = livre.getString("maison_edition");
                            String datePublication = livre.getString("date_publication");
                            String description = livre.getString("description");
                            double appreciationMoyenne = livre.getDouble("appreciation_moyenne");
                            int nombreAppreciations = livre.getInt("nombre_appreciations");
                            String id = livre.getString("id");
                            Livre book = new Livre(titre, auteur, isbn, maisonEdition, datePublication,description, appreciationMoyenne, nombreAppreciations, id);
                            bookList.add(book);
                        }
                        final ArrayList<Livre> finalBookList = bookList;
                        activity.runOnUiThread(() -> activity.updateBookList(finalBookList));
                    } catch (JSONException e) {
                        Log.e(TAG, "Erreur d'analyse JSON", e);
                        activity.runOnUiThread(() -> activity.showError("Échec de l'analyse de la réponse du serveur"));
                    }                 }
                else {
                    activity.runOnUiThread(() -> activity.showError("Échec de la récupération des données du serveur"));
                }
            }
        });
    }
}
