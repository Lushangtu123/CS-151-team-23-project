package cs151.data;

import cs151.model.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Comment entity
 * Handles all database operations for student comments
 */
public class CommentDAO {
    private static final String DB_URL = "jdbc:sqlite:student.db";
    
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
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Comment table initialized successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing Comment table: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Add a new comment to the database
     * @param comment The comment to add
     * @return true if successful, false otherwise
     */
    public boolean addComment(Comment comment) {
        if (!comment.isValid()) {
            return false;
        }
        
        String sql = "INSERT INTO Comment(student_id, content, date) VALUES(?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
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
    
    /**
     * Get all comments for a specific student, sorted by date (newest first)
     * @param studentId The ID of the student
     * @return List of comments for the student
     */
    public List<Comment> getCommentsByStudentId(int studentId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comment WHERE student_id = ? ORDER BY date DESC";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Comment comment = new Comment(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getString("content"),
                    rs.getString("date")
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving comments: " + e.getMessage());
            e.printStackTrace();
        }
        
        return comments;
    }
    
    /**
     * Get a single comment by ID
     * @param id The comment ID
     * @return The comment, or null if not found
     */
    public Comment getCommentById(int id) {
        String sql = "SELECT * FROM Comment WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
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
    
    /**
     * Update an existing comment
     * @param comment The comment with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateComment(Comment comment) {
        if (!comment.isValid()) {
            return false;
        }
        
        String sql = "UPDATE Comment SET content = ?, date = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, comment.getContent());
            stmt.setString(2, comment.getDateAsString());
            stmt.setInt(3, comment.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating comment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete a comment by ID
     * @param id The comment ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteComment(int id) {
        String sql = "DELETE FROM Comment WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting comment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete all comments for a specific student
     * @param studentId The student ID
     * @return true if successful, false otherwise
     */
    public boolean deleteCommentsByStudentId(int studentId) {
        String sql = "DELETE FROM Comment WHERE student_id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
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
    
    /**
     * Get the count of comments for a specific student
     * @param studentId The student ID
     * @return Number of comments
     */
    public int getCommentCountByStudentId(int studentId) {
        String sql = "SELECT COUNT(*) as count FROM Comment WHERE student_id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error counting comments: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
}

