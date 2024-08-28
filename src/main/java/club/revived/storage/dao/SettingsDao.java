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
                    SELECT kit, smartAutoKit, kitMessages, singleClickKitRoom, autokit FROM settings WHERE uuid = ?
                    """
            )){
                prts.setString(1, id.toString());
                final ResultSet set = prts.executeQuery();
                if(set.next()){
                    final int kit = set.getInt("kit");
                    final boolean smartAutokit = set.getBoolean("smartAutoKit");
                    final boolean messages = set.getBoolean("kitMessages");
                    final boolean singleClickKitRoom = set.getBoolean("singleClickKitRoom");
                    final boolean autokit = set.getBoolean("autokit");
                    return Optional.of(new Settings(id, smartAutokit, kit, messages, singleClickKitRoom, autokit));
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
                    INSERT INTO settings (uuid, kit, smartAutoKit, kitMessages, singleClickKitRoom, autokit)
                    VALUES (?, ?, ?, ?, ?, ?)
                    ON DUPLICATE KEY UPDATE
                    kit = VALUES(kit),
                    kitMessages = VALUES(kitMessages),
                    smartAutoKit = VALUES(smartAutoKit),
                    singleClickKitRoom = VALUES(singleClickKitRoom),
                    autokit = VALUES(autokit);
                    """)){
                statement.setString(1, settings.getOwner().toString());
                statement.setInt(2, settings.getSelectedKit());
                statement.setBoolean(3, settings.isSmartAutokit());
                statement.setBoolean(4, settings.isBroadcastMessages());
                statement.setBoolean(5, settings.isSingleClickKitRoom());
                statement.setBoolean(6, settings.isAutokit());
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
