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
    private ObservableList<Tour> tours = FXCollections.observableArrayList();
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
    private Tour currentTour;

    public MainViewModel(){
        tours.add(new Tour(1,"test","test2","test3","test4","test5",
                        10f,20));
        tours.add(new Tour(2,"testtttt","test22","testtttt3","test4","test5",
                10f,20));
        eventManager.subscribe("tour.save", this);
        eventManager.subscribe("tour.update", this);
        eventManager.subscribe("tour.delete", this);
    }

    public void setCurrentTour(Tour tour) {
        this.currentTour = tour;
        if(currentTour!=null){
            currentTourDescription.setValue(currentTour.getTourDescription());
            currentTourFrom.setValue(currentTour.getFrom());
            currentTourTo.setValue(currentTour.getTo());
            currentTourTransportType.setValue(currentTour.getTransportType());
            currentTourDescription.setValue(currentTour.getTourDescription());
            currentTourDistance.setValue(String.valueOf(currentTour.getDistance()));
            currentTourEstimatedTime.setValue(String.valueOf(currentTour.getEstimatedTime()));
        }else {
            currentTourDescription.setValue("");
        }
    }


    public ObservableList<Tour> getTour(){
        return tours;
    }

    @Override
    public void update(String event, Object data) {

    }

}
