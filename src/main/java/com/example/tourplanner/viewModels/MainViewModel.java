package com.example.tourplanner.viewModels;

import com.example.tourplanner.business.app.TourManager;
import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.business.events.EventListener;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import com.example.tourplanner.views.AssertView;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.image.Image;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainViewModel implements EventListener {


    private final TourManager tourManager = TourManagerImpl.getInstance();
    private final EventManager eventManager = EventMangerImpl.getInstance();
    final Logger logger = LogManager.getLogger(Database.class);

    private final Image image = new Image(new File("./src/main/resources/loading.gif").toURI().toString());

    @Getter
    private final IntegerProperty currentTourId = new SimpleIntegerProperty();
    @Getter
    private final StringProperty currentTourName = new SimpleStringProperty("");
    @Getter
    private final StringProperty currentTourDescription = new SimpleStringProperty("");
    @Getter
    private final StringProperty currentTourTo = new SimpleStringProperty("");
    @Getter
    private final StringProperty currentTourFrom = new SimpleStringProperty("");
    @Getter
    private final StringProperty currentTourTransportType = new SimpleStringProperty("");
    @Getter
    private final StringProperty currentTourDistance = new SimpleStringProperty("");
    @Getter
    private final StringProperty currentTourEstimatedTime = new SimpleStringProperty("");
    @Getter
    private final ObjectProperty<Image> currentImage = new SimpleObjectProperty();
    @Getter
    private final StringProperty search = new SimpleStringProperty("");
    @Getter
    private final ObservableList<TourViewModel> tours = FXCollections.observableArrayList();

    /**
     * A Filtered List Wraps an ObservableList and filters its content using the provided Predicate.
     * All changes in the ObservableList are propagated immediately to the FilteredList.
     */
    @Getter
    FilteredList<TourViewModel> filteredTours = new FilteredList<>(tours, s -> true);

    @Getter
    private final ObservableList<TourLogViewModel> currentTourLogs = FXCollections.observableArrayList();

    private TourViewModel currentTour;




    public MainViewModel() {
        eventManager.subscribe("tour.save", this);
        eventManager.subscribe("tour.update", this);
        eventManager.subscribe("tour.delete", this);
        eventManager.subscribe("tour-log.save", this);
        eventManager.subscribe("tour-log.update", this);
        eventManager.subscribe("tour-log.delete", this);


        loadTours();

        search.addListener((observable, oldNeedle, newNeedle) -> {
            logger.info("Filtering tours: " + newNeedle);
            String filter = search.getValue();
            if (filter == null || filter.length() == 0) {
                filteredTours.setPredicate(s -> true);
            } else {
                filteredTours.setPredicate(s -> s.contains(filter));
            }
        });
    }



    /**
     * Sets current tour
     *
     * @param currentTour current tour class from the tourViewModel
     *
     */
    public void setCurrentTour(TourViewModel currentTour) {
        this.currentImage.setValue(image);
        if (currentTour != null){
            this.currentTourId.setValue(currentTour.getTourId().getValue());
            if (currentTour.getImage().getValue() == null) {
                /*
                  The user interface cannot be directly updated from a non-application thread.
                  Instead, use Platform.runLater(), with the logic inside the Runnable object.
                 */
                Platform.runLater(this::loadCurrentTour);
            } else {
                setCurrentTourHelper(currentTour);

                Thread imageThread = new Thread(() -> this.currentImage.setValue(new Image(currentTour.getImage().getValue())));
                imageThread.start();
            }

            setCurrentTourHelper(currentTour);

            updateCurrentTourLogs(currentTour);
        }
        this.currentTour = currentTour;
    }

    public void setCurrentTourHelper(TourViewModel currentTour) {
        this.currentTourName.setValue(currentTour.getName().getValue());
        this.currentTourFrom.setValue(currentTour.getFrom().getValue());
        this.currentTourTo.setValue(currentTour.getTo().getValue());
        this.currentTourDescription.setValue(currentTour.getDescription().getValue());
        this.currentTourTransportType.setValue(currentTour.getTransportType().getValue());
        this.currentTourDistance.setValue(String.valueOf(currentTour.getDistance().getValue()));
        this.currentTourEstimatedTime.setValue(String.valueOf(currentTour.getEstimatedTime().getValue()));
    }

    @Override
    public void update(String event, Object data) {
        if ("tour.save".equals(event) || "tour.delete".equals(event)){
            loadTours();
        }

        if("tour.update".equals(event)){
            loadTours();
            loadCurrentTour();
        }

        if ("tour-log.save".equals(event) || "tour-log.update".equals(event) || "tour-log.delete".equals(event)) {
            this.loadCurrentTourLogs();
        }
    }



    public void deleteTour(TourViewModel tourViewModel){
        if(tourViewModel != null){
            boolean isDeleted = tourManager.deleteTour(tourViewModel.getTourId().getValue());
            if (isDeleted){
                eventManager.notify("tour.delete", tourViewModel);
            }
        }
    }

    public void deleteTourLog(TourLogViewModel tourLogViewModel){
        if (tourLogViewModel != null){
            boolean isDeleted = tourManager.deleteTourLog(tourLogViewModel.getTourLogId().getValue());
            if (isDeleted){
                eventManager.notify("tour-log.delete", tourLogViewModel);
            }
        }
    }

    /**
     * Reloads all tours
     */
    public void loadTours(){
        tours.clear();
        for (Tour tour : tourManager.getTours()){
            tours.add(new TourViewModel(tour));
        }
    }

    /**
     * Reloads current tour
     */
    public void loadCurrentTour(){
        Tour tour = tourManager.getTour(getCurrentTourId().getValue());
        setCurrentTour(new TourViewModel(tour));
    }

    public void updateCurrentTourLogs(TourViewModel currentTour){
        currentTourLogs.clear();
        for (TourLog tourLog : tourManager.getTourLogsOfTour(currentTour.populateTour())){
            currentTourLogs.add(new TourLogViewModel(tourLog));
        }
    }


    public void loadCurrentTourLogs() {
        Tour tour = tourManager.getTour(getCurrentTourId().getValue());
        this.updateCurrentTourLogs(new TourViewModel(tour));
    }

    public ObservableList<TourLogViewModel> getTourLogsForCharts(TourViewModel currentTour){
        currentTourLogs.clear();
        for (TourLog tourLog : tourManager.getTourLogsOfTour(currentTour.populateTour())){
            currentTourLogs.add(new TourLogViewModel(tourLog));
        }
        return currentTourLogs;
    }

    public double calculateAverageTime(TourViewModel currentTour){
        List<TourLog> tourLogList = tourManager.getTourLogsOfTour(currentTour.populateTour());
        return tourLogList
                .stream()
                .mapToDouble(TourLog::getTotalTime)
                .average()
                .orElseThrow(IllegalStateException::new);
    }

    public void generateTourReport(){
        try {
            tourManager.generateTourReport(currentTour.populateTour());
        } catch (IllegalStateException e) {
            AssertView.reportListError();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void generateReportSummaryStats(){
        tourManager.generateReportSummaryStats();
    }


    public void importTours(File file, boolean deleteAll){
        tourManager.importTours(file, deleteAll);
        loadTours();
    }





}
