package com.eq3.bibliotheque.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.eq3.bibliotheque.R;

public class AjoutLivreActivity extends AppCompatActivity {
    private Button btnRetour;
    private Button btnSauvegarder;

    /*private TextView txtTitre, txtAuteur, txtISBN, txtMaisonEdition,
            txtDatePublication, txtDescription;

     */
    private EditText edtTitre, edtAuteur, edtISBN, edtMaisonEdition,
            edtDatePublication, edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_livre);

        /*txtTitre = findViewById(R.id.txtTitre);
        txtAuteur = findViewById(R.id.txtAuteur);
        txtISBN = findViewById(R.id.txtISBN);
        txtMaisonEdition = findViewById(R.id.txtMaisonEdition);
        txtDatePublication = findViewById(R.id.txtDatePublication);
        txtDescription = findViewById(R.id.txtDescription);

         */

        edtTitre = findViewById(R.id.edtTitre);
        edtAuteur = findViewById(R.id.edtAuteur);
        edtISBN = findViewById(R.id.edtISBN);
        edtMaisonEdition = findViewById(R.id.edtMaisonEdition);
        edtDatePublication = findViewById(R.id.edtDatePublication);
        edtDescription = findViewById(R.id.edtDescription);

        btnRetour = findViewById(R.id.btnRetour);
        btnSauvegarder = findViewById(R.id.btnEnregistrer);
    }
}