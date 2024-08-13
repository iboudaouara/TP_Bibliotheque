package com.eq3.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.adaptateur.BookAdapter;
import com.eq3.bibliotheque.modele.BookModel;
import com.eq3.bibliotheque.modele.Livre;
import com.eq3.bibliotheque.presentateur.BookListPresentateur;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView bookListView;
    private BookAdapter bookAdapter;
    private ArrayList<Livre> bookList;
    private static final String TAG = "BookListActivity";
    private static final int REQUEST_CODE_BOOK_DETAIL = 1;
    private BookListPresentateur bookPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_livre);

        bookListView = findViewById(R.id.bookListView); // Assurez-vous que le ListView a cet ID
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(this, bookList);
        bookListView.setAdapter(bookAdapter);

        bookPresenter = new BookListPresentateur(new BookModel(), this);

        Button returnButton = findViewById(R.id.btnRetour_ListLivre);
        Button favoritesButton = findViewById(R.id.btnFavoris_ListLivre);

        returnButton.setOnClickListener(this);
        favoritesButton.setOnClickListener(this);

        bookListView.setOnItemClickListener((parent, view, position, id) -> {
            Livre selectedBook = bookList.get(position);
            Intent intent = new Intent(BookListActivity.this, AjoutLivreActivity.class);

            // Passe l'ID du livre pour récupérer les détails
            intent.putExtra("id", selectedBook.getId());

            startActivityForResult(intent, REQUEST_CODE_BOOK_DETAIL);
        });

        bookPresenter.fetchBooks();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRetour_ListLivre) {
            Intent intent1 = new Intent(BookListActivity.this, activity_login.class);
            startActivity(intent1);
            finish();
        } else if (v.getId() == R.id.btnFavoris_ListLivre) {
            // TODO: Implémenter la logique pour les favoris
        }
    }

    public void updateBookList(ArrayList<Livre> livres) {
        bookList.clear();
        bookList.addAll(livres);
        bookAdapter.notifyDataSetChanged();
    }

    public void showError(String message) {
        Log.e(TAG, message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BOOK_DETAIL && resultCode == RESULT_OK) {
            if (data != null && data.getStringExtra("id") != null) {
                // Rafraîchir la liste des livres après la mise à jour
                bookPresenter.fetchBooks();
            }
        }
    }
}
