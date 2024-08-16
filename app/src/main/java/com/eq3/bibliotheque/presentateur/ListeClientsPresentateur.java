package com.eq3.bibliotheque.presentateur;

import android.widget.ListView;

import com.eq3.bibliotheque.activities.ListeClientsActivity;
import com.eq3.bibliotheque.adaptateur.CompteAdapter;
import com.eq3.bibliotheque.dao.ComptesDAO;
import com.eq3.bibliotheque.modele.Utilisateur;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ListeClientsPresentateur {

    private ListeClientsActivity activite;
    private ComptesDAO comptesDAO;

    List<Utilisateur> listeUtilisateurs;

    public ListeClientsPresentateur(ListeClientsActivity activite, ComptesDAO comptesDAO) {
        this.activite = activite;
        this.comptesDAO = comptesDAO;
    }


    public void chargerComptes(ListView lvListeClients) {

        (new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    listeUtilisateurs = comptesDAO.getComptes();
                } catch (IOException | JSONException e) {

                    throw new RuntimeException(e);
                }
                    activite.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {



                             CompteAdapter adapter = new CompteAdapter(activite, listeUtilisateurs);
                             lvListeClients.setAdapter(adapter);



                        }
                    });



            }
        })).start();
    }
}
