package com.eq3.bibliotheque.modele;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Class contenant les informations necessaires pour un objet Livre
 *
 */
public class Livre implements Serializable {
    private String titre;
    private String auteur;
    private String isbn;
    private String maisonEdition;
    private String datePublication;
    private String description;
    private double appreciationMoyenne;
    private int nombreAppreciations;
    private String id;

    public Livre(String titre, String auteur, String isbn, String maisonEdition, String datePublication,
                String description, double appreciationMoyenne, int nombreAppreciations, String id) {
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.maisonEdition = maisonEdition;
        this.datePublication = datePublication;
        this.description = description;
        this.appreciationMoyenne = appreciationMoyenne;
        this.nombreAppreciations = nombreAppreciations;
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

    public String getDatePublication() {

        return datePublication;
    }

    public String getDescription() {

        return description;
    }

    public double getAppreciationMoyenne() {

        return appreciationMoyenne;
    }

    public int getNombreAppreciations() {

        return nombreAppreciations;
    }

    public String getId() {

        return id;
    }

    public void setAppreciationMoyenne(double appreciationMoyenne) {
        this.appreciationMoyenne = appreciationMoyenne;
    }

    public void setNombreAppreciations(int nombreAppreciations) {
        this.nombreAppreciations = nombreAppreciations;
    }

}
