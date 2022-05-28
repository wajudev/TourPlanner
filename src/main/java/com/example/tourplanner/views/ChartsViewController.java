package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.viewModels.MainViewModel;
import com.example.tourplanner.viewModels.TourLogViewModel;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChartsViewController implements Initializable {


    @FXML
    private LineChart lineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private final MainViewModel mainViewModel = new MainViewModel();

    @Getter
    private final ObservableList<TourViewModel> tourViewModels = mainViewModel.getTours();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(TourViewModel tourViewModel : tourViewModels){
            XYChart.Series<String, Float> series = new XYChart.Series<>();
            ObservableList<TourLogViewModel> tourLogViewModels = mainViewModel.getTourLogsForCharts(tourViewModel);
            series.setName(tourViewModel.getName().get());
            for (TourLogViewModel tourLogViewModel : tourLogViewModels){
                series.getData().add(new XYChart.Data<>(String.valueOf(tourLogViewModel.getTourLogId().getValue()), tourLogViewModel.getRating().get()));
            }
            lineChart.getData().add(series);
        }

/*        XYChart.Series<String, Float> series = new XYChart.Series<>();
        series.setName("Tour ratings One");

        series.getData().add(new XYChart.Data<>("1", 4.3f));
        series.getData().add(new XYChart.Data<>("2", 3.3f));
        series.getData().add(new XYChart.Data<>("3", 2.3f));
        series.getData().add(new XYChart.Data<>("4", 1.3f));
        series.getData().add(new XYChart.Data<>("5", 5f));

        XYChart.Series<String, Float> series2 = new XYChart.Series<>();
        series2.setName("Tour ratings 2");

        series2.getData().add(new XYChart.Data<>("1", 1.3f));
        series2.getData().add(new XYChart.Data<>("2", 2.3f));
        series2.getData().add(new XYChart.Data<>("3", 4.3f));
        series2.getData().add(new XYChart.Data<>("4", 0.3f));
        series2.getData().add(new XYChart.Data<>("5", 2f));

        lineChart.getData().addAll(series, series2);*/


    }

    public static void openModal(Stage owner) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("charts-view.fxml"));
        Scene scene = null;
        Stage stage= new Stage();
        try {
            scene = new Scene(fxmlLoader.load(), 620, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Tour Ratings");
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}
