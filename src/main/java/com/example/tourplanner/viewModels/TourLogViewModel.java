package com.example.tourplanner.viewModels;

import com.example.tourplanner.business.app.TourManager;
import com.example.tourplanner.business.app.TourManagerImpl;
import com.example.tourplanner.business.events.EventManager;
import com.example.tourplanner.business.events.EventMangerImpl;
import com.example.tourplanner.models.Tour;
import com.example.tourplanner.models.TourLog;
import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class TourLogViewModel {
    private final TourManager tourManager = TourManagerImpl.getInstance();
    private final EventManager eventManager = EventMangerImpl.getInstance();

    @Getter
    private final IntegerProperty tourLogId;
    @Getter
    private final ObjectProperty<LocalDate> date;
    @Getter
    private final StringProperty difficulty;
    @Getter
    private final StringProperty rating;
    @Getter
    private final StringProperty totalTime;
    @Getter
    private final StringProperty comment;

    @Getter
    @Setter
    private Tour tour;

    /**
     * Define getters for the properties
     *
     */
    public ObjectProperty<LocalDate> dateProperty(){
        return date;
    }

    public StringProperty difficultyProperty(){
        return difficulty;
    }

    public StringProperty ratingProperty(){
        return rating;
    }

    public StringProperty totalTimeProperty(){
        return totalTime;
    }

    public StringProperty commentProperty(){
        return comment;
    }


    public TourLogViewModel() {
        this.tourLogId = null;
        this.date = new SimpleObjectProperty<>();
        this.difficulty = new SimpleStringProperty("");
        this.rating = new SimpleStringProperty("");
        this.totalTime = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
    }

    public TourLogViewModel(TourLog tourLog){
        this.tourLogId =  new SimpleIntegerProperty(tourLog.getTourLogId());
        this.date = new SimpleObjectProperty<>(tourLog.getDate());
        this.difficulty = new SimpleStringProperty(tourLog.getDifficulty());
        this.rating = new SimpleStringProperty(tourLog.getRating());
        this.totalTime = new SimpleStringProperty(tourLog.getTotalTime());
        this.comment = new SimpleStringProperty(tourLog.getComment());
        this.tour = tourLog.getTour();
    }

    public int saveTourLog(){
        int result = tourManager.saveTourLog(this.populateTourLog());

        if(result > 0){
            eventManager.notify("tour-log.save", result);
        }
        return result;
    }

    public boolean updateTourLog(){
        TourLog tourLog = this.populateTourLog();
        boolean result = tourManager.updateTourLog(tourLog);
        if (result){
            eventManager.notify("tour-log.update", tourLog);
        }
        return result;
    }

    public TourLog populateTourLog(){
        return  new TourLog(
                tourLogId != null ? tourLogId.getValue() : null,
                date.getValue(),
                difficulty.getValue(),
                rating.getValue(),
                totalTime.getValue(),
                comment.getValue(),
                tour
        );
    }





}
