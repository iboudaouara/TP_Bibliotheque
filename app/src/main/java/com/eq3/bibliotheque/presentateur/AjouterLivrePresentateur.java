package com.eq3.bibliotheque.presentateur;

import android.os.Build;

import com.eq3.bibliotheque.activities.AjouterLivreActivity;
import com.eq3.bibliotheque.dao.LivreDAO;
import com.eq3.bibliotheque.modele.Livre;

import org.json.JSONException;

import java.io.IOException;

public class AjouterLivrePresentateur {

    private AjouterLivreActivity ajouterLivreActivity;
    private LivreDAO livreDao;

    public AjouterLivrePresentateur(AjouterLivreActivity ajouterLivreActivity,
                                    LivreDAO livreDao) {

        this.ajouterLivreActivity = ajouterLivreActivity;
        this.livreDao = livreDao;

    }

    public void ajouterLivre() throws IOException, JSONException {

        String titre = ajouterLivreActivity.getTitre();
        String auteur = ajouterLivreActivity.getAuteur();
        String isbn = ajouterLivreActivity.getISBN();
        String maisonEdition = ajouterLivreActivity.getMaisonEdition();
        String datePublication = ajouterLivreActivity.getDatePublication();
        String description = ajouterLivreActivity.getDescription();

        if (titre.isEmpty() || auteur.isEmpty() ||
                isbn.isEmpty() || maisonEdition.isEmpty() ||
                datePublication.isEmpty() || description.isEmpty() ) {

            /*Toast.makeText(ajouterLivreActivity, "Veuillez remplir tous les champs obligatoires",
                    Toast.LENGTH_SHORT).show();

             */

        } else {

            Livre livre = new Livre(titre, auteur, isbn,
                    maisonEdition, datePublication, description);
            try {
                livreDao.ajouterLivre(livre);
            } catch (IOException e) {
                throw new IOException(e);
            } catch (JSONException e) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                    throw new JSONException(e);
                }
            }

        }

    }


}
