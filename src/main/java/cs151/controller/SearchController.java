package cs151.controller;

import cs151.data.StudentDAO;
import cs151.model.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class SearchController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Student> studentTable;

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
    private TableColumn<Student, Void> actionColumn;

    @FXML
    private Button backButton;

    @FXML
    private Label messageLabel;

    private final StudentDAO studentDAO = new StudentDAO();

    @FXML
    public void initialize() {
        studentDAO.initTable();

        // Initialize data columns
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        academicStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAcademicStatus()));
        languagesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLanguagesAsString()));
        dbSkillsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDbSkills()));
        roleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole()));

        // Add action column to hold Delete
        addDeleteButtonToTable();

        // Start with empty table
        studentTable.setItems(FXCollections.observableArrayList());

        Label placeholder = new Label("Start typing to search for a student...");
        placeholder.setStyle("-fx-text-fill: gray; -fx-font-style: italic; -fx-font-size: 14px;");
        studentTable.setPlaceholder(placeholder);


        // Live search: Results appear as user type
        searchField.textProperty().addListener((obs, oldVal, newVal) -> searchStudents(newVal));
    }

    private void addDeleteButtonToTable() {
        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button deleteBtn = new Button("Delete");

            {
                deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 5 10;");
                deleteBtn.setOnAction(event -> {
                    Student student = getTableView().getItems().get(getIndex());
                    handleDelete(student);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(deleteBtn);
                    container.setSpacing(5);
                    setGraphic(container);
                }
            }
        });
    }

    private void searchStudents(String query) {
        if (query == null || query.trim().isEmpty()) {
            studentTable.setItems(FXCollections.observableArrayList());
            studentTable.setPlaceholder(new Label("Start typing to search for a student..."));
            return;
        }

        List<Student> students = studentDAO.getAllStudents("name", "ASC");
        String lowerQuery = query.toLowerCase();

        List<Student> filtered = students.stream()
                .filter(s ->
                        (s.getName() != null && s.getName().toLowerCase().contains(lowerQuery)) ||
                                (s.getAcademicStatus() != null && s.getAcademicStatus().toLowerCase().contains(lowerQuery)) ||
                                (s.getDbSkills() != null && s.getDbSkills().toLowerCase().contains(lowerQuery)) ||
                                (s.getRole() != null && s.getRole().toLowerCase().contains(lowerQuery)) ||
                                (s.getLanguagesAsString() != null && s.getLanguagesAsString().toLowerCase().contains(lowerQuery))
                )
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            studentTable.setPlaceholder(new Label("No students found matching your search."));
        }

        studentTable.setItems(FXCollections.observableArrayList(filtered));
    }

    @FXML
    private void onSearchClicked() {
        searchStudents(searchField.getText());
    }

    private void handleDelete(Student student) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText("Delete Student");
        confirm.setContentText("Are you sure you want to delete \"" + student.getName() + "\"?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean deleted = studentDAO.deleteStudent(student.getId());
                if (deleted) {
                    searchStudents(searchField.getText()); // refresh table
                }
            }
        });
    }

    @FXML
    private void onBackButtonClick() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/cs151/application/hello-view.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 900, 800);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error returning to home page: " + e.getMessage());
        }
    }
}
