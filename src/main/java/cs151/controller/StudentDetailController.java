package cs151.controller;

import cs151.controller.services.StudentsActionsHandler;
import cs151.model.Student;
import cs151.data.StudentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class StudentDetailController {

    @FXML
    private Label nameDetail;

    @FXML private Label academicStatusDetail;

    @FXML
    private Label employmentStatusDetail;

    @FXML
    private Label jobDetailsDetail;

    @FXML
    private Label languagesDetail;

    @FXML
    private Label dbSkillsDetail;

    @FXML
    private Label roleDetail;

    @FXML
    private Label commentsDetail;

    @FXML
    private Label flagDetail;

    @FXML
    private Button closeButton;

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    // ✅ Add message label
    @FXML private Label messageLabel;

    private StudentsActionsHandler actionsHandler;
    private Student student;

    public void setStudentDao(StudentDAO studentDao) {
        this.actionsHandler = new StudentsActionsHandler(studentDao);
    }

    /**
     * Populate fields with student data
     */
    public void setStudent(Student student) {
        this.student = student;
        nameDetail.setText(student.getName() != null ? student.getName() : "N/A");
        academicStatusDetail.setText(student.getAcademicStatus() != null ? student.getAcademicStatus() : "N/A");
        employmentStatusDetail.setText(student.getEmploymentStatus() != null ? student.getEmploymentStatus() : "N/A");
        jobDetailsDetail.setText(student.getJobDetails() != null ? student.getJobDetails() : "N/A");
        languagesDetail.setText(student.getLanguagesAsString() != null ? student.getLanguagesAsString() : "N/A");
        dbSkillsDetail.setText(student.getDbSkills() != null ? student.getDbSkills() : "N/A");
        roleDetail.setText(student.getRole() != null ? student.getRole() : "N/A");
        commentsDetail.setText(student.getComments() != null ? student.getComments() : "N/A");
        flagDetail.setText(student.getFlag() != null ? student.getFlag() : "N/A");
    }

    @FXML
    private void onCloseButtonClick() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onEditButtonClick() {
        try {
            actionsHandler.handleEdit(student);

            // Close student detail window
            Stage currentStage = (Stage) editButton.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (messageLabel != null) {
                messageLabel.setText("Error opening edit view: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onBackButtonClick() {
        Stage currentStage = (Stage) backButton.getScene().getWindow();
        currentStage.close();
    }
}
