package com.example.tourplanner.business.mapQuest;

import java.io.BufferedReader;
import java.io.IOException;

import com.example.tourplanner.business.ConfigurationManager;
import lombok.Getter;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Directions {
    private static HttpURLConnection connection;
    private URL url;

    @Getter
    private String lrLng;
    @Getter
    private String lrLat;
    @Getter
    private String ulLng;
    @Getter
    private String ulLat;

    @Getter
    private String sessionId;

    public Directions(String from, String to) {
        try {
            url = new URL("http://mapquestapi.com/directions/v2/route?key=" +
                    ConfigurationManager.getConfigProperty("MapQuestAPIKey") + "&from=" + from + "&to=" + to);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            System.out.println(responseCode);

            parse();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }


    public void parse() throws IOException, ParseException {
        String inline = "";
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }

        JSONParser parse = new JSONParser();
        JSONObject data_obj = (JSONObject) parse.parse(inline);
        JSONObject obj = (JSONObject) data_obj.get("route");

        sessionId = obj.get("sessionId").toString();

        JSONObject boundingBox = (JSONObject) obj.get("boundingBox");
        JSONObject lr = (JSONObject) boundingBox.get("lr");
        lrLng = lr.get("lng").toString();
        lrLat = lr.get("lat").toString();


        JSONObject ul = (JSONObject) boundingBox.get("ul");
        ulLng = ul.get("lng").toString();
        ulLat = ul.get("lat").toString();
        scanner.close();
    }
}
