package com.eq3.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.adaptateur.LivreAdaptateur;
import com.eq3.bibliotheque.dao.HttpJsonService; // Assurez-vous que vous importez HttpJsonService
import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.modele.Utilisateur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

public class ListeFavorisActivity extends AppCompatActivity {

    private ListView listeFavorisView;
    private LivreAdaptateur livreAdaptateur;
    private HttpJsonService httpJsonService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_favoris);

        listeFavorisView = findViewById(R.id.favorisListView);
        httpJsonService = new HttpJsonService(); // Initialiser HttpJsonService
        Utilisateur utilisateurConnecte = (Utilisateur) getIntent().getSerializableExtra("utilisateur");

        // Récupérer les livres favoris
        fetchLivresFavoris(utilisateurConnecte);

        listeFavorisView.setOnItemClickListener((parent, view, position, id) -> {
            Livre livreSelectionne = (Livre) listeFavorisView.getItemAtPosition(position);
            Intent intent = new Intent(ListeFavorisActivity.this, DetailsLivres.class);
            intent.putExtra("livre", livreSelectionne);
            intent.putExtra("utilisateur", utilisateurConnecte);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Rafraîchir la liste des favoris à chaque retour sur cette activité
        Utilisateur utilisateurConnecte = (Utilisateur) getIntent().getSerializableExtra("utilisateur");
        fetchLivresFavoris(utilisateurConnecte);
    }

    private void fetchLivresFavoris(Utilisateur utilisateur) {
        new Thread(() -> {
            try {
                List<Livre> livresFavoris = httpJsonService.getLivresFavoris();
                runOnUiThread(() -> {
                    if (livresFavoris != null) {
                        livreAdaptateur = new LivreAdaptateur(ListeFavorisActivity.this, new ArrayList<>(livresFavoris));
                        listeFavorisView.setAdapter(livreAdaptateur);
                    }
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace(); // Gérer les erreurs en fonction de vos besoins
            }
        }).start();
    }
}
