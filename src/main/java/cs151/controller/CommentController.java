package cs151.controller;

import cs151.data.StudentDAO;
import cs151.model.Comment;
import cs151.model.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class CommentController {

    private Student student;
    private StudentDAO studentDao;

    @FXML
    private Label studentNameLabel;

    @FXML
    private ListView<String> commentsList;

    @FXML
    private TextArea newComment;

    @FXML
    private Button backButton;

    public void setStudentDao(StudentDAO studentDao) {
        this.studentDao = studentDao;
    }

    public void setStudent(Student student) {
        this.student = student;
        if (studentNameLabel != null) {
            studentNameLabel.setText(student.getName());
        }

        if (commentsList != null) {
            loadComments();
        }
    }

    private void loadComments() {
        if (student == null || studentDao == null) return;

        List<Comment> comments = studentDao.getCommentsForStudent(student.getId());
        List<String> displayComments = comments.stream()
                .map(Comment::toString)
                .toList();

        commentsList.setItems(FXCollections.observableArrayList(displayComments));
    }

    @FXML
    private void onSaveComment() {
        String commentText = newComment.getText().trim();
        if (commentText.isEmpty()) {
            showAlert("Error", "Comment cannot be empty.");
            return;
        }

        boolean success = studentDao.addComment(student.getId(), commentText);
        if (success) {
            showAlert("Success", "Comment added successfully!");
            closeWindow();
        } else {
            showAlert("Error", "Failed to add comment.");
        }
    }

    @FXML
    private void onClose() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) studentNameLabel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onBackButtonClick() {
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }
}
