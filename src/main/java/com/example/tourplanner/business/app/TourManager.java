package com.example.tourplanner.business.app;

import com.example.tourplanner.models.Tour;

import java.util.List;

public interface TourManager {
    Tour getTour(int tourId);
    List<Tour> getTours();
    boolean updateTour(int tourId, Tour tour);
    int saveTour(Tour tour);
    boolean deleteTour(int tourId);
}
