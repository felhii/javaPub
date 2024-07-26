package edu.esprit.services;

import edu.esprit.entities.Forum;
import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServicePublication implements IService<Publication> {
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Publication publication) throws SQLException{

        String req = "INSERT INTO `publication`(`dateCreation`, `contenuP`,`image`, `idForum`,`idUser`) VALUES (?, ?, ?, ?,?)";

            PreparedStatement ps = cnx.prepareStatement(req);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String formattedDate = publication.getDateCreation().format(formatter);
            ps.setObject(1, formattedDate);// Utilisez setObject pour LocalDate
            ps.setString(2, publication.getContenu());
            ps.setString(3, publication.getImage());
            ps.setInt(4, publication.getForum().getIdForum());
            ps.setInt(5, publication.getUser().getIdUser());
            ps.executeUpdate();
            System.out.println("publiction ajouté !");

    }

    @Override
    public void modifier(Publication publication) throws SQLException {
        if (publication.getContenu() == null || publication.getContenu().isEmpty()) {
            System.out.println("Le contenu de la publication est obligatoire.");
        }else {
        String req = "UPDATE publication SET contenuP = ?, image = ? WHERE idP = ?";
        PreparedStatement  prepardstatement = cnx.prepareStatement(req);
        prepardstatement.setString(1, publication.getContenu());
        prepardstatement.setString(2, publication.getImage());
        prepardstatement.setInt(3, publication.getIdP());
//        prepardstatement.setInt(3, publication.getNbLike());
//        prepardstatement.setInt(4, publication.getUser().getIdUser());
        prepardstatement.executeUpdate();
        System.out.println("publiction modifié");}

    }

    public void modifierNbLike(Publication publication) throws SQLException {
        Pattern pattern = Pattern.compile("^-\\d+$");
        Matcher matcher = pattern.matcher(String.valueOf(publication.getNbLike()));

        if (matcher.matches()) {
            // Le nombre de likes est négatif
            System.out.println("Nombre de likes négatif !");
        } else {
            String req = "UPDATE publication SET nbLike = ? WHERE idP = ?";
            PreparedStatement prepardstatement = cnx.prepareStatement(req);
            prepardstatement.setInt(1, publication.getNbLike());
            prepardstatement.setInt(2, publication.getIdP());
            prepardstatement.executeUpdate();
            System.out.println("Nombre de likes de la publication modifié !");
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM publication WHERE idP = ?";


            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("publication supprimé !");


    }

    @Override
    public Publication getOneById(int id) {
        Publication publication = null;
        String req = "SELECT a.*, u.nom,f.titre FROM publication a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN forum f " +
                "ON a.idForum = f.idForum WHERE a.idP = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            User user=new User();
            Forum forum=new Forum();
            if (res.next()) {
                String contenu = res.getString("contenuP");
                String image = res.getString("image");
                int nbLike = res.getInt("nbLike");
                user.setNom(res.getString("nom"));
                forum.setTitre(res.getString("titre"));
                java.sql.Timestamp timestamp = res.getTimestamp("dateCreation");
                LocalDateTime dateCreation = timestamp.toLocalDateTime();

                publication = new Publication(dateCreation,contenu,image,nbLike,forum,user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return publication;
    }

    @Override
    public List<Publication> getAll() throws SQLException {
        String req = "SELECT a.*, u.nom,f.titre FROM publication a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN forum f ON a.idForum = f.idForum";
        Statement statement = cnx.createStatement();

        ResultSet cs = statement.executeQuery(req);
        List<Publication> list = new ArrayList<>();
        while (cs.next()) {
            Publication publication = new Publication();
            publication.setContenu(cs.getString("contenuP"));
            publication.setImage(cs.getString("image"));
            java.sql.Timestamp timestamp = cs.getTimestamp("dateCreation");
            LocalDateTime dateCreation = timestamp.toLocalDateTime();
            publication.setDateCreation(dateCreation);
            publication.setNbLike(cs.getInt("nbLike"));
            User user=new User();
            user.setNom(cs.getString("nom"));
            publication.setUser(user);
            Forum forum=new Forum();
            forum.setTitre(cs.getString("titre"));
            forum.setIdForum(cs.getInt("idForum"));
            publication.setForum(forum);




            list.add(publication);
        }
        return list;
    }


    public  List<Publication> getAll(int idForum)  {
        String req = "SELECT a.*, u.nom, f.titre FROM publication a INNER JOIN user u ON a.idUser = u.idUser INNER JOIN forum f ON a.idForum = f.idForum WHERE f.idForum = ?";
        try (PreparedStatement statement = cnx.prepareStatement(req)) {
            statement.setInt(1, idForum);
            ResultSet cs = statement.executeQuery();
            List<Publication> list = new ArrayList<>();
            while (cs.next()) {
                Publication publication = new Publication();
                publication.setIdP(cs.getInt("idP"));
                publication.setContenu(cs.getString("contenuP"));
                publication.setImage(cs.getString("image"));
                java.sql.Timestamp timestamp = cs.getTimestamp("dateCreation");
                LocalDateTime dateCreation = timestamp.toLocalDateTime();
                publication.setDateCreation(dateCreation);
                publication.setNbLike(cs.getInt("nbLike"));
                User user = new User();
                user.setNom(cs.getString("nom"));
                publication.setUser(user);
                Forum forum = new Forum();
                forum.setTitre(cs.getString("titre"));
                publication.setForum(forum);
                list.add(publication);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
