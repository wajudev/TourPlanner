package com.example.tourplanner.business.app;

import com.example.tourplanner.business.events.EventListener;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.dal.dao.TourDao;
import com.example.tourplanner.dal.intefaces.DalFactory;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourManagerImpl implements TourManager, EventListener {

    final Logger logger = LogManager.getLogger(Database.class);

    private static TourManager instance;

    public static TourManager getInstance(){
        if (instance == null){
            instance = new TourManagerImpl();
        }
        return instance;
    }

    public TourManagerImpl(){
        EventManager eventManager = EventMangerImpl.getInstance();
        eventManager.subscribe("tour.save", this);
        eventManager.subscribe("tour.update", this);
    }

    @Override
    public Tour getTour(int tourId) {
        logger.info("Get tour with id " + tourId + ".");
        TourDao tourDao = DalFactory.getTourDao();
        try {
            assert tourDao != null;
            return tourDao.get(tourId).orElse(null);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tour> getTours() {
        logger.info("Get all tours");
        TourDao tourDao = DalFactory.getTourDao();
        try {
            assert tourDao != null;
            return tourDao.getAll();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean updateTour(Tour tour) {
        logger.info("Update tour " + tour + ".");
        TourDao tourDao = DalFactory.getTourDao();
        try {
            assert tourDao != null;
            return tourDao.update(tour);
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int saveTour(Tour tour) {
        logger.info("Save tour " + tour + ".");
        TourDao tourDao = DalFactory.getTourDao();
        try {
            assert tourDao != null;
            return tourDao.save(tour);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean deleteTour(int tourId) {
        logger.info("Delete tour " + tourId + ".");
        TourDao tourDao = DalFactory.getTourDao();
        try{
            assert tourDao != null;
            return tourDao.delete(tourId);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tourContains(Tour tour, String searchedTerm, boolean isCaseSensitive) {

        String searchedString = tour.getTourId() +
                tour.getTourName() +
                tour.getFrom() +
                tour.getTo() +
                tour.getTransportType();

        if (!isCaseSensitive) {
            searchedTerm = searchedTerm.toLowerCase();
            searchedString = searchedString.toLowerCase();
        }

        return searchedString.contains(searchedTerm);
    }

    @Override
    public int saveTourLog(TourLog tourLog) {
        return 0;
    }

    @Override
    public boolean updateTourLog(TourLog tourLog) {
        return false;
    }


    @Override
    public void update(String event, Object data) {

    }
}
