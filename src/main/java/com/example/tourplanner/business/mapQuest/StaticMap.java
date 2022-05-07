package com.example.tourplanner.business.mapQuest;

import com.example.tourplanner.business.ConfigurationManager;
import lombok.Getter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StaticMap {
    private static HttpURLConnection connection;
    @Getter
    private URL url;
    private static final int MAP_WIDTH = 600;
    private static final int MAP_HEIGHT = 480;

    public StaticMap(String from, String to) {
        Directions direction = new Directions(from,to);
        try {
           url = new URL("https://www.mapquestapi.com/staticmap/v5/map?size="+MAP_WIDTH+","
                    +MAP_HEIGHT+"&key="+ConfigurationManager.getConfigProperty("MapQuestAPIKey")+
                    "&session="+direction.getSessionId()+"&boundingBox="
                    +direction.getUlLat()+","+direction.getUlLng()+","+direction.getLrLat()+","+direction.getLrLng());
            System.out.println(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
