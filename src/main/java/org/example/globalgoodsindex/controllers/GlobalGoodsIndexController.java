package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GlobalGoodsIndexController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}