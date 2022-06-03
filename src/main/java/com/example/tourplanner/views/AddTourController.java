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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTourController implements Initializable {
    @Getter
    private final TourViewModel viewModel = new TourViewModel();


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
        bindPropertyHelper(tourNameTextField, viewModel, fromTourTextField, toTourTextField, descriptionTextArea, transportTypeComboBox);

        titleLabel.setText("Add Tour");
        finishButton.setOnAction(this::addTourAction);
        cancelButton.setOnAction(this::cancelAddTourAction);
    }

    public static void bindPropertyHelper(TextField tourNameTextField, TourViewModel viewModel, TextField fromTourTextField, TextField toTourTextField, TextArea descriptionTextArea, ComboBox<String> transportTypeComboBox) {
        tourNameTextField.textProperty().bindBidirectional(viewModel.getName());
        fromTourTextField.textProperty().bindBidirectional(viewModel.getFrom());
        toTourTextField.textProperty().bindBidirectional(viewModel.getTo());
        descriptionTextArea.textProperty().bindBidirectional(viewModel.getDescription());

        transportTypeComboBox.valueProperty().bindBidirectional(viewModel.getTransportType());
        transportTypeComboBox.getItems().addAll("Car","Bicycle","Pedestrian");
        transportTypeComboBox.getSelectionModel().select(0); //Default Selection
    }

    /**
     * Add tour action.
     *
     * @param actionEvent An event which indicates that the component-defined action occurred.
     */
    public void addTourAction(ActionEvent actionEvent) {
        if (viewModel.saveTour() > 0) {
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
     * Open a new modal owned by owner to add a new tour.
     *
     * @param owner Top-level container or parent of current node
     */
    public static void openModal(Stage owner) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tour-view.fxml"));
        fxmlLoader.setController(new AddTourController());
        Scene scene = null;
        Stage stage= new Stage();
        try {
            scene = new Scene(fxmlLoader.load(), 620, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("add Tour");
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}
