package cs151.application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller for the main home page
 * Handles navigation to different sections of the application
 */
public class MainController {
    
    /**
     * Handles navigation to the Define Programming Languages page
     * Triggered when user clicks the "Define Programming Languages" button
     */
    @FXML
    protected void onLanguagesButtonClick(javafx.event.ActionEvent event) {
        try {
            // Get the current stage from the event source
            javafx.scene.Node source = (javafx.scene.Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            
            // Load the languages view
            FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("/cs151/application/languages-view.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load(), 700, 600);
            
            // Set the new scene
            stage.setTitle("Define Programming Languages");
            stage.setScene(scene);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading languages page: " + e.getMessage());
        }
    }
}