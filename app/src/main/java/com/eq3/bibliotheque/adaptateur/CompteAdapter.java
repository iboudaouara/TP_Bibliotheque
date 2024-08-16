package com.eq3.bibliotheque.adaptateur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.modele.Utilisateur;

import java.util.List;

public class CompteAdapter extends ArrayAdapter<Utilisateur> {

    private Context contexte;
    private List<Utilisateur> utilisateurs;

    public CompteAdapter(Context context, List<Utilisateur> utilisateurs) {
        super(context, R.layout.item_livre, utilisateurs);
        this.contexte = context;
        this.utilisateurs = utilisateurs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vue = convertView;

        // Si aucune vue recyclée n'est disponible, gonfle une nouvelle vue.
        if (vue == null) {
            vue = LayoutInflater.from(contexte).inflate(R.layout.client, parent, false);
        }

        // Récupère l'élément à la position spécifiée dans la liste.
        final Utilisateur utilisateur = utilisateurs.get(position);

        if (utilisateur != null) {

            // Liaisons des éléments d'interface utilisateur aux données du livre.
            TextView txtNomClient = vue.findViewById(R.id.txtNomClient);
            TextView txtEmailClient = vue.findViewById(R.id.txtEmailClient);

            // Définit le texte des TextViews avec les informations du livre.
            txtNomClient.setText(utilisateur.getPrenom() + " " + utilisateur.getNom());
            txtEmailClient.setText(utilisateur.getMail());

        }

        return vue;
    }
}
