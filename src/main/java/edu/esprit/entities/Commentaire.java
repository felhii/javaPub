package edu.esprit.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Commentaire {
    private int idCommentaire ;
    private LocalDateTime dateCreation;
    private String contenu;
    private User user;
    private Publication publication;
    private int rating;



    public Commentaire() {

    }

    public Commentaire( String contenu, User user, Publication publication, int rating) {
        this.dateCreation = LocalDateTime.now();
        this.contenu = contenu;
        this.user = user;
        this.publication = publication;
        this.rating = rating;
    }

    public Commentaire(int idCommentaire, String contenu,User user, Publication publication, int rating) {
        this.idCommentaire = idCommentaire;
        this.contenu = contenu;
        this.user = user;
        this.publication = publication;
        this.rating = rating;
    }

    public Commentaire(LocalDateTime dateCreation, String contenu,User user, Publication publication, int rating) {
        this.dateCreation = dateCreation;
        this.contenu = contenu;
        this.user = user;
        this.publication = publication;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commentaire that)) return false;
        return idCommentaire == that.idCommentaire;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommentaire);
    }

    public void setIdCommentaire(int idCommentaire) {
        this.idCommentaire = idCommentaire;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }



    public void setContenu(String contenu) {
        this.contenu = contenu;
    }





    public int getIdCommentaire() {
        return idCommentaire;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }



    public String getContenu() {
        return contenu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "dateCreation=" + dateCreation +
                ", contenu='" + contenu + '\'' +
                ", nomUser=" + user.getNom() +
                ", contenuPubliction=" + publication.getContenu() +
                ", rating=" + rating +
                '}';
    }
}
