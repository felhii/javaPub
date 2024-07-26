package edu.esprit.controllers;

import edu.esprit.entities.Formation;
import edu.esprit.entities.Forum;
import edu.esprit.services.ServiceForum;
import edu.esprit.utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AjouterForum {

    @FXML
    private Button AnnulerForum;

    @FXML
    private ComboBox<Formation> ComboboxForum;

    @FXML
    private TextArea DescriptionForum;

    @FXML
    private TextField TitreForum;

    @FXML
    private Button ajouterFormation;

    @FXML
    private Button btnCours;

    @FXML
    private Button btnFormation;

    @FXML
    private Button btnForum;

    @FXML
    private Button btnOutils;

    @FXML
    private Button btnReclamation;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnUser;

    @FXML
    private Pane pnlOverview;
    private final ServiceForum serviceForum=new ServiceForum();
    private DataSource MyConnection;



    public void InterfaceForumAction(ActionEvent actionEvent) {
    }


    public void AjouterForum(ActionEvent actionEvent) {
        try {
            Formation formation = (Formation) ComboboxForum.getValue();
            int idFormation = formation.getIdFormation();

            serviceForum.ajouter(new Forum(TitreForum.getText(), DescriptionForum.getText(), formation));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("GG");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void AnnulerForum(ActionEvent actionEvent) {
        TitreForum.clear();
        DescriptionForum.clear();
    }

    @FXML
    void initialize() {
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
        ObservableList<Formation> observableFormations = FXCollections.observableArrayList(formations);
        ComboboxForum.setItems(observableFormations);
    }






}
