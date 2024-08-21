package club.revived.storage.room;

import club.revived.LegacyKits;
import club.revived.util.enums.KitroomPage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class KitRoomData {

    public static void saveKitRoomPage(KitroomPage page, Inventory inventory){
        CompletableFuture.runAsync(() -> {
            File file = new File(LegacyKits.getInstance().getDataFolder(), "kitroom/" + page.toString().toLowerCase() + ".yml");
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (int slot = 0; slot < 45; slot++) {
                ItemStack stack = inventory.getItem(slot);
                if (stack != null) {
                    String b = Base64.getEncoder().encodeToString(stack.serializeAsBytes());
                    configuration.set(String.valueOf(slot), b);
                } else
                    configuration.set(String.valueOf(slot), null);
            }
            try {
                configuration.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static CompletableFuture<Map<Integer, ItemStack>> loadKitRoomPage(KitroomPage page){
        return CompletableFuture.supplyAsync(() -> {
            File file = new File(LegacyKits.getInstance().getDataFolder(), "kitroom/" + page.toString().toLowerCase() + ".yml");
            YamlConfiguration con = YamlConfiguration.loadConfiguration(file);
            Map<Integer, ItemStack> map = new ConcurrentHashMap<>();
            for(int slot = 0; slot < 45; slot++){
                String path = String.valueOf(slot);
                if (con.isSet(path)) {
                    String base64 = con.getString(path);
                    try {
                        byte[] bytes = Base64.getDecoder().decode(base64);
                        ItemStack item = ItemStack.deserializeBytes(bytes);
                        map.put(slot, item);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid base64 string at " + path + ": " + base64);
                        Bukkit.getConsoleSender().sendMessage(Arrays.toString(e.getStackTrace()));
                    }
                }
            }
            return map;
        });
    }
}
