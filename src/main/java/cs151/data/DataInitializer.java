package cs151.data;

import cs151.model.Language;
import cs151.model.Student;
import cs151.model.Comment;
import java.util.Arrays;
import java.util.List;

/**
 * DataInitializer - Ensures database is populated with exactly 3 languages and 5 students
 * This class is called on application startup to initialize required data
 * 
 * @version 0.6
 * @team 23
 */
public class DataInitializer {
    
    private final LanguageDAO languageDAO;
    private final StudentDAO studentDAO;
    
    public DataInitializer() {
        this.languageDAO = new LanguageDAO();
        this.studentDAO = new StudentDAO();
    }
    
    /**
     * Initializes the database with exactly 3 programming languages and 5 student profiles
     * This method clears existing data and repopulates with required data
     */
    public void initializeData() {
        System.out.println("=== Starting Data Initialization ===");
        
        // Initialize languages (exactly 3)
        initializeLanguages();
        
        // Initialize students (exactly 5)
        initializeStudents();
        
        System.out.println("=== Data Initialization Complete ===");
    }
    
    /**
     * Initializes exactly 3 programming languages: Java, Python, JavaScript
     */
    private void initializeLanguages() {
        List<Language> existingLanguages = languageDAO.getAllLanguages();
        
        // If we already have exactly 3 languages, no need to reinitialize
        if (existingLanguages.size() == 3) {
            System.out.println("Database already contains 3 languages. Skipping initialization.");
            return;
        }
        
        // Clear existing languages if count is not 3
        System.out.println("Clearing existing languages...");
        for (Language lang : existingLanguages) {
            languageDAO.deleteLanguage(lang.getId());
        }
        
        // Add exactly 3 languages
        String[] languages = {"Java", "Python", "JavaScript"};
        for (String langName : languages) {
            if (!languageDAO.isLanguageExists(langName)) {
                languageDAO.saveLanguage(langName);
                System.out.println("Added language: " + langName);
            }
        }
        
        System.out.println("Languages initialized: 3 languages added");
    }
    
    /**
     * Initializes exactly 5 student profiles with diverse data
     */
    private void initializeStudents() {
        List<Student> existingStudents = studentDAO.findAll();
        
        // If we already have exactly 5 students, no need to reinitialize
        if (existingStudents.size() == 5) {
            System.out.println("Database already contains 5 students. Skipping initialization.");
            return;
        }
        
        // Clear existing students if count is not 5
        System.out.println("Clearing existing students...");
        for (Student student : existingStudents) {
            studentDAO.delete(student.getId());
        }
        
        // Create exactly 5 students
        createStudent1();
        createStudent2();
        createStudent3();
        createStudent4();
        createStudent5();
        
        System.out.println("Students initialized: 5 students added");
    }
    
    private void createStudent1() {
        Student student = new Student(
            0,  // id will be auto-generated
            "Alice Johnson",
            "Senior",
            true,
            "Software Engineer at Google",
            Arrays.asList(new Language("Java"), new Language("Python")),
            Arrays.asList("MySQL", "MongoDB"),
            "Full-Stack",
            Arrays.asList(new Comment(0, 0, "Strong problem solver"), new Comment(0, 0, "Team leader")),
            "Green"
        );
        studentDAO.save(student);
        System.out.println("Added student: Alice Johnson");
    }
    
    private void createStudent2() {
        Student student = new Student(
            0,
            "Bob Smith",
            "Junior",
            false,
            "N/A",
            Arrays.asList(new Language("JavaScript"), new Language("Python")),
            Arrays.asList("PostgreSQL", "SQLite"),
            "Front-End",
            Arrays.asList(new Comment(0, 0, "Creative designer"), new Comment(0, 0, "Quick learner")),
            "Yellow"
        );
        studentDAO.save(student);
        System.out.println("Added student: Bob Smith");
    }
    
    private void createStudent3() {
        Student student = new Student(
            0,
            "Carol Williams",
            "Graduate",
            true,
            "Data Scientist at Microsoft",
            Arrays.asList(new Language("Python"), new Language("Java")),
            Arrays.asList("MongoDB", "Oracle"),
            "Data",
            Arrays.asList(new Comment(0, 0, "Expert in machine learning"), new Comment(0, 0, "Published researcher")),
            "Red"
        );
        studentDAO.save(student);
        System.out.println("Added student: Carol Williams");
    }
    
    private void createStudent4() {
        Student student = new Student(
            0,
            "David Brown",
            "Sophomore",
            false,
            "N/A",
            Arrays.asList(new Language("Java"), new Language("JavaScript")),
            Arrays.asList("MySQL", "SQLite"),
            "Back-End",
            Arrays.asList(new Comment(0, 0, "Strong backend skills"), new Comment(0, 0, "Interested in cloud computing")),
            "None"
        );
        studentDAO.save(student);
        System.out.println("Added student: David Brown");
    }
    
    private void createStudent5() {
        Student student = new Student(
            0,
            "Eva Martinez",
            "Senior",
            true,
            "Full Stack Developer at Amazon",
            Arrays.asList(new Language("JavaScript"), new Language("Java"), new Language("Python")),
            Arrays.asList("PostgreSQL", "MongoDB", "MySQL"),
            "Full-Stack",
            Arrays.asList(new Comment(0, 0, "Excellent communicator"), new Comment(0, 0, "Mentor to junior developers")),
            "Green"
        );
        studentDAO.save(student);
        System.out.println("Added student: Eva Martinez");
    }
}

