package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.viewModels.ExportViewModel;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExportTourController implements Initializable {
    @Getter
    private final TourViewModel viewModel = new TourViewModel();

    @Getter
    private final ExportViewModel exportViewModel = new ExportViewModel();



    // Data-binding references
    public ListView<TourViewModel> tourListView;
    public ListView<TourViewModel> exportTourListView;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tourListView.setItems(exportViewModel.getTourViewModels());
        exportTourListView.setItems(exportViewModel.getExportTours());

        tourListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                TourViewModel currentTour = tourListView.getSelectionModel().getSelectedItem();
                exportViewModel.fromToursToExportTours(currentTour);
            }
        });


        exportTourListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                TourViewModel currentTour = exportTourListView.getSelectionModel().getSelectedItem();
                exportViewModel.fromExportToursToTours(currentTour);
            }
        });
    }

    /**
     * Export action.
     *
     * @param actionEvent An event which indicates that the component-defined action occurred.
     */
    public void exportAction(ActionEvent actionEvent) {
        if(!exportTourListView.getItems().isEmpty()){

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("."));
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON","*.json"));
            File selectedFile = fileChooser.showSaveDialog(null);

            if(selectedFile!=null){
                exportViewModel.exportTours(selectedFile);
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
            }

        }else {
            AssertView.exportEmptyListError();
        }

    }

    public void moveAllToExportListAction(){
        exportViewModel.allFromToursToExportTours();
    }

    public void moveAllToTourListAction(){
        exportViewModel.allFromExportToursToTours();
    }


    /**
     * Cancel export action.
     *
     * @param actionEvent An event which indicates that the component-defined action occurred.
     */
    public void cancelAction(ActionEvent actionEvent){
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("export-view.fxml"));
        Scene scene = null;
        Stage stage = new Stage();
        try {
            scene = new Scene(fxmlLoader.load(), 620, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("export Tour");
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}
