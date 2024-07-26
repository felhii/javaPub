package edu.esprit.controllers;

import edu.esprit.entities.Formation;
import edu.esprit.entities.Forum;
import edu.esprit.entities.Publication;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceForum;
import edu.esprit.services.ServicePublication;
import edu.esprit.traduction.TranslatorText;
import edu.esprit.utils.DataSource;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.StringConverter;
import okhttp3.Response;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class test implements Initializable {

    @FXML
    private VBox mohamed;
    @FXML
    private ScrollPane ScrolFelhi;
    private final ServiceForum serviceForum =new ServiceForum();
    private final ServicePublication servicePublication =new ServicePublication();
    private String pathImage;
    private String selectedImagePathModifier=null;
    private  DataSource MyConnection;
    private int idForum;
    private int  idForumP;
    @FXML
    private TextField AncienFormationModifier;

    @FXML
    private TextArea DescriptionForumModifier;

    @FXML
    private ComboBox<Formation> ForumFormationModifier;
    @FXML
    private ComboBox<Forum> forumNom;

    @FXML
    private TextField TitreForumModifier;
    @FXML
    private Pane modiferForumPane;
    @FXML
    private TextArea DescriptionForumP;
    @FXML
    private TextArea nomFormationP;
    @FXML
    private TextArea contenuPublication;
    @FXML
    private Pane imagePubliction;
    @FXML
    private Pane PaneAjouterPublication;

    @FXML
    private TextField idUser;


    @FXML
    private TextArea TitreForumP;
    Forum forum1=new Forum();

    @FXML
    private ScrollPane ScrollPub;
    @FXML
    private Pane PUbAll;
    @FXML
    private Pane imagepublictionModifier;
    @FXML
    private TextArea ContenuPublictionModifier;
    private Publication publicationamodifier=new Publication();
    private User userP=new User();
    @FXML
    private Pane modifierPublictionFinale;
    @FXML
    private Text forumActif;
    @FXML
    private Text publicationLikk;
    private Timeline timeline;

    @FXML
    private TextField rechercheTextField;
    private String imageMeme;
    @FXML
    private Text nbPUB;







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshUI();
        chargerFormations();
        modiferForumPane.setVisible(false);
        modiferForumPane.setManaged(false);
        PUbAll.setVisible(false);
        PUbAll.setManaged(false);
        PaneAjouterPublication.setVisible(false);
        PaneAjouterPublication.setManaged(false);
        modifierPublictionFinale.setVisible(false);
        modifierPublictionFinale.setManaged(false);
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), this::updateIdForum));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        rechercheTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                List<Forum> forums=rechercherForums(newValue);
                createUIElements(forums);
            }
        });
        ScrolFelhi.setVisible(true);
        chargerForumNom();

    }
    private void refreshUI() {
        try {
            List<Forum> forums = serviceForum.getAll();


            createUIElements(forums);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Forum> rechercherForums(String titreRecherche) {
        List<Forum> resultats = new ArrayList<>();
        try {
            List<Forum> forums = serviceForum.getAll();
            for (Forum forum : forums) {
                if (forum.getFormation().getNomF().contains(titreRecherche)) {
                    resultats.add(forum);
                  //  System.out.println(resultats);
                }
            }
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Forum> rechercherForumsDate(LocalDateTime dateRecherche) {
        List<Forum> resultats = new ArrayList<>();
        try {
            List<Forum> forums = serviceForum.getAll();
            for (Forum forum : forums) {
                // Comparaison des dates
                if (forum.getDateCreation().isEqual(dateRecherche)) {
                    resultats.add(forum);
                   // System.out.println(resultats);
                }
            }
            return resultats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshUIPublicationNbLike(int x, TextArea textAreaNblike, Publication publication) {
        textAreaNblike.setText("likes : " + publication.getNbLike());
    }
    private void refreshUIPublication(int x) {

            List<Publication> publications = servicePublication.getAll(x);
            createUIElementsPubliction(publications);

    }

    private void createUIElementsPubliction(List<Publication> publications) {
        VBox PublictionAffichage=new VBox();
        PublictionAffichage.getChildren().clear();
        for (Publication publication : publications) {
            String contenu = "Contenu : "+publication.getContenu();
            String nbLike = "likes : "+publication.getNbLike();
            String date = "Date de creation : "+publication.getDateCreation().toString();
            String Image= publication.getImage();
            String nomUser="Nom User : "+publication.getUser().getNom();
            Pane tetePub = new Pane();
            Text textNomUser=createTextNomUser(nomUser);
            TextArea textAreaNomUser =createTextAreaPublicationNomUser(textNomUser,20,280,50,10,10) ;
            textAreaNomUser.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    // Afficher une boîte de dialogue de confirmation
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirmation de suppression");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce forum ?");

                    // Afficher la boîte de dialogue et attendre la réponse de l'utilisateur
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                servicePublication.supprimer(publication.getIdP());
                                // Afficher une alerte de succès après la suppression
                                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                                successAlert.setTitle("Suppression réussie");
                                successAlert.setHeaderText(null);
                                successAlert.setContentText("La suppression du publiction a été effectuée avec succès.");
                                successAlert.show();

                                // Rafraîchir les données dans le contrôleur principal
                                Platform.runLater(this::refreshUI);
                            } catch (SQLException e) {
                                // Afficher une alerte d'erreur en cas d'échec de la suppression
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setTitle("Erreur de suppression");
                                errorAlert.setHeaderText(null);
                                errorAlert.setContentText("Une erreur s'est produite lors de la suppression du publiction.\n");
                                errorAlert.show();
                            }
                        }
                    });


                }
                refreshUIPublication( forum1.getIdForum());
            });
            Text textDateCreation=createTextDateCreation(date);
            TextArea textAreaDateCreation =createTextAreaPublicationDateCreation(textDateCreation,26,210,50,300,10);
            tetePub.getChildren().addAll(textAreaNomUser, textAreaDateCreation);
            ImageView imageView = creerImageView(Image,200,170,10,10);
            Pane image=new Pane();
            image.setPrefWidth(200);
            image.setLayoutX(150);
            image.setLayoutY(60);
            image.getChildren().add(imageView);

            Pane finPub = new Pane();
            finPub.setLayoutX(0);
            finPub.setLayoutY(220);
            Text textContenu=createTextContenu(contenu);
            TextArea textAreaContenu=createTextAreaPublicationContenu(textContenu,60,406,70,10,10);
            textAreaContenu.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    servicePublication.getOneById(publication.getIdP());
                    ContenuPublictionModifier.setText(publication.getContenu());
                    publicationamodifier.setIdP(publication.getIdP());
                    publicationamodifier.setNbLike(publication.getNbLike());
                    publicationamodifier.setImage(publication.getImage());
                    imageMeme=publication.getImage();
                    publicationamodifier.setForum(forum1);

                    publicationamodifier.setUser(userP);
                    ImageView imageViewModifier = creerImageView(publication.getImage(),100,100,10,10);
                   // System.out.println(pathImage);

                    imagepublictionModifier.setLayoutX(118);
                    imagepublictionModifier.setLayoutY(121);
                    imagepublictionModifier.getChildren().add(imageViewModifier);

                    modifierPublictionFinale.setVisible(true);
                    modifierPublictionFinale.setManaged(true);




                }
                  refreshUIPublication( forum1.getIdForum());

            });
            Text textNblike=createTextNblike(nbLike);
            TextArea textAreaNblike=createButtonPublictionNblike(textNblike,10,80,40,427,10);
            Button myButtonPlus = createButtonPubliction("+", 427, 55,40,20);
            myButtonPlus.setStyle("-fx-background-color : #4B2F00; -fx-text-fill : #e7e5e5;");
            myButtonPlus.setOnAction(event -> {
               // System.out.println("Button clicked!");
                int newLikeCount = publication.getNbLike() + 1;
                Publication publication1 = new Publication();
                publication1.setIdP(publication.getIdP());
                publication1.setNbLike(newLikeCount);
                try {
                    servicePublication.modifierNbLike(publication1);
//                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
//                    successAlert.setTitle("Modification réussie");
//                    successAlert.setHeaderText(null);
//                    successAlert.setContentText("valider.");
//                    successAlert.show();
                } catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur de modification");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Non valid");
                    errorAlert.showAndWait();
                }

                // Update the UI with the new like count
                textNblike.setText("likes : " + newLikeCount);

                // Refresh the UI after updating the like count
                refreshUIPublicationNbLike(forum1.getIdForum(),textAreaNblike,publication1);
                myButtonPlus.setDisable(true);
            });
            Button myButtonMoin = createButtonPubliction("-", 467, 55,40,20);
            myButtonMoin.setStyle("-fx-background-color : #4B2F00; -fx-text-fill : #e7e5e5;");
            if (publication.getNbLike() == 0) {
                myButtonMoin.setDisable(true);
            }
            myButtonMoin.setOnAction(event -> {
                int foo = publication.getNbLike() - 1;
                if (foo < 0) {
                    myButtonMoin.setDisable(true);
                } else {
                    Publication publication1 = new Publication();
                    publication1.setIdP(publication.getIdP());
                    publication1.setNbLike(foo);

                    try {
                        servicePublication.modifierNbLike(publication1);
//                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
//                        successAlert.setTitle("Modification réussie");
//                        successAlert.setHeaderText(null);
//                        successAlert.setContentText("Validation.");
//                        successAlert.show();
                    } catch (SQLException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur de modification");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Non valide");
                        errorAlert.showAndWait();
                    }

                    refreshUIPublicationNbLike(forum1.getIdForum(), textAreaNblike, publication1);
                    myButtonMoin.setDisable(true);
                }
            });
            Button myButtonTraduction = createButtonPubliction("Traduction", 325, 14,80,20);
            myButtonTraduction.setStyle("-fx-background-color : #4B2F00; -fx-text-fill : #e7e5e5;");
            myButtonTraduction.setOnAction(event -> {
                TranslatorText translatorText = TranslatorText.getInstance();

                String textToTranslate = textContenu.getText();
                System.out.println(textToTranslate);
                String from = "fr";
                String to = "en";

                System.out.println("Envoi de la requête à l'API de traduction...");

                try {
                    Response response = translatorText.post(textToTranslate, from, to);

                    System.out.println("Réponse brute de l'API :");
                    String responseBody = response.body().string();
                    System.out.println(responseBody);

                    // Additional logging for debugging
                    int statusCode = response.code();
                    System.out.println("\nCode de statut HTTP : " + statusCode);

                    // Afficher uniquement le texte traduit
                    String translatedText = translatorText.extractTranslatedText(responseBody);
                    System.out.println("\nTexte traduit :");
                    System.out.println(translatedText);
                    textContenu.setText(translatedText);
                    textAreaContenu.setText(textContenu.getText());


                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }



            });
            Button myButtonTraductionAnnuler = createButtonPubliction("Annuler", 340, 48,60,20);
            myButtonTraductionAnnuler.setStyle("-fx-background-color : #4B2F00; -fx-text-fill : #e7e5e5;");
            myButtonTraductionAnnuler.setOnAction(event -> {
                TranslatorText translatorText = TranslatorText.getInstance();

                String textToTranslate = textContenu.getText();
                System.out.println(textToTranslate);
                String from = "en";
                String to = "fr";

                System.out.println("Envoi de la requête à l'API de traduction...");

                try {
                    Response response = translatorText.post(textToTranslate, from, to);

                    System.out.println("Réponse brute de l'API :");
                    String responseBody = response.body().string();
                    System.out.println(responseBody);

                    // Additional logging for debugging
                    int statusCode = response.code();
                    System.out.println("\nCode de statut HTTP : " + statusCode);

                    // Afficher uniquement le texte traduit
                    String translatedText = translatorText.extractTranslatedText(responseBody);
                    System.out.println("\nTexte traduit :");
                    System.out.println(translatedText);
                    textContenu.setText(translatedText);
                    textAreaContenu.setText(textContenu.getText());


                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }



            });





            finPub.getChildren().addAll( textAreaContenu,textAreaNblike,myButtonPlus,myButtonMoin,myButtonTraduction,myButtonTraductionAnnuler);
            Pane finalPane = new Pane();
            finalPane.setPrefWidth(500);
            finalPane.getChildren().addAll(tetePub, image, finPub);
            PublictionAffichage.getChildren().add(finalPane);
            PublictionAffichage.setSpacing(10);


        }
        ScrollPub.setContent(PublictionAffichage);
        ScrollPub.setStyle("-fx-background-color: transparent;");
    }

    private void createUIElements(List<Forum> forums) {
        mohamed.getChildren().clear();

        int i = 0;
        for (Forum forum : forums) {
            String index=" Forum Numero "+String.valueOf(i+1);
            String titreForum = "Titre : "+forum.getTitre();
            String description = "Description : "+forum.getDescription();
            String date = "Date de creation : "+forum.getDateCreation().toString();
            String nomFormation = "Nom formation : "+forum.getFormation().getNomF();

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




            TextField textFieldDate = creerTextField(textDate);
            TextField textFieldDescription =creerTextField(textDescription);
            TextField textFieldTitreForum =creerTextField(textTitreForum);
            textFieldTitreForum.setOnAction(event -> {
                modiferForumPane.setVisible(true);
                modiferForumPane.setManaged(true);


                try {
                    forum1 = serviceForum.getOneById(forum.getIdForum());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                forum1.setIdForum(forum.getIdForum());
               // System.out.println(forum1.getIdForum());
               // System.out.println(forum1.getFormation().getIdFormation());

                TitreForumModifier.setText(forum1.getTitre());
                DescriptionForumModifier.setText(forum1.getDescription());
                AncienFormationModifier.setText(forum1.getFormation().getNomF());
            });
            textFieldDescription.setOnAction(event -> {
//                modiferForumPane.setVisible(true);
//                modiferForumPane.setManaged(true);

                try {
                    forum1 = serviceForum.getOneById(forum.getIdForum());
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                forum1.setIdForum(forum.getIdForum());
               // System.out.println(forum1.getIdForum());
              //  System.out.println(forum1.getFormation().getIdFormation());

                // Mettez à jour les éléments d'interface utilisateur appropriés ici
                // TitreForumModifier.setText(forum1.getTitre());
                // AncienFormationModifier.setText(forum1.getFormation().getNomF());
            });
            TextArea textAreaNomFormation = creerTextArea(textNomFormation);


            i++;



            VBox vbox = new VBox();
            vbox.getChildren().addAll(textFieldTIndex,textFieldTitreForum, textFieldDate, textFieldDescription, textAreaNomFormation);
            HBox hbox = new HBox();

            hbox.getChildren().add(vbox);
            mohamed.getChildren().add(hbox);
            mohamed.setSpacing(5);
        }

        ScrolFelhi.setContent(mohamed);
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
        text.setFont(new Font("Cambria Bold", 20));
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
            textField.setPrefColumnCount(200);
            textField.setStyle(" -fx-text-fill: black; -fx-font-size: 12px; -fx-font-family: 'Cambria'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0);  -fx-border-width: 4;");
            return textField;
        }
    private TextField creerTextFieldIndex(Text text) {
        TextField textField = new TextField(text.getText());
        textField.setEditable(false);
        textField.setPrefColumnCount(200);
        textField.setStyle(" -fx-text-fill: black; -fx-font-size: 15px; -fx-font-family: 'Cambria Bold'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0);  -fx-border-width: 4;");
        return textField;
    }
    ///Pub.................
    private ImageView creerImageView(String cheminImage, int width, int height, double layoutX, double layoutY ) {
       // System.out.println(cheminImage);
        ImageView imageView = new ImageView();
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(layoutX);
        imageView.setLayoutY(layoutY);
        try {
            InputStream inputStream = getClass().getResourceAsStream("/PubImages/" + cheminImage);
            if (inputStream != null) {
                Image image = new Image(inputStream);
                imageView.setImage(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(200);
                imageView.setStyle("-fx-border-color: #FF0000FF;  -fx-border-width: 10;");
            } else {
                System.err.println("Le fichier " + cheminImage + " n'a pas pu etre chargé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageView;
    }

    private TextArea createTextAreaPublicationDateCreation(Text text, int caracteresParLigne, double width, double height, double layoutX, double layoutY) {
        String contenu = text.getText();
        StringBuilder sb = new StringBuilder();
        int caractereCompteur = 0;

        for (char caractere : contenu.toCharArray()) {
            sb.append(caractere);
            caractereCompteur++;

            if (caractereCompteur == caracteresParLigne) {
                sb.append("\n");
                caractereCompteur = 0;
            }
        }

        TextArea textArea = new TextArea(sb.toString().trim());
        textArea.setPrefWidth(width);
        textArea.setPrefHeight(height);
        textArea.setLayoutX(layoutX);
        textArea.setLayoutY(layoutY);
        textArea.setStyle("-fx-text-fill: black; -fx-font-size: 13px; -fx-font-family: 'Cambria'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0); -fx-border-width: 4;");
        textArea.setEditable(false);
        return textArea;
    }
    private TextArea createButtonPublictionNblike(Text text, int caracteresParLigne, double width, double height, double layoutX, double layoutY) {
        String contenu = text.getText();
        StringBuilder sb = new StringBuilder();
        int caractereCompteur = 0;

        for (char caractere : contenu.toCharArray()) {
            sb.append(caractere);
            caractereCompteur++;

            if (caractereCompteur == caracteresParLigne) {
                sb.append("\n");
                caractereCompteur = 0;
            }
        }

        TextArea textArea = new TextArea(sb.toString().trim());
        textArea.setPrefWidth(width);
        textArea.setPrefHeight(height);
        textArea.setLayoutX(layoutX);
        textArea.setLayoutY(layoutY);
        textArea.setEditable(false);
        textArea.setStyle("-fx-text-fill: black; -fx-font-size: 13px; -fx-font-family: 'Cambria'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0); -fx-border-width: 4;");
        return textArea;
    }
    private TextArea createTextAreaPublicationNomUser(Text text, int caracteresParLigne, double width, double height, double layoutX, double layoutY) {
        String contenu = text.getText();
        StringBuilder sb = new StringBuilder();
        int caractereCompteur = 0;

        for (char caractere : contenu.toCharArray()) {
            sb.append(caractere);
            caractereCompteur++;

            if (caractereCompteur == caracteresParLigne) {
                sb.append("\n");
                caractereCompteur = 0;
            }
        }

        TextArea textArea = new TextArea(sb.toString().trim());
        textArea.setPrefWidth(width);
        textArea.setPrefHeight(height);
        textArea.setLayoutX(layoutX);
        textArea.setLayoutY(layoutY);
        textArea.setStyle("-fx-text-fill: black; -fx-font-size: 13px; -fx-font-family: 'Cambria'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0); -fx-border-width: 4;");
        textArea.setEditable(false);
        return textArea;
    }
    private TextArea createTextAreaPublicationContenu(Text text, int caracteresParLigne, double width, double height, double layoutX, double layoutY) {
        String contenu = text.getText();
        StringBuilder sb = new StringBuilder();
        int caractereCompteur = 0;

        for (char caractere : contenu.toCharArray()) {
            sb.append(caractere);
            caractereCompteur++;

            if (caractereCompteur == caracteresParLigne) {
                sb.append("\n");
                caractereCompteur = 0;
            }
        }

        TextArea textArea = new TextArea(sb.toString().trim());
        textArea.setPrefWidth(width);
        textArea.setPrefHeight(height);
        textArea.setLayoutX(layoutX);
        textArea.setLayoutY(layoutY);
        textArea.setEditable(false);
        textArea.setStyle("-fx-text-fill: black; -fx-font-size: 13px; -fx-font-family: 'Cambria'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0); -fx-border-width: 4;");
        return textArea;
    }
    private Button createButtonPubliction(String text, double layoutX, double layoutY, double width, double height) {
        Button button = new Button(text);
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        return button;
    }
    private Text createTextContenu(String contenu) {
        Text text = new Text(contenu);
        return text;
    }
    private Text createTextNomUser(String contenu) {
        Text text = new Text(contenu);
        return text;
    }
    private Text createTextDateCreation(String contenu) {
        Text text = new Text(contenu);
        return text;
    }
    private Text createTextNblike(String contenu) {
        Text text = new Text(contenu);
        return text;
    }


    private TextArea creerTextArea(Text text) {
            TextArea textArea = new TextArea(text.getText());
            textArea.setEditable(false);
            textArea.setPrefWidth(200);
            textArea.setPrefHeight(50);
            textArea.setWrapText(true);
            textArea.setStyle("-fx-text-fill: black; -fx-font-size: 13px; -fx-font-family: 'Cambria'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0); -fx-border-width: 4;");            return textArea;
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
    void refreche(ActionEvent event) {
        Platform.runLater(this::refreshUI);
       // chargerFormations();

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
        ForumFormationModifier.getItems().addAll(formations);
        // Utilisez un StringConverter pour afficher uniquement les noms et prénoms dans le ChoiceBox
        ForumFormationModifier.setConverter(new StringConverter<Formation>() {

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
       /* ObservableList<Formation> observableFormations = FXCollections.observableArrayList(formations);
        ForumFormationModifier.setItems(observableFormations);*/
    }


    @FXML
    void ModifierForum(ActionEvent event) {

        // Créer une boîte de dialogue de confirmation
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
    void QuiterModifierForum(ActionEvent event) {
        modiferForumPane.setVisible(false);
        modiferForumPane.setManaged(false);

    }




    @FXML
    void GetPubliction(ActionEvent event) {
        TitreForumP.setText(forum1.getTitre());
        TitreForumP.setStyle("-fx-text-fill: black; -fx-font-size: 10px; -fx-font-family: 'Cambria Bold'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0); -fx-border-width: 4;");
        DescriptionForumP.setText(forum1.getDescription());
        DescriptionForumP.setStyle("-fx-text-fill: black; -fx-font-size: 10px; -fx-font-family: 'Cambria Bold'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0); -fx-border-width: 4;");
        nomFormationP.setText(forum1.getFormation().getNomF());
        nomFormationP.setStyle("-fx-text-fill: black; -fx-font-size: 10px; -fx-font-family: 'Cambria Bold'; -fx-border-color: rgba(75,47,0,0.61); -fx-background-color: rgba(0,128,0,0); -fx-border-width: 4;");
        refreshUIPublication( forum1.getIdForum());
        PUbAll.setVisible(true);
        PUbAll.setManaged(true);
        ScrolFelhi.setVisible(false);
    }

    @FXML
    void QuitterPubliction(ActionEvent event) {
        PUbAll.setVisible(false);
        PUbAll.setManaged(false);
        ScrolFelhi.setVisible(true);


    }
    


    public void QuitterAjouterPubliction(ActionEvent actionEvent) {
        PaneAjouterPublication.setVisible(false);
        PaneAjouterPublication.setManaged(false);

    }
    @FXML
    void OpenAjouterPub(ActionEvent event) {
        PaneAjouterPublication.setVisible(true);
        PaneAjouterPublication.setManaged(true);

    }
    @FXML
    public void AjouterPubliction(ActionEvent actionEvent) {
        try {
            // Utilisez directement forum1 au lieu de créer une nouvelle instance inutile de Forum
           // int idForum = forum1.getIdForum();

            User user = new User();
            int iduser = Integer.parseInt(idUser.getText());
            user.setIdUser(iduser);

            String contenu = contenuPublication.getText();
            String regex = "^[a-zA-Z0-9\\s]+$";
            if (contenu.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Champ(s) vide(s), Veuillez remplir tous les champs requis.");
                alert.showAndWait();
                return; // Arrêtez l'ajout si l'un des champs est vide
            }

            if (contenu.matches(regex)) {
                // Le contenu est valide, vous pouvez ajouter la publication
                servicePublication.ajouter(new Publication(contenu, pathImage, forum1, user));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("GG");
                alert.show();
            } else {
                // Affichez un message d'erreur si le contenu n'est pas valide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Le contenu ne doit contenir que des lettres, des chiffres et des espaces.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        // Vous devez adapter la méthode refreshUIPublication en fonction de votre code, car elle n'est pas fournie ici
         refreshUIPublication(forum1.getIdForum());
    }

    public void ImporteImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        // Obtenez le chemin du répertoire des ressources
        String currentDir = System.getProperty("user.dir");
        String resourceDir = currentDir + "/src/main/resources/PubImages";

        // Définissez le répertoire initial du FileChooser
        File initialDirectory = new File(resourceDir);
        fileChooser.setInitialDirectory(initialDirectory);

        fileChooser.setTitle("Sélectionner une image");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();

            // Extraire la partie après "resources/images"
            String relativePath = imagePath.substring(resourceDir.length() + 1);

            pathImage = relativePath;
           // System.out.println(pathImage);
            ImageView imageView = creerImageView(relativePath,100,100,10,10);
            imagePubliction.getChildren().add(imageView);


        }
    }

@FXML
void getImageModifier(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();

    // Obtenez le chemin du répertoire des ressources
    String currentDir = System.getProperty("user.dir");
    String resourceDir = currentDir + "/src/main/resources/PubImages";

    // Définissez le répertoire initial du FileChooser
    File initialDirectory = new File(resourceDir);
    fileChooser.setInitialDirectory(initialDirectory);

    fileChooser.setTitle("Sélectionner une image");
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
        String imagePath = selectedFile.getAbsolutePath();

        // Extraire la partie après "resources/images"
        String relativePath = imagePath.substring(resourceDir.length() + 1);

        selectedImagePathModifier = relativePath; // Stocker le chemin de l'image sélectionnée

        ImageView imageView = creerImageView(relativePath, 100, 100, 10, 10);
        imagepublictionModifier.getChildren().clear();
        imagepublictionModifier.getChildren().add(imageView);

    }

}

    @FXML
    void viderImagemodifier(ActionEvent event) {
        modifierPublictionFinale.setVisible(false);
        modifierPublictionFinale.setManaged(false);
    }

    @FXML
    void modifierPubliction(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation de modification");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir modifier ce publiction ?");

        // Afficher la boîte de dialogue de confirmation et attendre la réponse de l'utilisateur
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String contenuModifier = ContenuPublictionModifier.getText();
                String regex = "^[a-zA-Z0-9\\s]+$";
//                String ima = null;
//                if (ima.isEmpty()){ima=selectedImagePathModifier;}

                if (contenuModifier.matches(regex)) {
                    // Le contenu est valide, vous pouvez procéder à la modification
                    // Utiliser la variable qui stocke le chemin de l'image sélectionnée
                    Publication publicationfinal = new Publication(contenuModifier, selectedImagePathModifier, forum1, userP);
                    publicationfinal.setIdP(publicationamodifier.getIdP());

                    try {
                        servicePublication.modifier(publicationfinal);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Modification réussie");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("La modification du publiction a été effectuée avec succès.");
                        successAlert.show();
                    } catch (SQLException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur de modification");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Une erreur s'est produite lors de la modification du publication.\n" + e.getMessage());
                        errorAlert.showAndWait();
                    }
                } else {
                    // Affichez un message d'erreur si le contenu n'est pas valide
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setContentText("Le contenu ne doit contenir que des lettres, des chiffres et des espaces.");
                    errorAlert.showAndWait();
                }
            }
        });

        imagepublictionModifier.getChildren().clear();
        ContenuPublictionModifier.clear();
        refreshUIPublication(forum1.getIdForum());

    }

    private static Publication findPublicationWithMostLikes(List<Publication> publications) {
        Optional<Publication> publicationWithMostLikes = publications.stream()
                .max(Comparator.comparingInt(Publication::getNbLike));


        return publicationWithMostLikes.orElse(null);
    }
    private int refreshUIidForum() {
        try {
            List<Publication> publications = servicePublication.getAll();
            int idForum = findMostRepeatedIdForum(publications);
            return idForum;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static int findMostRepeatedIdForum(List<Publication> publications) {
        Map<Integer, Long> idForumCounts = publications.stream()
                .collect(Collectors.groupingBy(pub -> pub.getForum().getIdForum(), Collectors.counting()));

        return idForumCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }
    private void updateIdForum(ActionEvent event) {
        int idForum = refreshUIidForum();
       // System.out.println(idForum);

        try {
            Publication publicationWithMostLikes=findPublicationWithMostLikes(servicePublication.getAll());

            publicationLikk.setText("Titre Forum :"+publicationWithMostLikes.getForum().getTitre()+"\n"+"Nom User"+publicationWithMostLikes.getUser().getNom()+"\n"+publicationWithMostLikes.getNbLike()+"likes");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Forum forum= serviceForum.getOneById(idForum);
            forumActif.setText("zibest Forum :"+forum.getTitre());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static long countRepetitionsOfNomForum(List<Publication> publications, String nomForumToCount) {
        return publications.stream()
                .filter(pub -> pub.getForum().getTitre().equals(nomForumToCount))
                .count();
    }


    @FXML
    void combienub(ActionEvent event) {
        List<Publication> publications ;
        try {
            publications = servicePublication.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        String nomForumToCount =nomFormPubR.getText();

             Forum forum= forumNom.getValue();
        String nomForumToCount=forum.getTitre();
        long repetitionsOfNomForum =countRepetitionsOfNomForum(publications, nomForumToCount);
        nbPUB.setText("contien "+String.valueOf(repetitionsOfNomForum)+" publictions");
        forumNom.getSelectionModel().clearSelection();
        forumNom.setValue(null);

    }
    private void chargerForumNom() {
        List<Forum> forums= new ArrayList<>();
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery("SELECT titre FROM forum;");
            while (rs.next()) {

                String titre = rs.getString("titre");
                forums.add(new Forum(titre));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        forumNom.getItems().addAll(forums);
        // Utilisez un StringConverter pour afficher uniquement les noms et prénoms dans le ChoiceBox
        forumNom.setConverter(new StringConverter<Forum>() {

            @Override
            public String toString(Forum forum) {
                // Affichez le nom et prénom de l'utilisateur dans le ChoiceBox
                return forum != null ? forum.getTitre() : " " ;
            }

            @Override
            public Forum fromString(String string) {
                // Vous n'avez probablement pas besoin d'implémenter cette méthode pour un ChoiceBox
                return null;
            }
        });

    }


}