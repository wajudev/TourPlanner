module com.example.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tourplanner to javafx.fxml;
    exports com.example.tourplanner;
    exports com.example.tourplanner.views;
    opens com.example.tourplanner.views to javafx.fxml;
}