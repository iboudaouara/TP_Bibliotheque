package com.eq3.bibliotheque.adaptateur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.modele.Livre;

import java.util.List;

/**
 * Adaptateur personnalisé pour afficher une liste de livres dans un ListView.
 */
public class LivreAdaptateur extends ArrayAdapter<Livre> {

    private Context contexte;
    private List<Livre> livres;
    private OnStarClickListener starClickListener;

    /**
     * Constructeur pour l'adaptateur de livres.
     *
     * @param contexte Contexte actuel de l'application.
     * @param livres   Liste des livres à afficher.
     */
    public LivreAdaptateur(Context contexte, List<Livre> livres) {
        super(contexte, R.layout.item_livre, livres);
        this.contexte = contexte;
        this.livres = livres;
    }

    /**
     * Interface pour écouter les clics sur l'icône de notation (étoile).
     */
    public interface OnStarClickListener {
        void onStarClick(Livre livre, int position);
    }

    /**
     * Définit le listener pour les clics sur l'icône de notation (étoile).
     *
     * @param listener Listener pour les clics sur l'étoile.
     */
    public void setOnStarClickListener(OnStarClickListener listener) {
        this.starClickListener = listener;
    }

    /**
     * Méthode pour obtenir la vue d'un élément à une position spécifique dans la liste.
     *
     * @param position    Position de l'élément dans la liste.
     * @param convertView Vue recyclée à réutiliser si possible.
     * @param parent      Le parent auquel cette vue sera attachée.
     * @return Vue pour l'élément à la position spécifiée.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vue = convertView;

        // Si aucune vue recyclée n'est disponible, gonfle une nouvelle vue.
        if (vue == null) {
            vue = LayoutInflater.from(contexte).inflate(R.layout.item_livre, parent, false);
        }

        // Récupère l'élément à la position spécifiée dans la liste.
        final Livre livre = livres.get(position);

        if (livre != null) {

            // Liaisons des éléments d'interface utilisateur aux données du livre.
            TextView titreTextView = vue.findViewById(R.id.tvTitre);
            TextView dateTextView = vue.findViewById(R.id.tvDatePublication);
            TextView appreciationTextView = vue.findViewById(R.id.tvAppreciation);

            // Définit le texte des TextViews avec les informations du livre.
            titreTextView.setText(livre.getTitre());
            dateTextView.setText(livre.getDatePublication());
            appreciationTextView.setText(String.format("%.1f", livre.getAppreciationMoyenne()));

            // Ajout d'un listener pour gérer les clics sur le TextView de notation.
            appreciationTextView.setOnClickListener(v -> {
                if (starClickListener != null) {
                    starClickListener.onStarClick(livre, position);
                }
            });

        }

        return vue;
    }
}
