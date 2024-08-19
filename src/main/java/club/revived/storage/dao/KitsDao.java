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
                SELECT kitId, contents, name
                FROM kits
                WHERE uuid = ?
                """
            )) {
                prts.setString(1, id.toString());
                final ResultSet set = prts.executeQuery();
                HashMap<Integer, Kit> kits = new HashMap<>();

                while (set.next()) {
                    final int kitId = set.getInt("kitId");
                    final String content = set.getString("contents");
                    final String name = set.getString("name");
                    Map<Integer, ItemStack> contentMap = content.isEmpty()
                            ? new HashMap<>()
                            : Serializers.base64().deserializeItemStackMap(content);
                    kits.put(kitId, new Kit(id, kitId, name, contentMap));
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
        try (Connection connection = source.getConnection()) {
            for (Integer x : kitHolder.getList().keySet()) {
                Kit kit = kitHolder.getList().get(x);
                try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO kits (uuid, kitId, contents, name)
                VALUES (?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE contents = VALUES(contents);
            """)) {
                    statement.setString(1, kit.getOwner().toString());
                    statement.setInt(2, kit.getID());
                    statement.setString(3, Serializers.base64().serializeItemStacks(kit.getContent()));
                    statement.setString(4, kit.getName());
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
