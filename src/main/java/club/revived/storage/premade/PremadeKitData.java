package club.revived.storage.premade;

import club.revived.config.Files;
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

public class PremadeKitData {

    // Simple thing!!! Just base64 encoding and putting it into a config file haha

    public static CompletableFuture<Boolean> savePremadeKit(String kit, Inventory inventory) {
        return CompletableFuture.supplyAsync(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("premade-kits")), kit + ".yml"));
            FileConfiguration configuration = Files.config(file);
            for (int slot = 0; slot < 5; ++slot) {
                ItemStack item = inventory.getItem(slot);
                if (item != null) {
                    String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                    configuration.set(String.valueOf(slot+36), base64);
                } else {
                    configuration.set(String.valueOf(slot+36), null);
                }
            }
            for (int slot = 9; slot < 36; ++slot) {
                ItemStack item = inventory.getItem(slot);
                if (item != null) {
                    String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                    configuration.set(String.valueOf(slot), base64);
                } else {
                    configuration.set(String.valueOf(slot), null);
                }
            }
            for (int slot = 36; slot < 45; ++slot) {
                ItemStack item = inventory.getItem(slot);

                if (item != null) {
                    String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                    configuration.set(String.valueOf(slot-36), base64);
                } else {
                    configuration.set(String.valueOf(slot-36), null);
                }
            }
            Files.saveConfig(file, configuration);
            return true;
        });
    }

    public static CompletableFuture<Map<Integer, ItemStack>> loadPremadeKit(String kit){
        return CompletableFuture.supplyAsync(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("premade-kits")), kit + ".yml"));
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            Map<Integer, ItemStack> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 41; i++) {
                String path = String.valueOf(i);
                if (configuration.isSet(path)) {
                    String base64 = configuration.getString(path);
                    try {
                        byte[] bytes = Base64.getDecoder().decode(base64);
                        ItemStack item = Serializers.bytes().deserialize(bytes);
                        map.put(i, item);
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