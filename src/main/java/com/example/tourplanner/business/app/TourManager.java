package com.example.tourplanner.business.app;

import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;

import java.io.File;
import java.util.List;

public interface TourManager {
    Tour getTour(int tourId);
    List<Tour> getTours();
    boolean updateTour(Tour tour);
    int saveTour(Tour tour);
    boolean deleteTour(int tourId);
    boolean deleteAllTours();
    boolean tourContains(Tour populateTour, String searchedTerm, boolean isCaseSensitive);
    int saveTourLog(TourLog tourLog);
    boolean updateTourLog(TourLog tourLog);
    List<TourLog> getTourLogsOfTour(Tour tour);
    List<TourLog> getTourLogs();
    boolean deleteTourLog(int tourLogId);
    void exportTours(File file);
    void importTours(File file);
}
