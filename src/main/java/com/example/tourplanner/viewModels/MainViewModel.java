package com.example.tourplanner.viewModels;

import com.example.tourplanner.business.app.TourManager;
import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.business.events.EventListener;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.models.Tour;
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
        tours.add(new TourViewModel());
        eventManager.subscribe("tour.save", this);
        eventManager.subscribe("tour.update", this);
        eventManager.subscribe("tour.delete", this);
    }

    public void setCurrentTour(TourViewModel tour) {
        this.currentTour = tour;
        if(currentTour!=null){
            currentTourDescription.setValue(currentTour.getDescription().getValue());
            currentTourFrom.setValue(currentTour.getFrom().getValue());
            currentTourTo.setValue(currentTour.getTo().getValue());
            currentTourTransportType.setValue(currentTour.getTransportType().getValue());
            currentTourDistance.setValue(String.valueOf(currentTour.getDistance().getValue()));
            currentTourEstimatedTime.setValue(String.valueOf(currentTour.getEstimatedTime().getValue()));
        }else {
            currentTourDescription.setValue("");
        }
    }


    public ObservableList<TourViewModel> getTour(){
        return tours;
    }

    @Override
    public void update(String event, Object data) {

    }

}
