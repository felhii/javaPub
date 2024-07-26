package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Tesp {
    @FXML
    private VBox afficherPP;
    public void initialize() {
        Pane tetePub = new Pane();
        Text textNomUser=createTextNomUser("felhi");
        TextArea textAreaNomUser =createTextAreaPublicationNomUser(textNomUser,10,200,50,10,10) ;
        Text textDateCreation=createTextDateCreation("2024-02-23 22:10:00");
        TextArea textAreaDateCreation =createTextAreaPublicationDateCreation(textDateCreation,10,200,50,210,10);
        tetePub.getChildren().addAll(textAreaNomUser, textAreaDateCreation);
        ImageView imageView = creerImageView("images.jpg",180,150,150,10);
        Pane image=new Pane();
        image.getChildren().add(imageView);

        Pane felhi1 = new Pane();
        Text textContenu=createTextContenu("12345678910");
        TextArea textAreaContenu=createTextAreaPublicationContenu(textContenu,5,350,70,10,10);
        Text textNblike=createTextNblike("11");
        TextArea textAreaNblike=createButtonPublictionNblike(textNblike,6,50,48,360,10);
        Button myButton = createButtonPubliction("xx", 360, 55,50,20);


        felhi1.getChildren().addAll( textAreaContenu,textAreaNblike,myButton);
        afficherPP.getChildren().addAll(tetePub,image,felhi1);
        afficherPP.setSpacing(10);

    }
    private ImageView creerImageView(String cheminImage,int width,int height, double layoutX, double layoutY ) {
        System.out.println(cheminImage);
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
                System.err.println("Le fichier " + cheminImage + " n'a pas pu être chargé.");
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
}
