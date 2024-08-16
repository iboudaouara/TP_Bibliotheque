package com.eq3.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.modele.Utilisateur;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class activity_login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private List<Utilisateur> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loadUsers();

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                login(email, password);
            }

        });

    }

    private void loadUsers() {
        try {
            InputStream is = getAssets().open("bibliotheque.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Utilisateur>>(){}.getType();
            users = gson.fromJson(json, listType);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void login(String email, String password) {
        if (email.equals("Admin@example.com") && password.equals("tch057")) {
            // Connexion administrateur réussie
            Toast.makeText(this, "Connexion administrateur réussie", Toast.LENGTH_SHORT).show();
            // Ajoutez ici le code pour rediriger vers l'interface administrateur
        } else {
            boolean isValidUser = false;
            for (Utilisateur user : users) {
                if (user.getMail().equals(email) && user.getMotDePasse().equals(password)) {
                    isValidUser = true;
                    break;
                }
            }

            if (isValidUser) {
                // Connexion client réussie
                Toast.makeText(this, "Connexion client réussie", Toast.LENGTH_SHORT).show();
                // Ajoutez ici le code pour rediriger vers l'interface client
            } else {
                Toast.makeText(this, "Identifiants invalides", Toast.LENGTH_SHORT).show();
            }
        }
    }
}