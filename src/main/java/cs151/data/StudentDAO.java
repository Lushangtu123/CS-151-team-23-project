package cs151.data;

import cs151.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentDAO {
    private static final String DB_URL = "jdbc:sqlite:student.db";

    public StudentDAO() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Student student) {
        String sql = """
            INSERT INTO students (
                fullName, academicStatus, employed, jobDetails,
                programmingLanguages, databases, preferredRole,
                comments, flag
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFullName());
            pstmt.setString(2, student.getAcademicStatus());
            pstmt.setInt(3, student.isEmployed() ? 1 : 0);
            pstmt.setString(4, student.getJobDetails());
            pstmt.setString(5, String.join(",", student.getProgrammingLanguages()));
            pstmt.setString(6, String.join(",", student.getDatabases()));
            pstmt.setString(7, student.getPreferredRole());
            pstmt.setString(8, String.join(",", student.getComments()));
            pstmt.setString(9, student.getFlag()); //  store flag as string

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("fullName"),
                    rs.getString("academicStatus"),
                    rs.getInt("employed") == 1,
                    rs.getString("jobDetails"),
                    Arrays.asList(rs.getString("programmingLanguages").split(",")),
                    Arrays.asList(rs.getString("databases").split(",")),
                    rs.getString("preferredRole"),
                    Arrays.asList(rs.getString("comments").split(",")),
                    rs.getString("flag") //  read flag as string
                );
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public List<Student> findByName(String name) {
        List<Student> results = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE fullName LIKE ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                    rs.getInt("id"),
                    rs.getString("fullName"),
                    rs.getString("academicStatus"),
                    rs.getInt("employed") == 1,
                    rs.getString("jobDetails"),
                    Arrays.asList(rs.getString("programmingLanguages").split(",")),
                    Arrays.asList(rs.getString("databases").split(",")),
                    rs.getString("preferredRole"),
                    Arrays.asList(rs.getString("comments").split(",")),
                    rs.getString("flag") //  read flag as string
                );
                results.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
    public boolean isFullNameExists(String fullName) {
        String sql = "SELECT COUNT(*) FROM students WHERE LOWER(fullName) = LOWER(?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fullName.trim());
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
