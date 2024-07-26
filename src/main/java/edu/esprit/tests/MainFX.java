package edu.esprit.tests;

import edu.esprit.utils.DataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Gestion User");
        stage.show();

        if (DataSource.getInstance().getCnx() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" base de données !");
            alert.setContentText("GG tu est connecter a la base de donner !");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("base de données !");
            alert.setContentText("Échec de la connexion à la base de données");
            alert.showAndWait();
        }

    }
}
