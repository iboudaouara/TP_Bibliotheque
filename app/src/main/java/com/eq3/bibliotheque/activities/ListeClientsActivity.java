package com.eq3.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.adaptateur.CompteAdapter;
import com.eq3.bibliotheque.dao.ComptesDAO;
import com.eq3.bibliotheque.presentateur.ListeClientsPresentateur;

public class ListeClientsActivity extends AppCompatActivity
                                    implements View.OnClickListener{

    private ListView lvListeClients;
    private Button btnRetour;

    private ListeClientsPresentateur presentateur;
    private ComptesDAO comptesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_clients);

        lvListeClients = findViewById(R.id.lvListeClients);
        btnRetour = findViewById(R.id.btnRetourListeClients);

        btnRetour.setOnClickListener(this);

        presentateur = new ListeClientsPresentateur(ListeClientsActivity.this, new ComptesDAO());

        presentateur.chargerComptes(lvListeClients);

    }

    @Override
    public void onClick(View v) {

        if (v == btnRetour) {
            Intent intention = new Intent (this, MenuUtilisateur.class);
            startActivity(intention);
        }
    }
}