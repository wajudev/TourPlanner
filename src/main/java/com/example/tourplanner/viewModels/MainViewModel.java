package com.example.tourplanner.viewModels;

import com.example.tourplanner.business.app.TourManager;
import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.business.events.EventListener;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.business.mapQuest.Directions;
import com.example.tourplanner.business.mapQuest.StaticMap;
import com.example.tourplanner.dal.intefaces.Database;
import com.example.tourplanner.models.Tour;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.image.Image;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainViewModel implements EventListener {


    private final TourManager tourManager = TourManagerImpl.getInstance();
    private final EventManager eventManager = EventMangerImpl.getInstance();
    final Logger logger = LogManager.getLogger(Database.class);

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
    private TourViewModel currentTour;

    public MainViewModel(){
        eventManager.subscribe("tour.save", this);
        eventManager.subscribe("tour.update", this);
        eventManager.subscribe("tour.delete", this);

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
        if (currentTour != null){
            this.currentTourId.setValue(currentTour.getTourId().getValue());
            this.currentTourName.setValue(currentTour.getName().getValue());
            this.currentTourFrom.setValue(currentTour.getFrom().getValue());
            this.currentTourTo.setValue(currentTour.getTo().getValue());
            this.currentTourDescription.setValue(currentTour.getDescription().getValue());
            this.currentTourTransportType.setValue(currentTour.getTransportType().getValue());
            this.currentTourDistance.setValue(String.valueOf(currentTour.getDistance().getValue()));
            this.currentTourEstimatedTime.setValue(String.valueOf(currentTour.getEstimatedTime().getValue()));
            this.currentImage.setValue(new Image(currentTour.getImage().getValue()));
        }
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
    }

    public void deleteTour(TourViewModel tourViewModel){
        if(tourViewModel != null){
            boolean isDeleted = tourManager.deleteTour(tourViewModel.getTourId().getValue());
            if (isDeleted){
                eventManager.notify("tour.delete", tourViewModel);
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
}
