package com.eq3.bibliotheque.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.dao.LivreDao;

import org.json.JSONException;

import java.io.IOException;

public class DetailsLivres extends AppCompatActivity {

    private EditText editTitre, editAuteur, editISBN, editMaisonEdition, editDatePublication, editDescription;
    private TextView textMoyenneEvaluations;
    private RatingBar ratingBarEvaluation;
    private Button buttonEnregistrer, buttonRetour;
    private Livre livre;
    private LivreDao livreDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_livres);

        initializeViews();
        livreDao = new LivreDao();

        // Récupérer le livre passé en extra
        livre = (Livre) getIntent().getSerializableExtra("livre");
        if (livre != null) {
            populateFields();
        } else {
            Toast.makeText(this, "Erreur : Livre non trouvé", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité si le livre n'est pas trouvé
        }

        setupListeners();
    }

    private void initializeViews() {
        editTitre = findViewById(R.id.editTitre);
        editAuteur = findViewById(R.id.editAuteur);
        editISBN = findViewById(R.id.editISBN);
        editMaisonEdition = findViewById(R.id.editMaisonEdition);
        editDatePublication = findViewById(R.id.editDatePublication);
        editDescription = findViewById(R.id.editDescription);
        textMoyenneEvaluations = findViewById(R.id.textMoyenneEvaluations);
        ratingBarEvaluation = findViewById(R.id.ratingBarEvaluation);
        buttonEnregistrer = findViewById(R.id.buttonEnregistrer);
        buttonRetour = findViewById(R.id.buttonRetour);
    }

    private void populateFields() {
        editTitre.setText(livre.getTitre());
        editAuteur.setText(livre.getAuteur());
        editISBN.setText(livre.getIsbn());
        editMaisonEdition.setText(livre.getMaisonEdition());
        editDatePublication.setText(livre.getDatePublication());
        editDescription.setText(livre.getDescription());
        updateRatingDisplay();
    }

    private void updateRatingDisplay() {
        textMoyenneEvaluations.setText("Moyenne des évaluations : " + String.format("%.2f", livre.getAppreciationMoyenne()) +
                " (" + livre.getNombreAppreciations() + " évaluations)");
    }

    private void setupListeners() {
        buttonEnregistrer.setOnClickListener(v -> saveRating());
        buttonRetour.setOnClickListener(v -> finish());
    }

    private void saveRating() {
        float newRating = ratingBarEvaluation.getRating();
        try {
            if (livre.getNombreAppreciations() == 0) {
                // Première évaluation
                livre.setAppreciationMoyenne(newRating);
                livre.setNombreAppreciations(1);
            } else {
                // Mise à jour de l'évaluation
                double oldTotal = livre.getAppreciationMoyenne() * livre.getNombreAppreciations();
                double newTotal = oldTotal + newRating;
                int newCount = livre.getNombreAppreciations() + 1;
                livre.setAppreciationMoyenne(newTotal / newCount);
                livre.setNombreAppreciations(newCount);
            }

            if (livreDao.updateLivre(livre)) {
                updateRatingDisplay();
                Toast.makeText(this, "Évaluation enregistrée", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException | JSONException e) {
            Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}