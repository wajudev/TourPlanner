package com.example.tourplanner.business.mapQuest;

import java.io.IOException;

import com.example.tourplanner.business.ConfigurationManager;
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
    private HttpClient client = HttpClient.newBuilder().build();

    private static final int MAP_WIDTH = 300;
    private static final int MAP_HEIGHT = 240;

   public String sendRequest(String from, String to){

        URI resourceUrl =URI.create("http://mapquestapi.com/directions/v2/route?key=" +
                ConfigurationManager.getConfigProperty("MapQuestAPIKey") + "&from=" + from + "&to=" + to);


        HttpRequest request = HttpRequest.newBuilder(resourceUrl).build();
        try {
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
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

            return createImageStringURL(sessionId,lrLng,lrLat,ulLng,ulLat);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*public String sendRequest(String from, String to){

        URI resourceUrl =URI.create("http://mapquestapi.com/directions/v2/route?key=" +
                ConfigurationManager.getConfigProperty("MapQuestAPIKey") + "&from=" + from + "&to=" + to);


        HttpRequest request = HttpRequest.newBuilder(resourceUrl).build();
        CompletableFuture<HttpResponse<byte[]>> future = client.sendAsync(request,HttpResponse.BodyHandlers.ofByteArray());
        future.thenApply(httpResponse -> httpResponse.body()).thenAccept(imageData -> createImageStringURL("t","t","t","t","t"));
        try {
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
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

            return createImageStringURL(sessionId,lrLng,lrLat,ulLng,ulLat);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }*/

    private String createImageStringURL(String sessionId, String lrLng, String lrLat, String ulLng, String ulLat){
        String imageUrl ="https://www.mapquestapi.com/staticmap/v5/map?size="+MAP_WIDTH+","
                +MAP_HEIGHT+"&key="+ConfigurationManager.getConfigProperty("MapQuestAPIKey")+
                "&session="+ sessionId+"&boundingBox="
                +ulLat+","+ulLng+","+lrLat+","+lrLng;
        return imageUrl;
    }

}
