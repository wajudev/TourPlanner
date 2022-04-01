package com.example.tourplanner.business.app;

import com.example.tourplanner.models.Tour;

import java.util.List;

public interface TourManager {
    Tour getTour(String tourId);
    List<Tour> getTours();
    boolean updateTour(String tourId, Tour tour);
    boolean saveTour(Tour tour);
    boolean deleteTour(String tourId);
}
