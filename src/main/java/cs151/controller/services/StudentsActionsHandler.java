package cs151.controller.services;

import cs151.application.Main;
import cs151.controller.CommentsController;
import cs151.data.StudentDAO;
import cs151.model.Student;
import cs151.controller.StudentDetailController;
import cs151.controller.StudentsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.Optional;

public class StudentsActionsHandler implements ActionsHandler<Student> {
    private final StudentDAO studentDao;

    public StudentsActionsHandler(StudentDAO studentDao) {

        this.studentDao = studentDao;
    }

    @Override
    public void handleView(Student student, Runnable onWindowClose) {
        try {
            // Load the FXML for the detailed view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/student-detail-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(loader.load(), 900, 600));
            stage.setTitle("Student Details - " + student.getName());

            // Pass the student data to the controller
            StudentDetailController controller = loader.getController();
            controller.setActionsHandler(this);
            controller.setStudent(student);

            stage.setOnHidden(event -> {
                if (onWindowClose != null) {
                    onWindowClose.run();
                }
            });

            // Show the new window
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean handleDelete(Student student) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Student");
        alert.setHeaderText("Delete Student Profile");
        alert.setContentText("Are you sure you want to delete '" + student.getName() + "'? This cannot be undone.");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.OK) {
            return studentDao.deleteStudent(student.getId());
        }
        return false;
    }

    @Override
    public void handleEdit(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/students-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 1000, 800));
            stage.setTitle("Edit Student - " + student.getName());

            StudentsController controller = loader.getController();
            controller.handleEdit(student);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllComments(Student student, Stage mainStage) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/cs151/application/comments-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 900, 800));
            stage.setTitle("Comments for " + student.getName());

            CommentsController controller = loader.getController();
            controller.setStudent(student, mainStage);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load comments page.").show();
        }
    }
}
