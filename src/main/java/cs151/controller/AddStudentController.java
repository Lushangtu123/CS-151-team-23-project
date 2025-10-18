package cs151.controller;

import cs151.data.StudentDAO;
import cs151.model.MultiSelectDropDown;
import cs151.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label; 
import java.util.List;

public class AddStudentController {

    @FXML private TextField fullNameField;
    @FXML private ComboBox<String> academicStatusCombo;
    @FXML private RadioButton employedRadio;
    @FXML private RadioButton notEmployedRadio;
    @FXML private TextArea jobDetailsField;
    @FXML private Label jobLabel; // NEW: for dynamic visibility
    @FXML private ComboBox<String> preferredRoleCombo;
    @FXML private TextArea commentsArea;
    @FXML private ComboBox<String> flagCombo;
    @FXML private Label messageLabel;
    @FXML private HBox languagesContainer;
    @FXML private HBox databasesContainer;

    private final ToggleGroup employmentGroup = new ToggleGroup();
    private MultiSelectDropDown languagesDropdown;
    private MultiSelectDropDown databasesDropdown;
    private final StudentDAO studentDAO = new StudentDAO();

    @FXML
    public void initialize() {
        academicStatusCombo.getItems().addAll("Freshman", "Sophomore", "Junior", "Senior", "Graduate");
        preferredRoleCombo.getItems().addAll("Front-End", "Back-End", "Full-Stack", "Data", "Other");
        flagCombo.getItems().addAll("None", "Whitelist", "Blacklist");
        flagCombo.getSelectionModel().select("None");

        languagesDropdown = new MultiSelectDropDown(List.of("Java", "Python", "C++"));
        databasesDropdown = new MultiSelectDropDown(List.of("MySQL", "Postgres", "MongoDB", "SQLite", "Oracle"));

        languagesContainer.getChildren().add(languagesDropdown);
        databasesContainer.getChildren().add(databasesDropdown);

        employedRadio.setToggleGroup(employmentGroup);
        notEmployedRadio.setToggleGroup(employmentGroup);
        notEmployedRadio.setSelected(true);

        // NEW: toggle job label visibility
        jobLabel.setVisible(false);
        employmentGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            jobLabel.setVisible(employedRadio.isSelected());
        });
    }

    @FXML
    protected void onSaveStudent() {
        String name = fullNameField.getText().trim();
        String status = academicStatusCombo.getValue();
        boolean employed = employedRadio.isSelected();
        String job = jobDetailsField.getText().trim();
        List<String> langs = languagesDropdown.getSelectedItems();
        List<String> dbs = databasesDropdown.getSelectedItems();
        String role = preferredRoleCombo.getValue();
        String comments = commentsArea.getText().trim();
        String flag = flagCombo.getValue();

        // Required field check
        if (name.isEmpty() || status == null || langs.isEmpty() || dbs.isEmpty() || role == null) {
            showMessage("Please fill all required fields.", false);
            return;
        }

        // Job details check
        if (employed && job.isEmpty()) {
            showMessage("Job details required if employed.", false);
            return;
        }

        // Duplicate name check
        if (studentDAO.isFullNameExists(name)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Duplicate Name");
            alert.setHeaderText("Student Already Exists");
            alert.setContentText("A student with the name '" + name + "' already exists in the database.");
            alert.showAndWait();
            return;
        }

        // Create and save student
        Student student = new Student(
            0,
            name,
            status,
            employed,
            job,
            langs,
            dbs,
            role,
            List.of(comments),
            flag
        );

        studentDAO.save(student);
        showMessage("Student saved to database.", true);
    }

    @FXML 
    protected void onCancel() {
        flagCombo.getScene().getWindow().hide();
    }

    private void showMessage(String msg, boolean success) {
        messageLabel.setText(msg);
        messageLabel.setStyle(success
            ? "-fx-text-fill: green; -fx-font-weight: bold;"
            : "-fx-text-fill: red; -fx-font-weight: bold;");
    }
}
