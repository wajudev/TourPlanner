package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    public TextField transportTypeTextField;
    public TextArea descriptionTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourNameTextField.textProperty().bindBidirectional(viewModel.getName());
        fromTourTextField.textProperty().bindBidirectional(viewModel.getFrom());
        toTourTextField.textProperty().bindBidirectional(viewModel.getTo());
        transportTypeTextField.textProperty().bindBidirectional(viewModel.getTransportType());
        descriptionTextArea.textProperty().bindBidirectional(viewModel.getDescription());
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
    public static void openModal(Stage owner,TourViewModel tourViewModel){
        setViewModel(tourViewModel);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editTour-view.fxml"));
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
