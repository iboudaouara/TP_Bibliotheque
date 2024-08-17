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
import com.eq3.bibliotheque.dao.DataBaseHelper;
import com.eq3.bibliotheque.modele.LivreModele;
import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.modele.Utilisateur;
import com.eq3.bibliotheque.presentateur.ListeLivresPresentateur;

import java.util.ArrayList;

public class ListeLivresActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listeLivresView;
    private LivreAdaptateur livreAdaptateur;
    private ArrayList<Livre> listeLivres;
    private ListeLivresPresentateur livresPresentateur;
    private Utilisateur utilisateurConnecte;
    private DataBaseHelper dbHelper;

    private static final String TAG = "ListeLivresActivity";
    private static final int CODE_REQUETE_DETAIL_LIVRE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livre);

        listeLivresView = findViewById(R.id.bookListView);
        listeLivres = new ArrayList<>();
        livreAdaptateur = new LivreAdaptateur(this, listeLivres);
        listeLivresView.setAdapter(livreAdaptateur);

        livresPresentateur = new ListeLivresPresentateur(new LivreModele(), this);

        dbHelper = new DataBaseHelper(this);
        utilisateurConnecte = (Utilisateur) getIntent().getSerializableExtra("utilisateur");

        Button boutonRetour = findViewById(R.id.btnRetour_ListLivre);
        Button boutonFavoris = findViewById(R.id.btnFavoris_ListLivre);

        boutonRetour.setOnClickListener(this);
        boutonFavoris.setOnClickListener(this);

        listeLivresView.setOnItemClickListener((parent, view, position, id) -> {
            Livre livreSelectionne = listeLivres.get(position);
            Intent intent = new Intent(ListeLivresActivity.this, DetailsLivres.class);
            intent.putExtra("livre", livreSelectionne);
            intent.putExtra("utilisateur", utilisateurConnecte);
            startActivityForResult(intent, CODE_REQUETE_DETAIL_LIVRE);
        });

        livresPresentateur.fetchBooks();

        livreAdaptateur.setOnStarClickListener((livre, position) -> {
            if (dbHelper.estFavori(livre.getId(), utilisateurConnecte.getId())) {
                dbHelper.supprimerFavori(livre.getId(), utilisateurConnecte.getId());
            } else {
                dbHelper.ajouterFavori(livre.getId(), utilisateurConnecte.getId());
            }
            livreAdaptateur.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRetour_ListLivre) {
            Intent intent1 = new Intent(ListeLivresActivity.this, activity_login.class);
            startActivity(intent1);
            finish();
        } else if (v.getId() == R.id.btnFavoris_ListLivre) {
            Intent intent2 = new Intent(ListeLivresActivity.this, ListeFavorisActivity.class);
            intent2.putExtra("utilisateur", utilisateurConnecte);
            startActivity(intent2);
        }
    }

    public void updateBookList(ArrayList<Livre> livres) {
        listeLivres.clear();
        listeLivres.addAll(livres);
        livreAdaptateur.notifyDataSetChanged();
    }

    public void showError(String message) {
        Log.e(TAG, message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUETE_DETAIL_LIVRE && resultCode == RESULT_OK) {
            livresPresentateur.fetchBooks();
        }
    }
}