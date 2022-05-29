package com.example.tourplanner.views;


import com.example.tourplanner.viewModels.MainViewModel;
import com.example.tourplanner.viewModels.TourLogViewModel;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final MainViewModel mainViewModel = new MainViewModel();
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
    @FXML
    private ImageView imageView;

    @FXML
    private Button editTourButton;
    @FXML
    private Button deleteTourButton;
    @FXML
    private Button addLogButton;
    @FXML
    private Button editLogButton;
    @FXML
    private Button deleteLogButton;
    @FXML
    private Button chartsViewButton;


    @FXML
    private TableView<TourLogViewModel> currentTourLogTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeFields();
        initializeTourListView();
        initializeTourLogTable();
        chartsViewButton.setDisable(false);
    }
    private void initializeFields(){
        searchTextField.textProperty().bindBidirectional(mainViewModel.getSearch());
        descriptionLabel.textProperty().bind(mainViewModel.getCurrentTourDescription());
        fromLabel.textProperty().bind(mainViewModel.getCurrentTourFrom());
        toLabel.textProperty().bind(mainViewModel.getCurrentTourTo());
        transportTypeLabel.textProperty().bind(mainViewModel.getCurrentTourTransportType());
        distanceLabel.textProperty().bind(mainViewModel.getCurrentTourDistance());
        estimatedTimeLabel.textProperty().bind(mainViewModel.getCurrentTourEstimatedTime());
        imageView.imageProperty().bind(mainViewModel.getCurrentImage());

    }

    private void initializeTourListView(){
        tourListView.setItems(mainViewModel.getFilteredTours());
        tourListView.getSelectionModel().selectedItemProperty().addListener((observableValue, tourViewModel, t1) -> {
            mainViewModel.setCurrentTour(t1);
            activateButtons();
            deactivateButtons(t1);
        });
    }

    private void initializeTourLogTable(){
        currentTourLogTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("date"));
        currentTourLogTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        currentTourLogTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("rating"));
        currentTourLogTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("totalTime"));
        currentTourLogTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("comment"));

        currentTourLogTable.setItems(mainViewModel.getCurrentTourLogs());
    }

    public void activateButtons(){
        editTourButton.setDisable(false);
        deleteTourButton.setDisable(false);

        addLogButton.setDisable(false);
        editLogButton.setDisable(false);
        deleteLogButton.setDisable(false);
    }

    public void deactivateButtons(TourViewModel tour){
        if(tour==null){
            editTourButton.setDisable(true);
            deleteTourButton.setDisable(true);

            addLogButton.setDisable(true);
            editLogButton.setDisable(true);
            deleteLogButton.setDisable(true);
        }
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
        if(AssertView.deleteConfirmation()){
            mainViewModel.deleteTour(tourListView.getSelectionModel().getSelectedItem());
        }
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
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null){
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            AddTourLogController.openModal(stage, selectedTour);
        }
    }

    public void editTourLogAction(ActionEvent actionEvent) {
        TourLogViewModel selectedTourLog = currentTourLogTable.getSelectionModel().getSelectedItem();
        if (selectedTourLog != null){
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            EditTourLogController.openModal(stage, selectedTourLog);
        }
    }

    public void deleteTourLogAction() {
        mainViewModel.deleteTourLog(currentTourLogTable.getSelectionModel().getSelectedItem());
    }

    public void generateTourReportAction(ActionEvent actionEvent){
        TourViewModel selectedTour = tourListView.getSelectionModel().getSelectedItem();
        if (selectedTour != null){
            mainViewModel.generateTourReport(selectedTour);
        }
    }

    public void generateReportSummaryStats(){
        mainViewModel.generateReportSummaryStats();
    }

    public void chartsViewAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        ChartsViewController.openModal(stage);
    }
}