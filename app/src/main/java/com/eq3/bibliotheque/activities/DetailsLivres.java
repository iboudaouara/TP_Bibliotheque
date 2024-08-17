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

/**
 * Activity qui affiche les détails d'un livre et permet à l'utilisateur
 * de visualiser les informations et d'ajouter une évaluation.
 */
public class DetailsLivres extends AppCompatActivity {

    private EditText editTitre, editAuteur, editISBN, editMaisonEdition, editDatePublication, editDescription;
    private TextView textMoyenneEvaluations;
    private RatingBar ratingBarEvaluation;
    private Button buttonEnregistrer, buttonRetour;
    private Livre livre;
    private LivreDAO livreDao;
    private Utilisateur utilisateurConnecte;
    private DataBaseHelper dbHelper;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les vues, charge les données du livre et de l'utilisateur,
     * et configure les écouteurs d'événements.
     *
     * @param savedInstanceState Contient l'état précédemment sauvegardé de l'activité, si disponible.
     */
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
        }

        else {

            Toast.makeText(this, "Erreur : Livre ou utilisateur non trouvé", Toast.LENGTH_SHORT).show();
            finish();
        }

        setupListeners();
    }

    /**
     * Initialise les vues de l'interface utilisateur.
     */
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

    /**
     * Remplit les champs avec les informations du livre sélectionné.
     */
    private void populateFields() {

        editTitre.setText(livre.getTitre());
        editAuteur.setText(livre.getAuteur());
        editISBN.setText(livre.getIsbn());
        editMaisonEdition.setText(livre.getMaisonEdition());
        editDatePublication.setText(livre.getDatePublication());
        editDescription.setText(livre.getDescription());
        updateRatingDisplay();
    }

    /**
     * Met à jour l'affichage de la moyenne des évaluations et du nombre d'évaluations.
     */
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void updateRatingDisplay() {

        textMoyenneEvaluations.setText("Moyenne des évaluations : " + String.format("%.2f", livre.getAppreciationMoyenne()) +
                " (" + livre.getNombreAppreciations() + " évaluations)");
    }

    /**
     * Configure les écouteurs d'événements pour les boutons d'enregistrement et de retour.
     */
    private void setupListeners() {

        buttonEnregistrer.setOnClickListener(v -> saveRating());
        buttonRetour.setOnClickListener(v -> finish());
    }

    /**
     * Sauvegarde la nouvelle évaluation de l'utilisateur et met à jour les informations du livre.
     */
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
            }

            else {

                // Modification d'une évaluation existante
                livre.setAppreciationMoyenne(
                        (livre.getAppreciationMoyenne() * livre.getNombreAppreciations() - oldRating + newRating) /
                                livre.getNombreAppreciations()
                );
            }

            Log.d("DetailsLivres", "Mise à jour du livre : " + livre.getAppreciationMoyenne());

            // Lancer l'AsyncTask pour exécuter les opérations réseau
            new SaveRatingTask().execute(livre, newRating);

        }

        catch (Exception e) {

            Toast.makeText(this, "Une erreur inattendue est survenue : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("DetailsLivres", "Exception inattendue", e);
        }

    }

    /**
     * Classe interne pour exécuter des opérations en arrière-plan pour sauvegarder les évaluations.
     * Utilise AsyncTask pour éviter de bloquer le thread principal lors des opérations réseau.
     */
    @SuppressLint("StaticFieldLeak")
    private class SaveRatingTask extends AsyncTask<Object, Void, Boolean[]> {

        /**
         * Exécute en arrière-plan la mise à jour du livre et l'enregistrement de l'évaluation utilisateur.
         *
         * @param params Paramètres comprenant le livre et la nouvelle évaluation.
         * @return Un tableau de Boolean indiquant si les opérations ont réussi.
         */
        @Override
        protected Boolean[] doInBackground(Object... params) {

            Livre livre = (Livre) params[0];
            float newRating = (float) params[1];

            try {

                boolean livreUpdated = livreDao.updateLivre(livre);
                boolean evaluationSaved = dbHelper.saveEvaluationUtilisateur(livre.getId(), utilisateurConnecte.getId(), newRating);
                return new Boolean[]{livreUpdated, evaluationSaved};
            }

            catch (IOException | JSONException e) {

                Log.e("DetailsLivres", "Erreur lors de l'enregistrement", e);
                return null;
            }

        }

        /**
         * Appelée une fois que les opérations en arrière-plan sont terminées.
         * Met à jour l'interface utilisateur en fonction du résultat des opérations.
         *
         * @param result Résultat des opérations de mise à jour du livre et de l'évaluation.
         */
        @Override
        protected void onPostExecute(Boolean[] result) {

            if (result != null && result[0] && result[1]) {

                updateRatingDisplay();
                Toast.makeText(DetailsLivres.this, "Évaluation enregistrée", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);

                // Ajouter aux favoris si la note est >= 4
                if (ratingBarEvaluation.getRating() >= 4) {

                    dbHelper.ajouterFavori(livre.getId(), utilisateurConnecte.getId());
                }

                else {

                    dbHelper.supprimerFavori(livre.getId(), utilisateurConnecte.getId());
                }
            }

            else {

                Toast.makeText(DetailsLivres.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
