package com.eq3.bibliotheque.modele;

import java.util.List;

public class Bibliotheque {
    private List<Livre> livres;

    public List<Livre> getLivres() {
        return livres;
    }

    public void setLivres(List<Livre> livres) {
        this.livres = livres;
    }
}