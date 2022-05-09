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
    private final StringProperty difficulty;
    @Getter
    private final StringProperty comment;
    @Getter
    private final StringProperty rating;
    @Getter
    private final ObjectProperty<LocalDate> date;
    @Getter
    private final FloatProperty totalTime;

    @Getter
    @Setter
    private Tour tour;


    public TourLogViewModel() {
        this.tourLogId = null;
        this.difficulty = new SimpleStringProperty("");
        this.comment = new SimpleStringProperty("");
        this.rating = new SimpleStringProperty("");
        this.totalTime = new SimpleFloatProperty(0f);
        this.date = new SimpleObjectProperty<>();
    }

    public TourLogViewModel(TourLog tourLog){
        this.tourLogId =  new SimpleIntegerProperty(tourLog.getTourLogId());
        this.difficulty = new SimpleStringProperty(tourLog.getDifficulty());
        this.comment = new SimpleStringProperty(tourLog.getComment());
        this.rating = new SimpleStringProperty(tourLog.getRating());
        this.totalTime = new SimpleFloatProperty(tourLog.getTotalTime());
        this.date = new SimpleObjectProperty<>(tourLog.getDate());
        this.tour = new Tour();
    }

    public int saveTourLog(){
        int result = tourManager.saveTourLog(new TourLog(
                        null,
                date.getValue(),
                difficulty.getValue(),
                comment.getValue(),
                rating.getValue(),
                totalTime.getValue(),
                tour
        ));

        if(result > 0){
            eventManager.notify("tour-log.save", result);
        }
        return result;
    }

    public boolean updateTourLog(){
        TourLog tourLog = new TourLog(
                tourLogId.getValue(),
                date.getValue(),
                difficulty.getValue(),
                comment.getValue(),
                rating.getValue(),
                totalTime.getValue(),
                tour
        );
        boolean result = tourManager.updateTourLog(tourLog);
        if (result){
            eventManager.notify("tour-log.update", tourLog);
        }
        return result;
    }







}
