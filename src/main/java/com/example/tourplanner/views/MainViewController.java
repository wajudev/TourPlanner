package com.example.tourplanner.views;


import com.example.tourplanner.viewModels.MainViewModel;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField searchTextField;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTextField.textProperty().bindBidirectional(viewModel.getSearch());
        tourListView.setItems(viewModel.getFilteredTours());
        descriptionLabel.textProperty().bind(viewModel.getCurrentTourDescription());
        fromLabel.textProperty().bind(viewModel.getCurrentTourFrom());
        toLabel.textProperty().bind(viewModel.getCurrentTourTo());
        transportTypeLabel.textProperty().bind(viewModel.getCurrentTourTransportType());
        distanceLabel.textProperty().bind(viewModel.getCurrentTourDistance());
        estimatedTimeLabel.textProperty().bind(viewModel.getCurrentTourEstimatedTime());

        tourListView.getSelectionModel().selectedItemProperty().addListener((observableValue, tourViewModel, t1)
                -> viewModel.setCurrentTour(t1));
    }

    /**
     * Add tour action
     *
     * @param actionEvent The ActionEvent from the invoker.
     */
    public void addTourAction(ActionEvent actionEvent){
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        AddTourController.openModal(stage);
    }

    /**
     * Delete tour action
     */
    public void deleteTourAction(){
        viewModel.deleteTour(tourListView.getSelectionModel().getSelectedItem());
    }

    /**
     * Edit tour action
     *
     * @param actionEvent The ActionEvent from the invoker.
     */
    public void editTourAction(ActionEvent actionEvent){
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null){
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            EditTourController.openModal(stage,selectedTour);
            tourListView.getSelectionModel().select(selectedTour);
        }
    }

    public void addTourLogAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        AddTourLogController.openModal(stage);
    }

    public void editTourLogAction(ActionEvent actionEvent) {
    }

    public void deleteTourLogAction(ActionEvent actionEvent) {
    }
}