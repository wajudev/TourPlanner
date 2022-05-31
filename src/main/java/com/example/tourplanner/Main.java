package com.example.tourplanner;

import com.example.tourplanner.dal.intefaces.DalFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main extends Application {
    private static final Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Starting Application...");
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent, 800, 500);
        stage.setTitle("Tour Planner");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        DalFactory.init();
        launch();
        System.out.println(logger.getName());
        doSomething();
    }

    @Override
    public void stop(){
        logger.info("Stopping Application...");
    }

    private static void doSomething(){
        OtherApp app = new OtherApp();
        logger.info("In function doSomething....");
        logger.debug("In function doSomething....DEBUG");
        app.test();

    }


}