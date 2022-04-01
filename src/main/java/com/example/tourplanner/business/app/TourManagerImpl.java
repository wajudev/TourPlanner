package com.example.tourplanner.business.app;

import com.example.tourplanner.models.Tour;

import java.util.List;

public class TourManagerImpl implements TourManager {
    public TourManagerImpl(){

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
}
