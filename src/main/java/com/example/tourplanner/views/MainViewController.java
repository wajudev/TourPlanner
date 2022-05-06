package com.example.tourplanner.views;


import com.example.tourplanner.models.Tour;
import com.example.tourplanner.viewModels.MainViewModel;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final MainViewModel viewModel = new MainViewModel();
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

        tourListView.getSelectionModel().selectedItemProperty().addListener((observableValue, tourViewModel, t1) -> viewModel.setCurrentTour(t1));
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

    public void deleteTourAction(ActionEvent actionEvent){
        System.out.println("test3");
        viewModel.deleteTour(tourListView.getSelectionModel().getSelectedItem());
    }

    public void editTourAction(ActionEvent editEvent){

    }
}