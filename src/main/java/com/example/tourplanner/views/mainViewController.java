package com.example.tourplanner.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class mainViewController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}