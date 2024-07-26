package edu.esprit.entities;


import java.time.LocalDateTime;
import java.util.Objects;

public class Forum {
    private int idForum;
    private String titre;
    private LocalDateTime dateCreation;
    private String description;
    private Formation formation ;

    public Forum(String titre) {
        this.titre = titre;
    }

    public Forum(String titre, String description , Formation formation) {
        this.titre = titre;
        this.dateCreation = LocalDateTime.now();
        this.description = description ;
        this.formation = formation;
    }

    public Forum() {
    }

    public Forum(String titre, LocalDateTime dateCreation, String description, Formation formation) {
        this.titre = titre;
        this.dateCreation = dateCreation;
        this.description = description;
        this.formation = formation;
    }

    public Forum(int idForum, String titre, String description, Formation formation) {
        this.idForum = idForum;
        this.titre = titre;
        this.description = description;
        this.formation = formation;
    }

    public int getIdForum() {
        return idForum;
    }

    public void setIdForum(int idForum) {
        this.idForum = idForum;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }


    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forum forum)) return false;
        return idForum == forum.idForum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idForum);
    }

    @Override
    public String toString() {
        return "Forum{" +
                "titre='" + titre + '\'' +
                ", dateCreation=" + dateCreation +
                ", description='" + description + '\'' +
                ", nomFormation=" + formation.getNomF() +
                '}';
    }
}
