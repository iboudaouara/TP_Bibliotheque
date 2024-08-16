package com.eq3.bibliotheque.presentateur;

import android.os.Build;

import com.eq3.bibliotheque.activities.SupprimerLivreActivity;
import com.eq3.bibliotheque.dao.LivreDAO;
import com.eq3.bibliotheque.modele.Livre;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SupprimerLivrePresentateur {

    private SupprimerLivreActivity supprimerLivreActivity;
    private LivreDAO livreDao;

    public SupprimerLivrePresentateur(SupprimerLivreActivity supprimerLivreActivity,
                                      LivreDAO livreDao) {

        this.supprimerLivreActivity = supprimerLivreActivity;
        this.livreDao = livreDao;

    }

    public void rechercherLivreParISBN(String isbn) {

        try {
            List<Livre> listeLivres = livreDao.getLivres();
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

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
