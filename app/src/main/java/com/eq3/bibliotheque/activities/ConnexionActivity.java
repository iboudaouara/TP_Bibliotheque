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
import java.util.List;

public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "activity_login";
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ConnexionPresentateur loginPresentateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginPresentateur = new ConnexionPresentateur(new UtilisateurModele(),this);
        loginPresentateur.loadUsers();

        loginButton.setOnClickListener(this);
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
            Intent intent = new Intent(ConnexionActivity.this, MenuAdmin.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Connexion client réussie", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ConnexionActivity.this, ListeLivresActivity.class);
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
        for (Utilisateur user : users) {
            Log.d(TAG, "Utilisateur chargé: " + user.getCompte());
        }
        Toast.makeText(this, "Nombre d'utilisateurs chargés : " + users.size(), Toast.LENGTH_SHORT).show();
    }
}