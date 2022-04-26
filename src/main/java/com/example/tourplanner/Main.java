package com.example.tourplanner;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.show();
    }

    private static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        //launch();
        System.out.println(logger.getName());
        doSomething();
    }

    private static void doSomething(){
        OtherApp app = new OtherApp();
        logger.info("In function doSomething....");
        logger.debug("In function doSomething....DEBUG");
        app.test();

    }


}