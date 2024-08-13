package com.eq3.bibliotheque.adaptateur;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.modele.Livre;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Livre> {

    private Context context;
    private List<Livre> livres;

    public BookAdapter(Context context, List<Livre> livres) {
        super(context, R.layout.item_livre, livres);
        this.context = context;
        this.livres = livres;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_livre, parent, false);
        }

        final Livre livre = livres.get(position);

        if(livre != null){

            TextView titleTextView = view.findViewById(R.id.tvTitre);
            TextView dateTextView = view.findViewById(R.id.tvDatePublication);
            TextView ratingTextView = view.findViewById(R.id.tvAppreciation);

            titleTextView.setText(livre.getTitre());
            dateTextView.setText(livre.getDatePublication());
            ratingTextView.setText(String.format("%.1f", livre.getAppreciationMoyenne()));

            //ajouter une methode pour que le text change de couleur lorsque la souris passe dessus ?

        }



        return view;
    }
}
