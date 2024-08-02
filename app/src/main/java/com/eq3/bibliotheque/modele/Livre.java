package com.eq3.bibliotheque.modele;

import java.time.LocalDate;

/**
 * Class contenant les informations necessaires pour un objet Livre
 *
 */
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

    /**
     * Constructeur par défaut.
     */
    public Livre() {
    }

    /**
     * Constructeur avec tous les paramètres nécessaires pour initialiser un livre.
     *
     * @param titre Titre du livre.
     * @param auteur Auteur du livre.
     * @param isbn Numéro ISBN du livre.
     * @param maisonEdition Maison d'édition du livre.
     * @param datePublication Date de publication du livre.
     * @param description Description du livre.
     * @param appreciationMoyenne Moyenne des appréciations du livre.
     * @param nbrAppreciations Nombre d'appréciations du livre.
     * @param id Identifiant unique du livre.
     */
    public Livre(String titre, String auteur, String isbn, String maisonEdition, LocalDate datePublication,
                 String description, double appreciationMoyenne, int nbrAppreciations, String id) {

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

    // Getters et Setters

    /**
     * Obtient le titre du livre.
     *
     * @return Titre du livre.
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Définit le titre du livre.
     *
     * @param titre Titre du livre.
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * Obtient l'auteur du livre.
     *
     * @return Auteur du livre.
     */
    public String getAuteur() {
        return auteur;
    }

    /**
     * Définit l'auteur du livre.
     *
     * @param auteur Auteur du livre.
     */
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    /**
     * Obtient le numéro ISBN du livre.
     *
     * @return Numéro ISBN du livre.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Définit le numéro ISBN du livre.
     *
     * @param isbn Numéro ISBN du livre.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtient la maison d'édition du livre.
     *
     * @return Maison d'édition du livre.
     */
    public String getMaisonEdition() {
        return maisonEdition;
    }

    /**
     * Définit la maison d'édition du livre.
     *
     * @param maisonEdition Maison d'édition du livre.
     */
    public void setMaisonEdition(String maisonEdition) {
        this.maisonEdition = maisonEdition;
    }

    /**
     * Obtient la date de publication du livre.
     *
     * @return Date de publication du livre.
     */
    public LocalDate getDatePublication() {
        return datePublication;
    }

    /**
     * Définit la date de publication du livre.
     *
     * @param datePublication Date de publication du livre.
     */
    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    /**
     * Obtient la description du livre.
     *
     * @return Description du livre.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Définit la description du livre.
     *
     * @param description Description du livre.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtient la moyenne des appréciations du livre.
     *
     * @return Moyenne des appréciations du livre.
     */
    public double getAppreciationMoyenne() {
        return appreciationMoyenne;
    }

    /**
     * Définit la moyenne des appréciations du livre.
     *
     * @param appreciationMoyenne Moyenne des appréciations du livre.
     */
    public void setAppreciationMoyenne(double appreciationMoyenne) {
        this.appreciationMoyenne = appreciationMoyenne;
    }

    /**
     * Obtient le nombre d'appréciations du livre.
     *
     * @return Nombre d'appréciations du livre.
     */
    public int getNbrAppreciations() {
        return nbrAppreciations;
    }

    /**
     * Définit le nombre d'appréciations du livre.
     *
     * @param nbrAppreciations Nombre d'appréciations du livre.
     */
    public void setNbrAppreciations(int nbrAppreciations) {
        this.nbrAppreciations = nbrAppreciations;
    }

    /**
     * Obtient l'identifiant unique du livre.
     *
     * @return Identifiant unique du livre.
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du livre.
     *
     * @param id Identifiant unique du livre.
     */
    public void setId(String id) {
        this.id = id;
    }
}
