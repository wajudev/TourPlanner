package com.example.tourplanner.business.mapQuest;

import com.example.tourplanner.business.ConfigurationManager;
import lombok.Getter;

import java.net.HttpURLConnection;

public class StaticMap {
    @Getter
    private String url;


    private static final int MAP_WIDTH = 600;
    private static final int MAP_HEIGHT = 480;

    public StaticMap(String from, String to) {
        Directions direction = new Directions(from,to);
        url ="https://www.mapquestapi.com/staticmap/v5/map?size="+MAP_WIDTH+","
                +MAP_HEIGHT+"&key="+ConfigurationManager.getConfigProperty("MapQuestAPIKey")+
                "&session="+direction.getSessionId()+"&boundingBox="
                +direction.getUlLat()+","+direction.getUlLng()+","+direction.getLrLat()+","+direction.getLrLng();
    }
}
