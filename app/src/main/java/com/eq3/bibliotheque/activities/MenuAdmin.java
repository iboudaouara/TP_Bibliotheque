package com.eq3.bibliotheque.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.eq3.bibliotheque.R;

/**
 * Activité principale pour le menu administrateur.
 * Cette activité permet de naviguer vers différentes pages de gestion des livres et des clients.
 */
public class MenuAdmin extends AppCompatActivity implements View.OnClickListener {

    private Button AjouterLivre;
    private Button SupprimerLivre;
    private Button ListeDesUtilisateurs;
    private Button RetourConnexion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_admin);

        // Configurer l'écouteur des fenêtres pour gérer les marges de la barre système.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser les boutons.
        AjouterLivre = findViewById(R.id.btnAjouterMenu);
        SupprimerLivre = findViewById(R.id.btnSupprimerMenu);
        ListeDesUtilisateurs = findViewById(R.id.btnListeUserMenu);
        RetourConnexion = findViewById(R.id.btnRetourMenu);

        // Définir les écouteurs de clic pour les boutons.
        AjouterLivre.setOnClickListener(this);
        SupprimerLivre.setOnClickListener(this);
        ListeDesUtilisateurs.setOnClickListener(this);
        RetourConnexion.setOnClickListener(this);
    }

    /**
     * Gère les clics sur les boutons pour naviguer vers les différentes activités.
     *
     * @param v La vue qui a été cliquée.
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.btnAjouterMenu) {
            // Redirige vers l'activité pour ajouter un livre.
            intent = new Intent(MenuAdmin.this, AjouterLivreActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnSupprimerMenu) {
            // Redirige vers l'activité pour supprimer un livre.
            intent = new Intent(MenuAdmin.this, SupprimerLivreActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnListeUserMenu) {
            // Redirige vers l'activité pour afficher la liste des clients.
            intent = new Intent(MenuAdmin.this, ListeClientsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnRetourMenu) {
            // Redirige vers l'activité de connexion.
            intent = new Intent(MenuAdmin.this, ConnexionActivity.class);
            startActivity(intent);
            finish(); // Ferme l'activité actuelle.
        }
    }
}

