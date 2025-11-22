package cs151.data;

import cs151.model.Student;
import cs151.model.Comment;
import java.util.ArrayList;
import java.util.List;

/**
 * Initialize database with predefined students and comments for Version 0.9
 */
public class DataInitializer {
    private static final StudentDAO studentDao = new StudentDAO();
    private static final CommentDAO commentDao = new CommentDAO();
    private static final DataInfoDAO dataDao = new DataInfoDAO();

    /**
     * Initialize database with 6 students (3 blacklisted, 3 whitelisted)
     * and meaningful comments for each student
     */
    public static void initializeData() {
        // Initialize tables
        studentDao.initTable();
        commentDao.initTable();
        dataDao.initTable();

        // Check if table is initialized
        if (dataDao.isInitialized()) {
            System.out.println("Database already initialized.");
            return;
        }

        // Clear existing data for fresh start
        System.out.println("Initializing database with 6 students...");

        // Create 3 Whitelisted Students
        createWhitelistedStudents();

        // Create 3 Blacklisted Students
        createBlacklistedStudents();

        // Mark initialization
        dataDao.setInitialized();

        System.out.println("Data initialization complete!");
    }

    private static void createWhitelistedStudents() {
        // Student 1: Alice Johnson - Whitelist
        Student alice = new Student("Alice Johnson", "Senior");
        //alice.setEmail("alice.johnson@sjsu.edu");
        alice.setLanguagesFromString("Java, Python, C++");
        alice.setDbSkills("MySQL, PostgreSQL, MongoDB");
        alice.setRole("Full-Stack");
        alice.setEmploymentStatus("Not Employed");
        alice.setJobDetails("");
        alice.setFlag("Whitelist");

        if (studentDao.saveStudent(alice)) {
            // Add comments for Alice
            addComment(alice.getId(), "Alice demonstrates exceptional programming skills and consistently delivers high-quality code. Her understanding of object-oriented design principles is outstanding, and she actively participates in code reviews.");
            addComment(alice.getId(), "Excellent team player who mentors junior developers and contributes innovative solutions to complex problems. Her recent project on microservices architecture was particularly impressive and showed deep technical knowledge.");
            addComment(alice.getId(), "Strong communication skills and ability to explain technical concepts clearly. Alice would be an excellent addition to any software development team and has shown great leadership potential in group projects.");
        }

        // Student 2: Bob Smith - Whitelist
        Student bob = new Student("Bob Smith", "Junior");
        //bob.setEmail("bob.smith@sjsu.edu");
        bob.setLanguagesFromString("JavaScript, Python, Ruby");
        bob.setDbSkills("MongoDB, Redis, SQLite");
        bob.setRole("Back-End");
        bob.setEmploymentStatus("Not Employed");
        bob.setJobDetails("");
        bob.setFlag("Whitelist");

        if (studentDao.saveStudent(bob)) {
            addComment(bob.getId(), "Bob shows remarkable progress in backend development and has mastered RESTful API design patterns. His dedication to learning new technologies is commendable, particularly his recent work with Node.js and Express framework.");
            addComment(bob.getId(), "Demonstrates strong problem-solving abilities and writes clean, maintainable code. Bob's recent contributions to our database optimization project resulted in significant performance improvements and reduced query response times.");
            addComment(bob.getId(), "Reliable team member who consistently meets deadlines and produces quality work. His attention to detail and thorough testing practices have helped prevent numerous bugs from reaching production environments.");
        }

        // Student 3: Carol Martinez - Whitelist
        Student carol = new Student("Carol Martinez", "Sophomore");
        //carol.setEmail("carol.martinez@sjsu.edu");
        carol.setLanguagesFromString("Java, C++, SQL");
        carol.setDbSkills("Oracle, MySQL, PostgreSQL");
        carol.setRole("Data");
        carol.setEmploymentStatus("Not Employed");
        carol.setJobDetails("");
        carol.setFlag("Whitelist");

        if (studentDao.saveStudent(carol)) {
            addComment(carol.getId(), "Carol exhibits outstanding database design skills and deep understanding of normalization principles. Her work on optimizing complex queries has demonstrated advanced SQL proficiency beyond her current academic level.");
            addComment(carol.getId(), "Shows excellent analytical thinking and systematic approach to problem-solving. Carol's database performance tuning project achieved impressive results, reducing query execution time by over forty percent through strategic indexing.");
            addComment(carol.getId(), "Highly motivated student with strong work ethic and eagerness to learn advanced database concepts. Her documentation of database schemas and procedures is thorough and serves as excellent reference material for the team.");
        }
    }

    private static void createBlacklistedStudents() {
        // Student 4: David Lee - Blacklist
        Student david = new Student("David Lee", "Senior");
        //david.setEmail("david.lee@sjsu.edu");
        david.setLanguagesFromString("Python, JavaScript");
        david.setDbSkills("MySQL, SQLite");
        david.setRole("Front-End");
        david.setEmploymentStatus("Not Employed");
        david.setJobDetails("");
        david.setFlag("Blacklist");

        if (studentDao.saveStudent(david)) {
            addComment(david.getId(), "David frequently misses project deadlines and submits incomplete work. Multiple team members have reported communication difficulties and lack of collaboration. His code often lacks proper documentation and contains numerous bugs that require extensive revision.");
            addComment(david.getId(), "Demonstrates poor understanding of fundamental programming concepts despite being a senior student. Assignments are frequently submitted late with minimal effort shown. Recent group project suffered significant setbacks due to David's lack of contribution and preparation.");
            addComment(david.getId(), "Attendance issues and lack of engagement during team meetings have negatively impacted group dynamics. Code quality is consistently below acceptable standards with inadequate testing. Additional supervision and mentoring have not resulted in noticeable improvement.");
        }

        // Student 5: Emma Wilson - Blacklist
        Student emma = new Student("Emma Wilson", "Junior");
        //emma.setEmail("emma.wilson@sjsu.edu");
        emma.setLanguagesFromString("Java, C++");
        emma.setDbSkills("MySQL");
        emma.setRole("Full-Stack");
        emma.setEmploymentStatus("Not Employed");
        emma.setJobDetails("");
        emma.setFlag("Blacklist");

        if (studentDao.saveStudent(emma)) {
            addComment(emma.getId(), "Emma shows minimal effort in completing assignments and demonstrates poor time management skills. Her code submissions are frequently incomplete and lack basic error handling. Team members report difficulty working with her due to unresponsive communication.");
            addComment(emma.getId(), "Inconsistent work quality and failure to follow project requirements have been persistent issues. Recent code review revealed significant problems with logic implementation and violation of coding standards. Multiple reminders about deadlines have been necessary.");
            addComment(emma.getId(), "Limited technical growth despite receiving detailed feedback on previous assignments. Emma's participation in collaborative projects has been minimal, often leaving substantial work for other team members. Professional development and accountability need significant improvement.");
        }

        // Student 6: Frank Brown - Blacklist
        Student frank = new Student("Frank Brown", "Sophomore");
        //frank.setEmail("frank.brown@sjsu.edu");
        frank.setLanguagesFromString("Python");
        frank.setDbSkills("SQLite");
        frank.setRole("Data");
        frank.setEmploymentStatus("Not Employed");
        frank.setJobDetails("");
        frank.setFlag("Blacklist");

        if (studentDao.saveStudent(frank)) {
            addComment(frank.getId(), "Frank demonstrates fundamental knowledge gaps that hinder project progress. His inability to work independently requires constant supervision and assistance. Code submissions show lack of understanding of basic programming principles and best practices.");
            addComment(frank.getId(), "Repeated instances of plagiarism and academic dishonesty have been documented. Frank's work often contains code copied from online sources without proper attribution or understanding. This behavior violates academic integrity policies and professional ethics standards.");
            addComment(frank.getId(), "Negative attitude and resistance to feedback create challenges in collaborative environments. Frank's unwillingness to accept constructive criticism has prevented improvement. His presence in group projects has consistently resulted in conflict and reduced team productivity.");
        }
    }

    private static void addComment(int studentId, String content) {
        Comment comment = new Comment(studentId, content);
        commentDao.addComment(comment);
    }
}
