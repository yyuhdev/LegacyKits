package club.revived.storage.dao;

import club.revived.LegacyKits;
import club.revived.objects.Kit;
import club.revived.objects.KitHolder;
import com.zaxxer.hikari.HikariDataSource;
import dev.manere.utils.serializers.Serializers;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
public class KitsDao implements Dao<KitHolder> {

    private final HikariDataSource source;

    @Override
    public Optional<KitHolder> get(UUID id) {
        try (Connection connection = source.getConnection()) {
            try (PreparedStatement prts = connection.prepareStatement("""
                SELECT kit, kit_type, contents, ec_contents
                FROM kits
                WHERE uuid = ?
                """
            )) {
                prts.setString(1, id.toString());
                final ResultSet set = prts.executeQuery();
                List<Kit> kits = new ArrayList<>();

                while (set.next()) {
                    final int kitId = set.getInt("kit");
                    final String ec_contents = set.getString("ec_contents");
                    final String content = set.getString("contents");
                    Map<Integer, ItemStack> contentMap = content.isEmpty()
                            ? new HashMap<>()
                            : Serializers.base64().deserializeItemStackMap(content);
                    Map<Integer, ItemStack> ecContentMap = content.isEmpty()
                            ? new HashMap<>()
                            : Serializers.base64().deserializeItemStackMap(content);
                    kits.add(new Kit(id, kitId, contentMap, ecContentMap));
                }

                if (!kits.isEmpty()) {
                    return Optional.of(new KitHolder(id, kits));
                } else {
                    return Optional.empty();
                }

            }
        } catch (SQLException e) {
            LegacyKits.log("Failed to load KitHolder for UUID: " + id + " due to SQL error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<KitHolder> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(KitHolder kitHolder) {
        LegacyKits.log("Saving kits into database: " + kitHolder.toString());
        try (Connection connection = source.getConnection()) {
            for (Kit kit : kitHolder.getList()) {
                try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO kits (uuid, kit, contents, ec_contents)
                VALUES (?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE contents = VALUES(contents), ec_contents = VALUES(ec_contents);
            """)) {
                    statement.setString(1, kit.getOwner().toString());
                    statement.setInt(2, kit.getID());
                    statement.setString(3, Serializers.base64().serializeItemStacks(kit.getContent()));
                    statement.setString(4, Serializers.base64().serializeItemStacks(kit.getEnderchestContent()));
                    statement.setString(5, Serializers.base64().serializeItemStacks(kit.getContent()));
                    statement.setString(6, Serializers.base64().serializeItemStacks(kit.getEnderchestContent()));
                    statement.execute();
                }
            }
        } catch (SQLException e) {
            LegacyKits.log("Failed to save kits: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(KitHolder kitHolder, String[] params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(KitHolder kitHolder) {
        throw new UnsupportedOperationException();
    }
}
