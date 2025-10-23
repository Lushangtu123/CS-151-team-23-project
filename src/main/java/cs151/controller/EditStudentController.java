package cs151.controller;

import cs151.data.StudentDAO;
import cs151.model.Comment;
import cs151.model.HardCode;
import cs151.model.Language;
import cs151.model.MultiSelectDropDown;
import cs151.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class EditStudentController {

    @FXML private TextField fullNameField;
    @FXML private ComboBox<String> academicStatusCombo;
    @FXML private ComboBox<String> preferredRoleCombo;
    @FXML private MultiSelectDropDown languageDropdown;
    @FXML private MultiSelectDropDown databaseDropdown;
    @FXML private RadioButton employedRadio;
    @FXML private RadioButton notEmployedRadio;
    @FXML private TextArea jobDetailsField;
    @FXML private TextArea commentsArea;
    @FXML private CheckBox whiteListCheck;
    @FXML private CheckBox blackListCheck;
    @FXML private CheckBox noneCheck;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label messageLabel;

    private final StudentDAO dao = new StudentDAO();
    private Student student;

    @FXML
    public void initialize() {
        //academicStatusCombo.getItems().addAll("Freshman", "Sophomore", "Junior", "Senior", "Graduate");
        //preferredRoleCombo.getItems().addAll("Front-End", "Back-End", "Full-Stack", "Data", "Other");
        academicStatusCombo.getItems().addAll(HardCode.ACADEMIC_STATUSES);
        preferredRoleCombo.getItems().addAll(HardCode.PREFERRED_ROLES);

        ToggleGroup employmentGroup = new ToggleGroup();
        employedRadio.setToggleGroup(employmentGroup);
        notEmployedRadio.setToggleGroup(employmentGroup);
    }

    public void setStudent(Student student) {
        this.student = student;

        fullNameField.setText(student.getFullName());
        academicStatusCombo.setValue(student.getAcademicStatus());
        preferredRoleCombo.setValue(student.getPreferredRole());

        // LANGUAGES: get all distinct languages from DB
        Set<String> allLanguages = dao.findAll().stream()
            .flatMap(s -> s.getProgrammingLanguages().stream())
            .map(Language::getName)
            .collect(Collectors.toCollection(TreeSet::new)); // sorted, no duplicates

        List<String> studentLangs = student.getProgrammingLanguages().stream()
            .map(Language::getName)
            .collect(Collectors.toList());

        languageDropdown.setItems(new ArrayList<>(allLanguages));
        languageDropdown.setSelectedItems(studentLangs);

        // DATABASES: hardcoded list, mark student's selections
        //List<String> allDatabases = List.of("MySQL", "PostgreL", "MongoDB", "SQLite", "Oracle");
        List<String> allDatabases = HardCode.DATABASES;

        databaseDropdown.setItems(allDatabases);
        databaseDropdown.setSelectedItems(student.getDatabases());

        employedRadio.setSelected(student.isEmployed());
        notEmployedRadio.setSelected(!student.isEmployed());
        jobDetailsField.setText(student.getJobDetails());

        String combinedComments = student.getComments().stream()
            .map(Comment::getText)
            .collect(Collectors.joining("\n"));
        commentsArea.setText(combinedComments);

        switch (student.getFlag()) {
            case "Whitelist" -> whiteListCheck.setSelected(true);
            case "Blacklist" -> blackListCheck.setSelected(true);
            case "None" -> noneCheck.setSelected(true);
        }
    }

    @FXML
    public void onSaveStudent() {
        student.setFullName(fullNameField.getText());
        student.setAcademicStatus(academicStatusCombo.getValue());
        student.setPreferredRole(preferredRoleCombo.getValue());

        List<Language> selectedLangs = languageDropdown.getSelectedItems().stream()
            .map(Language::new)
            .collect(Collectors.toList());
        student.setProgrammingLanguages(selectedLangs);

        student.setDatabases(databaseDropdown.getSelectedItems());

        student.setEmployed(employedRadio.isSelected());
        student.setJobDetails(jobDetailsField.getText());

        List<Comment> commentList = commentsArea.getText().lines()
            .map(Comment::new)
            .collect(Collectors.toList());
        student.setComments(commentList);

        if (whiteListCheck.isSelected()) student.setFlag("Whitelist");
        else if (blackListCheck.isSelected()) student.setFlag("Blacklist");
        else if (noneCheck.isSelected()) student.setFlag("None");

        dao.update(student);
        messageLabel.setText("Student updated successfully.");

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
