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

                            Livre livreTrouve = null;

                            System.out.println("Ici le problème: " +
                                    supprimerLivreActivity.getIsbn());

                            for (Livre livre : listeLivres) {
                                if (livre.getIsbn().equals(supprimerLivreActivity.getIsbn())) {

                                    livreTrouve = livre;

                                    break;
                                }
                            }

                            final Livre livreFinal = livreTrouve;







                        } catch (IOException | JSONException e) {
                            throw new RuntimeException(e);
                        }



            }
        })).start();

    }

    public void supprimerLivre() throws IOException, JSONException {


        try {
            livreDao.supprimerLivre((livreDao.getLivres()).get(0));
        } catch (IOException e) {
            throw new IOException(e);
        } catch (JSONException e) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                throw new JSONException(e);
            }
        }
    }


}
