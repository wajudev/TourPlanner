package com.example.tourplanner.dal.dao;

import com.example.tourplanner.dal.intefaces.GenericDao;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;

import java.sql.SQLException;
import java.util.List;

public interface TourDao extends GenericDao<Tour> {

    List<TourLog> getTourLogsOfTour(Tour tour) throws SQLException;
}
