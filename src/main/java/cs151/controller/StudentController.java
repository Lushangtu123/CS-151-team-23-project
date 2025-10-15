package cs151.controller;

import cs151.model.Language;
import cs151.model.Student;
import cs151.data.LanguageDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class StudentController {

    @FXML
    private Button backButton;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> academicStatusCombo;

    @FXML
    private ComboBox<String> roleCombo;

    @FXML
    private ComboBox<Language> languagesCombo;

    @FXML
    private ComboBox<String> dbSkillsCombo;

    @FXML
    private RadioButton notEmployed;

    @FXML
    private RadioButton employed;

    @FXML
    private TextField jobDetail;

    @FXML
    private TextArea evaluationComment;

    @FXML
    private CheckBox whitelistCheckbox;

    @FXML
    private CheckBox blacklistCheckbox;

    @FXML
    private Button saveButton;

    private ToggleGroup jobStatusGroup;

    private ObservableList<Language> availableLanguages;

    private LanguageDAO languageDAO = new LanguageDAO();

    @FXML
    public void initialize() {
        academicStatusCombo.setItems(FXCollections.observableArrayList("Freshman", "Sophomore", "Junior", "Senior", "Graduate"));
        roleCombo.setItems(FXCollections.observableArrayList("Back-End", "Front-End", "Full-Stack", "Data", "Other"));
        dbSkillsCombo.setItems(FXCollections.observableArrayList("MySQL", "PostgreSQL", "MongoDB", "SQLite"));

        jobStatusGroup = new ToggleGroup();
        employed.setToggleGroup(jobStatusGroup);
        notEmployed.setToggleGroup(jobStatusGroup);
        employed.setSelected(true);

        // Load languages from DB via DAO
        availableLanguages = FXCollections.observableArrayList(languageDAO.getAllLanguages());
        languagesCombo.setItems(availableLanguages);

        // Enforce mutual exclusivity between checkboxes
        whitelistCheckbox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                blacklistCheckbox.setSelected(false);
            }
        });

        blacklistCheckbox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                whitelistCheckbox.setSelected(false);
            }
        });

        saveButton.setOnAction(e -> onSaveStudent());
    }

    private void onSaveStudent() {
        try {
            String fullName = nameField.getText().trim();
            String academicStatus = academicStatusCombo.getValue();
            String preferredRole = roleCombo.getValue();
            Language selectedLanguage = languagesCombo.getValue();
            String dbSkill = dbSkillsCombo.getValue();
            boolean isEmployed = employed.isSelected();
            String job = jobDetail.getText();
            String comment = evaluationComment.getText();

            boolean isWhitelisted = whitelistCheckbox.isSelected();
            boolean isBlacklisted = blacklistCheckbox.isSelected();

            // Validate required fields
            if (fullName.isEmpty() || academicStatus == null || selectedLanguage == null || dbSkill == null || preferredRole == null) {
                showAlert("Missing Required Fields", "Please fill out all required fields (marked with *).");
                return;
            }
            //Require details if employed
            if (isEmployed && job.isEmpty()) {
                showAlert("Missing Job Detail", "Please enter the job detail since the student is employed.");
                return;
            }

            // Prevent both flags selected
            if (isWhitelisted && isBlacklisted) {
                showAlert("Validation Error", "A student cannot be both whitelisted and blacklisted.");
                return;
            }

            Student student = new Student(
                    fullName, academicStatus, preferredRole, selectedLanguage,
                    dbSkill, isEmployed, job, comment,
                    isWhitelisted, isBlacklisted
            );

            //TODO: Save student form to DB

            System.out.println("Saved Student: " + student); // placeholder
            showAlert("Success", "Student profile saved successfully!");

        } catch (IllegalArgumentException | IllegalStateException ex) {
            showAlert("Validation Error", ex.getMessage());
        } catch (Exception ex) {
            showAlert("Unexpected Error", "Something went wrong: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    protected void onBackButtonClick() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(
                    getClass().getResource("/cs151/application/hello-view.fxml")
            );
            javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load(), 700, 600);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
