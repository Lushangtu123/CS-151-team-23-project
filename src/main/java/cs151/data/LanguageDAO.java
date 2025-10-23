package cs151.data;

import cs151.model.Language;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAO {
    private static final String DB_URL = "jdbc:sqlite:student.db";

    // Initialize Language table if it doesn't exist
    public void initTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Language (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL
            );
        """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save a new language to the database
    public void saveLanguage(String name) {
        String sql = "INSERT INTO Language(name) VALUES(?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all languages from the database
    public List<Language> getAllLanguages() {
        List<Language> list = new ArrayList<>();
        String sql = "SELECT id, name FROM Language";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Language(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Check if a language already exists by name
    public boolean isLanguageExists(String name) {
        String sql = "SELECT COUNT(*) FROM Language WHERE LOWER(name) = LOWER(?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a language by ID
    public void deleteLanguage(int id) {
        String sql = "DELETE FROM Language WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update a language name by ID
    public void updateLanguage(int id, String newName) {
        String sql = "UPDATE Language SET name = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Constructor to ensure Language table exists, automatically create Language table if missing
    public LanguageDAO() {
        initTable();  
    }
}
