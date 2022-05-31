module com.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.controlsfx.controls;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires org.json;
    requires java.net.http;
    requires kernel;
    requires layout;
    requires io;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens com.example.tourplanner to javafx.fxml;
    exports com.example.tourplanner;
    exports com.example.tourplanner.models;
    exports com.example.tourplanner.views;
    exports com.example.tourplanner.viewModels;
    opens com.example.tourplanner.views to javafx.fxml;
}