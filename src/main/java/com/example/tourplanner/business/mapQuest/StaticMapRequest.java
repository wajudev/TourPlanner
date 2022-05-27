package com.example.tourplanner.business.mapQuest;

import com.example.tourplanner.business.ConfigurationManager;
import com.example.tourplanner.models.Tour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class StaticMapRequest {

    private static final HttpClient client = HttpClient.newBuilder().build();

    private static final char UNIT_IN_KILOMETER = 'k';

    private static final int MAP_WIDTH = 300;
    private static final int MAP_HEIGHT = 240;

    private static Tour tour;

    static final Logger logger = LogManager.getLogger(StaticMapRequest.class);



    public static Tour getImageRequest(String from, String to, String transportType) {
         String lrLng;
         String lrLat;
         String ulLng;
         String ulLat;
         String sessionId;

        URI resourceUrl = URI.create("http://mapquestapi.com/directions/v2/route?key=" +
                ConfigurationManager.getConfigProperty("MapQuestAPIKey") + "&from=" + from + "&to=" + to
                + "&unit=" + UNIT_IN_KILOMETER + "&routeType=" + transportType + "&manMaps=false");


        HttpRequest request = HttpRequest.newBuilder(resourceUrl).build();


        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        future.thenApply(response -> response.body()).thenAccept(result ->{

        } );
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

    private static String createImageStringURL(String sessionId, String lrLng, String lrLat, String ulLng, String ulLat) {
        return "https://www.mapquestapi.com/staticmap/v5/map?size=" + MAP_WIDTH + ","
                + MAP_HEIGHT + "&key=" + ConfigurationManager.getConfigProperty("MapQuestAPIKey") +
                "&session=" + sessionId + "&boundingBox="
                + ulLat + "," + ulLng + "," + lrLat + "," + lrLng;
    }

    public static boolean checkError(String from, String to, String transportType) {

        HttpResponse<String> response = null;

        URI resourceUrl = URI.create("http://mapquestapi.com/directions/v2/route?key=" +
                ConfigurationManager.getConfigProperty("MapQuestAPIKey") + "&from=" + from + "&to=" + to
                + "&unit=" + UNIT_IN_KILOMETER + "&routeType=" + transportType + "&manMaps=false");

        HttpRequest request = HttpRequest.newBuilder(resourceUrl).build();
        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        future.thenApply(httpResponse -> httpResponse.body());

        response = future.join();

        try {
            JSONObject json = new JSONObject(response.body());
            JSONObject obj = (JSONObject) json.get("route");
            obj.get("sessionId");
        }catch (JSONException e){
            logger.error("No such route");
            return false;
        }
        return true;
    }

    public static void doJSON(HttpResponse<String> response){
        String lrLng;
        String lrLat;
        String ulLng;
        String ulLat;
        String sessionId;

        long tine = System.nanoTime();
        System.out.println((System.nanoTime()-tine)/1000000);


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
        tour=new Tour(getDistance(obj), getEstimatedTime(obj), createImageStringURL(sessionId, lrLng, lrLat, ulLng, ulLat));
    }


    private static String getEstimatedTime(JSONObject route) {
        return route.get("formattedTime").toString();
    }

    private static String getSessionID(JSONObject route) {
        return route.get("sessionId").toString();
    }

    private static Float getDistance(JSONObject route) {
        //Rounded to 2 decimal places
        return Math.round(Float.parseFloat(route.get("distance").
                toString()) * 100.0) / 100.0f;
    }

}
