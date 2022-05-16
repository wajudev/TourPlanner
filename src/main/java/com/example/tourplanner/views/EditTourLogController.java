package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.viewModels.TourLogViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLogController implements Initializable {
    @Getter
    @Setter
    private static TourLogViewModel tourLogViewModel = new TourLogViewModel();

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField difficultyTextField;
    @FXML
    private TextField ratingTextField;
    @FXML
    private TextField totalTimeTextField;
    @FXML
    private TextArea commentTextArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.valueProperty().bindBidirectional(tourLogViewModel.getDate());
        difficultyTextField.textProperty().bindBidirectional(tourLogViewModel.getDifficulty());
        ratingTextField.textProperty().bindBidirectional(tourLogViewModel.getRating());
        totalTimeTextField.textProperty().bindBidirectional(tourLogViewModel.getTotalTime());
        commentTextArea.textProperty().bindBidirectional(tourLogViewModel.getComment());
    }
    public void cancelEditTourLogAction(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void updateTourLogAction(ActionEvent actionEvent) {
        if (tourLogViewModel.updateTourLog()) {
            Node node = (Node) actionEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Open a new modal owned by owner to edit a tour log.
     *
     * @param owner Top-level container or parent of current node
     */
    public static void openModal(Stage owner, TourLogViewModel tourLogViewModel){
        setTourLogViewModel(tourLogViewModel);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("editTourLog-view.fxml"));
        Scene scene = null;
        Stage stage= new Stage();
        try {
            scene = new Scene(fxmlLoader.load(), 620, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("edit Tour Log");
        stage.initOwner(owner);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
    }
}
