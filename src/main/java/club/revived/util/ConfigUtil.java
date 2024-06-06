package club.revived.util;

import club.revived.config.Files;
import dev.manere.utils.serializers.Serializers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigUtil {
    public boolean saveEnderChest(UUID uuid, String kitNumber, Inventory inventory) {
        File file = Files.create(new File(Files.mkdirs(Files.file("user_data")), uuid + ".yml"));
        FileConfiguration configuration = Files.config(file);

        String basePath = "ender_chest." + kitNumber;

        for (int i = 0; i < 27; i++) {
            ItemStack item = inventory.getItem(i);

            if (item != null) {
                String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                configuration.set(basePath + "." + i, base64);
            } else {
                configuration.set(basePath + "." + i, null);
            }
        }

        return Files.saveConfig(file, configuration);
    }

    public Map<Integer, ItemStack> load(UUID uuid, String kitNumber) {
        File file = Files.create(new File(Files.mkdirs(Files.file("user_data")), uuid + ".yml"));
        FileConfiguration configuration = Files.config(file);

        Map<Integer, ItemStack> map = new ConcurrentHashMap<>();

        String basePath = "kit." + kitNumber;

        for (int i = 0; i < 41; i++) {
            String path = basePath + "." + i;
            if (configuration.isSet(path)) {
                String base64 = configuration.getString(path);
                try {
                    byte[] bytes = Base64.getDecoder().decode(base64);
                    ItemStack item = Serializers.bytes().deserialize(bytes);

                    map.put(i, item);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid base64 string at " + path + ": " + base64);
                    e.printStackTrace();
                }
            }
        }

        return map;
    }

    public boolean save(UUID uuid, String kitNumber, Inventory inventory) {
        File file = Files.create(new File(Files.mkdirs(Files.file("user_data")), uuid + ".yml"));
        FileConfiguration configuration = Files.config(file);

        for (int i = 0; i < 41; i++) {
            ItemStack item = inventory.getItem(i);

            if (item != null) {
                String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                configuration.set("kit." + kitNumber + "." + i, base64);
            } else {
                configuration.set("kit." + kitNumber + "." + i, null);
            }
        }

        return Files.saveConfig(file, configuration);
    }


    public Map<Integer, ItemStack> loadEnderChest(UUID uuid, String kitNumber) {
        File file = Files.create(new File(Files.mkdirs(Files.file("user_data")), uuid + ".yml"));
        FileConfiguration configuration = Files.config(file);

        Map<Integer, ItemStack> map = new ConcurrentHashMap<>();

        String basePath = "ender_chest." + kitNumber;

        for (int i = 0; i < 27; i++) {
            String path = basePath + "." + i;
            if (configuration.isSet(path)) {
                String base64 = configuration.getString(path);
                try {
                    byte[] bytes = Base64.getDecoder().decode(base64);
                    ItemStack item = Serializers.bytes().deserialize(bytes);
                    map.put(i, item);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid base64 string at " + path + ": " + base64);
                    e.printStackTrace();
                }
            }
        }

        return map;
    }


}
