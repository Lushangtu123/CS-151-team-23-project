package cs151.controller;

import cs151.data.StudentDAO;
import cs151.model.Student;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;

import java.util.List;

public class StudentsController {

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> statusColumn;
    @FXML private TableColumn<Student, String> roleColumn;

    private final StudentDAO studentDAO = new StudentDAO();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("academicStatus"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("preferredRole"));

        loadStudents();
    }

    private void loadStudents() {
        List<Student> students = studentDAO.findAll();
        studentTable.getItems().setAll(students);
    }

    @FXML
    protected void onSearchStudent() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Student");
        dialog.setHeaderText("Enter student name to search:");
        dialog.setContentText("Name:");

        dialog.showAndWait().ifPresent(name -> {
            List<Student> matches = studentDAO.findByName(name);
            if (matches.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No students found with name: " + name);
                alert.showAndWait();
            } else {
                studentTable.getItems().setAll(matches);
            }
        });
    }

    @FXML
    protected void onClearSearch() {
        loadStudents();
    }
}
