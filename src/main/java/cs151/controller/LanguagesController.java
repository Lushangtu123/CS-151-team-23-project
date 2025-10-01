package cs151.controller;

import cs151.model.Language;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller for the Define Programming Languages page
 * Handles CRUD operations for programming languages
 */
public class LanguagesController {
    
    @FXML
    private TextField languageNameField;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private TableView<Language> languagesTable;
    
    @FXML
    private TableColumn<Language, String> nameColumn;
    
    @FXML
    private TableColumn<Language, Void> actionsColumn;
    
    @FXML
    private Label messageLabel;
    
    // Observable list to hold languages
    private ObservableList<Language> languagesList = FXCollections.observableArrayList();
    
    // Counter for generating IDs (in-memory only for v0.2)
    private int nextId = 1;
    
    /**
     * Initialize method called automatically by JavaFX
     * Sets up the table and event handlers
     */
    @FXML
    public void initialize() {
        // Set up the table column
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        // Set up the actions column with Edit and Delete buttons
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            
            {
                editButton.setOnAction(event -> {
                    Language language = getTableView().getItems().get(getIndex());
                    handleEdit(language);
                });
                
                deleteButton.setOnAction(event -> {
                    Language language = getTableView().getItems().get(getIndex());
                    handleDelete(language);
                });
                
                editButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    javafx.scene.layout.HBox buttons = new javafx.scene.layout.HBox(5);
                    buttons.getChildren().addAll(editButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });
        
        // Bind the table to the observable list
        languagesTable.setItems(languagesList);
        
        // Clear message when user starts typing
        languageNameField.textProperty().addListener((obs, oldVal, newVal) -> {
            messageLabel.setText("");
        });
    }
    
    /**
     * Handles the Save button click
     * Validates and adds/updates a language
     */
    @FXML
    protected void onSaveButtonClick() {
        String languageName = languageNameField.getText().trim();
        
        // Validation: Check if name is empty
        if (languageName.isEmpty()) {
            showMessage("Please add a language", "error");
            return;
        }
        
        // Validation: Check if name already exists
        if (isLanguageExists(languageName)) {
            showMessage("Language '" + languageName + "' already exists!", "error");
            return;
        }
        
        // Create and add the new language
        Language newLanguage = new Language(nextId++, languageName);
        languagesList.add(newLanguage);
        
        // Clear the input field
        languageNameField.clear();
        
        // Show success message
        showMessage("Language '" + languageName + "' added successfully!", "success");
    }
    
    /**
     * Handles editing a language
     * @param language The language to edit
     */
    private void handleEdit(Language language) {
        // Create a dialog for editing
        TextInputDialog dialog = new TextInputDialog(language.getName());
        dialog.setTitle("Edit Language");
        dialog.setHeaderText("Edit Programming Language");
        dialog.setContentText("Language Name:");
        
        dialog.showAndWait().ifPresent(newName -> {
            newName = newName.trim();
            
            // Validate new name
            if (newName.isEmpty()) {
                showMessage("Language name cannot be empty!", "error");
                return;
            }
            
            // Check if new name already exists (excluding current language)
            if (!newName.equalsIgnoreCase(language.getName()) && isLanguageExists(newName)) {
                showMessage("Language '" + newName + "' already exists!", "error");
                return;
            }
            
            // Update the language name
            language.setName(newName);
            languagesTable.refresh();
            showMessage("Language updated successfully!", "success");
        });
    }
    
    /**
     * Handles deleting a language
     * @param language The language to delete
     */
    private void handleDelete(Language language) {
        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Language");
        alert.setHeaderText("Delete Programming Language");
        alert.setContentText("Are you sure you want to delete '" + language.getName() + "'?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                languagesList.remove(language);
                showMessage("Language '" + language.getName() + "' deleted successfully!", "success");
            }
        });
    }
    
    /**
     * Checks if a language with the given name already exists
     * @param name The language name to check
     * @return true if exists, false otherwise
     */
    private boolean isLanguageExists(String name) {
        return languagesList.stream()
                .anyMatch(lang -> lang.getName().equalsIgnoreCase(name));
    }
    
    /**
     * Displays a message to the user
     * @param message The message to display
     * @param type The type of message ("success" or "error")
     */
    private void showMessage(String message, String type) {
        messageLabel.setText(message);
        if (type.equals("success")) {
            messageLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else {
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }
    
    /**
     * Handles the Back button click
     * Returns to the home page
     */
    @FXML
    protected void onBackButtonClick() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/cs151/application/hello-view.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 600, 500);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error returning to home page: " + e.getMessage(), "error");
        }
    }
}