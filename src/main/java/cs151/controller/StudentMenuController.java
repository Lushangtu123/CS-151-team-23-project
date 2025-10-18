package cs151.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentMenuController {

    @FXML
    protected void onAddStudentClick() {
        openWrappedWindow("/cs151/application/add-student.fxml", "Add Student");
    }

    @FXML
    protected void onStudentTableClick() {
        openFixedWindow("/cs151/application/student-table.fxml", "Student Table", 900, 700);
    }

    private void openWrappedWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error opening " + title + " window: " + e.getMessage());
        }
    }

    private void openFixedWindow(String fxmlPath, String title, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load(), width, height);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error opening " + title + " window: " + e.getMessage());
        }
    }
}
