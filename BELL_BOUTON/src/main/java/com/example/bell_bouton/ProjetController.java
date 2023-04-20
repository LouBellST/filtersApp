package com.example.bell_bouton;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.Group;

public class ProjetController {
    @FXML
    private Label image;

    @FXML
    protected void loadButton() {

        image.setText("Hi");

    }
}