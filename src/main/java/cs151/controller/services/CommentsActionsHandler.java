package cs151.controller.services;

import cs151.controller.CommentDetailController;
import cs151.data.CommentDAO;
import cs151.data.StudentDAO;
import cs151.model.Comment;
import cs151.controller.StudentProfileReportController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CommentsActionsHandler implements ActionsHandler<Comment> {

    private final CommentDAO commentDao;

    public CommentsActionsHandler(CommentDAO commentDao) {
        this.commentDao = commentDao;
    }


    @Override
    public void handleView(Comment comment, Runnable onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/comment-detail-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 600, 450));
            stage.setTitle("Comment Detail");

            // Set the comment in the detail controller
            CommentDetailController controller = loader.getController();
            controller.setComment(comment);

            // Optional callback when window closes
            stage.setOnHidden(event -> {
                if (onClose != null) onClose.run();
            });

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load comment detail.");
            alert.showAndWait();
        }
    }

    @Override
    public void handleEdit(Comment comment) {
        throw new UnsupportedOperationException("Edit not supported.");
    }

    @Override
    public boolean handleDelete(Comment comment) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Comment");
        alert.setHeaderText("Delete Comment");
        alert.setContentText(
                "Created on: " + comment.getDateAsString() + "\n\n" +
                        (comment.getContent().length() > 80
                                ? comment.getContent().substring(0, 80) + "..."
                                : comment.getContent())
        );

        Optional<ButtonType> response = alert.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.OK) {
            return commentDao.deleteComment(comment.getId());
        }
        return false;
    }
}
