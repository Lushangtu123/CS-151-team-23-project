package cs151.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Student entity
 * Used for managing student profiles in the system
 */
public class Student {
    private int id;
    private String name;                    // Required
    private String academicStatus;          // Required (e.g., Freshman, Sophomore, Junior, Senior, Graduate)
    private String email;                   // Optional
    private List<String> languages;         // Optional - programming languages the student knows
    private String dbSkills;                // Optional - database skills
    private String role;                    // Optional - role in team/project
    private String interests;               // Optional - areas of interest

    /**
     * Constructor for creating a new Student (without ID)
     * @param name Student's name (required)
     * @param academicStatus Student's academic status (required)
     */
    public Student(String name, String academicStatus) {
        this.name = name;
        this.academicStatus = academicStatus;
        this.languages = new ArrayList<>();
    }

    /**
     * Constructor with ID (used when loading from database)
     * @param id The unique identifier
     * @param name Student's name
     * @param academicStatus Student's academic status
     */
    public Student(int id, String name, String academicStatus) {
        this.id = id;
        this.name = name;
        this.academicStatus = academicStatus;
        this.languages = new ArrayList<>();
    }

    /**
     * Full constructor with all fields
     */
    public Student(int id, String name, String academicStatus, String email, 
                   List<String> languages, String dbSkills, String role, String interests) {
        this.id = id;
        this.name = name;
        this.academicStatus = academicStatus;
        this.email = email;
        this.languages = languages != null ? languages : new ArrayList<>();
        this.dbSkills = dbSkills;
        this.role = role;
        this.interests = interests;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getDbSkills() {
        return dbSkills;
    }

    public void setDbSkills(String dbSkills) {
        this.dbSkills = dbSkills;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    /**
     * Get languages as a comma-separated string for display
     */
    public String getLanguagesAsString() {
        return languages != null && !languages.isEmpty() 
            ? String.join(", ", languages) 
            : "";
    }

    /**
     * Set languages from a comma-separated string
     */
    public void setLanguagesFromString(String languagesStr) {
        this.languages = new ArrayList<>();
        if (languagesStr != null && !languagesStr.trim().isEmpty()) {
            String[] parts = languagesStr.split(",");
            for (String part : parts) {
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    this.languages.add(trimmed);
                }
            }
        }
    }

    /**
     * Validates if the student profile has all required fields
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() 
            && academicStatus != null && !academicStatus.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", academicStatus='" + academicStatus + '\'' +
                ", email='" + email + '\'' +
                ", languages=" + languages +
                ", dbSkills='" + dbSkills + '\'' +
                ", role='" + role + '\'' +
                ", interests='" + interests + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

