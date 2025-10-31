package cs151.controller.services;

import cs151.data.StudentDAO;
import cs151.model.Student;
import cs151.controller.StudentDetailController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Optional;

public class StudentsActionsHandler {
    private final StudentDAO studentDao;

    public StudentsActionsHandler(StudentDAO studentDao) {
        this.studentDao = studentDao;
    }

    public void handleView(Student student) {
        try {
            // Load the FXML for the detailed view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/application/student-detail-view.fxml"));
            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(loader.load(), 900, 600));
            stage.setTitle("Student Details - " + student.getName());

            // Pass the student data to the controller
            StudentDetailController controller = loader.getController();
            controller.setStudent(student);

            // Show the new window
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Optional fallback: show an alert if FXML fails
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot show student details");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

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

    public Student handleEdit(Student student) {
        return student;
    }
}
