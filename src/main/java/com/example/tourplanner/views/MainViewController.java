package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.viewModels.MainViewModel;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private MainViewModel viewModel = new MainViewModel();
    @FXML
    private ListView<TourViewModel> tourListView;
    @FXML
    private Label fromLabel;
    @FXML
    private Label toLabel;
    @FXML
    private Label transportTypeLabel;
    @FXML
    private Label distanceLabel;
    @FXML
    private Label estimatedTimeLabel;
    @FXML
    private Label descriptionLabel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourListView.setItems(viewModel.getTour());
        descriptionLabel.textProperty().bind(viewModel.getCurrentTourDescription());
        fromLabel.textProperty().bind(viewModel.getCurrentTourFrom());
        toLabel.textProperty().bind(viewModel.getCurrentTourTo());
        transportTypeLabel.textProperty().bind(viewModel.getCurrentTourTransportType());
        distanceLabel.textProperty().bind(viewModel.getCurrentTourDistance());
        estimatedTimeLabel.textProperty().bind(viewModel.getCurrentTourEstimatedTime());

        tourListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TourViewModel>() {

            @Override
            public void changed(ObservableValue<? extends TourViewModel> observableValue, TourViewModel tourViewModel, TourViewModel t1) {
                viewModel.setCurrentTour(t1);
            }
        });
    }

    /**
     * Method to add a tour with a title action
     *
     * @param actionEvent The ActionEvent from the invoker.
     */
    public void addTourAction(ActionEvent actionEvent){
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        AddTourController.openModal(stage);
    }
}