package cs151.controller;

import cs151.application.Main;
import cs151.data.StudentDAO;
import cs151.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for Reports View
 * Handles displaying and filtering student reports by flag status
 */
public class ReportsController {

    @FXML
    private Button backButton;

    @FXML
    private RadioButton whitelistRadio;

    @FXML
    private RadioButton blacklistRadio;

    @FXML
    private RadioButton allRadio;

    @FXML
    private TableView<Student> studentsTable;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> academicStatusColumn;

    @FXML
    private TableColumn<Student, String> languagesColumn;

    @FXML
    private TableColumn<Student, String> dbSkillsColumn;

    @FXML
    private TableColumn<Student, String> roleColumn;

    @FXML
    private TableColumn<Student, String> flagColumn;

    @FXML
    private Label countLabel;

    private final StudentDAO studentDao = new StudentDAO();
    private ToggleGroup filterGroup;

    @FXML
    public void initialize() {
        // Setup toggle group for radio buttons
        filterGroup = new ToggleGroup();
        whitelistRadio.setToggleGroup(filterGroup);
        blacklistRadio.setToggleGroup(filterGroup);
        allRadio.setToggleGroup(filterGroup);

        // Initialize table columns
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        academicStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAcademicStatus()));
        languagesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLanguagesAsString()));
        dbSkillsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDbSkills()));
        roleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole()));
        flagColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFlag()));

        // Style flag column
        flagColumn.setCellFactory(column -> new TableCell<Student, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("Whitelist".equals(item)) {
                        setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    } else if ("Blacklist".equals(item)) {
                        setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        // Handle double-click on row to show detailed profile
        studentsTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    showStudentProfileReport(row.getItem());
                }
            });
            return row;
        });

        // Load all students by default
        loadStudents("All");
    }

    @FXML
    private void onFilterChange() {
        RadioButton selected = (RadioButton) filterGroup.getSelectedToggle();
        if (selected == whitelistRadio) {
            loadStudents("Whitelist");
        } else if (selected == blacklistRadio) {
            loadStudents("Blacklist");
        } else {
            loadStudents("All");
        }
    }

    private void loadStudents(String filter) {
        List<Student> allStudents = studentDao.getAllStudentsSortedByName();
        List<Student> filteredStudents;

        if ("All".equals(filter)) {
            filteredStudents = allStudents;
        } else {
            filteredStudents = allStudents.stream()
                    .filter(s -> filter.equals(s.getFlag()))
                    .collect(Collectors.toList());
        }

        studentsTable.setItems(FXCollections.observableArrayList(filteredStudents));
        countLabel.setText("Total: " + filteredStudents.size() + " student" + 
                          (filteredStudents.size() != 1 ? "s" : ""));
    }

    private void showStudentProfileReport(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("student-profile-report-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 900, 700));
            stage.setTitle("Student Profile Report - " + student.getName());

            StudentProfileReportController controller = loader.getController();
            controller.setStudent(student);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load student profile report.");
        }
    }

    @FXML
    private void onBackButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 800);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to return to home page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

