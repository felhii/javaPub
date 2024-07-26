package edu.esprit.entities;

public class Formation {
    private int idFormation;
    private String nomF;

    public int getIdFormation() {
        return idFormation;
    }

    public Formation(int idFormation, String nomF) {
        this.idFormation = idFormation;
        this.nomF = nomF;
    }
    public Formation(){}

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getNomF() {
        return nomF;
    }


    public void setNomF(String nomF) {
        this.nomF = nomF;
    }
}
