package cs151.controller;

import cs151.controller.services.NavigationHandler;
import cs151.model.Comment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Controller for Comment Detail View
 * Displays the full content of a comment
 */
public class CommentDetailController {

    @FXML
    private Label dateLabel;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Button closeButton;

    /**
     * Set the comment to display
     */
    public void setComment(Comment comment) {
        dateLabel.setText(comment.getDateAsString());
        commentTextArea.setText(comment.getContent());
    }

    @FXML
    private void onCloseClick() {
        NavigationHandler nav =  new NavigationHandler();
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}

