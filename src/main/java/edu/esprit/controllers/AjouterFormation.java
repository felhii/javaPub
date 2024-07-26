package edu.esprit.controllers;

import edu.esprit.entities.Formation;
//import edu.esprit.services.ServiceFormation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class AjouterFormation {
    @FXML
    private TextField nomF;

    @FXML
    private TextArea descripF;
    @FXML
    private DatePicker dateDF;
    @FXML
    private DatePicker dateFF;
    @FXML
    private TextField prixF;
    @FXML
    private TextField nbrCourF;

    @FXML
    void InterfaceForumAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterForum.fxml"));
            nomF.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.setTitle("Error");
            alert.show();
        }

    }







}
//    private final ServiceFormation sp = new ServiceFormation();


//    @FXML
//    void AjouterFormationAction(ActionEvent event) {
//        java.sql.Date dateDebut = java.sql.Date.valueOf(dateDF.getValue());
//        java.sql.Date dateFin = java.sql.Date.valueOf(dateFF.getValue());
//        try {
////            sp.ajouter(new Formation(nomF.getText(),
////                    descripF.getText(),
////                    dateDebut,
////                    dateFin,
////                    Double.parseDouble(prixF.getText()),
////                    Integer.parseInt(nbrCourF.getText())));
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Success");
//            alert.setContentText("Formation ajoutée avec succès !");
//            alert.show();
////        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("SQL Exception");
//            alert.setContentText(e.getMessage());
//            alert.showAndWait();
//        }



//   public void navigatetoAfficherPersonneAction(ActionEvent actionEvent) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/AfficherFormation.fxml"));
//            nomF.getScene().setRoot(root);
//        } catch (IOException e) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText("Sorry");
//            alert.setTitle("Error");
//            alert.show();
//        }
//
//    }
