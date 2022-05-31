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

    public static void mapQuestError(){
        System.out.println("test");
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.ERROR,
                "Please enter a correct from and to location. Please note that max. distance is 400km for pedestrians",
                ok);

        alert.setTitle("Input Error");
        alert.showAndWait();
    }

    public static void helpWindow(){
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Version 1.1.0\n Contact: if20b277@technikum-wien.at",
                ok);

        alert.setTitle("Input Error");
        alert.showAndWait();
    }

    public static void exportEmptyListError(){
        ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.ERROR,
                "Please select Items from the left column before you press export",
                ok);

        alert.setTitle("Input Error");
        alert.showAndWait();
    }

    public static boolean deleteAllConfirmation(){
        ButtonType deleteButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType keepButton = new ButtonType("NO", ButtonBar.ButtonData.NO);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to delete all tours before import?",
                deleteButton,
                keepButton);

        alert.setTitle("Delete Warning");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get()==deleteButton;
    }
}
