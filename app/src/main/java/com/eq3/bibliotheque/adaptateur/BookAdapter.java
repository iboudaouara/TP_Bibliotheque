package com.eq3.bibliotheque.adaptateur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.modele.Livre;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Livre> livres;
    private OnBookClickListener listener;

    public BookAdapter(List<Livre> livres, OnBookClickListener listener) {

        this.livres = livres;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {

        Livre livre = livres.get(position);
        holder.bind(livre);
    }

    @Override
    public int getItemCount() {

        return livres.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView dateTextView;
        TextView ratingTextView;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onBookClick(livres.get(position));
                }
            });

        }

        void bind(Livre livre) {

            titleTextView.setText(livre.getTitre());
            dateTextView.setText(livre.getDate_publication());
            ratingTextView.setText(String.format("%.1f", livre.getAppreciation_moyenne()));
        }

    }

    public interface OnBookClickListener {

        void onBookClick(Livre livre);
    }

}
