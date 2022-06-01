package com.example.tourplanner.business.app;

import com.example.tourplanner.business.events.EventListener;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.business.mapQuest.StaticMapRequest;
import com.example.tourplanner.business.report.Report;
import com.example.tourplanner.dal.dao.TourDao;
import com.example.tourplanner.dal.dao.TourLogDao;
import com.example.tourplanner.dal.intefaces.DalFactory;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import com.example.tourplanner.views.AssertView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
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
            Tour tour =tourDao.get(tourId).orElse(null);
            if(tour!=null){
                logger.info("Get tour route from api... ");
                Tour temp = StaticMapRequest.getImageRequest(tour.getFrom(),tour.getTo(),getTransportType(tour.getTransportType()));
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
            logger.info("Check if " + tour + "route is correct");
            if(StaticMapRequest.checkError(tour.getFrom(),tour.getTo(),getTransportType(tour.getTransportType()))){
                return tourDao.update(tour);
            }
            return false;
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
            logger.info("Check if " + tour + "route is correct");
            if(StaticMapRequest.checkError(tour.getFrom(),tour.getTo(),getTransportType(tour.getTransportType()))){
                return tourDao.save(tour);
            }
            return 0;
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
    public boolean deleteAllTours(){
        logger.info("Delete all tours.");
        TourDao tourDao = DalFactory.getTourDao();
        try {
            assert tourDao !=null;
            return tourDao.deleteAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean tourContains(Tour tour, String searchedTerm, boolean isCaseSensitive) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tour.getTourId());
        stringBuilder.append(tour.getTourName());
        stringBuilder.append(tour.getFrom());
        stringBuilder.append(tour.getTo());
        stringBuilder.append(tour.getTransportType());


        List<TourLog> tourLogs = this.getTourLogsOfTour(tour);
        for (TourLog tourLog : tourLogs){
            stringBuilder.append(tourLog.getDate());
            stringBuilder.append(tourLog.getComment());
        }

        String searchedString = stringBuilder.toString();

        if (!isCaseSensitive) {
            searchedTerm = searchedTerm.toLowerCase();
            searchedString = searchedString.toLowerCase();
        }

        return searchedString.contains(searchedTerm);
    }

    @Override
    public int saveTourLog(TourLog tourLog) {
        logger.info("Save tour log " + tourLog + ".");
        TourLogDao tourLogDao = DalFactory.getTourLogDao();
        try{
            assert tourLogDao != null;
            return tourLogDao.save(tourLog);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean updateTourLog(TourLog tourLog) {
        logger.info("Update tour log " + tourLog + ".");
        TourLogDao tourLogDao = DalFactory.getTourLogDao();
        try {
            assert tourLogDao != null;
            return tourLogDao.update(tourLog);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<TourLog> getTourLogs() {
        logger.info("Get all tour logs");
        TourLogDao tourLogDao = DalFactory.getTourLogDao();
        try {
            assert tourLogDao != null;
            return tourLogDao.getAll();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean deleteTourLog(int tourLogId) {
        logger.info("Delete tour log " + tourLogId + ".");
        TourLogDao tourLogDao = DalFactory.getTourLogDao();
        try {
            assert tourLogDao != null;
            return tourLogDao.delete(tourLogId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void generateTourReport(Tour tour) throws IllegalStateException,IOException{
        logger.info("Generate tour report for " + tour.getTourName() + ".");
            Report.tourReport(tour);
    }

    @Override
    public void generateReportSummaryStats() {
        logger.info("Generate tour summary report with statistical data.");
        try {
            List<Tour> tours = this.getTours();
            Report.reportSummaryStats(tours);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TourLog> getTourLogsOfTour(Tour tour) {
        logger.info("Get all tour logs for tour " + tour + ".");
        TourDao tourDao = DalFactory.getTourDao();
        try {
            assert tourDao != null;
            return tourDao.getTourLogsOfTour(tour);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    private String getTransportType(String transportType){
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
    public void exportTours(File file,List<Tour> tours){
        logger.info("Export Tours from JSON File: " + file + ".");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file,tours);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void importTours(File file, boolean deleteAllTours){
        logger.info("Import Tours from JSON File: " + file + ".");
        ObjectMapper objectMapper= new ObjectMapper();
        try {
            List<Tour> tours= objectMapper.readValue(file, new TypeReference<>() {
            });
            if(deleteAllTours){
                deleteAllTours();
            }
            for (Tour tour:tours) {
                saveTour(tour);
            }
            System.out.println(tours.get(0).getFrom());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void update(String event, Object data) {

    }

}
