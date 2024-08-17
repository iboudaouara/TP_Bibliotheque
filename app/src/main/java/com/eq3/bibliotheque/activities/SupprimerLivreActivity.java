package com.eq3.bibliotheque.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.dao.LivreDAO;
import com.eq3.bibliotheque.presentateur.SupprimerLivrePresentateur;

import org.json.JSONException;

import java.io.IOException;

public class SupprimerLivreActivity extends AppCompatActivity
                                        implements View.OnClickListener {

    // Vues
    private Button btnRechercher, btnRetour, btnSupprimer;

    private EditText edtISBN, edtAuteur, edtMaisonEdition, edtDatePublication, edtDescription;

    // Présentateur
    private SupprimerLivrePresentateur supprimerLivrePresentateur;

    private String isbn, auteur, maisonEdition, datePublication, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_livre);

        // Trouver les vues
        btnRechercher = findViewById(R.id.btnRechercher);
        btnRetour = findViewById(R.id.btnRetourSupprimer);
        btnSupprimer = findViewById(R.id.btnSupprimer);

        edtISBN = findViewById(R.id.edtRechercheISBN);
        edtAuteur = findViewById(R.id.edtAuteurSupprimer);
        edtMaisonEdition = findViewById(R.id.edtMaisonEditionSupprimer);
        edtDatePublication = findViewById(R.id.edtDatePublicationSupprimer);
        edtDescription = findViewById(R.id.edtDescriptionSupprimer);

        // Ajouter des écouteurs pour les boutons
        btnRechercher.setOnClickListener(this);
        btnRetour.setOnClickListener(this);
        btnSupprimer.setOnClickListener(this);

        // Créer le présentateur
        supprimerLivrePresentateur = new SupprimerLivrePresentateur(this, new LivreDAO(this));

    }

    @Override
    public void onClick(View v) {

        // Pour lancer la recherche du livre
        if (v == btnRechercher) {

            Toast.makeText(this, "Vous avez lancé la recherche.",
                    Toast.LENGTH_SHORT).show();

            supprimerLivrePresentateur.rechercherLivreParISBN();
            System.out.println("Après");

        }

        // Pour annuler la suppresion d'un livre
        else if (v == btnRetour) {

            setResult(RESULT_CANCELED);
            finish();

        }

        // Pour confirmer la suppresion d'un livre
        else if (v == btnSupprimer) {

            try {
                supprimerLivrePresentateur.supprimerLivre();
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }

            setResult(RESULT_OK);
            finish();

        }
    }

    public String getIsbn() {
        return edtISBN.getText().toString();
    }

    public String getAuteur() {
        return edtAuteur.getText().toString();
    }

    public String getMaisonEdition() {
        return edtMaisonEdition.getText().toString();
    }

    public String getDatePublication() {
        return edtDatePublication.getText().toString();
    }

    public String getDescription() {
        return edtDescription.getText().toString();
    }
}