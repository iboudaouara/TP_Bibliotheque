package com.eq3.bibliotheque.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.eq3.bibliotheque.R;
import com.eq3.bibliotheque.modele.Utilisateur;
import com.eq3.bibliotheque.modele.UtilisateurModele;
import com.eq3.bibliotheque.presentateur.ConnexionPresentateur;
import java.util.List;

/**
 * Activity de connexion pour l'application de bibliothèque.
 * Cette activité gère l'interface utilisateur pour la connexion
 * des utilisateurs et la redirection vers les menus appropriés.
 */
public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ConnexionActivity";
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ConnexionPresentateur loginPresentateur;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise l'interface utilisateur et le présentateur de connexion.
     *
     * @param savedInstanceState Contient l'état précédemment sauvegardé de l'activité, si disponible.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginPresentateur = new ConnexionPresentateur(new UtilisateurModele(), this);
        loginPresentateur.loadUsers();

        loginButton.setOnClickListener(this);
    }

    /**
     * Gère les événements de clic pour les vues de l'activité.
     * Dans ce cas, il traite le clic sur le bouton de connexion.
     *
     * @param v Vue qui a été cliquée.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.loginButton) {

            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            loginPresentateur.login(email, password);
        }
    }

    /**
     * Affiche un message de succès de connexion et redirige l'utilisateur
     * vers l'interface administrateur ou client en fonction de ses droits.
     *
     * @param isAdmin Indique si l'utilisateur est un administrateur.
     * @param utilisateur Objet utilisateur authentifié.
     */
    public void showLoginSuccess(boolean isAdmin, Utilisateur utilisateur) {

        if (isAdmin) {

            Toast.makeText(this, "Connexion administrateur réussie", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ConnexionActivity.this, MenuAdmin.class);
            intent.putExtra("utilisateur", utilisateur);
            startActivity(intent);
        }

        else {

            Toast.makeText(this, "Connexion client réussie", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ConnexionActivity.this, ListeLivresActivity.class);
            intent.putExtra("utilisateur", utilisateur);
            startActivity(intent);
        }

        finish();
    }

    /**
     * Affiche un message d'erreur de connexion lorsque les identifiants sont invalides.
     */
    public void showLoginError() {

        Toast.makeText(this, "Identifiants invalides", Toast.LENGTH_SHORT).show();
    }

    /**
     * Affiche un message d'erreur de chargement en cas de problème lors du chargement des utilisateurs.
     *
     * @param message Le message d'erreur à afficher.
     */
    public void showLoadError(String message) {

        Toast.makeText(this, "Erreur de chargement : " + message, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Erreur de chargement : " + message);
    }

    /**
     * Met à jour la liste des utilisateurs chargés et affiche le nombre total d'utilisateurs.
     *
     * @param users La liste des utilisateurs chargés.
     */
    public void updateUserList(List<Utilisateur> users) {

        for (Utilisateur user : users) {

            Log.d(TAG, "Utilisateur chargé: " + user.getCompte());
        }

        Toast.makeText(this, "Nombre d'utilisateurs chargés : " + users.size(), Toast.LENGTH_SHORT).show();
    }

}
