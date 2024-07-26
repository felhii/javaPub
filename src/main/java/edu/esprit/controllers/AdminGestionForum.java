package edu.esprit.controllers;

import edu.esprit.entities.Formation;
import edu.esprit.entities.Forum;
import edu.esprit.services.ServiceForum;
import edu.esprit.utils.DataSource;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AdminGestionForum implements Initializable {
    @FXML
    private TextField AncienFormationModifier;

    @FXML
    private TextArea DescriptionForumModifier;

    @FXML
    private VBox ForumAfficheVbox;

    @FXML
    private ComboBox<Formation> ForumFormationModifier;

    @FXML
    private ScrollPane ScrollForum;

    @FXML
    private TextField TitreForumModifier;

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
    private Pane modiferForumPane;
    Forum forum1=new Forum();
    private DataSource MyConnection;
    private int idForum;
    private final ServiceForum serviceForum =new ServiceForum();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshUI();
        chargerFormations();
        modiferForumPane.setVisible(false);
        modiferForumPane.setManaged(false);
    }
    protected void refreshUI() {
        try {
            List<Forum> forums = serviceForum.getAll();
            createUIElements(forums);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void createUIElements(List<Forum> forums) {
        ForumAfficheVbox.getChildren().clear(); // Clear existing elements

        int i = 0;
        for (Forum forum : forums) {
            String index=" forum Numero "+String.valueOf(i+1);
            String titreForum = "nom : "+forum.getTitre();
            String description = "Description : "+forum.getDescription();
            String date = "date de creation : "+forum.getDateCreation().toString();
            String nomFormation = "nom formation : "+forum.getFormation().getNomF();

            Text texteIndex=ajouterIndex(index);
            Text textTitreForum=ajouterIndex(titreForum);
            Text textDescription=ajouterRetourALaLigne(description,30);
            Text textDate=ajouterIndex(date);
            Text textNomFormation=ajouterRetourALaLigne(nomFormation,20);

            TextField textFieldTIndex=creerTextFieldIndex(texteIndex);
            textFieldTIndex.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                if (event.getClickCount() == 2) {
                    idForum = forum.getIdForum();
                }
            });

            TextField textFieldTitreForum =creerTextField(textTitreForum);
            TextField textFieldDate = creerTextField(textDate);
            TextArea textAreaDescription =creerTextArea(textDescription);
            TextArea textAreaNomFormation = creerTextArea(textNomFormation);
            textFieldTitreForum.setOnAction(event -> {
                modiferForumPane.setVisible(true);
                modiferForumPane.setManaged(true);

                try {
                    forum1 = serviceForum.getOneById(forum.getIdForum());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                forum1.setIdForum(forum.getIdForum());
                System.out.println(forum1.getIdForum());
                System.out.println(forum1.getFormation().getIdFormation());

                TitreForumModifier.setText(forum1.getTitre());
                DescriptionForumModifier.setText(forum1.getDescription());
                AncienFormationModifier.setText(forum1.getFormation().getNomF());
            });


            i++;



            VBox vbox = new VBox();
            vbox.getChildren().addAll(textFieldTIndex,textFieldTitreForum, textFieldDate, textAreaDescription, textAreaNomFormation);
            HBox hbox = new HBox();
            hbox.getChildren().add(vbox);
            ForumAfficheVbox.getChildren().add(hbox);
            ForumAfficheVbox.setSpacing(10);
        }

        ScrollForum.setContent(ForumAfficheVbox);
    }

    @FXML
    void AjouterForumStage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTEST.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            // Définir la largeur et la hauteur de la fenêtre
            //stage.setWidth(800); // Remplacez 800 par la largeur souhaitée
            //stage.setHeight(600); // Remplacez 600 par la hauteur souhaitée

            stage.show();
        } catch (IOException e) {

        }

    }

    @FXML
    void InterfaceForumAction(ActionEvent event) {

    }

    @FXML
    void ModifierForum(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de modification");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier ce forum ?");

        // Afficher la boîte de dialogue de confirmation et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // L'utilisateur a cliqué sur OK, procédez à la modification
                Formation formation = (ForumFormationModifier.getValue() == null) ? forum1.getFormation() : ForumFormationModifier.getValue();
                Forum forum2 = new Forum(forum1.getIdForum(), TitreForumModifier.getText(), DescriptionForumModifier.getText(), formation);

                try {
                    serviceForum.modifier(forum2);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Modification réussie");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("La modification du forum a été effectuée avec succès.");
                    successAlert.show();
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur de modification");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Une erreur s'est produite lors de la modification du forum.\n" + e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        });

    }

    @FXML
    void QuitterModifierForum(ActionEvent event) {
        modiferForumPane.setVisible(false);
        modiferForumPane.setManaged(false);

    }

    @FXML
    void SupprimerForum(ActionEvent event) {
        // Afficher une boîte de dialogue de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce forum ?");

        // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    serviceForum.supprimer(idForum);
                    // Afficher une alerte de succès après la suppression
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Suppression réussie");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("La suppression du forum a été effectuée avec succès.");
                    successAlert.show();

                    // Rafraîchir les données dans le contrôleur principal
                    Platform.runLater(this::refreshUI);
                } catch (SQLException e) {
                    // Afficher une alerte d'erreur en cas d'échec de la suppression
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur de suppression");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Une erreur s'est produite lors de la suppression du forum.\n");
                    errorAlert.show();
                }
            }
        });

    }

    @FXML
    void refreche(ActionEvent event) {
        Platform.runLater(this::refreshUI);

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

        ObservableList<Formation> observableFormations = FXCollections.observableArrayList(formations);
        ForumFormationModifier.setItems(observableFormations);
    }
    private Text ajouterRetourALaLigne(String str, int nombreDeMotsParLigne) {
        // Supprimer tous les retours à la ligne existants
        String strSansRetoursALaLigne = str.replace("\n", " ");

        StringBuilder sb = new StringBuilder(strSansRetoursALaLigne);
        int motCompteur = 0;

        for (int i = 0; i < sb.length(); i++) {
            if (Character.isWhitespace(sb.charAt(i))) {
                motCompteur++;
                if (motCompteur == nombreDeMotsParLigne) {
                    sb.insert(i + 1, "\n");
                    motCompteur = 0; // Réinitialiser le compteur de mots pour la prochaine ligne
                }
            }
        }

        // Créer un nouvel objet Text avec la chaîne de caractères
        Text text = new Text(sb.toString());

        // Définir la police et la couleur du texte
        text.setFont(new Font("Arial", 20));
        text.setFill(Color.BLACK);

        return text;
    }
    private Text ajouterIndex(String str) {
        Text text = new Text(str);
        return text;
    }


    private TextField creerTextField(Text text) {
        TextField textField = new TextField(text.getText());
        textField.setEditable(false);
        textField.setPrefColumnCount(250);
        textField.setStyle(" -fx-text-fill: black; -fx-font-size: 12px; -fx-font-family: 'Dubai'; -fx-border-color: #4B2F00; -fx-background-color: rgb(255,255,255);  -fx-border-width: 3;");
        return textField;
    }
    private TextField creerTextFieldIndex(Text text) {
        TextField textField = new TextField(text.getText());
        textField.setEditable(false);
        textField.setPrefColumnCount(250);
        textField.setStyle(" -fx-text-fill: black; -fx-font-size: 15px; -fx-font-family: 'Dubai'; -fx-border-color: #4B2F00; -fx-background-color: rgb(255,255,255);  -fx-border-width: 3;");
        return textField;
    }


    private TextArea creerTextArea(Text text) {
        TextArea textArea = new TextArea(text.getText());
        textArea.setEditable(false);
        textArea.setPrefWidth(250);
        textArea.setPrefHeight(50);
        textArea.setWrapText(true);
        textArea.setStyle("-fx-text-fill: black; -fx-font-size: 15px; -fx-font-family: 'Dubai'; -fx-border-color: #4B2F00; -fx-background-color: rgb(255,255,255); -fx-border-width: 3;");
        return textArea;
    }








}
