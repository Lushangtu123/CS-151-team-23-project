package cs151.controller.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationHandler {

    /**
     * Closes the current stage (popup or window).
     */
    public void closeWindow(Stage stage) {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Navigates from the current stage to the home page.
     */
    public void goToHome(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 800);
            stage.setScene(scene);
            stage.setTitle("Student Information Management System");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
