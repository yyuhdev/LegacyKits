package club.revived.storage.dao;

import club.revived.LegacyKits;
import club.revived.objects.Kit;
import club.revived.objects.KitHolder;
import club.revived.objects.KitType;
import com.zaxxer.hikari.HikariDataSource;
import dev.manere.utils.serializers.Serializers;
import lombok.RequiredArgsConstructor;

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
        try (Connection connection = source.getConnection()){
            try(PreparedStatement prts = connection.prepareStatement("""
                    SELECT kit, kit_type, contents FROM kits WHERE uuid = ?
                    """
            )){
                prts.setString(1, id.toString());
                final ResultSet set = prts.executeQuery();
                List<Kit> kits = new ArrayList<>();
                while (set.next()) {
                    final int kit = set.getInt("kit");
                    final KitType type = KitType.valueOf(set.getString("kit_type"));
                    final String content = set.getString("contents");
                    if(content.isEmpty()) {
                        kits.add(new Kit(id, kit, new HashMap<>(), type));
                        continue;
                    }
                    kits.add(new Kit(id, kit, Serializers.base64().deserializeItemStackMap(content), type));
                }
                return Optional.of(new KitHolder(id, kits));
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<KitHolder> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(KitHolder kitHolder) {
        LegacyKits.log("Saving kits into database" + kitHolder.toString());
        try (Connection connection = source.getConnection()) {
            for(Kit kit : kitHolder.getList()){
                try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO kits (uuid,kit,kit_type,contents) VALUES (?,?,?,?)
                ON DUPLICATE KEY UPDATE contents = ?;
                """)){
                    statement.setString(1, kit.getOwner().toString());
                    statement.setInt(2, kit.getID());
                    statement.setString(3, kit.getType().toString());
                    statement.setString(4, Serializers.base64().serializeItemStacks(kit.getContent()));
                    statement.setString(5, Serializers.base64().serializeItemStacks(kit.getContent()));
                    statement.execute();
                }
            }
        } catch (SQLException e){
            throw new RuntimeException();
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
