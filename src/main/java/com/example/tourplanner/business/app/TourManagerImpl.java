package com.example.tourplanner.business.app;

import com.example.tourplanner.business.events.EventListener;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.models.Tour;

import java.util.ArrayList;
import java.util.List;

public class TourManagerImpl implements TourManager, EventListener {

    private final EventManager eventManager = EventMangerImpl.getInstance();

    private static TourManager instance;

    public static TourManager getInstance(){
        if (instance == null){
            instance = new TourManagerImpl();
        }
        return instance;
    }

    public TourManagerImpl(){
        eventManager.subscribe("save", this);
        eventManager.subscribe("update", this);
    }

    @Override
    public Tour getTour(String tourId) {
        return null;
    }

    @Override
    public List<Tour> getTours() {
        return null;
    }

    @Override
    public boolean updateTour(String tourId, Tour tour) {
        return false;
    }

    @Override
    public boolean saveTour(Tour tour) {
        return false;
    }

    @Override
    public boolean deleteTour(String tourId) {
        return false;
    }


    @Override
    public void update(String event, Object data) {

    }
}
