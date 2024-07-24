package club.revived.util;

import club.revived.AithonKits;
import club.revived.config.Files;
import club.revived.util.enums.Page;
import dev.manere.utils.scheduler.Schedulers;
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
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigUtil {

    public CompletableFuture<Boolean> saveEnderChest(UUID uuid, String kitNumber, Inventory inventory) {
        return CompletableFuture.supplyAsync(() -> {
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
            Files.saveConfig(file, configuration);
            return true;
        });
    }

    public void clear(UUID uuid) {
        Schedulers.async().execute(() -> {
            File file = new File(AithonKits.getInstance().getDataFolder(), "user_data//" + uuid + ".yml");
            if(file.delete()){
                Bukkit.getConsoleSender().sendMessage("Just deleted the kits from " + uuid);
            }
        });
    }

    public CompletableFuture<Map<Integer, ItemStack>> load(UUID uuid, String kitNumber) {
        return CompletableFuture.supplyAsync(() -> {
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
                        Bukkit.getConsoleSender().sendMessage(Arrays.toString(e.getStackTrace()));
                    }
                }
            }

            return map;
        });
    }

    public void saveKitRoomPage(Page page, Inventory inventory){
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

    public CompletableFuture<Map<Integer, ItemStack>> loadKitRoomPage(Page page){
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

    public CompletableFuture<Boolean> save(UUID uuid, String kitNumber, Inventory inventory) {
        return CompletableFuture.supplyAsync(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("user_data")), uuid + ".yml"));
            FileConfiguration configuration = Files.config(file);
            for (int slot = 0; slot < 5; ++slot) {
                ItemStack item = inventory.getItem(slot);
                if (item != null) {
                    String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                    configuration.set("kit." + kitNumber + "." + (slot+36), base64);
                } else {
                    configuration.set("kit." + kitNumber + "." + (slot+36), null);
                }
            }
            for (int slot = 9; slot < 36; ++slot) {
                ItemStack item = inventory.getItem(slot);
                if (item != null) {
                    String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                    configuration.set("kit." + kitNumber + "." + slot, base64);
                } else {
                    configuration.set("kit." + kitNumber + "." + slot, null);
                }
            }
            for (int slot = 36; slot < 45; ++slot) {
                ItemStack item = inventory.getItem(slot);

                if (item != null) {
                    String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                    configuration.set("kit." + kitNumber + "." + (slot-36), base64);
                } else {
                    configuration.set("kit." + kitNumber + "." + (slot-36), null);
                }
            }
            Files.saveConfig(file, configuration);
            return true;
        });
    }

    public CompletableFuture<Boolean> savePremadeKit(String kit, Inventory inventory) {
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

    public CompletableFuture<Map<Integer, ItemStack>> loadPremadeKit(String kit){
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

    public CompletableFuture<Map<Integer, ItemStack>> loadEnderChest(UUID uuid, String kitNumber) {
        return CompletableFuture.supplyAsync(() -> {
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
                        Bukkit.getConsoleSender().sendMessage(Arrays.toString(e.getStackTrace()));
                    }
                }
            }

            return map;
        });
    }
}
