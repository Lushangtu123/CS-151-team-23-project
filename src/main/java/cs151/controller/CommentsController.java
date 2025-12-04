package cs151.controller;

import cs151.application.Main;
import cs151.controller.services.ActionsHandler;
import cs151.controller.services.CommentsActionsHandler;
import cs151.data.CommentDAO;
import cs151.model.Comment;
import cs151.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
    private ActionsHandler<Comment> actionsHandler;
    
    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        commentDao = new CommentDAO();
        
        // Initialize Comment table
        commentDao.initTable();
        actionsHandler = new CommentsActionsHandler(commentDao);
        // Display today's date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
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

        HBox headerBox = new HBox();
        headerBox.setSpacing(10);
        headerBox.setStyle("-fx-alignment: center-left; -fx-padding: 0 0 5 0;");

        Label dateLabel = new Label("📅 " + comment.getDateAsString());
        dateLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px; -fx-font-weight: normal;");

        Button deleteBtn = new Button("Delete");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 12px; " +
                "-fx-padding: 5 15; -fx-background-radius: 3; -fx-cursor: hand;");
        deleteBtn.setOnAction(e -> handleDeleteComment(comment));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        headerBox.getChildren().addAll(dateLabel, spacer, deleteBtn);

        // Comment text
        Label contentLabel = new Label(comment.getContent());
        contentLabel.setWrapText(true);
        contentLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 16px;");

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
        boolean deleted = actionsHandler.handleDelete(comment);
            if (deleted) {
                showMessage("Comment deleted successfully!", "success");
                loadComments();
            } else {
                showMessage("Failed to delete comment.", "error");
            }
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
     * Handle navigating back to search page
     */
    @FXML
    private void handleBackToSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("search-view.fxml"));
            Scene scene = new Scene(loader.load(), 1150, 800);
            
            Stage stage = (Stage) addCommentButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showMessage("Error loading search page.", "error");
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

