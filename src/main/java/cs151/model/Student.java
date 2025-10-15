package cs151.model;

public class Student {
    private int id;
    private String fullName;
    private String academicStatus;
    private String preferredRole;
    private Language language;
    private String dbSkill;
    private boolean isEmployed;
    private String jobDetail;
    private String evaluationComment;
    private boolean isWhitelisted;   // Mutually exclusive with isBlacklisted
    private boolean isBlacklisted;

    // Constructors
    public Student(String fullName, String academicStatus, String preferredRole,
                   Language language, String dbSkill, boolean isEmployed,
                   String jobDetail, String evaluationComment,
                   boolean isWhitelisted, boolean isBlacklisted) {

        if (isWhitelisted && isBlacklisted) {
            throw new IllegalArgumentException("A student cannot be both whitelisted and blacklisted.");
        }

        this.fullName = fullName;
        this.academicStatus = academicStatus;
        this.preferredRole = preferredRole;
        this.language = language;
        this.dbSkill = dbSkill;
        this.isEmployed = isEmployed;
        this.jobDetail = jobDetail;
        this.evaluationComment = evaluationComment;
        this.isWhitelisted = isWhitelisted;
        this.isBlacklisted = isBlacklisted;
    }

    public Student(int id, String fullName, String academicStatus, String preferredRole,
                   Language language, String dbSkill, boolean isEmployed,
                   String jobDetail, String evaluationComment,
                   boolean isWhitelisted, boolean isBlacklisted) {
        this(fullName, academicStatus, preferredRole, language, dbSkill, isEmployed,
                jobDetail, evaluationComment, isWhitelisted, isBlacklisted);
        this.id = id;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    public String getPreferredRole() {
        return preferredRole;
    }

    public void setPreferredRole(String preferredRole) {
        this.preferredRole = preferredRole;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getDbSkill() {
        return dbSkill;
    }

    public void setDbSkill(String dbSkill) {
        this.dbSkill = dbSkill;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public void setEmployed(boolean employed) {
        isEmployed = employed;
    }

    public String getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(String jobDetail) {
        this.jobDetail = jobDetail;
    }

    public String getEvaluationComment() {
        return evaluationComment;
    }

    public void setEvaluationComment(String evaluationComment) {
        this.evaluationComment = evaluationComment;
    }

    public boolean isWhitelisted() {
        return isWhitelisted;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    public void setWhitelisted(boolean isWhitelisted) {
        if (isWhitelisted && this.isBlacklisted) {
            throw new IllegalStateException("Cannot whitelist a student who is already blacklisted.");
        }
        this.isWhitelisted = isWhitelisted;
    }

    public void setBlacklisted(boolean isBlacklisted) {
        if (isBlacklisted && this.isWhitelisted) {
            throw new IllegalStateException("Cannot blacklist a student who is already whitelisted.");
        }
        this.isBlacklisted = isBlacklisted;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fullName='" + fullName + '\'' +
                ", academicStatus='" + academicStatus + '\'' +
                ", preferredRole='" + preferredRole + '\'' +
                ", language=" + (language != null ? language.getName() : "null") +
                ", dbSkill='" + dbSkill + '\'' +
                ", isEmployed=" + isEmployed +
                ", jobDetail='" + jobDetail + '\'' +
                ", evaluationComment='" + evaluationComment + '\'' +
                ", isWhitelisted=" + isWhitelisted +
                ", isBlacklisted=" + isBlacklisted +
                '}';
    }
}
