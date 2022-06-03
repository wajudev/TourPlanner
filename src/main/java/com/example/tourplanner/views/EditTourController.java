package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditTourController implements Initializable {
    @Getter
    @Setter
    private static TourViewModel viewModel = new TourViewModel();


    // Data-binding references
    public TextField tourNameTextField;
    public TextField fromTourTextField;
    public TextField toTourTextField;
    public TextArea descriptionTextArea;

    public Button finishButton;
    public Button cancelButton;
    public Label titleLabel;

    @FXML
    private ComboBox<String> transportTypeComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddTourController.bindPropertyHelper(tourNameTextField, viewModel, fromTourTextField, toTourTextField, descriptionTextArea, transportTypeComboBox);

        finishButton.setOnAction(this::editTourAction);
        finishButton.setText("Edit Tour");
        cancelButton.setOnAction(this::cancelAddTourAction);

        titleLabel.setText("Edit Tour");


    }

    /**
     * Edit tour action.
     *
     * @param actionEvent An event which indicates that the component-defined action occurred.
     */
    public void editTourAction(ActionEvent actionEvent) {
        if (viewModel.updateTour()) {
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        }else {
            AssertView.mapQuestError();
        }
    }

    /**
     * Cancel add tour action.
     *
     * @param actionEvent An event which indicates that the component-defined action occurred.
     */
    public void cancelAddTourAction(ActionEvent actionEvent){
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    /**
     * Open a new modal owned by owner to edit a new tour.
     *
     * @param owner Top-level container or parent of current node
     */
    public static void openModal(Stage owner, TourViewModel tourViewModel){
        setViewModel(tourViewModel);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tour-view.fxml"));
        fxmlLoader.setController(new EditTourController());
        Scene scene = null;
        Stage stage= new Stage();
        try {
            scene = new Scene(fxmlLoader.load(), 620, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("edit Tour");
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}
