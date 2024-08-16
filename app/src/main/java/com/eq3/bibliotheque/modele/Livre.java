package com.eq3.bibliotheque.modele;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class contenant les informations necessaires pour un objet Livre
 *
 */
public class Livre {

    /*L'annotation @JsonProperty est utilisée pour indiquer comment les champs d'une classe Java
    doivent être mappés aux propriétés correspondantes dans le JSON lors de la sérialisation et
    de la désérialisation. Elle permet de spécifier des noms de propriétés JSON personnalisés ou
    de gérer des différences de noms entre les champs de la classe et les clés du JSON.*/

    @JsonProperty("titre")
    private String titre;

    @JsonProperty("auteur")
    private String auteur;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("maison_edition")
    private String maisonEdition;

    @JsonProperty("date_publication")
    private String datePublication;

    @JsonProperty("description")
    private String description;

    @JsonProperty("appreciation_moyenne")
    private double appreciationMoyenne;

    @JsonProperty("nombre_appreciations")
    private int nombreAppreciations;

    @JsonProperty("id")
    private String id;

    /**
     * Constructeur par defaut
     */
    public Livre(){

    }

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

    public Livre(String titre, String auteur, String isbn, String maisonEdition, String datePublication, String description) {
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.maisonEdition = maisonEdition;
        this.datePublication = datePublication;
        this.description = description;
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

