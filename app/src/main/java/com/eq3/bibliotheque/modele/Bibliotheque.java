package com.eq3.bibliotheque.modele;

import java.util.List;

public class Bibliotheque {
    private List<Livre> livres;
    private List<Utilisateur> comptes;

    public List<Livre> getLivres() {

        return livres;
    }

    public void setLivres(List<Livre> livres) {

        this.livres = livres;
    }

    public List<Utilisateur> getComptes() {

        return comptes;
    }

    public void setComptes(List<Utilisateur> comptes) {

        this.comptes = comptes;
    }

}