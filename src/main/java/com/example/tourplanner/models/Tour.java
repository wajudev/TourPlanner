package com.example.tourplanner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tour {
    private String tourId;
    private String tourName;
    private String tourDescription;
    private String from;
    private String to;
    private String transportType;
    private float distance;
    private int estimatedTime;
    private String routeInformationImageURL;
}
