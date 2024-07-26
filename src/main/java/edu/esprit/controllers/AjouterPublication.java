package edu.esprit.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.entities.Forum;
import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.services.ServicePublication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AjouterPublication {

//    @FXML
//    private TextArea fxContenuFelhi;
//
//    @FXML
//    private TextField fxImageFelhi;
//    @FXML
//    private TextField idUser;
//
//    @FXML
//    private TextField idforum;
//    private final ServicePublication servicePublication=new ServicePublication();
//
//
//
//
//    @FXML
//    void ImageImportFelhi(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Sélectionner une image");
//        File selectedFile = fileChooser.showOpenDialog(null);
//        if (selectedFile != null) {
//            String imagePath = selectedFile.getAbsolutePath();
//            fxImageFelhi.setText(imagePath);
//        }
//    }
//
//    @FXML
//    void affecterPublication(ActionEvent event) {
//        try {
//            Forum forum=new Forum();
//            int idForum = Integer.parseInt(idforum.getText());
//            forum.setIdForum(idForum);
//            User user=new User();
//            int iduser = Integer.parseInt(idUser.getText());
//            user.setIdUser(iduser);
//            servicePublication.ajouter(new Publication(fxContenuFelhi.getText(),fxImageFelhi.getText(),forum,user));
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Success");
//            alert.setContentText("GG");
//            alert.show();
//        } catch (SQLException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("SQL Exception");
//            alert.setContentText(e.getMessage());
//            alert.showAndWait();
//        }
//
//    }
//
//    @FXML
//    void annulerSaisiePublictionFelhi(ActionEvent event) {
//        fxContenuFelhi.clear();
//        fxImageFelhi.clear();
//        idUser.clear();
//        idforum.clear();
//
//    }
//    @FXML
//    void navig(ActionEvent event)  {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/AfficherForum.fxml"));
//            fxImageFelhi.getScene().setRoot(root);
//        } catch (IOException e) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setContentText(e.getMessage());
//            alert.setTitle("Error");
//            alert.show();
//        }
//    }


}
