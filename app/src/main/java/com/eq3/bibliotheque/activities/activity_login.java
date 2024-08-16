package com.eq3.bibliotheque.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.modele.Utilisateur;
import com.eq3.bibliotheque.modele.UtilisateurModele;
import com.eq3.bibliotheque.presentateur.ConnexionPresentateur;
import com.eq3.bibliotheque.presentateur.LoginPresentateur;

import java.util.List;

public class activity_login extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ConnexionPresentateur loginPresentateur;
    private static final String TAG = "activity_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(this);

        // Initialisation du présentateur
        loginPresentateur = new ConnexionPresentateur(new UtilisateurModele(), this);

        // Chargement des utilisateurs
        loginPresentateur.loadUsers(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginButton) {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            loginPresentateur.login(email, password);
        }
    }

    public void showLoginSuccess(boolean isAdmin) {
        if (isAdmin) {
            Toast.makeText(this, "Connexion administrateur réussie", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity_login.this, MenuAdmin.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Connexion client réussie", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity_login.this, MenuUtilisateur.class);
            startActivity(intent);
        }
        finish();
    }

    public void showLoginError() {
        Toast.makeText(this, "Identifiants invalides", Toast.LENGTH_SHORT).show();
    }

    public void showLoadError(String message) {
        Toast.makeText(this, "Erreur de chargement : " + message, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Erreur de chargement : " + message);
    }

    public void updateUserList(List<Utilisateur> users) {
        // Cette méthode peut être utilisée pour mettre à jour l'interface utilisateur si nécessaire
        Toast.makeText(this, "Nombre d'utilisateurs chargés : " + users.size(), Toast.LENGTH_SHORT).show();
    }
}