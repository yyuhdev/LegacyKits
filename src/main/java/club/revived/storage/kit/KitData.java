    package club.revived.storage.kit;

    import club.revived.LegacyKits;
    import club.revived.util.MessageUtil;
    import dev.manere.utils.scheduler.Schedulers;
    import dev.manere.utils.serializers.Serializers;
    import dev.manere.utils.text.color.TextStyle;
    import org.bukkit.Bukkit;
    import org.bukkit.Material;
    import org.bukkit.Sound;
    import org.bukkit.entity.Player;
    import org.bukkit.inventory.Inventory;
    import org.bukkit.inventory.ItemStack;

    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.*;
    import java.util.function.Consumer;

    public class KitData {

        /*

        Thanks manere for providing me with this

         */

        public static void saveAsync(String playerUUID, int kitNumber, Inventory inventory){
            Bukkit.getScheduler().runTaskAsynchronously(LegacyKits.getInstance(), () -> save(playerUUID, kitNumber, inventory));
        }

        public static void load(Player player, int kit){
            contentsAsync(player, kit, contents -> {
                if (contents.isEmpty()) {
                    player.sendActionBar(TextStyle.color("<#ff0000>That kit is empty!"));
                    return;
                }
                player.setHealth(20);
                player.setFoodLevel(20);
                player.getActivePotionEffects().clear();
                player.setSaturation(20);
                MessageUtil.send(player, "messages.kit_load");
                player.playSound(player, Sound.ITEM_ARMOR_EQUIP_NETHERITE,1,1);
                LegacyKits.getInstance().lastUsedKits.put(player.getUniqueId(), kit);
                for (Player global : Bukkit.getOnlinePlayers()) {
                    if(global.getLocation().getNearbyPlayers(250).contains(player))
                        MessageUtil.broadcast(player, global, "broadcast_messages.kit_load");
                }
                player.getInventory().setContents(contents.values().toArray(new ItemStack[0]));
//                for (Map.Entry<Integer, ItemStack> entry : contents.entrySet()) {
//                    int slot = entry.getKey();
//                    ItemStack item = entry.getValue();
//
//                    if (slot >= 0 && slot < inventory.getSize()) {
//                        inventory.setItem(slot, item);
//                    } else {
//                        return;
//                    }
//                }
            });
        }

        public static void contentsAsync(Player player, int kitNumber, Consumer<Map<Integer, ItemStack>> callback) {
           Bukkit.getAsyncScheduler().runNow(LegacyKits.getInstance(), st -> {
                Map<Integer, ItemStack> kitContents = contents(player.getUniqueId().toString(), kitNumber);
                Schedulers.sync().execute(() -> callback.accept(kitContents));
            });
        }

        public static void contentsAsync(String playerUUID, int kitNumber, Consumer<Map<Integer, ItemStack>> callback) {
            Schedulers.async().execute(() -> {
                Map<Integer, ItemStack> kitContents = contents(playerUUID, kitNumber);
                Schedulers.sync().execute(() -> callback.accept(kitContents));
            });
        }

        public static Map<Integer, ItemStack> contents(String playerUUID, int kitNumber) {
            ResultSet rs = null;
            try {
                try (PreparedStatement stmt = LegacyKits.getSql().getConnection().prepareStatement("SELECT contents FROM legacy_kits WHERE player_uuid = ? AND kit_number = ? AND kit_type = ?")) {
                    stmt.setString(1, playerUUID);
                    stmt.setInt(2, kitNumber);
                    stmt.setString(3, "kit");
                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        String data = rs.getString("contents");
                        return Serializers.base64().deserializeItemStackMap(data);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        //noinspection CallToPrintStackTrace
                        e.printStackTrace();
                    }
                }
            }
            return new HashMap<>();
        }


        public static void delete(String playerUUID, int kitNumber) {
            try {
                try (PreparedStatement stmt = LegacyKits.getSql().getConnection().prepareStatement("SELECT contents FROM legacy_kits WHERE player_uuid = ? AND kit_number = ? AND kit_type = ?")) {
                    stmt.setString(1, playerUUID);
                    stmt.setInt(2, kitNumber);
                    stmt.setString(3, "kit");
                    stmt.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }

        public static void save(String playerUUID, int kitNumber, Inventory inventory) {
            Map<Integer, ItemStack> contents =  new HashMap<>();
            for (int slot = 0; slot < 5; ++slot) {
                ItemStack item = inventory.getItem(slot);
                contents.put(slot+36, Objects.requireNonNullElseGet(item, () -> new ItemStack(Material.AIR)));
            }
            for (int slot = 9; slot < 36; ++slot) {
                ItemStack item = inventory.getItem(slot);
                contents.put(slot, Objects.requireNonNullElseGet(item, () -> new ItemStack(Material.AIR)));
            }
            for (int slot = 36; slot < 45; ++slot) {
                ItemStack item = inventory.getItem(slot);
                contents.put(slot-36, Objects.requireNonNullElseGet(item, () -> new ItemStack(Material.AIR)));
            }
            try {
                String data = Serializers.base64().serializeItemStacks(contents);

                try (PreparedStatement stmt = LegacyKits.getSql().getConnection().prepareStatement(
                        "INSERT INTO legacy_kits " +
                                "(player_uuid, kit_number, kit_type, contents) " +
                                "VALUES (?, ?, ?, ?) " +
                                "ON DUPLICATE KEY UPDATE " +
                                "contents = ?")) {

                    stmt.setString(1, playerUUID);
                    stmt.setInt(2, kitNumber);
                    stmt.setString(3, "kit");
                    stmt.setString(4, data);
                    stmt.setString(5, data);
                    stmt.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
