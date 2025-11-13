package cs151.controller;

import cs151.data.StudentDAO;
import cs151.model.Comment;
import cs151.model.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class CommentController {

    private Student student;
    private StudentDAO studentDao;

    @FXML
    private Label studentNameLabel;

    @FXML
    private ListView<Comment> commentsList;

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
        commentsList.setItems(FXCollections.observableArrayList(comments));
        commentsList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Comment comment, boolean empty) {
                super.updateItem(comment, empty);
                if (empty || comment == null) {
                    setGraphic(null);
                } else {
                    HBox row = new HBox(10);
                    row.setStyle("-fx-padding: 8; -fx-background-color: #ffffff; -fx-background-radius: 5;");

                    // VBox to stack timestamp and comment
                    VBox commentBox = new VBox(2); // small spacing between date and comment
                    Label dateLabel = new Label(comment.getFormattedTimestamp());
                    dateLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #7f8c8d;"); // smaller gray text
                    Label commentLabel = new Label(comment.getComment());
                    commentLabel.setWrapText(true);
                    commentLabel.setStyle("-fx-font-size: 14px;");

                    commentBox.getChildren().addAll(dateLabel, commentLabel);

                    // Delete button
                    Button deleteBtn = new Button("Delete");
                    deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 5;");
                    deleteBtn.setOnAction(e -> {
                        boolean success = studentDao.deleteComment(comment.getId());
                        if (success) {
                            loadComments();
                        } else {
                            showAlert("Error", "Failed to delete comment.");
                        }
                    });

                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);

                    row.getChildren().addAll(commentBox, spacer, deleteBtn);
                    setGraphic(row);
                }
            }
        });

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
            loadComments();
            newComment.clear();
        } else {
            showAlert("Error", "Failed to add comment.");
        }
    }

    @FXML
    private void onClearComment() {
        newComment.clear();
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
