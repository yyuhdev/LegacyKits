package club.revived.storage.kit_room;

import club.revived.config.Files;
import club.revived.util.enums.Page;
import dev.manere.utils.serializers.Serializers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class KitRoomData {

    public static void saveKitRoomPage(Page page, Inventory inventory){
        CompletableFuture.runAsync(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("kitroom")), page.toString().toLowerCase() + ".yml"));
            FileConfiguration configuration = Files.config(file);
            for (int slot = 0; slot < 45; slot++) {
                ItemStack stack = inventory.getItem(slot);
                if (stack != null) {
                    String b = Base64.getEncoder().encodeToString(stack.serializeAsBytes());
                    configuration.set(String.valueOf(slot), b);
                } else
                    configuration.set(String.valueOf(slot), null);
            }
            Files.saveConfig(file, configuration);
        });
    }

    public static CompletableFuture<Map<Integer, ItemStack>> loadKitRoomPage(Page page){
        return CompletableFuture.supplyAsync(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("kitroom")), page.toString().toLowerCase() + ".yml"));
            YamlConfiguration con = YamlConfiguration.loadConfiguration(file);
            Map<Integer, ItemStack> map = new ConcurrentHashMap<>();
            for(int slot = 0; slot < 45; slot++){
                String path = String.valueOf(slot);
                if (con.isSet(path)) {
                    String base64 = con.getString(path);
                    try {
                        byte[] bytes = Base64.getDecoder().decode(base64);
                        ItemStack item = Serializers.bytes().deserialize(bytes);
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
