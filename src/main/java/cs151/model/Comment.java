package cs151.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a comment attached to a student.
 * Each comment has text, a timestamp, and a reference to a student.
 */
public class Comment {
    private int id;
    private int studentId;
    private String comment;
    private LocalDateTime timestamp;

    public Comment(int id, int studentId, String comment, LocalDateTime timestamp) {
        this.id = id;
        this.studentId = studentId;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public Comment(String comment) {
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Format timestamp for easy display
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    @Override
    public String toString() {
        return "[" + getFormattedTimestamp() + "] " + comment;
    }
}
