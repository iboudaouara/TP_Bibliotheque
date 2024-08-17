package com.eq3.bibliotheque.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.dao.DataBaseHelper;
import com.eq3.bibliotheque.dao.LivreDAO;
import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.modele.Utilisateur;

import org.json.JSONException;

import java.io.IOException;

public class DetailsLivres extends AppCompatActivity {

    private EditText editTitre, editAuteur, editISBN, editMaisonEdition, editDatePublication, editDescription;
    private TextView textMoyenneEvaluations;
    private RatingBar ratingBarEvaluation;
    private Button buttonEnregistrer, buttonRetour;
    private Livre livre;
    private LivreDAO livreDao;
    private Utilisateur utilisateurConnecte;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_livres);

        initializeViews();
        livreDao = new LivreDAO(this);
        dbHelper = new DataBaseHelper(this);

        livre = (Livre) getIntent().getSerializableExtra("livre");
        utilisateurConnecte = (Utilisateur) getIntent().getSerializableExtra("utilisateur");

        if (livre != null && utilisateurConnecte != null) {
            populateFields();
            float evaluationUtilisateur = dbHelper.getEvaluationUtilisateur(livre.getId(), utilisateurConnecte.getId());
            ratingBarEvaluation.setRating(evaluationUtilisateur);
        } else {
            Toast.makeText(this, "Erreur : Livre ou utilisateur non trouvé", Toast.LENGTH_SHORT).show();
            finish();
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

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
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
            Log.d("DetailsLivres", "Nouvelle évaluation : " + newRating);

            float oldRating = dbHelper.getEvaluationUtilisateur(livre.getId(), utilisateurConnecte.getId());
            Log.d("DetailsLivres", "Ancienne évaluation : " + oldRating);

            if (oldRating == 0) {
                // Nouvelle évaluation
                livre.setAppreciationMoyenne(
                        (livre.getAppreciationMoyenne() * livre.getNombreAppreciations() + newRating) /
                                (livre.getNombreAppreciations() + 1)
                );
                livre.setNombreAppreciations(livre.getNombreAppreciations() + 1);
            } else {
                // Modification d'une évaluation existante
                livre.setAppreciationMoyenne(
                        (livre.getAppreciationMoyenne() * livre.getNombreAppreciations() - oldRating + newRating) /
                                livre.getNombreAppreciations()
                );
            }

            Log.d("DetailsLivres", "Mise à jour du livre : " + livre.getAppreciationMoyenne());

            // Lancer l'AsyncTask pour exécuter les opérations réseau
            new SaveRatingTask().execute(livre, newRating);

        } catch (Exception e) {
            Toast.makeText(this, "Une erreur inattendue est survenue : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("DetailsLivres", "Exception inattendue", e);
        }
    }

    // AsyncTask pour les opérations réseau
    @SuppressLint("StaticFieldLeak")
    private class SaveRatingTask extends AsyncTask<Object, Void, Boolean[]> {
        @Override
        protected Boolean[] doInBackground(Object... params) {
            Livre livre = (Livre) params[0];
            float newRating = (float) params[1];
            try {
                boolean livreUpdated = livreDao.updateLivre(livre);
                boolean evaluationSaved = dbHelper.saveEvaluationUtilisateur(livre.getId(), utilisateurConnecte.getId(), newRating);
                return new Boolean[]{livreUpdated, evaluationSaved};
            } catch (IOException | JSONException e) {
                Log.e("DetailsLivres", "Erreur lors de l'enregistrement", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Boolean[] result) {
            if (result != null && result[0] && result[1]) {
                updateRatingDisplay();
                Toast.makeText(DetailsLivres.this, "Évaluation enregistrée", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);

                // Ajouter aux favoris si la note est >= 4
                if (ratingBarEvaluation.getRating() >= 4) {
                    dbHelper.ajouterFavori(livre.getId(), utilisateurConnecte.getId());
                } else {
                    dbHelper.supprimerFavori(livre.getId(), utilisateurConnecte.getId());
                }
            } else {
                Toast.makeText(DetailsLivres.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
            }
        }
    }



}
