package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.viewModels.MainViewModel;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private ListView<Tour> tourListView;


    private MainViewModel viewModel = new MainViewModel();

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
    private Button addTourButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourListView.setItems(viewModel.getTourNames());
        tourListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tour>() {
            @Override
            public void changed(ObservableValue<? extends Tour> observableValue, Tour tour, Tour t1) {
                fromLabel.setText(t1.getFrom());
                toLabel.setText(t1.getTo());
                transportTypeLabel.setText(t1.getTransportType());
                distanceLabel.setText(String.valueOf(t1.getDistance()));
                estimatedTimeLabel.setText(String.valueOf(t1.getEstimatedTime()));
                descriptionLabel.setText(t1.getTourDescription());
            }
        });
        addTourButton.setOnMouseClicked(e -> {
            /*Tour tour = tourListView.getSelectionModel().getSelectedItem();
            fromLabel.setText(tour.getFrom());*/
        });

    }
}