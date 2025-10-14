package cs151.controller;

import cs151.data.StudentDAO;
import cs151.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private TextField emailField;
    
    @FXML
    private TextField languagesField;
    
    @FXML
    private TextField dbSkillsField;
    
    @FXML
    private TextField roleField;
    
    @FXML
    private TextArea interestsArea;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Button clearButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private TableView<Student> studentsTable;
    
    @FXML
    private TableColumn<Student, String> nameColumn;
    
    @FXML
    private TableColumn<Student, String> academicStatusColumn;
    
    @FXML
    private TableColumn<Student, String> emailColumn;
    
    @FXML
    private TableColumn<Student, String> languagesColumn;
    
    @FXML
    private TableColumn<Student, String> dbSkillsColumn;
    
    @FXML
    private TableColumn<Student, String> roleColumn;
    
    @FXML
    private TableColumn<Student, Void> actionsColumn;
    
    @FXML
    private Label messageLabel;
    
    @FXML
    private Label formTitleLabel;
    
    @FXML
    private Label studentCountLabel;
    
    private final StudentDAO dao = new StudentDAO();
    private ObservableList<Student> studentsList;
    private Student editingStudent = null; // Track if we're editing
    
    /**
     * Initialize method called automatically by JavaFX
     */
    @FXML
    public void initialize() {
        // Initialize database table
        dao.initTable();
        
        // Set up academic status options
        academicStatusCombo.setItems(FXCollections.observableArrayList(
            "Freshman", "Sophomore", "Junior", "Senior", "Graduate", "PhD"
        ));
        
        // Set up table columns with larger font
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setStyle("-fx-font-size: 16px;");
        academicStatusColumn.setCellValueFactory(new PropertyValueFactory<>("academicStatus"));
        academicStatusColumn.setStyle("-fx-font-size: 16px;");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setStyle("-fx-font-size: 16px;");
        
        // Custom cell value factory for languages (display as comma-separated string)
        languagesColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLanguagesAsString())
        );
        languagesColumn.setStyle("-fx-font-size: 16px;");
        
        dbSkillsColumn.setCellValueFactory(new PropertyValueFactory<>("dbSkills"));
        dbSkillsColumn.setStyle("-fx-font-size: 16px;");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.setStyle("-fx-font-size: 16px;");
        
        // Set up the actions column with Edit and Delete buttons
        actionsColumn.setCellFactory(column -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final Button viewButton = new Button("View");
            
            {
                editButton.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    handleEdit(student);
                });
                
                deleteButton.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    handleDelete(student);
                });
                
                viewButton.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    handleView(student);
                });
                
                editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 12;");
                deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 12;");
                viewButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 12;");
            }
            
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    javafx.scene.layout.HBox buttons = new javafx.scene.layout.HBox(5);
                    buttons.setAlignment(javafx.geometry.Pos.CENTER);
                    buttons.getChildren().addAll(viewButton, editButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });
        
        // Load students from database
        loadStudents();
        
        // Clear message when user starts typing
        nameField.textProperty().addListener((obs, old, val) -> messageLabel.setText(""));
        academicStatusCombo.valueProperty().addListener((obs, old, val) -> messageLabel.setText(""));
    }
    
    /**
     * Load all students from database and display in table
     */
    private void loadStudents() {
        studentsList = FXCollections.observableArrayList(dao.getAllStudentsSortedByName());
        
        studentsTable.setItems(studentsList);
        studentsTable.refresh();
        
        // Sort by name column by default
        studentsTable.getSortOrder().clear();
        studentsTable.getSortOrder().add(nameColumn);
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        studentsTable.sort();
        
        // Update count label
        updateStudentCount();
    }
    
    /**
     * Update the student count label
     */
    private void updateStudentCount() {
        int count = studentsList != null ? studentsList.size() : 0;
        studentCountLabel.setText("Total: " + count + " student" + (count != 1 ? "s" : ""));
    }
    
    /**
     * Handle Save button click
     */
    @FXML
    protected void onSaveButtonClick() {
        // Create student from form data
        Student student;
        
        if (editingStudent != null) {
            // Editing existing student
            student = editingStudent;
        } else {
            // Creating new student
            student = new Student(nameField.getText().trim(), 
                                 academicStatusCombo.getValue());
        }
        
        // Set all fields
        student.setName(nameField.getText().trim());
        student.setAcademicStatus(academicStatusCombo.getValue());
        student.setEmail(emailField.getText().trim());
        student.setLanguagesFromString(languagesField.getText().trim());
        student.setDbSkills(dbSkillsField.getText().trim());
        student.setRole(roleField.getText().trim());
        student.setInterests(interestsArea.getText().trim());
        
        // Validate student
        String validationError = dao.validateStudent(student);
        if (!validationError.isEmpty()) {
            showMessage(validationError, "error");
            return;
        }
        
        // Save to database
        boolean success;
        if (editingStudent != null) {
            success = dao.updateStudent(student);
            if (success) {
                showMessage("Student '" + student.getName() + "' updated successfully!", "success");
            } else {
                showMessage("Failed to update student.", "error");
                return;
            }
        } else {
            success = dao.saveStudent(student);
            if (success) {
                showMessage("Student '" + student.getName() + "' created successfully!", "success");
            } else {
                showMessage("Failed to create student.", "error");
                return;
            }
        }
        
        // Reload table and clear form
        loadStudents();
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
        emailField.clear();
        languagesField.clear();
        dbSkillsField.clear();
        roleField.clear();
        interestsArea.clear();
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
        emailField.setText(student.getEmail() != null ? student.getEmail() : "");
        languagesField.setText(student.getLanguagesAsString());
        dbSkillsField.setText(student.getDbSkills() != null ? student.getDbSkills() : "");
        roleField.setText(student.getRole() != null ? student.getRole() : "");
        interestsArea.setText(student.getInterests() != null ? student.getInterests() : "");
        
        // Update form title
        formTitleLabel.setText("Edit Student Profile: " + student.getName());
        saveButton.setText("Update Student");
        
        // Scroll to top
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
                boolean success = dao.deleteStudent(student.getId());
                if (success) {
                    showMessage("Student '" + student.getName() + "' deleted successfully!", "success");
                    loadStudents();
                    
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
        details.append("Email: ").append(student.getEmail() != null ? student.getEmail() : "N/A").append("\n");
        details.append("Languages: ").append(!student.getLanguagesAsString().isEmpty() ? student.getLanguagesAsString() : "N/A").append("\n");
        details.append("DB Skills: ").append(student.getDbSkills() != null ? student.getDbSkills() : "N/A").append("\n");
        details.append("Role: ").append(student.getRole() != null ? student.getRole() : "N/A").append("\n");
        details.append("Interests: ").append(student.getInterests() != null ? student.getInterests() : "N/A");
        
        alert.setContentText(details.toString());
        alert.showAndWait();
    }
    
    /**
     * Display a message to the user
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
     * Handle Back button click
     */
    @FXML
    protected void onBackButtonClick() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(
                getClass().getResource("/cs151/application/hello-view.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 700, 600);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error returning to home page: " + e.getMessage(), "error");
        }
    }
}

