package cs151.data;

import cs151.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Student entity
 * Handles all database operations for students
 */
public class StudentDAO {
    private static final String DB_URL = "jdbc:sqlite:student.db";

    /**
     * Initialize Student table if it doesn't exist
     */
    public void initTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Student (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                academicStatus TEXT NOT NULL,
                email TEXT,
                languages TEXT,
                dbSkills TEXT,
                role TEXT,
                interests TEXT
            );
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
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
            INSERT INTO Student(name, academicStatus, email, languages, dbSkills, role, interests)
            VALUES(?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getAcademicStatus());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getLanguagesAsString());
            stmt.setString(5, student.getDbSkills());
            stmt.setString(6, student.getRole());
            stmt.setString(7, student.getInterests());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
                student.setInterests(rs.getString("interests"));
                
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
                student.setInterests(rs.getString("interests"));
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
            SET name = ?, academicStatus = ?, email = ?, languages = ?, 
                dbSkills = ?, role = ?, interests = ?
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
            stmt.setString(7, student.getInterests());
            stmt.setInt(8, student.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
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

