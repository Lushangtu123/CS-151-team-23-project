package cs151.data;

import cs151.model.Student;
import cs151.model.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * Data Access Object for Student entity
 * Handles all database operations for students
 */
public class StudentDAO {
    private static final String DB_URL = "jdbc:sqlite:student.db";
    private final CommentDAO commentDao = new CommentDAO();

    /**
     * Initialize Student table if it doesn't exist
     */
    public void initTable() {
        String studentSql = """
            CREATE TABLE IF NOT EXISTS Student (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                academicStatus TEXT NOT NULL,
                email TEXT,
                languages TEXT,
                dbSkills TEXT,
                role TEXT,
                employmentStatus TEXT,
                jobDetails TEXT,
                flag TEXT
            );
        """;

        String commentSql = """
                CREATE TABLE IF NOT EXISTS Comments (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                studentId INTEGER NOT NULL,
                comment TEXT NOT NULL,
                timestamp DATETIME NOT NULL,
                FOREIGN KEY(studentId) REFERENCES Student(id)
                );
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(studentSql);
            stmt.execute(commentSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a new student to the database
     * @param student The student to save
     * @return true if successful, false otherwise
     */
    public boolean saveStudent(Student student) {
        if (!student.isValid()) {
            return false;
        }

        String sql = """
            INSERT INTO Student(name, academicStatus, email, languages, dbSkills, role,
                                        employmentStatus, jobDetails, flag)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getAcademicStatus());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getLanguagesAsString());
            stmt.setString(5, student.getDbSkills());
            stmt.setString(6, student.getRole());
            stmt.setString(7, student.getEmploymentStatus());
            stmt.setString(8, student.getJobDetails());
            stmt.setString(9, student.getFlag());

            int rows = stmt.executeUpdate();
            if (rows == 0) return false;

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int studentId = rs.getInt(1);
                    student.setId(studentId);
                    // Save comments using CommentDAO
                    for (Comment c : student.getComments()) {
                        Comment newComment = new Comment(studentId, c.getComment());
                        commentDao.addComment(newComment);
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add comment in student profiles
     */
    public boolean addComment(int studentId, String comment) {
        String sql = "INSERT INTO Comments (studentId, comment, timestamp) VALUES(?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, comment);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all comments for a specific student
     */
    public List<Comment> getCommentsForStudent(int studentId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comments WHERE studentId = ? ORDER BY timestamp ASC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment c = new Comment(
                        rs.getInt("id"),
                        rs.getInt("studentId"),
                        rs.getString("comment"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                );
                comments.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comments;
    }

    /**
     * Retrieve all students from the database, sorted by name (A-Z, case-insensitive)
     * @return List of all students sorted alphabetically by name
     */
    public List<Student> getAllStudentsSortedByName() {
        return getAllStudents("name", "ASC");
    }

    /**
     * Retrieve all students with custom sorting
     * @param sortBy Column to sort by (e.g., "name", "academicStatus")
     * @param order Sort order ("ASC" or "DESC")
     * @return List of students sorted according to parameters
     */
    public List<Student> getAllStudents(String sortBy, String order) {
        List<Student> list = new ArrayList<>();
        
        // Validate sortBy to prevent SQL injection
        String validSortBy = switch (sortBy.toLowerCase()) {
            case "name", "academicstatus", "email" -> sortBy;
            default -> "name";
        };
        
        // Validate order
        String validOrder = "DESC".equalsIgnoreCase(order) ? "DESC" : "ASC";
        
        String sql = "SELECT * FROM Student ORDER BY " + validSortBy + " COLLATE NOCASE " + validOrder;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("academicStatus")
                );
                student.setEmail(rs.getString("email"));
                
                // Parse languages from comma-separated string
                String languagesStr = rs.getString("languages");
                if (languagesStr != null && !languagesStr.isEmpty()) {
                    student.setLanguagesFromString(languagesStr);
                }
                
                student.setDbSkills(rs.getString("dbSkills"));
                student.setRole(rs.getString("role"));
                student.setEmploymentStatus(rs.getString("employmentStatus"));
                student.setJobDetails(rs.getString("jobDetails"));
                student.setFlag(rs.getString("flag"));
                student.setComments(getCommentsForStudent(student.getId()));

                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Get a student by ID
     * @param id The student ID
     * @return The student, or null if not found
     */
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM Student WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("academicStatus")
                );
                student.setEmail(rs.getString("email"));
                student.setLanguagesFromString(rs.getString("languages"));
                student.setDbSkills(rs.getString("dbSkills"));
                student.setRole(rs.getString("role"));
                student.setEmploymentStatus(rs.getString("employmentStatus"));
                student.setJobDetails(rs.getString("jobDetails"));
                student.setFlag(rs.getString("flag"));
                student.setComments(getCommentsForStudent(id));
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update an existing student
     * @param student The student with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateStudent(Student student) {
        if (!student.isValid()) {
            return false;
        }

        String sql = """
            UPDATE Student
            SET name = ?, academicStatus = ?, email = ?, languages = ?,\s
                   dbSkills = ?, role = ?, employmentStatus = ?, jobDetails = ?,\s
                    flag = ?
            WHERE id = ?
        """;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getAcademicStatus());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getLanguagesAsString());
            stmt.setString(5, student.getDbSkills());
            stmt.setString(6, student.getRole());
            stmt.setString(7, student.getEmploymentStatus());
            stmt.setString(8, student.getJobDetails());
            stmt.setString(9, student.getFlag());
            stmt.setInt(10, student.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) return false;

            for (Comment c : student.getComments()) {
                if (c.getId() == 0) {
                    addComment(student.getId(), c.getComment());
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete a student by ID
     * @param id The student ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM Student WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validate student profile
     * @param student The student to validate
     * @return Error message if invalid, empty string if valid
     */
    public String validateStudent(Student student) {
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return "Name is required";
        }
        
        if (student.getAcademicStatus() == null || student.getAcademicStatus().trim().isEmpty()) {
            return "Academic Status is required";
        }
        
        // Validate email format if provided
        if (student.getEmail() != null && !student.getEmail().trim().isEmpty()) {
            String email = student.getEmail().trim();
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return "Invalid email format";
            }
        }
        
        return ""; // Valid
    }

    /**
     * Check if a language is referenced by any student
     * @param languageName The language name to check
     * @return true if the language is used by at least one student
     */
    public boolean isLanguageReferenced(String languageName) {
        String sql = "SELECT COUNT(*) FROM Student WHERE languages LIKE ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Check if the language appears in the comma-separated list
            stmt.setString(1, "%" + languageName + "%");
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove a language from all students who have it
     * @param languageName The language to remove
     * @return Number of students updated
     */
    public int unassignLanguageFromAllStudents(String languageName) {
        List<Student> students = getAllStudents("name", "ASC");
        int updatedCount = 0;
        
        for (Student student : students) {
            List<String> languages = student.getLanguages();
            if (languages.contains(languageName)) {
                languages.remove(languageName);
                student.setLanguages(languages);
                if (updateStudent(student)) {
                    updatedCount++;
                }
            }
        }
        
        return updatedCount;
    }
}

