package cs151.controller;

import cs151.data.StudentDAO;
import cs151.model.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentTableController {

    @FXML private TableView<Student> studentTable;

    @FXML private TableColumn<Student, String> nameCol;
    @FXML private TableColumn<Student, String> statusCol;
    @FXML private TableColumn<Student, String> employmentCol;
    @FXML private TableColumn<Student, String> jobCol;
    @FXML private TableColumn<Student, String> languagesCol;
    @FXML private TableColumn<Student, String> databasesCol;
    @FXML private TableColumn<Student, String> roleCol;
    @FXML private TableColumn<Student, String> commentsCol;
    @FXML private TableColumn<Student, String> flagCol;

    private final StudentDAO dao = new StudentDAO();

    @FXML
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("academicStatus"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("preferredRole"));
        flagCol.setCellValueFactory(new PropertyValueFactory<>("flag"));
        jobCol.setCellValueFactory(new PropertyValueFactory<>("jobDetails"));

        employmentCol.setCellValueFactory(cell -> 
            new SimpleStringProperty(cell.getValue().isEmployed() ? "Employed" : "Not Employed"));

        languagesCol.setCellValueFactory(cell -> 
            new SimpleStringProperty(String.join(", ", cell.getValue().getProgrammingLanguages())));

        databasesCol.setCellValueFactory(cell -> 
            new SimpleStringProperty(String.join(", ", cell.getValue().getDatabases())));

        commentsCol.setCellValueFactory(cell -> 
            new SimpleStringProperty(String.join(", ", cell.getValue().getComments())));

        List<Student> students = dao.findAll().stream()
            .sorted(Comparator.comparing(s -> s.getFullName().toLowerCase()))
            .collect(Collectors.toList());

        studentTable.setItems(FXCollections.observableArrayList(students));
    }
}
