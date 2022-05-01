package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.viewModels.MainViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    private AnchorPane anchorPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDescription();
        addTourButton.setOnMouseClicked(e -> {
            openAddWindow();
        });
    }

    public void initializeDescription(){
        tourListView.setItems(viewModel.getTour());
        descriptionLabel.textProperty().bind(viewModel.getCurrentTourDescription());
        fromLabel.textProperty().bind(viewModel.getCurrentTourFrom());
        toLabel.textProperty().bind(viewModel.getCurrentTourTo());
        transportTypeLabel.textProperty().bind(viewModel.getCurrentTourTransportType());
        distanceLabel.textProperty().bind(viewModel.getCurrentTourDistance());
        estimatedTimeLabel.textProperty().bind(viewModel.getCurrentTourEstimatedTime());

        tourListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tour>() {
            @Override
            public void changed(ObservableValue<? extends Tour> observableValue, Tour tour, Tour t1) {
                viewModel.setCurrentTour(t1);
            }
        });
    }

    public void openAddWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("addTour-view.fxml"));
        Scene scene = null;
        Stage stage= new Stage();
        try {
            scene = new Scene(fxmlLoader.load(), 600, 330);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("add Tour");
        stage.initOwner(anchorPane.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}