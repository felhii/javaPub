package edu.esprit.entities;

import java.util.Objects;


public class Avis {
    private int idAvis;
    private int rate;
    private User user;
    private Formation formation;

    public Avis(){}



    public Avis(int rate, Formation formation, User user) {
        this.rate = rate;
        this.user = user;
        this.formation = formation;
    }

    public Avis(int idAvis, int rate, Formation formation, User user) {
        this.idAvis = idAvis;
        this.rate = rate;
        this.user = user;
        this.formation = formation;
    }

    public int getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avis avis)) return false;
        return idAvis == avis.idAvis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAvis);
    }

    @Override
    public String toString() {
        return "Avis{" +
                "rate=" + rate +
                ", nomUser='" + user.getNom() + '\'' +
                ", nomFormation='" + formation.getNomF() + '\'' +
                '}';
    }
}
