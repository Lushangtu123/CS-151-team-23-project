package cs151.data;

import cs151.model.Student;
import cs151.model.Language;
import cs151.model.Comment;
import java.util.stream.Collectors;

import java.sql.*;
import java.util.*;


public class StudentDAO {
    private static final String DB_URL = "jdbc:sqlite:student.db";

    public StudentDAO() {
        // Create DB and table if not exists, insert a test student if necessary.
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
   
            // Use Java text block for CREATE TABLE 
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    fullName TEXT,
                    academicStatus TEXT,
                    employed INTEGER,
                    jobDetails TEXT,
                    programmingLanguages TEXT,
                    databases TEXT,
                    preferredRole TEXT,
                    comments TEXT,
                    flag TEXT
                )
            """);
 
            // Quick check to list number of rows present (for early diagnostics)
            try (ResultSet rs2 = stmt.executeQuery("SELECT COUNT(*) FROM students")) {
                if (rs2.next()) {
                    System.out.println("DEBUG: Students table row count = " + rs2.getInt(1));
                } else {
                    System.out.println("DEBUG: Could not determine students table row count.");
                }
            } catch (SQLException e) {
                System.err.println("DEBUG: Error reading students count: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("DEBUG: Unable to connect/create DB: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Save a student to DB
    public void save(Student student) {
        String sql = "INSERT INTO students (fullName, academicStatus, employed, jobDetails, programmingLanguages, databases, preferredRole, comments, flag) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFullName());
            pstmt.setString(2, student.getAcademicStatus());
            pstmt.setInt(3, student.isEmployed() ? 1 : 0);
            pstmt.setString(4, student.getJobDetails());
            pstmt.setString(5, student.getProgrammingLanguages().stream().map(Language::getName).collect(Collectors.joining(",")));
            pstmt.setString(6, String.join(",", student.getDatabases()));
            pstmt.setString(7, student.getPreferredRole());
            pstmt.setString(8, student.getComments().stream()
                    .map(Comment::getText)
                    .collect(Collectors.joining(",")));
            pstmt.setString(9, student.getFlag());

            int updated = pstmt.executeUpdate();
            System.out.println("DEBUG: save() executed, rows affected: " + updated);
        } catch (SQLException e) {
            System.err.println("DEBUG: save() error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Find all students
    public List<Student> findAll() {
        String sql = "SELECT * FROM students";
    //    System.out.println("DEBUG: findAll() -> SQL: " + sql);
        return executeQuery(sql, Collections.emptyList());
    }

    // Find by name (used by StudentsController.onSearchStudent)
    public List<Student> findByName(String name) {
        String sql = "SELECT * FROM students WHERE LOWER(fullName) = LOWER(?)";
    //    System.out.println("DEBUG: findByName() -> SQL: " + sql + " | param: " + name.trim());
        return executeQuery(sql, List.of(name.trim()));
    }


    // A multi-criteria search (kept intact)
    public List<Student> findByMultiCriteria(Map<String, String> criteria) {
        StringBuilder sql = new StringBuilder("SELECT * FROM students WHERE 1=1");
        List<String> values = new ArrayList<>();

        if (criteria.containsKey("Full Name")) {
            sql.append(" AND LOWER(fullName) = LOWER(?)");
            values.add(criteria.get("Full Name").trim());
        }
        if (criteria.containsKey("Academic Status")) {
            sql.append(" AND LOWER(academicStatus) LIKE LOWER(?)");
            values.add("%" + criteria.get("Academic Status").trim() + "%");
        }
        if (criteria.containsKey("Programming Language")) {
            sql.append(" AND LOWER(programmingLanguages) LIKE LOWER(?)");
            values.add("%" + criteria.get("Programming Language").trim() + "%");
        }
        if (criteria.containsKey("Database Skill")) {
            sql.append(" AND LOWER(databases) LIKE LOWER(?)");
            values.add("%" + criteria.get("Database Skill").trim() + "%");
        }
        if (criteria.containsKey("Preferred Role")) {
            sql.append(" AND LOWER(preferredRole) LIKE LOWER(?)");
            values.add("%" + criteria.get("Preferred Role").trim() + "%");
        }

     //   System.out.println("DEBUG: findByMultiCriteria() -> SQL: " + sql + " | params: " + values);
        return executeQuery(sql.toString(), values);
    }



    // Convert a ResultSet row into a Student object
    private Student rowToStudent(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String fullName = rs.getString("fullName");
        String academicStatus = rs.getString("academicStatus");
        boolean employed = rs.getInt("employed") == 1;
        String jobDetails = rs.getString("jobDetails");

        List<Language> programmingLanguages = Arrays.stream(Optional.ofNullable(rs.getString("programmingLanguages")).orElse("")
                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Language::new)
                .collect(Collectors.toList());

        List<String> databases = Arrays.stream(Optional.ofNullable(rs.getString("databases")).orElse("")
                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        String preferredRole = rs.getString("preferredRole");

        List<Comment> comments = Arrays.stream(Optional.ofNullable(rs.getString("comments")).orElse("")
                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(text -> new Comment(0, id, text))
                .collect(Collectors.toList());

        String flag = Optional.ofNullable(rs.getString("flag")).orElse("None");

        return new Student(id, fullName, academicStatus, employed, jobDetails,
                programmingLanguages, databases, preferredRole, comments, flag);
    }

    // Execute a parameterized query and return list of students
    private List<Student> executeQuery(String sql, List<String> values) {
        List<Student> students = new ArrayList<>();

        //System.out.println("DEBUG: executeQuery() -> SQL: " + sql + " | params: " + values);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // bind parameters if any
            for (int i = 0; i < values.size(); i++) {
                pstmt.setString(i + 1, values.get(i));
                System.out.println("DEBUG:   bind param " + (i+1) + " = [" + values.get(i) + "]");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                int rowCount = 0;
                while (rs.next()) {
                    Student s = rowToStudent(rs);
                    System.out.println("DEBUG:   row -> id=" + s.getId() + " name=[" + s.getFullName() + "]");
                    students.add(s);
                    rowCount++;
                }
                //System.out.println("DEBUG: executeQuery() -> rows returned = " + rowCount);
            }

        } catch (SQLException e) {
            System.err.println("DEBUG: executeQuery() SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return students;
    }
    public boolean isFullNameExists(String name) {
        String sql = "SELECT COUNT(*) FROM students WHERE LOWER(fullName) = LOWER(?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void update(Student student) {
        String sql = """
            UPDATE students SET
                fullName = ?,
                academicStatus = ?,
                employed = ?,
                jobDetails = ?,
                preferredRole = ?,
                flag = ?
            WHERE id = ?
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFullName());
            stmt.setString(2, student.getAcademicStatus());
            stmt.setBoolean(3, student.isEmployed());
            stmt.setString(4, student.getJobDetails());
            stmt.setString(5, student.getPreferredRole());
            stmt.setString(6, student.getFlag());
            stmt.setInt(7, student.getId());

            stmt.executeUpdate();

            updateLanguages(student);
            updateDatabases(student);
            updateComments(student);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Language helpers ---
    private void updateLanguages(Student student) {
        deleteLanguages(student.getId());
        for (Language lang : student.getProgrammingLanguages()) {
            saveLanguageForStudent(student.getId(), lang.getName());
        }
    }

    private void deleteLanguages(int studentId) {
        String sql = "DELETE FROM StudentLanguage WHERE studentId = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveLanguageForStudent(int studentId, String languageName) {
        String sql = "INSERT INTO StudentLanguage(studentId, languageName) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, languageName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Database helpers ---
    private void updateDatabases(Student student) {
        deleteDatabases(student.getId());
        for (String db : student.getDatabases()) {
            saveDatabaseForStudent(student.getId(), db);
        }
    }

    private void deleteDatabases(int studentId) {
        String sql = "DELETE FROM StudentDatabase WHERE studentId = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveDatabaseForStudent(int studentId, String dbName) {
        String sql = "INSERT INTO StudentDatabase(studentId, databaseName) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, dbName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Comment helpers ---
    private void updateComments(Student student) {
        deleteComments(student.getId());
        for (Comment comment : student.getComments()) {
            saveCommentForStudent(student.getId(), comment.getText());
        }
    }

    private void deleteComments(int studentId) {
        String sql = "DELETE FROM Comment WHERE studentId = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveCommentForStudent(int studentId, String text) {
        String sql = "INSERT INTO Comment(studentId, text) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, text);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Delete student ---
    public void delete(int studentId) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
}
