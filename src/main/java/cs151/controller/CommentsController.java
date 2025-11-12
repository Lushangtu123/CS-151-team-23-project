package cs151.controller;

import cs151.application.Main;
import cs151.data.CommentDAO;
import cs151.model.Comment;
import cs151.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller for the Comments View
 * Handles displaying and adding comments for a student
 */
public class CommentsController {
    
    @FXML
    private Text pageTitle;
    
    @FXML
    private Text studentNameLabel;
    
    @FXML
    private Label commentCountLabel;
    
    @FXML
    private VBox commentsContainer;
    
    @FXML
    private TextArea newCommentArea;
    
    @FXML
    private Label todayDateLabel;
    
    @FXML
    private Button addCommentButton;
    
    @FXML
    private Label messageLabel;
    
    private CommentDAO commentDao;
    private Student currentStudent;
    
    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        commentDao = new CommentDAO();
        
        // Initialize Comment table
        commentDao.initTable();
        
        // Display today's date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        todayDateLabel.setText(today.format(formatter));
    }
    
    /**
     * Set the student whose comments will be displayed
     * @param student The student
     */
    public void setStudent(Student student) {
        this.currentStudent = student;
        studentNameLabel.setText("Student: " + student.getName());
        loadComments();
    }
    
    /**
     * Load and display all comments for the current student
     */
    private void loadComments() {
        if (currentStudent == null) {
            return;
        }
        
        List<Comment> comments = commentDao.getCommentsByStudentId(currentStudent.getId());
        
        // Update comment count
        commentCountLabel.setText("(" + comments.size() + " comment" + (comments.size() != 1 ? "s" : "") + ")");
        
        // Clear existing comments
        commentsContainer.getChildren().clear();
        
        if (comments.isEmpty()) {
            Label emptyLabel = new Label("No comments yet. Add the first evaluation comment below.");
            emptyLabel.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 14px; -fx-padding: 20;");
            commentsContainer.getChildren().add(emptyLabel);
        } else {
            // Display each comment
            for (Comment comment : comments) {
                VBox commentBox = createCommentBox(comment);
                commentsContainer.getChildren().add(commentBox);
            }
        }
    }
    
    /**
     * Create a visual box for displaying a comment
     * @param comment The comment to display
     * @return VBox containing the comment
     */
    private VBox createCommentBox(Comment comment) {
        VBox box = new VBox(8);
        box.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-border-color: #dee2e6; " +
                    "-fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        
        // Date header with delete button
        HBox headerBox = new HBox(10);
        headerBox.setStyle("-fx-alignment: center-left;");
        
        Label dateLabel = new Label("📅 " + comment.getDateAsString());
        dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2980b9; -fx-font-size: 14px;");
        
        Button deleteBtn = new Button("Delete");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; " +
                          "-fx-padding: 5 15; -fx-background-radius: 3; -fx-cursor: hand;");
        deleteBtn.setOnAction(e -> handleDeleteComment(comment));
        
        headerBox.getChildren().addAll(dateLabel, deleteBtn);
        HBox.setMargin(deleteBtn, new Insets(0, 0, 0, 10));
        
        // Comment content
        Label contentLabel = new Label(comment.getContent());
        contentLabel.setWrapText(true);
        contentLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 14px;");
        
        // Separator
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #dee2e6;");
        
        box.getChildren().addAll(headerBox, contentLabel);
        
        return box;
    }
    
    /**
     * Handle adding a new comment
     */
    @FXML
    private void handleAddComment() {
        String content = newCommentArea.getText().trim();
        
        // Validate input
        if (content.isEmpty()) {
            showMessage("Please enter a comment before submitting.", "error");
            return;
        }
        
        if (currentStudent == null) {
            showMessage("No student selected.", "error");
            return;
        }
        
        // Create new comment
        Comment newComment = new Comment(currentStudent.getId(), content);
        
        // Save to database
        boolean success = commentDao.addComment(newComment);
        
        if (success) {
            showMessage("Comment added successfully!", "success");
            newCommentArea.clear();
            loadComments(); // Refresh the comments list
        } else {
            showMessage("Failed to add comment. Please try again.", "error");
        }
    }
    
    /**
     * Handle deleting a comment
     * @param comment The comment to delete
     */
    private void handleDeleteComment(Comment comment) {
        // Confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Comment");
        alert.setHeaderText("Are you sure you want to delete this comment?");
        alert.setContentText("Date: " + comment.getDateAsString() + "\n" +
                           "Content: " + (comment.getContent().length() > 50 ? 
                           comment.getContent().substring(0, 50) + "..." : comment.getContent()));
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = commentDao.deleteComment(comment.getId());
                if (success) {
                    showMessage("Comment deleted successfully!", "success");
                    loadComments(); // Refresh the list
                } else {
                    showMessage("Failed to delete comment.", "error");
                }
            }
        });
    }
    
    /**
     * Handle clearing the input field
     */
    @FXML
    private void handleClear() {
        newCommentArea.clear();
        messageLabel.setText("");
    }
    
    /**
     * Handle navigating back to student details
     */
    @FXML
    private void handleBackToDetails() {
        if (currentStudent == null) {
            handleBackToHome();
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("student-detail-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 800);
            
            StudentDetailController controller = loader.getController();
            controller.setStudent(currentStudent);
            
            Stage stage = (Stage) addCommentButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error loading student details page.", "error");
        }
    }
    
    /**
     * Handle navigating back to home
     */
    @FXML
    private void handleBackToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 800);
            Stage stage = (Stage) addCommentButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error loading home page.", "error");
        }
    }
    
    /**
     * Display a message to the user
     * @param message The message text
     * @param type "success" or "error"
     */
    private void showMessage(String message, String type) {
        messageLabel.setText(message);
        if ("success".equals(type)) {
            messageLabel.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        } else {
            messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        }
    }
}

