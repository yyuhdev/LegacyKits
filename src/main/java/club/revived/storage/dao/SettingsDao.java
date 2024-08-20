package club.revived.storage.dao;

import club.revived.objects.settings.Settings;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SettingsDao implements Dao<Settings>{

    private final HikariDataSource source;

    @Override
    public Optional<Settings> get(UUID id) {
        try (Connection connection = source.getConnection()){
            try(PreparedStatement prts = connection.prepareStatement("""
                    SELECT kit, smartAutoKit FROM settings WHERE uuid = ?
                    """
            )){
                prts.setString(1, id.toString());
                final ResultSet set = prts.executeQuery();
                if(set.next()){
                    final int kit = set.getInt("kit");
                    final boolean smartAutokit = set.getBoolean("smartAutoKit");
                    return Optional.of(new Settings(id, smartAutokit, kit));
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Settings> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(Settings settings) {
        try (Connection connection = source.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO settings (uuid, kit, smartAutoKit)
                    VALUES (?, ?, ?)
                    ON DUPLICATE KEY UPDATE
                    kit = VALUES(kit),
                    smartAutoKit = VALUES(smartAutoKit);
                    """)){
                statement.setString(1, settings.getOwner().toString());
                statement.setInt(2, settings.getSelectedKit());
                statement.setBoolean(3, settings.isSmartAutokit());
                statement.execute();
            }
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Settings settings, String[] params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Settings settings) {

    }
}
