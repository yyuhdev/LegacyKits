package club.revived.util.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlTablebuilder {

    private final SqlDataManager db;

    public SqlTablebuilder(SqlDataManager db) {
        this.db = db;
    }

    public void createKitsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS legacy_kits (" +
                "player_uuid VARCHAR(36) NOT NULL," +
                "kit_number INT NOT NULL," +
                "kit_type VARCHAR(36) NOT NULL," +
                "contents TEXT(65535) NOT NULL," +
                "PRIMARY KEY (player_uuid, kit_number, kit_type)" +
                ");";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
