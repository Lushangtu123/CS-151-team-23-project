package cs151.model;

/**
 * Represents a Programming Language entity
 * Used for managing the list of programming languages in the system
 */
public class Language {
    private int id;
    private String name;

    /**
     * Constructor for creating a new Language
     * @param name The name of the programming language
     */
    public Language(String name) {
        this.name = name;
    }

    /**
     * Constructor with ID (used when loading from database)
     * @param id The unique identifier
     * @param name The name of the programming language
     */
    public Language(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Language language = (Language) obj;
        return name.equalsIgnoreCase(language.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}