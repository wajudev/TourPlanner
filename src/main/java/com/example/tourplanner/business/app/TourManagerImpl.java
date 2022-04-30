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
        eventManager.subscribe("tour saved", this);
        eventManager.subscribe("tour updated", this);
    }

    @Override
    public Tour getTour(int tourId) {
        return null;
    }

    @Override
    public List<Tour> getTours() {
        return null;
    }

    @Override
    public boolean updateTour(int tourId, Tour tour) {
        return false;
    }

    @Override
    public int saveTour(Tour tour) {
        return -1;
    }

    @Override
    public boolean deleteTour(int tourId) {
        return false;
    }


    @Override
    public void update(String event, Object data) {

    }
}
