package cs151.model;

public class Comment {
    private int id;
    private int studentId;
    private String text;

    public Comment() {}

    public Comment(int id, int studentId, String text) {
        this.id = id;
        this.studentId = studentId;
        this.text = text;
    }

    //  constructor for convenience
    public Comment(String text) {
        this.text = text;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }

    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }
}
