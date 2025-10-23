package cs151.controller;

import cs151.data.LanguageDAO;
import cs151.data.StudentDAO;
import cs151.model.HardCode;
import cs151.model.Language;
import cs151.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchStudentController {

    @FXML private CheckBox nameCheck;
    @FXML private CheckBox statusCheck;
    @FXML private CheckBox languageCheck;
    @FXML private CheckBox databaseCheck;
    @FXML private CheckBox roleCheck;

    @FXML private TextField nameField;
    @FXML private ComboBox<String> statusCombo;
    @FXML private ComboBox<String> languageCombo;
    @FXML private ComboBox<String> databaseCombo;
    @FXML private ComboBox<String> roleCombo;

    @FXML private Label resultMessage;
    @FXML private TableView<Student> resultsTable;

    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> statusColumn;
    @FXML private TableColumn<Student, String> employedColumn;
    @FXML private TableColumn<Student, String> jobColumn;
    @FXML private TableColumn<Student, String> languagesColumn;
    @FXML private TableColumn<Student, String> databasesColumn;
    @FXML private TableColumn<Student, String> roleColumn;
    @FXML private TableColumn<Student, String> flagColumn;

    private final StudentDAO dao = new StudentDAO();

    @FXML
    public void initialize() {
        // Static dropdowns
        //statusCombo.getItems().addAll("Freshman", "Sophomore", "Junior", "Senior", "Graduate");
        //databaseCombo.getItems().addAll("MySQL", "PostgreSQL", "Oracle", "MongoDB", "SQLite");
        //roleCombo.getItems().addAll("Front-End", "Back-End", "Full-Stack", "Data", "Other");
        statusCombo.getItems().addAll(HardCode.ACADEMIC_STATUSES);
        databaseCombo.getItems().addAll(HardCode.DATABASES);
        roleCombo.getItems().addAll(HardCode.PREFERRED_ROLES);

        // Dynamic language dropdown from LanguageDAO
        List<Language> languages = new LanguageDAO().getAllLanguages();
        if (languages != null) {
            List<String> languageNames = languages.stream()
                .map(Language::getName)
                .filter(name -> name != null && !name.trim().isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

            languageCombo.getItems().addAll(languageNames);
        }

        // Table column bindings
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFullName()));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAcademicStatus()));
        employedColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isEmployed() ? "Yes" : "No"));
        jobColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJobDetails()));
        languagesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLanguagesAsString()));
        databasesColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDatabasesAsString()));
        roleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPreferredRole()));
        flagColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFlag()));
    }

    @FXML
    private void onSearchClick() {
        Map<String, String> criteria = new HashMap<>();
        boolean atLeastOneMarked = false;
        boolean hasEmptyMarked = false;

        if (nameCheck.isSelected()) {
            atLeastOneMarked = true;
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                criteria.put("Full Name", name);
            } else {
                hasEmptyMarked = true;
            }
        }

        if (statusCheck.isSelected()) {
            atLeastOneMarked = true;
            String status = statusCombo.getValue();
            if (status != null && !status.trim().isEmpty()) {
                criteria.put("Academic Status", status.trim());
            } else {
                hasEmptyMarked = true;
            }
        }

        if (languageCheck.isSelected()) {
            atLeastOneMarked = true;
            String lang = languageCombo.getValue();
            if (lang != null && !lang.trim().isEmpty()) {
                criteria.put("Programming Language", lang.trim());
            } else {
                hasEmptyMarked = true;
            }
        }

        if (databaseCheck.isSelected()) {
            atLeastOneMarked = true;
            String db = databaseCombo.getValue();
            if (db != null && !db.trim().isEmpty()) {
                criteria.put("Database Skill", db.trim());
            } else {
                hasEmptyMarked = true;
            }
        }

        if (roleCheck.isSelected()) {
            atLeastOneMarked = true;
            String role = roleCombo.getValue();
            if (role != null && !role.trim().isEmpty()) {
                criteria.put("Preferred Role", role.trim());
            } else {
                hasEmptyMarked = true;
            }
        }

        if (!atLeastOneMarked) {
            resultMessage.setText("Please select at least one search criterion.");
            resultsTable.getItems().clear();
            return;
        }

        if (hasEmptyMarked && criteria.isEmpty()) {
            resultMessage.setText("Please fill in the selected criteria.");
            resultsTable.getItems().clear();
            return;
        }

        List<Student> results = dao.findByMultiCriteria(criteria);
        resultsTable.getItems().setAll(results);

        resultMessage.setText(results.isEmpty()
            ? "No matching student profiles found."
            : results.size() + " student profile(s) found.");
    }


}
