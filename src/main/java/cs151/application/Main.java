package cs151.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main application class for Student Information Management System
 * Entry point for the JavaFX application
 * 
 * @version 0.2
 * @team 23
 */
public class Main extends Application {
    
    /**
     * Starts the JavaFX application
     * Loads the home page as the initial view
     * 
     * @param stage The primary stage for this application
     * @throws IOException If the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/cs151/view/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 800);
        stage.setTitle("Student Information Management System - v0.5");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(800);
        stage.setMinHeight(700);
        stage.show();
    }

    /**
     * Main method - entry point of the application
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}