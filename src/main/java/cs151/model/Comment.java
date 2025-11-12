package cs151.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Comment entity for student evaluations
 * Each comment is associated with a student and has a date stamp
 */
public class Comment {
    private int id;
    private int studentId;
    private String content;
    private LocalDate date;
    
    // Date formatter for display
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * Constructor for creating a new comment (without ID)
     * Automatically sets date to today
     * @param studentId The ID of the student this comment belongs to
     * @param content The comment content
     */
    public Comment(int studentId, String content) {
        this.studentId = studentId;
        this.content = content;
        this.date = LocalDate.now();
    }
    
    /**
     * Constructor with ID (used when loading from database)
     * @param id The unique identifier
     * @param studentId The student ID
     * @param content The comment content
     * @param date The date the comment was created
     */
    public Comment(int id, int studentId, String content, LocalDate date) {
        this.id = id;
        this.studentId = studentId;
        this.content = content;
        this.date = date;
    }
    
    /**
     * Constructor with date as string
     * @param id The unique identifier
     * @param studentId The student ID
     * @param content The comment content
     * @param dateStr The date as string (yyyy-MM-dd format)
     */
    public Comment(int id, int studentId, String content, String dateStr) {
        this.id = id;
        this.studentId = studentId;
        this.content = content;
        this.date = LocalDate.parse(dateStr, DISPLAY_FORMATTER);
    }
    
    // Getters and Setters
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
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    /**
     * Get date as formatted string
     * @return Date in yyyy-MM-dd format
     */
    public String getDateAsString() {
        return date.format(DISPLAY_FORMATTER);
    }
    
    /**
     * Validates if the comment has required fields
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return studentId > 0 
            && content != null 
            && !content.trim().isEmpty() 
            && date != null;
    }
    
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}

