package com.example.tourplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tour {
    private Integer tourId;
    private String tourName;
    private String tourDescription;
    private String from;
    private String to;
    private String transportType;
    private Float distance;
    private Integer estimatedTime;
    private String routeInformationImageURL;

    public Tour(Integer tourId, String tourName, String tourDescription, String from, String to, String transportType,
                Float distance, Integer estimatedTime) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.tourDescription = tourDescription;
        this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
    }

    @Override
    public String toString() {
        return tourName;
    }
}
