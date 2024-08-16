package com.eq3.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.adaptateur.LivreAdaptateur;
import com.eq3.bibliotheque.dao.DataBaseHelper;
import com.eq3.bibliotheque.modele.Livre;

import java.util.ArrayList;
import java.util.List;

public class ListeFavorisActivity extends AppCompatActivity {

    private ListView listeFavorisView;
    private LivreAdaptateur livreAdaptateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_favoris);

        listeFavorisView = findViewById(R.id.favorisListView);
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        List<Livre> favoris = dbHelper.getFavoris();

        livreAdaptateur = new LivreAdaptateur(this, new ArrayList<>(favoris));
        listeFavorisView.setAdapter(livreAdaptateur);

        listeFavorisView.setOnItemClickListener((parent, view, position, id) -> {
            Livre livreSelectionne = favoris.get(position);
            Intent intent = new Intent(ListeFavorisActivity.this, DetailsLivres.class);
            intent.putExtra("livre", livreSelectionne);
            startActivity(intent);
        });

    }

}