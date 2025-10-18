package cs151.model;

import java.util.Arrays;
import java.util.List;

public class Student {
    private int id;
    private String fullName;
    private String academicStatus;
    private boolean employed;
    private String jobDetails;
    private List<String> programmingLanguages;
    private List<String> databases;
    private String preferredRole;
    private List<String> comments;
    private String flag; 

    public Student(int id, String fullName, String academicStatus, boolean employed, String jobDetails,
                   List<String> programmingLanguages, List<String> databases, String preferredRole,
                   List<String> comments, String flag) {
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

    public List<String> getProgrammingLanguages() { return programmingLanguages; }
    public void setProgrammingLanguages(List<String> programmingLanguages) { this.programmingLanguages = programmingLanguages; }

    public List<String> getDatabases() { return databases; }
    public void setDatabases(List<String> databases) { this.databases = databases; }

    public String getPreferredRole() { return preferredRole; }
    public void setPreferredRole(String preferredRole) { this.preferredRole = preferredRole; }

    public List<String> getComments() { return comments; }
    public void setComments(List<String> comments) { this.comments = comments; }

    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }

    // Serialize to pipe-delimited record
    public String toRecord() {
        return String.join("|",
            String.valueOf(id),
            fullName,
            academicStatus,
            employed ? "Employed" : "Not Employed",
            jobDetails,
            String.join(",", programmingLanguages),
            String.join(",", databases),
            preferredRole,
            String.join(",", comments),
            flag != null ? flag : "None"
        );
    }

    // Deserialize from record
    public static Student fromRecord(String line) {
        String[] parts = line.split("\\|", -1); // -1 keeps empty fields
        int id = Integer.parseInt(parts[0]);
        String fullName = parts[1];
        String academicStatus = parts[2];
        boolean employed = "Employed".equals(parts[3]);
        String jobDetails = parts[4];
        List<String> programmingLanguages = Arrays.asList(parts[5].split(","));
        List<String> databases = Arrays.asList(parts[6].split(","));
        String preferredRole = parts[7];
        List<String> comments = Arrays.asList(parts[8].split(","));
        String flag = parts.length > 9 ? parts[9] : "None";

        return new Student(id, fullName, academicStatus, employed, jobDetails,
            programmingLanguages, databases, preferredRole, comments, flag);
    }
}
