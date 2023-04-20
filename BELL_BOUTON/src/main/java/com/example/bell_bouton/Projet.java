package com.example.bell_bouton;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.stage.FileChooser;

import java.awt.image.BufferedImage;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.google.gson.Gson;
import javafx.scene.control.TextArea;


public class Projet extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Pour creer une fenetre file Chooser (pas encore affichée à l'écran mais elle existe)
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));


        Button button1 = new Button("Parcourir");
        Button buttonReverse = new Button("Horizontal");
        Button buttonReverseH = new Button("Vertical");
        Button buttonGBR = new Button("Filtre GBR");
        Button buttonNB = new Button("Filtre Noir&Blanc");
        Button buttonSepia = new Button("Filtre Sepia");
        Button buttonReset = new Button("Reset");

        Button buttonSave = new Button("Save");
        Button buttonTags = new Button("Parcourir par tag");
        Button buttonAddTag = new Button("Ajouter un tag");
        Button buttonResetTags = new Button("Supprimer un tag");

        // Créer l'élément qui englobe tous les autres pour après afficher le tout
        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);

        // Mise en place du traitement d'image
        List<TraitementImage> listeImages = new ArrayList<>();
        ImageView ip = new ImageView();
        ip.setFitWidth(470);
        ip.setFitHeight(330);
        ip.setPreserveRatio(true);

        // liste dans laquelle il y a que l'objet image pour le globaliser et en faire un json lors du save
        List<monImage> jsonImage = new ArrayList<>();

        // la colonne de texte dans laquelle on affiche les tags
        TextArea textArea = new TextArea();
        textArea.setMinWidth(90);
        textArea.setEditable(false);

        // barre de texte pour afficher les tags
        TextArea textAreaTags = new TextArea();
        textAreaTags.setMaxHeight(10);
        textAreaTags.setMinWidth(560);
        textAreaTags.setEditable(false);


        // Evenement lors de l'utilisation de bouton 1
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Affiche le file chooser
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {

                    listeImages.clear();
                    jsonImage.clear();
                    textArea.clear();
                    textAreaTags.clear();
                    textArea.appendText("  Modifs : \n\n");

                    /*
                    try {
                        Gson gson = new Gson();
                        JsonReader reader = new JsonReader(new FileReader("src/main/java/com/example/bell_bouton/Infos.JSON"));
                        Wrapper datas = gson.fromJson(reader, Wrapper.class);

                        if(datas != null){
                            for(monImage i : datas.getMesImages()){
                                if(i.imagePath.equals(file.toURI().toString())){

                                }
                            }
                        } else{

                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                     */


                    // créer un element image si on a select un fichier
                    Image image1 = new Image(file.toURI().toString());
                    TraitementImage monImage = new TraitementImage(image1);
                    ip.setImage(image1);
                    listeImages.add(monImage);

                    monImage json = new monImage(file.toURI().toString());
                    jsonImage.add(json);
                }
            }
        });

        buttonReverse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(listeImages.isEmpty() == false){
                    listeImages.get(0).reverseImage();
                    ip.setImage(listeImages.get(0).getNewImage());

                    jsonImage.get(0).addModif("reverse");
                    textArea.appendText("reverse \n");
                }
            }
        });

        buttonReverseH.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(listeImages.isEmpty() == false){
                    listeImages.get(0).reverseHImage();
                    ip.setImage(listeImages.get(0).getNewImage());

                    jsonImage.get(0).addModif("reverseV");
                    textArea.appendText("reverseV \n");
                }
            }
        });

        buttonGBR.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(listeImages.isEmpty() == false){
                    listeImages.get(0).filtreGBR();
                    ip.setImage(listeImages.get(0).getNewImage());

                    jsonImage.get(0).addModif("filtreGBR");
                    textArea.appendText("filtreGBR \n");
                }
            }
        });

        buttonNB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(listeImages.isEmpty() == false){
                    listeImages.get(0).filtreNB();
                    ip.setImage(listeImages.get(0).getNewImage());

                    jsonImage.get(0).addModif("filtreNB");
                    textArea.appendText("filtreNB \n");
                }
            }
        });

        buttonSepia.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(listeImages.isEmpty() == false){
                    listeImages.get(0).filtreSepia();
                    ip.setImage(listeImages.get(0).getNewImage());

                    jsonImage.get(0).addModif("filtreSepia");
                    textArea.appendText("filtreSepia \n");
                }
            }
        });

        buttonReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(listeImages.isEmpty() == false){
                    listeImages.get(0).reset();
                    ip.setImage(listeImages.get(0).getNewImage());

                    jsonImage.get(0).resetModifs();
                    textArea.clear();
                    textArea.appendText("  Modifs : \n\n");
                }
            }
        });

        buttonSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(jsonImage.isEmpty() == false) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setContentText("Nom de l'image : ");
                    dialog.showAndWait();
                    String nomFile = dialog.getResult();

                    // pour save l'image dans le dossier ressources
                    File outputFile = new File("ressources/" + nomFile + ".png");
                    BufferedImage bImage = SwingFXUtils.fromFXImage(listeImages.get(0).getNewImage(), null);

                    jsonImage.get(0).setImagePath(outputFile.toURI().toString());
                    jsonImage.get(0).setNomImage(nomFile);
                    try {
                        ImageIO.write(bImage, "png", outputFile);

                        // pour save l'image dans le fichier JSON
                        Gson gson = new Gson();
                        JsonReader reader = new JsonReader(new FileReader("src/main/java/com/example/bell_bouton/Infos.JSON"));
                        Wrapper datas = gson.fromJson(reader, Wrapper.class);

                        if(datas != null){
                            datas.getMesImages().add(jsonImage.get(0));
                            String json = gson.toJson(datas);
                            FileWriter writer = new FileWriter("src/main/java/com/example/bell_bouton/Infos.JSON");
                            writer.write(json);
                            writer.close();
                        } else{
                            Wrapper w = new Wrapper();
                            w.getMesImages().add(jsonImage.get(0));
                            String json = gson.toJson(w);
                            FileWriter writer = new FileWriter("src/main/java/com/example/bell_bouton/Infos.JSON");
                            writer.write(json);
                            writer.close();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        buttonTags.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setContentText("Chercher un tag : ");
                dialog.showAndWait();
                String nomTag = dialog.getResult();

                try {
                    Gson gson = new Gson();
                    JsonReader reader = new JsonReader(new FileReader("src/main/java/com/example/bell_bouton/Infos.JSON"));
                    Wrapper datas = gson.fromJson(reader, Wrapper.class);

                    List<String> pathsByTag = new ArrayList<>();

                    if(datas != null){
                       for(monImage image : datas.getMesImages()){
                           for(String tag : image.listeTags){
                               if(tag.equals(nomTag)){
                                   System.out.println(image.nomImage);
                               }
                           }
                       }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        buttonAddTag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(jsonImage.isEmpty() == false){
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setContentText("Ajouter un tag : ");
                    dialog.showAndWait();
                    String nomTag = dialog.getResult();

                    jsonImage.get(0).addTag(nomTag);
                    textAreaTags.appendText(nomTag + ",\t");
                }
            }
        });

        buttonResetTags.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(jsonImage.isEmpty() == false) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setContentText("Tag à supprimer : ");
                    dialog.showAndWait();
                    String nomTag = dialog.getResult();

                    jsonImage.get(0).removeTag(nomTag);
                    textAreaTags.clear();
                    for(String tag : jsonImage.get(0).listeTags){
                        textAreaTags.appendText(tag + ",\t");
                    }
                }
            }
        });


        // Boutons du haut
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(10));
        buttons.setSpacing(5);
        buttons.fillHeightProperty();
        buttons.getChildren().addAll(button1, buttonReverse, buttonReverseH, buttonGBR, buttonNB, buttonSepia, buttonReset);


        // Boutons du bas
        HBox buttonsBottom = new HBox();
        buttonsBottom.setPadding(new Insets(10));
        buttonsBottom.setSpacing(5);
        buttonsBottom.fillHeightProperty();
        buttonsBottom.getChildren().addAll(buttonSave, buttonTags, buttonAddTag, buttonResetTags);


        // Image et liste des modifs horizontalement
        HBox main = new HBox();
        main.setPadding(new Insets(10));
        main.setSpacing(5);
        main.getChildren().addAll(ip, textArea);

        // Image+modifs et liste des tags verticalement
        VBox main2 = new VBox();
        main2.setPadding(new Insets(10));
        main2.setSpacing(5);
        main2.getChildren().addAll(main, textAreaTags);


        // Groupe avec tous les sous-groupes faits au-dessus
        root.getChildren().addAll(buttons, main2, buttonsBottom);


        // Créer une scene et un stage ( pas important de comprendre comment ca fonctionne )
        Scene scene = new Scene(root, 650, 550);

        stage.setTitle("Bibliothèque d'images");
        stage.setScene(scene);
        stage.show();
    }


    public class Wrapper{
        public List<monImage> mesImages = new ArrayList<>();
        public Wrapper() {}
        public List<monImage> getMesImages() {
            return mesImages;
        }
    }

    public class monImage{
        public String nomImage;
        public String imagePath;
        public List<String> listeModifs = new ArrayList<>();
        public List<String> listeTags = new ArrayList<>();
        public monImage(String path){
            this.imagePath = path;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public void setNomImage(String nomImage) {
            this.nomImage = nomImage;
        }

        public void addModif(String modif){
            this.listeModifs.add(modif);
        }
        public void resetModifs(){
            this.listeModifs.clear();
        }

        public void addTag(String tag){
            this.listeTags.add(tag);
        }
        public void removeTag(String tag){
            if(this.listeTags.contains(tag) == true){
                this.listeTags.remove(tag);
            }
        }
    }


    public class TraitementImage{
        public Image oldImage;
        public Image newImage;
        public TraitementImage(Image i){
            this.oldImage = i;
            this.newImage = i;
        }

        public Image getNewImage() {
            return newImage;
        }

        public Image getOldImage() {
            return oldImage;
        }

        public void reset(){
            this.newImage = oldImage;
        }

        private void reverseImage(){

            // Matrice (X x Y) des pixels de l'image (lisible mais pas modifiable)
            PixelReader px = newImage.getPixelReader();

            // Créer une image vierge de la taille de l'image de reference
            WritableImage wImage = new WritableImage(
                    (int)newImage.getWidth(),
                    (int)newImage.getHeight());
            // Créer une matrice (X x Y) des pixels de l'image vierge (modifiable)
            PixelWriter pixelWriter = wImage.getPixelWriter();

            // double boucle parcourir les pixels de l'image de ref et les attribuer a l'image vierge (en inversant les px)
            for (int y = 0; y < newImage.getHeight(); y++){
                for (int x = 0; x < newImage.getWidth(); x++){
                    Color color = px.getColor((int)newImage.getWidth()-1-x, y);
                    pixelWriter.setColor(x,y,color);
                }
            }

            // return la nouvelle image inversée
            this.newImage = wImage;
        }

        // FONCTION POUR REVERSE UNE IMAGE VERTICALEMENT ( EN CRÉÉ UNE NOUVELLE ET LA RETURN )
        private void reverseHImage(){

            // Matrice (X x Y) des pixels de l'image (lisible mais pas modifiable)
            PixelReader px = newImage.getPixelReader();

            // Créer une image vierge de la taille de l'image de reference
            WritableImage wImage = new WritableImage(
                    (int)newImage.getWidth(),
                    (int)newImage.getHeight());
            // Créer une matrice (X x Y) des pixels de l'image vierge (modifiable)
            PixelWriter pixelWriter = wImage.getPixelWriter();

            // double boucle parcourir les pixels de l'image de ref et les attribuer a l'image vierge (en inversant les px)
            for (int y = 0; y < newImage.getHeight(); y++){
                for (int x = 0; x < newImage.getWidth(); x++){
                    Color color = px.getColor(x, (int)newImage.getHeight()-1-y);
                    pixelWriter.setColor(x,y,color);
                }
            }

            // return la nouvelle image inversée
            this.newImage = wImage;
        }

        private void filtreGBR(){

            // Matrice (X x Y) des pixels de l'image (lisible mais pas modifiable)
            PixelReader px = newImage.getPixelReader();

            // Créer une image vierge de la taille de l'image de reference
            WritableImage wImage = new WritableImage(
                    (int)newImage.getWidth(),
                    (int)newImage.getHeight());
            // Créer une matrice (X x Y) des pixels de l'image vierge (modifiable)
            PixelWriter pixelWriter = wImage.getPixelWriter();

            // double boucle parcourir les pixels de l'image de ref et les attribuer a l'image vierge (en inversant les px)
            for (int y = 0; y < newImage.getHeight(); y++){
                for (int x = 0; x < newImage.getWidth(); x++){
                    Color color = px.getColor(x, y);
                    int red = (int)(color.getRed()*255);
                    int blue = (int)(color.getBlue()*255);
                    int green = (int)(color.getGreen()*255);

                    Color c = Color.rgb(green,blue,red);
                    pixelWriter.setColor(x,y,c);
                }
            }

            // return la nouvelle image inversée
            this.newImage = wImage;
        }

        private void filtreNB() {

            // Matrice (X x Y) des pixels de l'image (lisible mais pas modifiable)
            PixelReader px = newImage.getPixelReader();

            // Créer une image vierge de la taille de l'image de reference
            WritableImage wImage = new WritableImage(
                    (int) newImage.getWidth(),
                    (int) newImage.getHeight());
            // Créer une matrice (X x Y) des pixels de l'image vierge (modifiable)
            PixelWriter pixelWriter = wImage.getPixelWriter();

            // double boucle parcourir les pixels de l'image de ref et les attribuer a l'image vierge (en inversant les px)
            for (int y = 0; y < newImage.getHeight(); y++) {
                for (int x = 0; x < newImage.getWidth(); x++) {
                    Color color = px.getColor(x, y);
                    int red = (int) (color.getRed() * 255);
                    int blue = (int) (color.getBlue() * 255);
                    int green = (int) (color.getGreen() * 255);
                    int moyenne = (red + blue + green) / 3;

                    Color c = Color.rgb(moyenne, moyenne, moyenne);
                    pixelWriter.setColor(x, y, c);
                }
            }
            // return la nouvelle image inversée
            this.newImage = wImage;
        }

        private void filtreSepia() {

            // Matrice (X x Y) des pixels de l'image (lisible mais pas modifiable)
            PixelReader px = newImage.getPixelReader();

            // Créer une image vierge de la taille de l'image de reference
            WritableImage wImage = new WritableImage(
                    (int) newImage.getWidth(),
                    (int) newImage.getHeight());
            // Créer une matrice (X x Y) des pixels de l'image vierge (modifiable)
            PixelWriter pixelWriter = wImage.getPixelWriter();

            // double boucle parcourir les pixels de l'image de ref et les attribuer a l'image vierge (en inversant les px)
            for (int y = 0; y < newImage.getHeight(); y++) {
                for (int x = 0; x < newImage.getWidth(); x++) {
                    Color color = px.getColor(x, y);
                    int red = (int)(color.getRed() * 255);
                    int blue = (int)(color.getBlue() * 255);
                    int green = (int)(color.getGreen() * 255);

                    int newR = (int)((red * 0.393) + (blue * 0.189) + (green * 0.769));
                    int newG = (int)((red * 0.349) + (blue * 0.131) + (green * 0.534));
                    int newB = (int)((red * 0.272) + (blue *  0.168) + (green * 0.686));
                    if(newR > 255){newR = 255;}
                    if(newG > 255){newG = 255;}
                    if(newB > 255){newB = 255;}

                    Color c = Color.rgb(newR, newG, newB);
                    pixelWriter.setColor(x, y, c);
                }
            }
            // return la nouvelle image inversée
            this.newImage = wImage;
        }

    }


    public static void main(String[] args) {
        launch();
    }
}