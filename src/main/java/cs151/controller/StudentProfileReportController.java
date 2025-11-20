package cs151.controller;

import cs151.application.Main;
import cs151.data.CommentDAO;
import cs151.model.Comment;
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

/**
 * Controller for Student Profile Report View
 * Shows student information and their comments in tabular format
 */
public class StudentProfileReportController {

    @FXML
    private Button backButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label academicStatusLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label languagesLabel;

    @FXML
    private Label dbSkillsLabel;

    @FXML
    private Label roleLabel;

    @FXML
    private Label flagLabel;

    @FXML
    private TableView<Comment> commentsTable;

    @FXML
    private TableColumn<Comment, String> dateColumn;

    @FXML
    private TableColumn<Comment, String> commentColumn;

    @FXML
    private Label commentCountLabel;

    private final CommentDAO commentDao = new CommentDAO();
    private Student currentStudent;

    @FXML
    public void initialize() {
        // Initialize table columns
        dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDateAsString()));
        
        // Show excerpt of comment (first 100 characters)
        commentColumn.setCellValueFactory(data -> {
            String fullComment = data.getValue().getContent();
            String excerpt = fullComment.length() > 100 ? 
                            fullComment.substring(0, 100) + "..." : fullComment;
            return new SimpleStringProperty(excerpt);
        });

        // Handle click on comment row to show full content
        commentsTable.setRowFactory(tv -> {
            TableRow<Comment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    showCommentDetail(row.getItem());
                }
            });
            return row;
        });
    }

    /**
     * Set the student whose profile will be displayed
     */
    public void setStudent(Student student) {
        this.currentStudent = student;
        
        // Display student information
        nameLabel.setText(student.getName());
        academicStatusLabel.setText(student.getAcademicStatus());
        emailLabel.setText(student.getEmail() != null ? student.getEmail() : "N/A");
        languagesLabel.setText(student.getLanguagesAsString());
        dbSkillsLabel.setText(student.getDbSkills() != null ? student.getDbSkills() : "N/A");
        roleLabel.setText(student.getRole() != null ? student.getRole() : "N/A");
        
        // Style flag label
        String flag = student.getFlag();
        flagLabel.setText(flag != null ? flag : "N/A");
        if ("Whitelist".equals(flag)) {
            flagLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold; -fx-font-size: 15px;");
        } else if ("Blacklist".equals(flag)) {
            flagLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-font-size: 15px;");
        }
        
        // Load and display comments
        loadComments();
    }

    private void loadComments() {
        List<Comment> comments = commentDao.getCommentsByStudentId(currentStudent.getId());
        commentsTable.setItems(FXCollections.observableArrayList(comments));
        commentCountLabel.setText(comments.size() + " comment" + (comments.size() != 1 ? "s" : ""));
    }

    private void showCommentDetail(Comment comment) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("comment-detail-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 600, 450));
            stage.setTitle("Comment Detail");

            CommentDetailController controller = loader.getController();
            controller.setComment(comment);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load comment detail.");
        }
    }

    @FXML
    private void onBackButtonClick() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

