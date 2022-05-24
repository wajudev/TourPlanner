package com.example.tourplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TourLog {
    private Integer tourLogId;
    private LocalDate date;
    private String difficulty;
    private Float rating;
    private String totalTime;
    private String comment;
    private Tour tour;

}
