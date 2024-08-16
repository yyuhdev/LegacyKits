package club.revived.storage.kit;

import club.revived.LegacyKits;
import club.revived.config.MessageHandler;
import dev.manere.utils.scheduler.Schedulers;
import dev.manere.utils.serializers.Serializers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class EnderchestData {

    /*

    Thanks manere for providing me with this

     */

    public static void saveAsync(String playerUUID, int kitNumber, Inventory inventory){
        Bukkit.getScheduler().runTaskAsynchronously(LegacyKits.getInstance(), () -> save(playerUUID, kitNumber, inventory));
    }

    public static void load(Player player, int kit){
        Inventory inventory = player.getEnderChest();
        contentsAsync(player, kit, contents -> {
            if (contents.isEmpty()) {
                player.sendRichMessage(MessageHandler.of("LOADING_EMPTY_KIT"));
                return;
            }
            player.sendRichMessage(MessageHandler.of("ENDERCHEST_LOAD")
                    .replace("<ec>", String.valueOf(kit))
            );
            for (Player global : Bukkit.getOnlinePlayers()) {
                if(global.getLocation().getNearbyPlayers(250).contains(player))
                    global.sendRichMessage(MessageHandler.of("ENDERCHEST_LOAD_BROADCAST")
                            .replace("<player>", player.getName())
                            .replace("<ec>", String.valueOf(kit))
                    );
            }
            player.getEnderChest().setContents(contents.values().toArray(new ItemStack[0]));
        });
    }

    public static void contentsAsync(Player player, int kitNumber, Consumer<Map<Integer, ItemStack>> callback) {
        Schedulers.async().execute(() -> {
            Map<Integer, ItemStack> kitContents = contents(player.getUniqueId().toString(), kitNumber);
            Schedulers.sync().execute(() -> callback.accept(kitContents));
        });
    }

    public static Map<Integer, ItemStack> contents(String playerUUID, int kitNumber) {
        try (Connection connection = LegacyKits.getSql().getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "SELECT contents FROM legacy_kits WHERE player_uuid = ? AND kit_number = ? AND kit_type = ?")) {
            stmt.setString(1, playerUUID);
            stmt.setInt(2, kitNumber);
            stmt.setString(3, "enderchest");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String data = rs.getString("contents");
                    return Serializers.base64().deserializeItemStackMap(data);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new HashMap<>();
    }

    public static void save(String playerUUID, int kitNumber, Inventory inventory) {
        Map<Integer, ItemStack> contents = new HashMap<>();
        for (int i = 0; i < 27; i++) {
            ItemStack item = inventory.getItem(i);
            contents.put(i, Objects.requireNonNullElseGet(item, () -> new ItemStack(Material.AIR)));
        }
        try (Connection connection = LegacyKits.getSql().getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO legacy_kits " +
                             "(player_uuid, kit_number, kit_type, contents) " +
                             "VALUES (?, ?, ?, ?) " +
                             "ON DUPLICATE KEY UPDATE " +
                             "contents = ?")) {

            String data = Serializers.base64().serializeItemStacks(contents);
            stmt.setString(1, playerUUID);
            stmt.setInt(2, kitNumber);
            stmt.setString(3, "enderchest");
            stmt.setString(4, data);
            stmt.setString(5, data);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}