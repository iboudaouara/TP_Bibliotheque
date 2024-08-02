package com.eq3.bibliotheque.modele;

public class Utilisateur {

    private String mail;
    private String nom;
    private String prenom;
    private String id;

    public Utilisateur(){

    }

    public Utilisateur(String mail, String nom, String prneom, String id){

        this.mail = mail;
        this.nom = nom;
        this.prenom = prneom;
        this.id = id;

    }


    public String getCompte() {
        return mail;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getId() {
        return id;
    }

    public void setCompte(String compte) {
        this.mail = mail;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setId(String id) {
        this.id = id;
    }
}
