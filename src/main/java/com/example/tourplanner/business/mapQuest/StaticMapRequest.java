package com.example.tourplanner.business.mapQuest;

import java.io.IOException;

import com.example.tourplanner.business.ConfigurationManager;
import com.example.tourplanner.models.Tour;
import javafx.scene.image.Image;
import lombok.Getter;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class StaticMapRequest {
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
    private final HttpClient client = HttpClient.newBuilder().build();

    private static final char UNIT_IN_KILOMETER = 'k';

    private static final int MAP_WIDTH = 300;
    private static final int MAP_HEIGHT = 240;


    public Tour sendRequest(String from, String to, String transportType) {

        URI resourceUrl = URI.create("http://mapquestapi.com/directions/v2/route?key=" +
                ConfigurationManager.getConfigProperty("MapQuestAPIKey") + "&from=" + from + "&to=" + to
                + "&unit=" + UNIT_IN_KILOMETER + "&routeType=" + transportType);


        HttpRequest request = HttpRequest.newBuilder(resourceUrl).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            JSONObject obj = (JSONObject) json.get("route");

            sessionId = getSessionID(obj);

            JSONObject boundingBox = (JSONObject) obj.get("boundingBox");
            JSONObject lr = (JSONObject) boundingBox.get("lr");
            lrLng = lr.get("lng").toString();
            lrLat = lr.get("lat").toString();


            JSONObject ul = (JSONObject) boundingBox.get("ul");
            ulLng = ul.get("lng").toString();
            ulLat = ul.get("lat").toString();

            return new Tour(getDistance(obj), getEstimatedTime(obj), createImageStringURL(sessionId, lrLng, lrLat, ulLng, ulLat));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Tour sendAsyncRequest(String from, String to, String transportType) {

        URI resourceUrl = URI.create("http://mapquestapi.com/directions/v2/route?key=" +
                ConfigurationManager.getConfigProperty("MapQuestAPIKey") + "&from=" + from + "&to=" + to
                + "&unit=" + UNIT_IN_KILOMETER + "&routeType=" + transportType + "&manMaps=false");


        HttpRequest request = HttpRequest.newBuilder(resourceUrl).build();
        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        future.thenApply(httpResponse -> httpResponse.body());



        //HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> response = future.join();


        JSONObject json = new JSONObject(response.body());
        JSONObject obj = (JSONObject) json.get("route");


        sessionId = obj.get("sessionId").toString();

        JSONObject boundingBox = (JSONObject) obj.get("boundingBox");
        JSONObject lr = (JSONObject) boundingBox.get("lr");
        lrLng = lr.get("lng").toString();
        lrLat = lr.get("lat").toString();



        JSONObject ul = (JSONObject) boundingBox.get("ul");
        ulLng = ul.get("lng").toString();
        ulLat = ul.get("lat").toString();

        return new Tour(getDistance(obj), getEstimatedTime(obj), createImageStringURL(sessionId, lrLng, lrLat, ulLng, ulLat));

    }

    private String createImageStringURL(String sessionId, String lrLng, String lrLat, String ulLng, String ulLat) {
        return "https://www.mapquestapi.com/staticmap/v5/map?size=" + MAP_WIDTH + ","
                + MAP_HEIGHT + "&key=" + ConfigurationManager.getConfigProperty("MapQuestAPIKey") +
                "&session=" + sessionId + "&boundingBox="
                + ulLat + "," + ulLng + "," + lrLat + "," + lrLng;
    }

    private String getEstimatedTime(JSONObject route) {
        return route.get("formattedTime").toString();
    }

    private String getSessionID(JSONObject route) {
        return route.get("sessionId").toString();
    }

    private Float getDistance(JSONObject route) {
        //Rounded to 2 decimal places
        return Math.round(Float.parseFloat(route.get("distance").
                toString()) * 100.0) / 100.0f;
    }

}
