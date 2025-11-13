package cs151.controller;

import cs151.controller.services.CommentsHandler;
import cs151.controller.services.StudentsActionsHandler;
import cs151.data.StudentDAO;
import cs151.model.Comment;
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
    private TableColumn<Student, Void> actionsColumn;

    @FXML
    private TableColumn<Student, Void> commentColumn;

    @FXML
    private Button backButton;

    @FXML
    private Label messageLabel;

    private final StudentDAO studentDao = new StudentDAO();
    private final StudentsActionsHandler actionsHandler = new StudentsActionsHandler(studentDao);
    private final CommentsHandler commentsHandler = new CommentsHandler(studentDao);

    @FXML
    public void initialize() {
        studentDao.initTable();

        // Initialize data columns
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        academicStatusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAcademicStatus()));
        languagesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLanguagesAsString()));
        dbSkillsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDbSkills()));
        roleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole()));

        // Add action column to handle actions
        addActionColumnToTable();
        // Add comment column to handle comments actions
        addCommentColumnToTable();
        // Start with empty table
        studentTable.setItems(FXCollections.observableArrayList());

        Label placeholder = new Label("Start typing to search for a student...");
        placeholder.setStyle("-fx-text-fill: gray; -fx-font-style: italic; -fx-font-size: 14px;");
        studentTable.setPlaceholder(placeholder);


        // Live search: Results appear as user type
        searchField.textProperty().addListener((obs, oldVal, newVal) -> searchStudents(newVal));
    }

    private void addActionColumnToTable() {
        actionsColumn.setCellFactory(column -> new TableCell<>() {
            private final Button viewBtn = new Button("View");
            private final Button deleteBtn = new Button("Delete");

            {
                viewBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 5 10;");
                deleteBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 5 10;");

                // Handle view action
                viewBtn.setOnAction(event -> {
                    Student student = getCurrentStudent();
                    actionsHandler.handleView(student, () -> {
                        // Refresh table or clear search query when child window closes
                        searchField.clear();
                        searchStudents("");
                    });
                });

                // Handle delete action
                deleteBtn.setOnAction(event -> {
                    boolean deleted = actionsHandler.handleDelete(getCurrentStudent());
                    if (deleted) searchStudents(searchField.getText());
                });
            }

            private Student getCurrentStudent() {
                return getTableView().getItems().get(getIndex());
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(10, viewBtn, deleteBtn); // spacing = 10
                    container.setStyle("-fx-alignment: center;");
                    setGraphic(container);
                }
            }
        });
    }

    private void addCommentColumnToTable() {
        commentColumn.setCellFactory(column -> new TableCell<>() {
            private final Button viewCommentsBtn = new Button("All Comments");
            //private final Button addCommentBtn = new Button("Add");

            {
                viewCommentsBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 5 10;");
                //addCommentBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 5 10;");

                // Handle View Comments
                viewCommentsBtn.setOnAction(event -> {
                    Student student = getCurrentStudent();
                    commentsHandler.handleViewComments(student);
                });

                /*
                // Handle Add Comment
                addCommentBtn.setOnAction(event -> {
                    Student student = getCurrentStudent();
                    commentsHandler.handleAddComment(student);
                });
                 */
            }

            private Student getCurrentStudent() {
                return getTableView().getItems().get(getIndex());
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(10, viewCommentsBtn);
                    container.setStyle("-fx-alignment: center;");
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

        List<Student> students = studentDao.getAllStudents("name", "ASC");
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

    @FXML
    private void onBackButtonClick() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/cs151/application/hello-view.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 900, 800);
            stage.setScene(scene);
            stage.setTitle("Student Information Management System");
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error returning to home page: " + e.getMessage());
        }
    }
}
