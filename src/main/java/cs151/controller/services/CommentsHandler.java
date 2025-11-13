package cs151.controller.services;

import cs151.data.StudentDAO;
import cs151.model.Student;
import cs151.controller.StudentsController;
import cs151.controller.CommentController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CommentsHandler {
    private final StudentDAO studentDao;
    public CommentsHandler(StudentDAO studentDao) {
        this.studentDao = studentDao;
    }
    public void handleViewComments(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/comments-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 900, 600));
            stage.setTitle("Comments for " + student.getName());

            CommentController controller = loader.getController();
            controller.setStudentDao(studentDao);
            controller.setStudent(student);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
