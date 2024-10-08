package com.eq3.bibliotheque.modele;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Class contenant les informations necessaires pour un Utilisateur
 *
 */
public class Utilisateur implements Serializable {
    private String compte;
    private String nom;
    private String prenom;
    private String id;
    public static final String MDP_UTILISATEUR = "mdp123";

    /**
     * Constructeur par défaut.
     */
    public Utilisateur() {

    }

    /**
     * Constructeur par attributs
     *
     * @param compte Adresse e-mail de l'utilisateur.
     * @param nom Nom de l'utilisateur.
     * @param prenom Prénom de l'utilisateur.
     * @param id Identifiant unique de l'utilisateur.
     */
    public Utilisateur(String compte, String nom, String prenom, String id) {

        this.compte = compte;
        this.nom = nom;
        this.prenom = prenom;
        this.id = id;
    }

    /**
     * Obtient l'adresse e-mail de l'utilisateur.
     *
     * @return Adresse e-mail de l'utilisateur.
     */
    public String getCompte() {
        return compte;
    }

    /**
     * Définit l'adresse e-mail de l'utilisateur.
     *
     * @param compte Adresse e-mail de l'utilisateur.
     */
    public void setCompte(String compte) {
        this.compte = compte;
    }

    /**
     * Obtient le nom de l'utilisateur.
     *
     * @return Nom de l'utilisateur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'utilisateur.
     *
     * @param nom Nom de l'utilisateur.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient le prénom de l'utilisateur.
     *
     * @return Prénom de l'utilisateur.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom de l'utilisateur.
     *
     * @param prenom Prénom de l'utilisateur.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Obtient l'identifiant unique de l'utilisateur.
     *
     * @return Identifiant unique de l'utilisateur.
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de l'utilisateur.
     *
     * @param id Identifiant unique de l'utilisateur.
     */
    public void setId(String id) {

        this.id = id;
    }

    public String getMotDePasse() {

        return MDP_UTILISATEUR;
    }


}
