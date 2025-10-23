package cs151.controller;

import cs151.data.StudentDAO;
import cs151.data.LanguageDAO;
import cs151.model.Comment;
import cs151.model.HardCode;
import cs151.model.Language;
import cs151.model.MultiSelectDropDown;
import cs151.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

public class AddStudentController {

    @FXML private TextField fullNameField;
    @FXML private ComboBox<String> academicStatusCombo;
    @FXML private RadioButton employedRadio;
    @FXML private RadioButton notEmployedRadio;
    @FXML private TextArea jobDetailsField;
    @FXML private Label jobLabel;
    @FXML private ComboBox<String> preferredRoleCombo;
    @FXML private TextArea commentsArea;
    @FXML private Label messageLabel;
    @FXML private HBox languagesContainer;
    @FXML private HBox databasesContainer;
    @FXML private CheckBox blackListCheck;
    @FXML private CheckBox whiteListCheck;
    @FXML private CheckBox noneCheck;
    @FXML private Button cancelButton;

    private final ToggleGroup employmentGroup = new ToggleGroup();
    private MultiSelectDropDown languagesDropdown;
    private MultiSelectDropDown databasesDropdown;
    private final StudentDAO studentDAO = new StudentDAO();
    private final LanguageDAO languageDAO = new LanguageDAO();

    @FXML
    public void initialize() {
        //academicStatusCombo.getItems().addAll("Freshman", "Sophomore", "Junior", "Senior", "Graduate");
        academicStatusCombo.getItems().addAll(HardCode.ACADEMIC_STATUSES);

        //preferredRoleCombo.getItems().addAll("Front-End", "Back-End", "Full-Stack", "Data", "Other");
        preferredRoleCombo.getItems().addAll(HardCode.PREFERRED_ROLES);


        List<Language> languages = languageDAO.getAllLanguages();
        List<String> availableLanguages = languages.stream().map(Language::getName).collect(Collectors.toList());

        languagesDropdown = new MultiSelectDropDown(availableLanguages);
        //databasesDropdown = new MultiSelectDropDown(List.of("MySQL", "Postgres", "MongoDB", "SQLite", "Oracle"));
        databasesDropdown = new MultiSelectDropDown(HardCode.DATABASES);


        languagesContainer.getChildren().add(languagesDropdown);
        databasesContainer.getChildren().add(databasesDropdown);

        employedRadio.setToggleGroup(employmentGroup);
        notEmployedRadio.setToggleGroup(employmentGroup);
        employedRadio.setSelected(true);

        jobLabel.setVisible(true);
        employmentGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            jobDetailsField.setDisable(!employedRadio.isSelected());
        });

        whiteListCheck.setOnAction(e -> {
            if (whiteListCheck.isSelected()) {
                blackListCheck.setSelected(false);
                noneCheck.setSelected(false);
            }
        });
        blackListCheck.setOnAction(e -> {
            if (blackListCheck.isSelected()) {
                whiteListCheck.setSelected(false);
                noneCheck.setSelected(false);
            }
        });
        noneCheck.setOnAction(e -> {
            if (noneCheck.isSelected()) {
                whiteListCheck.setSelected(false);
                blackListCheck.setSelected(false);
            }
        });
    }

    @FXML
    protected void onSaveStudent() {
        String rawName = fullNameField.getText();
        String name = rawName == null ? "" : rawName.trim().replaceAll("\\s+", " ");
        String status = academicStatusCombo.getValue();
        boolean employed = employedRadio.isSelected();
        String job = jobDetailsField.getText().trim();
        List<Language> langs = languagesDropdown.getSelectedItems().stream()
                .map(Language::new)
                .collect(Collectors.toList());
        List<String> dbs = databasesDropdown.getSelectedItems();
        String role = preferredRoleCombo.getValue();
        String comments = commentsArea.getText().trim();

        String flag = noneCheck.isSelected() ? "None"
                    : whiteListCheck.isSelected() ? "Whitelist"
                    : blackListCheck.isSelected() ? "Blacklist"
                    : "None";

        if (name.isEmpty() || status == null || langs.isEmpty() || dbs.isEmpty() || role == null) {
            showMessage("Please fill all required fields.", false);
            return;
        }

        if (employed && job.isEmpty()) {
            showMessage("Job details required if employed.", false);
            return;
        }

        if (studentDAO.isFullNameExists(name)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Duplicate Name");
            alert.setHeaderText("Student Already Exists");
            alert.setContentText("A student with the name '" + name + "' already exists in the database.");
            alert.showAndWait();
            return;
        }

        Comment comment = new Comment(0, 0, comments);
        Student student = new Student(
            0,
            name,
            status,
            employed,
            job,
            langs,
            dbs,
            role,
            List.of(comment),
            flag
        );

        studentDAO.save(student);
        showMessage("Student saved to database.", true);
    }

    @FXML
    public void onCancel(ActionEvent event) {
        ((Stage) cancelButton.getScene().getWindow()).hide();
    }

    private void showMessage(String msg, boolean success) {
        messageLabel.setText(msg);
        messageLabel.setStyle(success
            ? "-fx-text-fill: green; -fx-font-weight: bold;"
            : "-fx-text-fill: red; -fx-font-weight: bold;");
    }
}
