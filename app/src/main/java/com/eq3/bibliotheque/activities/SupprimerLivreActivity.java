package com.eq3.bibliotheque.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.dao.LivreDAO;
import com.eq3.bibliotheque.presentateur.SupprimerLivrePresentateur;

public class SupprimerLivreActivity extends AppCompatActivity
                                        implements View.OnClickListener {

    // Vues
    private Button btnRechercher, btnRetour, btnSupprimer;

    // Présentateur
    private SupprimerLivrePresentateur supprimerLivrePresentateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_livre);

        // Trouver les vues
        btnRechercher = findViewById(R.id.btnRechercher);
        btnRetour = findViewById(R.id.btnRetourSupprimer);
        btnSupprimer = findViewById(R.id.btnSupprimer);

        // Ajouter des écouteurs pour les boutons
        btnRechercher.setOnClickListener(this);
        btnRetour.setOnClickListener(this);
        btnSupprimer.setOnClickListener(this);

        // Créer le présentateur
        supprimerLivrePresentateur = new SupprimerLivrePresentateur(this, new LivreDAO());

    }

    @Override
    public void onClick(View v) {

        // Pour lancer la recherche du livre
        if (v == btnRechercher) {

            Toast.makeText(this, "Vous avez lancé la recherche.",
                    Toast.LENGTH_SHORT).show();

        }

        // Pour annuler la suppresion d'un livre
        else if (v == btnRetour) {

            setResult(RESULT_CANCELED);
            finish();

        }

        // Pour confirmer la suppresion d'un livre
        else if (v == btnSupprimer) {

            setResult(RESULT_OK);
            finish();

        }
    }
}