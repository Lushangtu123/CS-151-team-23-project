package cs151.controller.services;

import cs151.application.Main;
import cs151.controller.StudentProfileReportController;
import cs151.data.StudentDAO;
import cs151.model.Student;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsActionsHandler implements ActionsHandler<Student> {

    private final StudentDAO studentDao;

    public ReportsActionsHandler(StudentDAO studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void handleView(Student student, Runnable onClose) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("student-profile-report-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), 900, 700));
            stage.setTitle("Student Profile Report - " + student.getName());

            StudentProfileReportController controller = loader.getController();
            controller.setStudent(student);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load student profile report.").show();
        }
    }

    @Override
    public void handleEdit(Student item) {
        throw new UnsupportedOperationException("Edit is not supported in Reports view.");
    }

    @Override
    public boolean handleDelete(Student item) {
        throw new UnsupportedOperationException("Delete is not supported in Reports view.");
    }
}
