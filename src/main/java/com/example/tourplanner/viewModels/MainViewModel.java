package com.example.tourplanner.viewModels;

import com.example.tourplanner.business.app.TourManager;
import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.business.events.EventListener;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.models.Tour;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class MainViewModel implements EventListener {
    private ObservableList<TourViewModel> tours = FXCollections.observableArrayList();
    private final TourManager tourManager = TourManagerImpl.getInstance();
    private final EventManager eventManager = EventMangerImpl.getInstance();

    @Getter
    private IntegerProperty currentTourId = new SimpleIntegerProperty();
    @Getter
    private StringProperty currentTourName = new SimpleStringProperty("");
    @Getter
    private StringProperty currentTourDescription = new SimpleStringProperty("");
    @Getter
    private StringProperty currentTourTo = new SimpleStringProperty("");
    @Getter
    private StringProperty currentTourFrom = new SimpleStringProperty("");
    @Getter
    private StringProperty currentTourTransportType = new SimpleStringProperty("");
    @Getter
    private StringProperty currentTourDistance = new SimpleStringProperty("");
    @Getter
    private StringProperty currentTourEstimatedTime = new SimpleStringProperty("");

    @Getter
    private TourViewModel currentTour;

    public MainViewModel(){
        // FIXME Lokal tour anlegen mit id, um logik zu testen.
        // TODO JUnits tests schreiben.
        // TODO TODOS richtig ordnen.
        // NOTE: Verletzung des MVVMs wurde aufgehoben.
        // -> fml, shoot me in the head and bury me in a landfill alongside JAVAFX ;-) lg Lanre @Tom
        eventManager.subscribe("tour.save", this);
        eventManager.subscribe("tour.update", this);
        eventManager.subscribe("tour.delete", this);

        loadTours();
    }

    /**
     * Reloads tour
     */
    public void loadTours(){
        tours.clear();
        for (Tour tour : tourManager.getTours()){
            tours.add(new TourViewModel(tour));
        }
    }

    /**
     * Sets current tour
     *
     * @param currentTour current tour class from the tourViewModel
     *
     */
    public void setCurrentTour(TourViewModel currentTour) {
        if (currentTour != null){
            this.currentTourName.setValue(currentTour.getName().getValue());
            this.currentTourFrom.setValue(currentTour.getFrom().getValue());
            this.currentTourTo.setValue(currentTour.getTo().getValue());
            this.currentTourDescription.setValue(currentTour.getDescription().getValue());
            this.currentTourTransportType.setValue(currentTour.getTransportType().getValue());
            this.currentTourDistance.setValue(String.valueOf(currentTour.getDistance().getValue()));
            this.currentTourEstimatedTime.setValue(String.valueOf(currentTour.getEstimatedTime().getValue()));
        }
    }


    public ObservableList<TourViewModel> getTour(){
        return tours;
    }

    @Override
    public void update(String event, Object data) {
        if ("tour.save".equals(event) || "tour.delete".equals(event)){
            loadTours();
        }

        if("tour.update".equals(event)){
            loadTours();
        }
    }

    public void deleteTour(TourViewModel tourViewModel){
        boolean isDeleted = tourManager.deleteTour(tourViewModel.getId().getValue());
        if (isDeleted){
            eventManager.notify("tour.delete", tourViewModel);
        }
    }

}
