package cs151.controller;

import cs151.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StudentDetailController {

    @FXML private Label nameDetail;
    @FXML private Label academicStatusDetail;
    @FXML private Label employmentStatusDetail;
    @FXML private Label jobDetailsDetail;
    @FXML private Label languagesDetail;
    @FXML private Label dbSkillsDetail;
    @FXML private Label roleDetail;
    @FXML private Label commentsDetail;
    @FXML private Label flagDetail;

    @FXML private Button closeButton;
    @FXML private Button backButton;

    // ✅ Add message label
    @FXML private Label messageLabel;

    /**
     * Populate fields with student data
     */
    public void setStudent(Student student) {
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
    private void onBackButtonClick() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/cs151/application/hello-view.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 1000, 800);
            stage.setScene(scene);
            stage.setTitle("Student Information Management System");
        } catch (Exception e) {
            e.printStackTrace();
            if (messageLabel != null) {
                messageLabel.setText("Error returning to home page: " + e.getMessage());
            }
        }
    }
}
