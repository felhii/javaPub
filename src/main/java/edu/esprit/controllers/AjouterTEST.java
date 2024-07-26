package edu.esprit.controllers;

import edu.esprit.entities.Formation;
import edu.esprit.entities.Forum;
import edu.esprit.services.ServiceForum;
import edu.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterTEST implements Initializable {

    @FXML
    private ComboBox<Formation> AfficherFormationNom;

    @FXML
    private TextField TItreForum;

    @FXML
    private TextArea descriptionForum;
    private final ServiceForum serviceForum=new ServiceForum();
    private DataSource MyConnection;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        chargerFormations();

    }



    @FXML
    void AjouterForum(ActionEvent event) {
        try {
            Formation formation = (Formation) AfficherFormationNom.getValue();
           // int idFormation = formation.getIdFormation();

            String titre = TItreForum.getText();
            String description = descriptionForum.getText();
            if (titre.isEmpty() || description.isEmpty()) {
                showAlert("Champ(s) vide(s)", "Veuillez remplir tous les champs requis.");
                return; // Arrêtez l'ajout si l'un des champs est vide
            }

            // Vérification Regex pour le titre (exemple de regex, ajustez selon vos besoins)
            if (!titre.matches("^[a-zA-Z0-9\\s]+$")) {
                showAlert("Titre invalide", "Utilisez des caractères alphanumériques et des espaces uniquement.");
                return; // Arrêtez l'ajout si le titre n'est pas valide
            }

            // Vérification Regex pour la description (exemple de regex, ajustez selon vos besoins)
            if (!description.matches("^[a-zA-Z0-9\\s]+$")) {
                showAlert("Description invalide", "Utilisez des caractères alphanumériques et des espaces uniquement.");
                return; // Arrêtez l'ajout si la description n'est pas valide
            }

            serviceForum.ajouter(new Forum(titre, description, formation));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("GG");
            alert.show();
        } catch (SQLException e) {
//            showAlert("SQL Exception", e.getMessage());
        }
        chargerFormations();

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void AnnulerSaisie(ActionEvent event) {
        TItreForum.clear();
        descriptionForum.clear();

    }

    @FXML
    void retour(ActionEvent event) {
        Node source = (Node) event.getSource();
        Scene currentScene = source.getScene();
        Stage stage = (Stage) currentScene.getWindow();
        stage.close();

    }

    private void chargerFormations() {
        List<Formation> formations = new ArrayList<>();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery("SELECT formation.idFormation, formation.nom FROM formation LEFT JOIN forum ON formation.idFormation = forum.idFormation WHERE forum.idFormation IS NULL");
            while (rs.next()) {
                int idFormation = rs.getInt("idFormation");
                String nomFormation = rs.getString("nom");
                formations.add(new Formation(idFormation, nomFormation));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        AfficherFormationNom.getItems().addAll(formations);
        // Utilisez un StringConverter pour afficher uniquement les noms et prénoms dans le ChoiceBox
        AfficherFormationNom.setConverter(new StringConverter<Formation>() {

            @Override
            public String toString(Formation formation) {
                // Affichez le nom et prénom de l'utilisateur dans le ChoiceBox
                return formation != null ? formation.getNomF() : " " ;
            }

            @Override
            public Formation fromString(String string) {
                // Vous n'avez probablement pas besoin d'implémenter cette méthode pour un ChoiceBox
                return null;
            }
        });

//        ObservableList<Formation> observableFormations = FXCollections.observableArrayList(formations);
//        AfficherFormationNom.setItems(observableFormations);
    }
}
