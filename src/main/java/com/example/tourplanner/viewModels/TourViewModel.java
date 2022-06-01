package com.example.tourplanner.viewModels;

import com.example.tourplanner.business.app.TourManager;
import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.models.Tour;
import javafx.beans.property.*;
import lombok.Getter;

public class TourViewModel {
    private final TourManager tourManager = TourManagerImpl.getInstance();
    private final EventManager eventManager = EventMangerImpl.getInstance();

    @Getter
    private final IntegerProperty tourId;

    @Getter
    private final StringProperty name;

    @Getter
    private final StringProperty description;

    @Getter
    private final StringProperty from;

    @Getter
    private final StringProperty to;

    @Getter
    private final StringProperty transportType;

    @Getter
    private final FloatProperty distance;

    @Getter
    private final StringProperty estimatedTime;

    @Getter
    private final StringProperty image;




    public TourViewModel(){
        this.tourId = null;
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.from = new SimpleStringProperty("");
        this.to = new SimpleStringProperty("");
        this.transportType = new SimpleStringProperty("");
        this.distance = new SimpleFloatProperty(0);
        this.estimatedTime = new SimpleStringProperty("");
        this.image = new SimpleStringProperty("");
    }

    public TourViewModel(Tour tour) {
        this.tourId = new SimpleIntegerProperty(tour.getTourId());
        this.name = new SimpleStringProperty(tour.getTourName());
        this.description = new SimpleStringProperty(tour.getTourDescription());
        this.from = new SimpleStringProperty(tour.getFrom());
        this.to = new SimpleStringProperty(tour.getTo());
        this.transportType = new SimpleStringProperty(tour.getTransportType());
        this.distance = new SimpleFloatProperty(tour.getDistance());
        this.estimatedTime = new SimpleStringProperty(tour.getEstimatedTime());
        this.image = new SimpleStringProperty(tour.getRouteInformationImageURL());
    }

    @Override
    public String toString() {
        return name.getValue();
    }

    public int saveTour() {
        int result = tourManager.saveTour(new Tour(null, name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), distance.getValue(), estimatedTime.getValue()));
        if (result > 0){
            eventManager.notify("tour.save", result);
        }
        return result;
    }

    public boolean updateTour() {
        Tour tour = new Tour(tourId.getValue(), name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), 23f, "");
        boolean result = tourManager.updateTour(tour);
        if (result){
            eventManager.notify("tour.update", true);
        }
        return result;
    }

    /**
     * Populates class objects from database.
     */
    public Tour populateTour() {
        return new Tour(tourId.getValue(), name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), distance.getValue(), estimatedTime.getValue(),image.getValue()) ;
    }

    /**
     * Method to check if a searched string is present in the database.
     * @param searchedValue value to be searched.
     * @return true or false, if present or not.
     */
    public boolean contains(String searchedValue) {
        return tourManager.tourContains(populateTour(), searchedValue, false);
    }
}
