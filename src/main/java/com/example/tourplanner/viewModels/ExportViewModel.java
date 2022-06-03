package com.example.tourplanner.viewModels;

import com.example.tourplanner.business.app.TourManager;
import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExportViewModel {

    private final TourManager tourManager = TourManagerImpl.getInstance();
    private final MainViewModel mainViewmodel = new MainViewModel();
    @Getter
    private final ObservableList<TourViewModel> tourViewModels = mainViewmodel.getTours();
    @Getter
    private final ObservableList<TourViewModel> exportTours = FXCollections.observableArrayList();

    public void fromToursToExportTours(TourViewModel tour){
        exportTours.add(tour);
        tourViewModels.remove(tour);
    }
    public void fromExportToursToTours(TourViewModel tour){
        tourViewModels.add(tour);
        exportTours.remove(tour);
    }

    public void allFromToursToExportTours(){
        exportTours.addAll(tourViewModels);
        tourViewModels.clear();
    }
    public void allFromExportToursToTours(){
        tourViewModels.addAll(exportTours);
        exportTours.clear();
    }

    public void exportTours(File file){
        List<Tour> tours= new ArrayList<>();

        for (TourViewModel tour: exportTours) {
            tours.add(tour.populateTour());
        }
        tourManager.exportTours(file,tours);
    }

}
