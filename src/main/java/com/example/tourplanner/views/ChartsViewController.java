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
import javafx.scene.chart.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChartsViewController implements Initializable {


    @FXML
    private LineChart lineChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private PieChart pieChart;
    private final MainViewModel mainViewModel = new MainViewModel();
    @Getter
    private final ObservableList<TourViewModel> tourViewModels = mainViewModel.getTours();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createLineChartForRatings();
        createPieChartForAverageTime();
    }

    public void createLineChartForRatings(){
        //Make List to save your XYChartSeries
        List<XYChart.Series> seriesList = new ArrayList<>();

        for (TourViewModel tourViewModel : tourViewModels){
            XYChart.Series<String, Float> series = new XYChart.Series<>();
            List<TourLogViewModel> tourLogViewModels = mainViewModel.getTourLogsForCharts(tourViewModel);
            if(tourLogViewModels.size()==0){
                continue;
            }
            series.setName(tourViewModel.getName().get());
            int counter = 1;
            for (TourLogViewModel tourLogViewModel : tourLogViewModels){
                series.getData().add(new XYChart.Data<>(String.valueOf(counter),
                        tourLogViewModel.getRating().get()));
                counter++;
            }
            seriesList.add(series);
            //lineChart.getData().add(seriesList.get(seriesList.size()-1));
        }
        lineChart.getData().addAll(seriesList);
    }

    public void createPieChartForAverageTime(){
        for (TourViewModel tourViewModel : tourViewModels){
            try {
                PieChart.Data pieChartData = new PieChart.Data(tourViewModel.getName().getValue(),
                        mainViewModel.calculateAverageTime(tourViewModel));
                pieChart.getData().add(pieChartData);
            }catch (IllegalStateException e){
                continue;
            }
        }
    }

    public static void openModal(Stage owner) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("charts-view.fxml"));
        Scene scene = null;
        Stage stage= new Stage();
        try {
            scene = new Scene(fxmlLoader.load(), 800, 800);
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
