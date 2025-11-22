package cs151.data;

import java.sql.*;

public class DataInfoDAO {
    private static final String DB_URL = "jdbc:sqlite:student.db";

    public void initTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS DataInfo (
                    id INTEGER PRIMARY KEY CHECK (id = 1),
                    initialized INTEGER NOT NULL
                );
            """);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isInitialized() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT initialized FROM DataInfo WHERE id = 1")) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("initialized") == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setInitialized() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT OR REPLACE INTO DataInfo (id, initialized) VALUES (1, 1)")) {
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
