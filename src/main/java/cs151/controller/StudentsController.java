package cs151.controller;

import cs151.data.LanguageDAO;
import cs151.data.StudentDAO;
import cs151.model.Language;
import cs151.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for the Student Management page
 * Handles CRUD operations for student profiles
 */
public class StudentsController {
    
    @FXML
    private TextField nameField;
    
    @FXML
    private ComboBox<String> academicStatusCombo;
    
    @FXML
    private ListView<String> languagesListView;
    
    @FXML
    private ListView<String> dbSkillsListView;
    
    @FXML
    private TextField roleField;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Button clearButton;
    
    @FXML
    private Button showListButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Label messageLabel;
    
    @FXML
    private Label formTitleLabel;
    
    private final StudentDAO studentDao = new StudentDAO();
    private final LanguageDAO languageDao = new LanguageDAO();
    private Student editingStudent = null; // Track if we're editing
    
    // Available options for multi-select
    private final ObservableList<String> availableLanguages = FXCollections.observableArrayList();
    private final ObservableList<String> availableDbSkills = FXCollections.observableArrayList(
        "MySQL", "PostgreSQL", "MongoDB", "Oracle", "SQL Server", 
        "SQLite", "Redis", "Cassandra", "DynamoDB", "Firebase"
    );
    
    /**
     * Initialize method called automatically by JavaFX
     */
    @FXML
    public void initialize() {
        // Initialize database tables
        studentDao.initTable();
        languageDao.initTable();
        
        // Load available languages from database
        loadAvailableLanguages();
        
        // Set up academic status options
        academicStatusCombo.setItems(FXCollections.observableArrayList(
            "Freshman", "Sophomore", "Junior", "Senior", "Graduate", "PhD"
        ));
        
        // Set up multi-select ListViews with EXTRA LARGE size - 26px font, 100px height, balanced padding
        languagesListView.setItems(availableLanguages);
        languagesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        languagesListView.setCellFactory(lv -> new javafx.scene.control.ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    // Increased height to 100px with balanced padding for full text display
                    setStyle("-fx-font-size: 26px; -fx-padding: 20 20 20 20; -fx-alignment: CENTER_LEFT; -fx-line-spacing: 5px;");
                    setPrefHeight(100);
                    setMinHeight(100);
                    setMaxHeight(100);
                }
            }
        });
        
        dbSkillsListView.setItems(availableDbSkills);
        dbSkillsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        dbSkillsListView.setCellFactory(lv -> new javafx.scene.control.ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    // Increased height to 100px with balanced padding for full text display
                    setStyle("-fx-font-size: 26px; -fx-padding: 20 20 20 20; -fx-alignment: CENTER_LEFT; -fx-line-spacing: 5px;");
                    setPrefHeight(100);
                    setMinHeight(100);
                    setMaxHeight(100);
                }
            }
        });
        
        // Clear message when user starts typing
        nameField.textProperty().addListener((obs, old, val) -> messageLabel.setText(""));
        academicStatusCombo.valueProperty().addListener((obs, old, val) -> messageLabel.setText(""));
        
        // Force adjust all Labels after scene is loaded to fix font metrics issue
        javafx.application.Platform.runLater(() -> {
            if (nameField.getScene() != null) {
                adjustAllLabels(nameField.getScene().getRoot());
            }
        });
    }
    
    /**
     * Recursively adjust all Labels in the scene to fix JavaFX font metrics issue
     */
    private void adjustAllLabels(javafx.scene.Node node) {
        if (node instanceof Label) {
            Label label = (Label) node;
            String text = label.getText();
            if (text != null && !text.isEmpty()) {
                // Force specific font to avoid system font metrics issues
                javafx.scene.text.Font font = javafx.scene.text.Font.font(
                    "System", 
                    label.getFont().getStyle().contains("BOLD") ? javafx.scene.text.FontWeight.BOLD : javafx.scene.text.FontWeight.NORMAL,
                    label.getFont().getSize()
                );
                label.setFont(font);
                
                // For field labels (ending with *)
                if (text.endsWith("*")) {
                    label.setMinHeight(75);
                    label.setPrefHeight(75);
                    label.setPadding(new javafx.geometry.Insets(22, 0, 22, 0));
                }
                // For hint labels (containing parentheses)
                else if (text.contains("(") && text.contains(")")) {
                    label.setMinHeight(50);
                    label.setPrefHeight(50);
                    label.setPadding(new javafx.geometry.Insets(12, 0, 12, 0));
                }
            }
        } else if (node instanceof javafx.scene.Parent) {
            for (javafx.scene.Node child : ((javafx.scene.Parent) node).getChildrenUnmodifiable()) {
                adjustAllLabels(child);
            }
        }
    }
    
    /**
     * Load available languages from database
     */
    private void loadAvailableLanguages() {
        availableLanguages.clear();
        List<Language> languages = languageDao.getAllLanguages();
        // Sort alphabetically
        languages.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        for (Language lang : languages) {
            availableLanguages.add(lang.getName());
        }
    }
    
    /**
     * Handle Show List button click - Display students in a dialog
     */
    @FXML
    protected void onShowListButtonClick() {
        // Create a dialog
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Student List");
        dialog.setHeaderText("All Students (Sorted A-Z)");
        
        // Create TableView for the dialog
        TableView<Student> studentsTable = new TableView<>();
        studentsTable.setPrefWidth(1200);
        studentsTable.setPrefHeight(500);
        studentsTable.setStyle("-fx-font-size: 15px;");
        studentsTable.setFixedCellSize(45);
        
        // Set up columns
        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(150);
        nameCol.setStyle("-fx-font-size: 15px;");
        
        TableColumn<Student, String> statusCol = new TableColumn<>("Academic Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("academicStatus"));
        statusCol.setPrefWidth(140);
        statusCol.setStyle("-fx-font-size: 15px;");
        
        TableColumn<Student, String> langCol = new TableColumn<>("Languages");
        langCol.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLanguagesAsString())
        );
        langCol.setPrefWidth(200);
        langCol.setStyle("-fx-font-size: 15px;");
        
        TableColumn<Student, String> dbCol = new TableColumn<>("DB Skills");
        dbCol.setCellValueFactory(new PropertyValueFactory<>("dbSkills"));
        dbCol.setPrefWidth(180);
        dbCol.setStyle("-fx-font-size: 15px;");
        
        TableColumn<Student, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setPrefWidth(130);
        roleCol.setStyle("-fx-font-size: 15px;");
        
        // Actions column
        TableColumn<Student, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setPrefWidth(250);
        actionsCol.setCellFactory(column -> new TableCell<>() {
            private final Button viewBtn = new Button("View");
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            
            {
                viewBtn.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    handleView(student);
                });
                
                editBtn.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    handleEdit(student);
                    dialog.close();
                });
                
                deleteBtn.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    handleDelete(student);
                    // Refresh table after deletion
                    ObservableList<Student> students = FXCollections.observableArrayList(
                        studentDao.getAllStudentsSortedByName()
                    );
                    studentsTable.setItems(students);
                });
                
                viewBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 5 10;");
                editBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 5 10;");
                deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 5 10;");
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    javafx.scene.layout.HBox buttons = new javafx.scene.layout.HBox(5);
                    buttons.setAlignment(javafx.geometry.Pos.CENTER);
                    buttons.getChildren().addAll(viewBtn, editBtn, deleteBtn);
                    setGraphic(buttons);
                }
            }
        });
        
        studentsTable.getColumns().addAll(nameCol, statusCol, langCol, dbCol, roleCol, actionsCol);
        
        // Load students
        ObservableList<Student> students = FXCollections.observableArrayList(
            studentDao.getAllStudentsSortedByName()
        );
        studentsTable.setItems(students);
        
        // Sort by name
        studentsTable.getSortOrder().add(nameCol);
        nameCol.setSortType(TableColumn.SortType.ASCENDING);
        studentsTable.sort();
        
        // Create content with student count
        javafx.scene.layout.VBox content = new javafx.scene.layout.VBox(10);
        content.setPadding(new javafx.geometry.Insets(10));
        
        Label countLabel = new Label("Total: " + students.size() + " student" + (students.size() != 1 ? "s" : ""));
        countLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        content.getChildren().addAll(countLabel, studentsTable);
        
        // Set dialog content
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        
        // Show dialog
        dialog.showAndWait();
    }
    
    /**
     * Handle Save button click
     */
    @FXML
    protected void onSaveButtonClick() {
        // Get selected languages
        List<String> selectedLanguages = languagesListView.getSelectionModel().getSelectedItems();
        String languagesStr = String.join(", ", selectedLanguages);
        
        // Get selected DB skills
        List<String> selectedDbSkills = dbSkillsListView.getSelectionModel().getSelectedItems();
        String dbSkillsStr = String.join(", ", selectedDbSkills);
        
        // Validate all required fields
        String name = nameField.getText().trim();
        String academicStatus = academicStatusCombo.getValue();
        String role = roleField.getText().trim();
        String interests = "";
        
        if (name.isEmpty()) {
            showMessage("Error: Full Name is required.", "error");
            return;
        }
        
        if (academicStatus == null || academicStatus.isEmpty()) {
            showMessage("Error: Academic Status is required.", "error");
            return;
        }
        
        if (selectedLanguages.isEmpty()) {
            showMessage("Error: At least one Programming Language must be selected.", "error");
            return;
        }
        
        if (selectedDbSkills.isEmpty()) {
            showMessage("Error: At least one Database Skill must be selected.", "error");
            return;
        }
        
        if (role.isEmpty()) {
            showMessage("Error: Role is required.", "error");
            return;
        }
        
        
        // Create or update student
        Student student;
        
        if (editingStudent != null) {
            // Editing existing student
            student = editingStudent;
        } else {
            // Creating new student
            student = new Student(name, academicStatus);
        }
        
        // Set all fields
        student.setName(name);
        student.setAcademicStatus(academicStatus);
        student.setEmail(null); // No email field
        student.setLanguagesFromString(languagesStr);
        student.setDbSkills(dbSkillsStr);
        student.setRole(role);
        student.setInterests(interests);
        
        // Save to database
        boolean success;
        if (editingStudent != null) {
            success = studentDao.updateStudent(student);
            if (success) {
                showMessage("Student '" + student.getName() + "' updated successfully!", "success");
            } else {
                showMessage("Failed to update student.", "error");
                return;
            }
        } else {
            success = studentDao.saveStudent(student);
            if (success) {
                showMessage("Student '" + student.getName() + "' created successfully!", "success");
            } else {
                showMessage("Failed to create student.", "error");
                return;
            }
        }
        
        // Clear form
        clearForm();
    }
    
    /**
     * Handle Clear button click
     */
    @FXML
    protected void onClearButtonClick() {
        clearForm();
        showMessage("Form cleared.", "success");
    }
    
    /**
     * Clear the form
     */
    private void clearForm() {
        nameField.clear();
        academicStatusCombo.setValue(null);
        languagesListView.getSelectionModel().clearSelection();
        dbSkillsListView.getSelectionModel().clearSelection();
        roleField.clear();
        messageLabel.setText("");
        editingStudent = null;
        formTitleLabel.setText("Create New Student Profile");
        saveButton.setText("Save Student");
    }
    
    /**
     * Handle Edit button click
     */
    private void handleEdit(Student student) {
        editingStudent = student;
        
        // Populate form with student data
        nameField.setText(student.getName());
        academicStatusCombo.setValue(student.getAcademicStatus());
        
        // Select languages
        languagesListView.getSelectionModel().clearSelection();
        List<String> studentLanguages = student.getLanguages();
        for (String lang : studentLanguages) {
            int index = availableLanguages.indexOf(lang);
            if (index >= 0) {
                languagesListView.getSelectionModel().select(index);
            }
        }
        
        // Select DB skills
        dbSkillsListView.getSelectionModel().clearSelection();
        if (student.getDbSkills() != null && !student.getDbSkills().isEmpty()) {
            String[] skills = student.getDbSkills().split(",");
            for (String skill : skills) {
                String trimmedSkill = skill.trim();
                int index = availableDbSkills.indexOf(trimmedSkill);
                if (index >= 0) {
                    dbSkillsListView.getSelectionModel().select(index);
                }
            }
        }
        
        roleField.setText(student.getRole() != null ? student.getRole() : "");
        
        // Update form title
        formTitleLabel.setText("Edit Student Profile: " + student.getName());
        saveButton.setText("Update Student");
        
        // Show message
        showMessage("Editing student. Update the fields and click 'Update Student'.", "success");
    }
    
    /**
     * Handle Delete button click
     */
    private void handleDelete(Student student) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Student");
        alert.setHeaderText("Delete Student Profile");
        alert.setContentText("Are you sure you want to delete '" + student.getName() + "'?\nThis action cannot be undone.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = studentDao.deleteStudent(student.getId());
                if (success) {
                    showMessage("Student '" + student.getName() + "' deleted successfully!", "success");
                    
                    // Clear form if we were editing this student
                    if (editingStudent != null && editingStudent.getId() == student.getId()) {
                        clearForm();
                    }
                } else {
                    showMessage("Failed to delete student.", "error");
                }
            }
        });
    }
    
    /**
     * Handle View button click - show detailed information
     */
    private void handleView(Student student) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Student Details");
        alert.setHeaderText("Student Profile: " + student.getName());
        
        StringBuilder details = new StringBuilder();
        details.append("ID: ").append(student.getId()).append("\n");
        details.append("Name: ").append(student.getName()).append("\n");
        details.append("Academic Status: ").append(student.getAcademicStatus()).append("\n");
        details.append("Languages: ").append(!student.getLanguagesAsString().isEmpty() ? student.getLanguagesAsString() : "N/A").append("\n");
        details.append("DB Skills: ").append(student.getDbSkills() != null ? student.getDbSkills() : "N/A").append("\n");
        details.append("Role: ").append(student.getRole() != null ? student.getRole() : "N/A").append("\n");
        
        alert.setContentText(details.toString());
        alert.showAndWait();
    }
    
    /**
     * Display a message to the user
     */
    private void showMessage(String message, String type) {
        messageLabel.setText(message);
        if (type.equals("success")) {
            messageLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 14px;");
        } else {
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 14px;");
        }
    }
    
    /**
     * Handle Back button click
     */
    @FXML
    protected void onBackButtonClick() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/cs151/application/hello-view.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 900, 800);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error returning to home page: " + e.getMessage(), "error");
        }
    }
}
