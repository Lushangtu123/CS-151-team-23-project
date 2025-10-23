package cs151.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import cs151.controller.SearchStudentController;
import cs151.data.StudentDAO;
import cs151.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 * Controller for the main home page.
 * Handles navigation to different sections of the application.
 */
public class MainController {

    @FXML
    protected void onLanguagesButtonClick(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("/cs151/view/languages-view.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load(), 900, 800);

            stage.setTitle("Define Programming Languages");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading languages page: " + e.getMessage());
        }
    }

    @FXML
    protected void onStudentsButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("/cs151/view/student-menu.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);

            Stage newStage = new Stage();
            newStage.setTitle("Student Profile Menu");
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error opening student menu: " + e.getMessage());
        }
    }
 
}
