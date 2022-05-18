package com.example.tourplanner.models;

import lombok.*;


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
    //private String routeInformationImageURL;

    @Override
    public String toString() {
        return tourName;
    }
}
