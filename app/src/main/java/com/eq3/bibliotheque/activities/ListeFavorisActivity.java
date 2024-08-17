package com.eq3.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.adaptateur.LivreAdaptateur;
import com.eq3.bibliotheque.dao.HttpJsonService;
import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.modele.Utilisateur;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/**
 * Activity qui affiche la liste des livres favoris d'un utilisateur.
 * L'utilisateur peut cliquer sur un livre pour voir ses détails.
 */
public class ListeFavorisActivity extends AppCompatActivity {

    private ListView listeFavorisView;
    private LivreAdaptateur livreAdaptateur;
    private HttpJsonService httpJsonService;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les composants de l'interface utilisateur, récupère l'utilisateur connecté
     * et charge la liste de ses livres favoris.
     *
     * @param savedInstanceState Contient l'état précédemment sauvegardé de l'activité, si disponible.
     */
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

    /**
     * Méthode appelée lorsque l'activité reprend (par exemple, après être passée en arrière-plan).
     * Rafraîchit la liste des livres favoris de l'utilisateur.
     */
    @Override
    protected void onResume() {

        super.onResume();
        // Rafraîchir la liste des favoris à chaque retour sur cette activité
        Utilisateur utilisateurConnecte = (Utilisateur) getIntent().getSerializableExtra("utilisateur");
        fetchLivresFavoris(utilisateurConnecte);
    }

    /**
     * Récupère les livres favoris de l'utilisateur en utilisant HttpJsonService
     * et met à jour la ListView avec les livres récupérés.
     *
     * @param utilisateur L'utilisateur pour lequel récupérer les livres favoris.
     */
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
            }

            catch (IOException | JSONException e) {

                e.printStackTrace();
            }
        }).start();
    }
}
