package com.eq3.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.eq3.bibliotheque.R;

public class MenuUtilisateur extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utilisateur);

        Button btnListeLivres = findViewById(R.id.btnListeLivres);
        Button btnFavoris = findViewById(R.id.btnFavoris);

        btnListeLivres.setOnClickListener(v -> {
            startActivity(new Intent(MenuUtilisateur.this, ListeLivresActivity.class));
        });

        btnFavoris.setOnClickListener(v -> {
            startActivity(new Intent(MenuUtilisateur.this, ListeFavorisActivity.class));
        });

    }

}