package com.example.tourplanner.views;

import com.example.tourplanner.Main;
import com.example.tourplanner.viewModels.TourLogViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.Rating;

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
    private ComboBox difficultyComboBox;
    @FXML
    private Rating tourRating;
    @FXML
    private TextField totalTimeTextField;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private Text titleText;
    public Button finishButton;
    public Button cancelButton;

    private final ObservableList<String> difficultyList = FXCollections.observableArrayList("Easy", "Moderate", "Challenging", "Demanding", "Strenuous");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        titleText.setText("Edit Tour Log");

        datePicker.valueProperty().bindBidirectional(tourLogViewModel.getDate());
        tourRating.ratingProperty().bindBidirectional(tourLogViewModel.getRating());
        totalTimeTextField.textProperty().bindBidirectional(tourLogViewModel.getTotalTime(), new NumberStringConverter());
        commentTextArea.textProperty().bindBidirectional(tourLogViewModel.getComment());

        difficultyComboBox.valueProperty().bindBidirectional(tourLogViewModel.getDifficulty());
        difficultyComboBox.setItems(difficultyList);

        finishButton.setOnAction(this::updateTourLogAction);
        cancelButton.setOnAction(this::cancelEditTourLogAction);
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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tourLog-view.fxml"));
        fxmlLoader.setController(new EditTourLogController());
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
