package cs151.data;

import cs151.model.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private static final String DB_URL = "jdbc:sqlite:student.db";

    /**
     * Helper method to get a connection with foreign keys enabled
     */
    private Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }

    /**
     * Initialize Comment table if it doesn't exist
     */
    public void initTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Comment (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_id INTEGER NOT NULL,
                content TEXT NOT NULL,
                date TEXT NOT NULL,
                FOREIGN KEY (student_id) REFERENCES Student(id) ON DELETE CASCADE
            );
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Comment table initialized successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing Comment table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean addComment(Comment comment) {
        if (!comment.isValid()) return false;

        String sql = "INSERT INTO Comment(student_id, content, date) VALUES(?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, comment.getStudentId());
            stmt.setString(2, comment.getContent());
            stmt.setString(3, comment.getDateAsString());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error adding comment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Comment> getCommentsByStudentId(int studentId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comment WHERE student_id = ? ORDER BY date DESC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                comments.add(new Comment(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getString("content"),
                        rs.getString("date")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving comments: " + e.getMessage());
            e.printStackTrace();
        }

        return comments;
    }

    public Comment getCommentById(int id) {
        String sql = "SELECT * FROM Comment WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Comment(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getString("content"),
                        rs.getString("date")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving comment: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateComment(Comment comment) {
        if (!comment.isValid()) return false;

        String sql = "UPDATE Comment SET content = ?, date = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, comment.getContent());
            stmt.setString(2, comment.getDateAsString());
            stmt.setInt(3, comment.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating comment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteComment(int id) {
        String sql = "DELETE FROM Comment WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting comment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCommentsByStudentId(int studentId) {
        String sql = "DELETE FROM Comment WHERE student_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error deleting comments: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int getCommentCountByStudentId(int studentId) {
        String sql = "SELECT COUNT(*) as count FROM Comment WHERE student_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("count");

        } catch (SQLException e) {
            System.err.println("Error counting comments: " + e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }
}
