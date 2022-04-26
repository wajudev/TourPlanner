package com.example.tourplanner.viewModels;

import com.example.tourplanner.business.app.TourManager;
import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.business.events.EventListener;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MainViewModel implements EventListener {
    private ObservableList<Tour> tours = FXCollections.observableArrayList();
    private final TourManager tourManager = TourManagerImpl.getInstance();
    private final EventManager eventManager = EventMangerImpl.getInstance();

    public MainViewModel(){
        tours.add(new Tour("test0","test","test2","test3","test4","test5",
                        10,20,"test6"));
        eventManager.subscribe("save", this);
        eventManager.subscribe("update", this);
        eventManager.subscribe("delete", this);
    }

    public ObservableList<String> getTourNames(){
        ObservableList<String> tourNames = FXCollections.observableArrayList();
        for (Tour tour: tours) {
            tourNames.add(tour.getTourName());
        }
        return tourNames;
    }

    @Override
    public void update(String event, Object data) {

    }
}
