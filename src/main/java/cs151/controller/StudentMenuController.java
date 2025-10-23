package cs151.controller;

import java.net.URL;
import java.util.ResourceBundle;

import cs151.data.LanguageDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class StudentMenuController {

	@FXML
	private void onAddStudentClick(ActionEvent event) {
	    LanguageDAO languageDAO = new LanguageDAO();
	    if (languageDAO.getAllLanguages().isEmpty()) {
	        Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("Missing Language Skills");
	        alert.setHeaderText("No Languages Found");
	        alert.setContentText("Please enter Language Skills first before adding a student.");
	        alert.showAndWait();
	        return;
	    }

	    openFixedWindow("/cs151/view/add-student.fxml", "Add Student");
	}

    @FXML
    private void onSearchStudentClick(ActionEvent event) {
        openFixedWindow("/cs151/view/search-student.fxml", "Search Student Profiles");
    }

    @FXML
    private void onStudentTableClick(ActionEvent event) {
        openFixedWindow("/cs151/view/student-table.fxml", "Student Table");
    }

    private void openFixedWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.err.println("Error opening window: " + title);
            e.printStackTrace();
        }
    }
}
