package com.example.tourplanner.models;

import lombok.*;

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
    private String estimatedTime;
    private String routeInformationImageURL;

    public Tour(Integer tourId, String tourName, String tourDescription, String from, String to, String transportType,
                Float distance, String estimatedTime) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.tourDescription = tourDescription;
        this.from = from;
        this.to = to;
        this.transportType = transportType;
        this.distance = distance;
        this.estimatedTime = estimatedTime;
    }

    public Tour(Float distance, String estimatedTime, String routeInformationImageURL) {
        this.distance = distance;
        this.estimatedTime = estimatedTime;
        this.routeInformationImageURL = routeInformationImageURL;
    }

    @Override
    public String toString() {
        return tourName;
    }
}
