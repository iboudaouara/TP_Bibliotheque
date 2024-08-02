package com.eq3.bibliotheque.modele;

import java.time.LocalDate;

public class Livre {

    private String titre;
    private String auteur;
    private String isbn;
    private String maisonEdition;
    private LocalDate datePublication;
    private String description;
    private double appreciationMoyenne;
    private int nbrAppreciations;
    private String id;


    public Livre(){

    }

    //constructeur
    public Livre(String titre, String auteur, String isbn, String maisonEdition, LocalDate datePublication,
                 String description, double appreciationMoyenne, int nbrAppreciations, String id){

        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.maisonEdition = maisonEdition;
        this.datePublication = datePublication;
        this.description = description;
        this.appreciationMoyenne = appreciationMoyenne;
        this.nbrAppreciations = nbrAppreciations;
        this.id = id;

    }


    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getMaisonEdition() {
        return maisonEdition;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public String getDescription() {
        return description;
    }

    public double getAppreciationMoyenne() {
        return appreciationMoyenne;
    }

    public int getNbrAppreciations() {
        return nbrAppreciations;
    }

    public String getId() {
        return id;
    }


    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setMaisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAppreciationMoyenne(double appreciationMoyenne) {
        this.appreciationMoyenne = appreciationMoyenne;
    }

    public void setNbrAppreciations(int nbrAppreciations) {
        this.nbrAppreciations = nbrAppreciations;
    }

    public void setId(String id) {
        this.id = id;
    }
}
