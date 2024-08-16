package com.eq3.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.adaptateur.LivreAdaptateur;
import com.eq3.bibliotheque.modele.LivreModele;
import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.presentateur.ListeLivresPresentateur;

import java.util.ArrayList;

/**
 * Activité pour afficher la liste des livres.
 * Cette activité gère l'affichage de la liste des livres et permet à l'utilisateur
 * de naviguer vers les détails d'un livre ou de revenir à l'écran de connexion.
 */
public class ListeLivresActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listeLivresView;
    private LivreAdaptateur livreAdaptateur;
    private ArrayList<Livre> listeLivres;
    private static final String TAG = "ListeLivresActivity";
    private static final int CODE_REQUETE_DETAIL_LIVRE = 1;
    private ListeLivresPresentateur livresPresentateur;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les composants de l'interface utilisateur et le présentateur.
     *
     * @param savedInstanceState L'état de l'activité précédemment enregistré, ou null si elle n'a pas encore été créée.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livre);

        // Initialisation des composants de l'interface utilisateur
        listeLivresView = findViewById(R.id.bookListView); // Assurez-vous que le ListView a cet ID
        listeLivres = new ArrayList<>();
        livreAdaptateur = new LivreAdaptateur(this, listeLivres);
        listeLivresView.setAdapter(livreAdaptateur);

        // Initialisation du présentateur
        livresPresentateur = new ListeLivresPresentateur(new LivreModele(), this);

        // Configuration des boutons
        Button boutonRetour = findViewById(R.id.btnRetour_ListLivre);
        Button boutonFavoris = findViewById(R.id.btnFavoris_ListLivre);

        boutonRetour.setOnClickListener(this);
        boutonFavoris.setOnClickListener(this);

        // Configuration du comportement lorsque l'utilisateur clique sur un livre
        listeLivresView.setOnItemClickListener((parent, view, position, id) -> {
            Livre livreSelectionne = listeLivres.get(position);
            Intent intent = new Intent(ListeLivresActivity.this, DetailsLivres.class);

            // Passe l'ID du livre pour récupérer les détails
            intent.putExtra("livre", livreSelectionne);

            startActivityForResult(intent, CODE_REQUETE_DETAIL_LIVRE);
        });

        // Récupération des livres via le présentateur
        livresPresentateur.fetchBooks();
    }

    /**
     * Méthode appelée lorsqu'un des boutons est cliqué.
     * Gère la navigation vers l'écran de connexion ou la logique des favoris.
     *
     * @param v La vue qui a été cliquée.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnRetour_ListLivre) {

            Intent intent1 = new Intent(ListeLivresActivity.this, activity_login.class);
            startActivity(intent1);
            finish();
        }

        else if (v.getId() == R.id.btnFavoris_ListLivre) {

            // TODO: Implémenter la logique pour les favoris
        }
    }

    /**
     * Met à jour la liste des livres affichés dans l'interface utilisateur.
     *
     * @param livres La nouvelle liste de livres à afficher.
     */
    public void updateBookList(ArrayList<Livre> livres) {

        listeLivres.clear();
        listeLivres.addAll(livres);
        livreAdaptateur.notifyDataSetChanged();
    }

    /**
     * Affiche un message d'erreur dans les logs.
     *
     * @param message Le message d'erreur à afficher.
     */
    public void showError(String message) {

        Log.e(TAG, message);
    }

    /**
     * Méthode appelée lorsqu'une activité lancée avec startActivityForResult() se termine.
     * Rafraîchit la liste des livres après une mise à jour.
     *
     * @param requestCode Le code de requête passé lors de l'appel à startActivityForResult().
     * @param resultCode  Le code de résultat renvoyé par l'activité enfant.
     * @param data        Les données renvoyées par l'activité enfant.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUETE_DETAIL_LIVRE && resultCode == RESULT_OK) {
            if (data != null && data.getStringExtra("id") != null) {
                // Rafraîchir la liste des livres après la mise à jour
                livresPresentateur.fetchBooks();
            }
        }
    }
}
