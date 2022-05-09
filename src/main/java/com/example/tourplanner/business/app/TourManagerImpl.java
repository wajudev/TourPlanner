package com.example.tourplanner.business.app;

import com.example.tourplanner.business.events.EventListener;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.business.mapQuest.StaticMapRequest;
import com.example.tourplanner.dal.dao.TourDao;
import com.example.tourplanner.dal.intefaces.DalFactory;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.models.Tour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourManagerImpl implements TourManager, EventListener {

    final Logger logger = LogManager.getLogger(Database.class);

    private final StaticMapRequest mapRequest = new StaticMapRequest();

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
            Tour tour =tourDao.get(tourId).orElse(null);
            if(tour!=null){
                Tour temp = mapRequest.sendRequest(tour.getFrom(),tour.getTo(),getTansportType(tour.getTransportType()));
                tour.setRouteInformationImageURL(temp.getRouteInformationImageURL());
                if(tour.getDistance() ==0 || tour.getEstimatedTime().equals("")){
                    tour.setDistance(temp.getDistance());
                    tour.setEstimatedTime(temp.getEstimatedTime());
                    updateTour(tour);
                }
            }
            return tour;
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

    public String getTansportType(String transportType){
        if (!transportType.equals("fastest") && !transportType.equals("bicycle") && !transportType.equals("pedestrian")){
            return switch (transportType) {
                case "Car" -> "fastest";
                case "Bicycle" -> "bicycle";
                case "Pedestrian" -> "pedestrian";
                default -> "Error:WrongTransportType";
            };
        }
        return transportType;
    }


    @Override
    public void update(String event, Object data) {

    }
}
