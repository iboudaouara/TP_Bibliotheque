package com.eq3.bibliotheque.presentateur;

import android.app.Activity;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.activities.SupprimerLivreActivity;
import com.eq3.bibliotheque.dao.LivreDAO;
import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.modele.LivreModele;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SupprimerLivrePresentateur {

    private SupprimerLivreActivity supprimerLivreActivity;
    private LivreModele livreModele;

    private LivreDAO livreDao;
    Livre livreTrouve = null;

    public SupprimerLivrePresentateur(SupprimerLivreActivity supprimerLivreActivity,
                                      LivreDAO livreDao) {

        this.supprimerLivreActivity = supprimerLivreActivity;
        this.livreDao = livreDao;

    }

    public void rechercherLivreParISBN() {

        (new Thread(new Runnable() {
            @Override
            public void run() {

                        try {
                            List<Livre> listeLivres;

                            listeLivres = livreDao.getLivres();



                            System.out.println("Ici le problème: " +
                                    supprimerLivreActivity.getIsbn());

                            for (Livre livre : listeLivres) {
                                if (livre.getIsbn().equals(supprimerLivreActivity.getIsbn())) {

                                    livreTrouve = livre;

                                    break;
                                }
                            }

                            System.out.println("Titre: " + livreTrouve.getTitre());

                            supprimerLivreActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    EditText edtAuteur = supprimerLivreActivity.findViewById(R.id.edtAuteurSupprimer);
                                    edtAuteur.setText(livreTrouve.getAuteur());

                                    EditText edtMaisonEdition = supprimerLivreActivity.findViewById(R.id.edtMaisonEditionSupprimer);
                                    edtMaisonEdition.setText(livreTrouve.getMaisonEdition());

                                    EditText edtDatePublication = supprimerLivreActivity.findViewById(R.id.edtDatePublicationSupprimer);
                                    edtDatePublication.setText(livreTrouve.getDatePublication());

                                    EditText edtDescription = supprimerLivreActivity.findViewById(R.id.edtDescriptionSupprimer);
                                    edtDescription.setText(livreTrouve.getDescription());
                                }
                            });







                        } catch (IOException | JSONException e) {
                            throw new RuntimeException(e);
                        }



            }
        })).start();

    }

    public void supprimerLivre() throws IOException, JSONException {

        (new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    livreDao.supprimerLivre(livreTrouve);

                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        })).start();
    }


}
