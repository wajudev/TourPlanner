package com.example.tourplanner.views;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AssertView {
    public static boolean deleteConfirmation(){
        ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this tour?",
                deleteButton,
                cancelButton);

        alert.setTitle("Delete Warning");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get()==deleteButton;
    }

    public static boolean mapQuestError(){
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.ERROR,
                "Please enter a correct from and to location. Please note that max. distance is 400km for pedestrians",
                ok);

        alert.setTitle("Input Error");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get()==ok;
    }
}
