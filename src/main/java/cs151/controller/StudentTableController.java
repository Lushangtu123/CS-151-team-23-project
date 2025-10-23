package cs151.controller;

import cs151.data.StudentDAO;
import cs151.model.Comment;
import cs151.model.Language;
import cs151.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
    @FXML private TableColumn<Student, Void> actionCol;

    private final StudentDAO dao = new StudentDAO();

    @FXML
    public void initialize() {
        bindColumns();
        setupActionColumn();
        refreshTable();
    }

    private void bindColumns() {
        nameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("fullName"));
        statusCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("academicStatus"));
        roleCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("preferredRole"));
        flagCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("flag"));
        jobCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("jobDetails"));

        employmentCol.setCellValueFactory(cell ->
            new SimpleStringProperty(cell.getValue().isEmployed() ? "Employed" : "Not Employed"));

        languagesCol.setCellValueFactory(cell ->
            new SimpleStringProperty(
                cell.getValue().getProgrammingLanguages().stream()
                    .map(Language::getName)
                    .collect(Collectors.joining(", "))
            ));

        databasesCol.setCellValueFactory(cell ->
            new SimpleStringProperty(String.join(", ", cell.getValue().getDatabases())));

        commentsCol.setCellValueFactory(cell ->
            new SimpleStringProperty(
                cell.getValue().getComments().stream()
                    .map(Comment::getText)
                    .collect(Collectors.joining(", "))
            ));
    }

    private void setupActionColumn() {
        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox box = new HBox(10, editBtn, deleteBtn);

            {
            	editBtn.setStyle("-fx-background-color: #B0C270; -fx-text-fill: white; -fx-font-weight: bold;");   
                deleteBtn.setStyle("-fx-background-color: #BA829A; -fx-text-fill: white; -fx-font-weight: bold;"); 
                
                editBtn.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    openEditWindow(student);
                });

                deleteBtn.setOnAction(e -> {
                    Student student = getTableView().getItems().get(getIndex());
                    confirmAndDelete(student);
                });
   
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
    }

    private void openEditWindow(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cs151/view/edit-student.fxml"));
            Parent root = loader.load();
            EditStudentController controller = loader.getController();
            controller.setStudent(student);

            Stage stage = new Stage();
            stage.setTitle("Edit Student");
            stage.setScene(new Scene(root));

            // Refresh table after edit window closes
            stage.setOnHidden((WindowEvent we) -> refreshTable());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error opening edit window: " + e.getMessage());
        }
    }    

    private void confirmAndDelete(Student student) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText("Delete Student: " + student.getFullName());
        confirm.setContentText("Are you sure you want to delete this student?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("DEBUG: Deleting student id=" + student.getId() + " name=[" + student.getFullName() + "]");
            dao.delete(student.getId());

            studentTable.getItems().remove(student);
            System.out.println("DEBUG: Student removed from TableView.");
        } else {
            System.out.println("DEBUG: Deletion cancelled by user.");
        }
    }
    

    private void refreshTable() {
        List<Student> students = dao.findAll().stream()
            .sorted(Comparator.comparing(s -> s.getFullName().toLowerCase()))
            .collect(Collectors.toList());

        studentTable.setItems(FXCollections.observableArrayList(students));
    }
}
