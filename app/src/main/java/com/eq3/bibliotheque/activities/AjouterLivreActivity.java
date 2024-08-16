package com.eq3.bibliotheque.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.dao.LivreDAO;
import com.eq3.bibliotheque.presentateur.AjouterLivrePresentateur;

import org.json.JSONException;

import java.io.IOException;

public class AjouterLivreActivity extends AppCompatActivity implements View.OnClickListener {

    // Vues
    private TextView txtTitre, txtAuteur, txtISBN, txtMaisonEdition,
            txtDatePublication, txtDescription;

    private EditText edtTitre, edtAuteur, edtISBN, edtMaisonEdition,
            edtDatePublication, edtDescription;

    private Button btnRetour, btnAjouter;

    // Pour récupérer les données entrées
    private String titre, auteur, isbn, maisonEdition,
            datePublication, description;

    // Présentateur pour ajouter un livre
    private AjouterLivrePresentateur ajouterLivrePresentateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_livre);

        // Trouver les vues
        txtTitre = findViewById(R.id.txtTitre);
        edtTitre = findViewById(R.id.edtTitre);

        txtAuteur = findViewById(R.id.txtAuteur);
        edtAuteur = findViewById(R.id.edtAuteur);

        txtISBN = findViewById(R.id.txtISBN);
        edtISBN = findViewById(R.id.edtISBN);

        txtMaisonEdition = findViewById(R.id.txtMaisonEdition);
        edtMaisonEdition = findViewById(R.id.edtMaisonEdition);

        txtDatePublication = findViewById(R.id.txtDatePublication);
        edtDatePublication = findViewById(R.id.edtDatePublication);

        txtDescription = findViewById(R.id.txtDescription);
        edtDescription = findViewById(R.id.edtDescription);

        btnRetour = findViewById(R.id.btnRetourAjouter);
        btnAjouter = findViewById(R.id.btnSupprimer);

        // Créer le présentateur
        ajouterLivrePresentateur = new AjouterLivrePresentateur(this, new LivreDAO());

        // Ajouter des écouteurs aux boutons
        btnRetour.setOnClickListener(this);
        btnAjouter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        // Annuler l'ajout du livre
        if (v == btnRetour) {

            setResult(RESULT_CANCELED);
            finish();

        }
        // Confirmer l'ajout du livre
        else if (v == btnAjouter) {
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Utiliser le présentateur pour ajouter le livre
                        ajouterLivrePresentateur.ajouterLivre();

                    }  catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            })).start();



            setResult(RESULT_OK);
            finish();
        }


    }


    public String getTitre() {
        return edtTitre.getText().toString();
    }

    public String getAuteur() {
        return edtAuteur.getText().toString();
    }

    public String getISBN() {
        return edtISBN.getText().toString();
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
