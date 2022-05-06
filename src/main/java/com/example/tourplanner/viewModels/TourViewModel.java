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
    private final IntegerProperty id;

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
    private final IntegerProperty estimatedTime;


    public TourViewModel(){
        this.id = null;
        this.name = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.from = new SimpleStringProperty("");
        this.to = new SimpleStringProperty("");
        this.transportType = new SimpleStringProperty("");
        this.distance = new SimpleFloatProperty(0);
        this.estimatedTime = new SimpleIntegerProperty(0);
    }

    public TourViewModel(Tour tour) {
        this.id = new SimpleIntegerProperty(tour.getTourId());
        this.name = new SimpleStringProperty(tour.getTourName());
        this.description = new SimpleStringProperty(tour.getTourDescription());
        this.from = new SimpleStringProperty(tour.getFrom());
        this.to = new SimpleStringProperty(tour.getTo());
        this.transportType = new SimpleStringProperty(tour.getTransportType());
        this.distance = new SimpleFloatProperty(tour.getDistance());
        this.estimatedTime = new SimpleIntegerProperty(tour.getEstimatedTime());
    }

    @Override
    public String toString() {
        return name.getValue();
    }

    public int saveTour() {
        int result = tourManager.saveTour(new Tour(null, name.getValue(), description.getValue(), from.getValue(), to.getValue(), transportType.getValue(), 23f, 33));
        if (result > 0){
            eventManager.notify("tour.save", result);
        }
        return result;
    }
}
