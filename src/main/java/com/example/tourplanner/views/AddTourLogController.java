package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.viewModels.TourLogViewModel;
import com.example.tourplanner.viewModels.TourViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import lombok.Getter;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTourLogController implements Initializable {
    @Getter
    private static final TourLogViewModel tourLogViewModel = new TourLogViewModel();

    @Getter
    private static TourViewModel tourViewModel = new TourViewModel();

    public static void setTourViewModel(TourViewModel tourViewModel) {
        AddTourLogController.tourViewModel = tourViewModel;
        tourLogViewModel.setTour(tourViewModel.populateTour());
    }

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox difficultyComboBox;
    @FXML
    private Rating tourRating;
    @FXML
    private TextField totalTimeTextField;
    @FXML
    private TextArea commentTextArea;

    public Button finishButton;
    public Button cancelButton;

    private final ObservableList<String> difficultyList = FXCollections.observableArrayList("Easy", "Moderate", "Challenging", "Demanding", "Strenuous");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindPropertyHelper(datePicker, tourLogViewModel, tourRating, totalTimeTextField, commentTextArea, difficultyComboBox, difficultyList);
        difficultyComboBox.getSelectionModel().select(0);

        finishButton.setOnAction(this::addTourLogAction);
        cancelButton.setOnAction(this::cancelTourLogAction);


    }

    public static void bindPropertyHelper(DatePicker datePicker, TourLogViewModel tourLogViewModel, Rating tourRating, TextField totalTimeTextField, TextArea commentTextArea, ComboBox difficultyComboBox, ObservableList<String> difficultyList) {
        datePicker.valueProperty().bindBidirectional(tourLogViewModel.getDate());
        tourRating.ratingProperty().bindBidirectional(tourLogViewModel.getRating());
        totalTimeTextField.textProperty().bindBidirectional(tourLogViewModel.getTotalTime(), new NumberStringConverter());
        commentTextArea.textProperty().bindBidirectional(tourLogViewModel.getComment());

        difficultyComboBox.valueProperty().bindBidirectional(tourLogViewModel.getDifficulty());
        difficultyComboBox.setItems(difficultyList);
    }

    public void addTourLogAction(ActionEvent actionEvent) {
        if (tourLogViewModel.saveTourLog() > 0) {
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        }
    }

    public void cancelTourLogAction(ActionEvent actionEvent){
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }


    public static void openModal(Stage owner, TourViewModel tourViewModel) {
        setTourViewModel(tourViewModel);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tourLog-view.fxml"));
        fxmlLoader.setController(new AddTourLogController());
        Scene scene = null;
        Stage stage= new Stage();
        try {
            scene = new Scene(fxmlLoader.load(), 620, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("add Tour Log");
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
    }

}
