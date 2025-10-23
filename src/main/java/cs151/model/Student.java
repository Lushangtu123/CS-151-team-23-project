package cs151.model;

import java.util.List;
import java.util.stream.Collectors;

public class Student {
    private int id;
    private String fullName;
    private String academicStatus;
    private boolean employed;
    private String jobDetails;
    private List<Language> programmingLanguages;
    private List<String> databases;
    private String preferredRole;
    private List<Comment> comments;
    private String flag; 

    public Student(int id, String fullName, String academicStatus, boolean employed, String jobDetails,
                   List<Language> programmingLanguages, List<String> databases, String preferredRole,
                   List<Comment> comments, String flag) {
        this.id = id;
        this.fullName = fullName;
        this.academicStatus = academicStatus;
        this.employed = employed;
        this.jobDetails = jobDetails;
        this.programmingLanguages = programmingLanguages;
        this.databases = databases;
        this.preferredRole = preferredRole;
        this.comments = comments;
        this.flag = flag;
    }

    // Getters and setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getAcademicStatus() { return academicStatus; }
    public void setAcademicStatus(String academicStatus) { this.academicStatus = academicStatus; }

    public boolean isEmployed() { return employed; }
    public void setEmployed(boolean employed) { this.employed = employed; }

    public String getJobDetails() { return jobDetails; }
    public void setJobDetails(String jobDetails) { this.jobDetails = jobDetails; }

    public List<Language> getProgrammingLanguages() { return programmingLanguages; }
    public void setProgrammingLanguages(List<Language> programmingLanguages) { this.programmingLanguages = programmingLanguages; }

    public List<String> getDatabases() { return databases; }
    public void setDatabases(List<String> databases) { this.databases = databases; }

    public String getPreferredRole() { return preferredRole; }
    public void setPreferredRole(String preferredRole) { this.preferredRole = preferredRole; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }    
    
    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }
    
    public String getLanguagesAsString() {
        return programmingLanguages == null ? "" :
            programmingLanguages.stream()
                .map(lang -> lang.getName().toLowerCase())
                .collect(Collectors.joining(" | "));
    }

    public String getDatabasesAsString() {
        return databases == null ? "" :
            databases.stream()
                .map(String::toLowerCase)
                .collect(Collectors.joining(" | "));
    }

    
    
}
