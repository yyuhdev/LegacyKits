package club.revived.util;

import club.revived.AithonKits;
import club.revived.config.Files;
import dev.manere.utils.scheduler.Schedulers;
import dev.manere.utils.serializers.Serializers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigUtil {
    public boolean saveEnderChest(UUID uuid, String kitNumber, Inventory inventory) {

        return Objects.requireNonNull(Schedulers.async().supply(() -> {

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
            return Boolean.TRUE;
        }));
    }

    public void clear(UUID uuid) {
        Schedulers.async().execute(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("user_data")), uuid + ".yml"));
            if(file.delete()){
                Bukkit.getConsoleSender().sendMessage("Just deleted the kits from " + uuid);
            }
        });
    }

    public Map<Integer, ItemStack> load(UUID uuid, String kitNumber) {
        return (Schedulers.async().supply(() -> {
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
                        Bukkit.getConsoleSender().sendMessage(e.getStackTrace().toString());
                    }
                }
            }

            return map;
        }));
    }


    public boolean save(UUID uuid, String kitNumber, Inventory inventory) {
        return Objects.requireNonNull(Schedulers.async().supply(() -> {
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
            Files.saveConfig(file, configuration);
            return Boolean.TRUE;
        }));
    }

    public boolean loadPremadeEnderchest(String kit, Inventory inventory){
        return Schedulers.async().supply(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("premade-enderchests")),kit + ".yml"));
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (int i = 0; i < 27; i++) {
                String base64 = configuration.getString(String.valueOf(i));

                if (base64 != null) {
                    byte[] bytes = Base64.getDecoder().decode(base64);
                    ItemStack item = Serializers.bytes().deserialize(bytes);
                    inventory.setItem(i, item);
                } else {
                    inventory.setItem(i, null);
                }
            }
            return Boolean.TRUE;
        }).booleanValue();
    }

    public boolean savePremadeEnderchest(String ec, Inventory inventory) {
        return Objects.requireNonNull(Schedulers.async().supply(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("premade-enderchests")),ec + ".yml"));
            FileConfiguration configuration = Files.config(file);

            for (int i = 0; i < 27; i++) {
                ItemStack item = inventory.getItem(i);

                if (item != null) {
                    String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                    configuration.set(String.valueOf(i), base64);
                } else {
                    configuration.set(String.valueOf(i), null);
                }
            }

            Files.saveConfig(file, configuration);
            return Boolean.TRUE;
        }));
    }

    public boolean savePremadeKit(String kit, Inventory inventory) {
        return Objects.requireNonNull(Schedulers.async().supply(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("premade-kits")),kit + ".yml"));
            FileConfiguration configuration = Files.config(file);

            for (int i = 0; i < 41; i++) {
                ItemStack item = inventory.getItem(i);

                if (item != null) {
                    String base64 = Base64.getEncoder().encodeToString(Serializers.bytes().serialize(item));
                    configuration.set(String.valueOf(i), base64);
                } else {
                    configuration.set(String.valueOf(i), null);
                }
            }

            Files.saveConfig(file, configuration);
            return Boolean.TRUE;
        }));
    }

    public boolean loadPremade(String kit, Inventory inventory){
        return Schedulers.async().supply(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("premade-kits")),kit + ".yml"));
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (int i = 0; i < 41; i++) {
                String base64 = configuration.getString(String.valueOf(i));

                if (base64 != null) {
                    byte[] bytes = Base64.getDecoder().decode(base64);
                    ItemStack item = Serializers.bytes().deserialize(bytes);
                    inventory.setItem(i, item);
                } else {
                    inventory.setItem(i, null);
                }

            }

            return Boolean.TRUE;
        }).booleanValue();
    }

    public boolean loadPremadeKit(String kit, PlayerInventory inventory){
        return Schedulers.async().supply(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("premade-kits")),kit + ".yml"));
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (int i = 0; i < inventory.getSize(); i++) {
                String base64 = configuration.getString(String.valueOf(i));

                if (base64 != null) {
                    byte[] bytes = Base64.getDecoder().decode(base64);
                    ItemStack item = Serializers.bytes().deserialize(bytes);
                    inventory.setItem(i, item);
                } else {
                    inventory.setItem(i, null);
                }

            }

            if(configuration.getString(String.valueOf(39)) != null){
                inventory.setHelmet(Serializers.bytes().deserialize(
                        Base64.getDecoder().decode(
                                configuration.getString(String.valueOf(39)))));
            }
            if(configuration.getString(String.valueOf(38)) != null){
                inventory.setChestplate(Serializers.bytes().deserialize(
                        Base64.getDecoder().decode(
                                configuration.getString(String.valueOf(38)))));
            }
            if(configuration.getString(String.valueOf(37)) != null){
                inventory.setLeggings(Serializers.bytes().deserialize(
                        Base64.getDecoder().decode(
                        configuration.getString(String.valueOf(37)))));
            }
            if(configuration.getString(String.valueOf(36)) != null){
                inventory.setBoots(Serializers.bytes().deserialize(
                        Base64.getDecoder().decode(
                                configuration.getString(String.valueOf(36)))));
            }




            return Boolean.TRUE;
        }).booleanValue();
    }


    public void load(Inventory inventory) {
        File file = Files.create(new File(AithonKits.getInstance().getDataFolder(),"premade-kit.yml"));
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        for (int i = 0; i < inventory.getSize(); i++) {
            String base64 = configuration.getString(String.valueOf(i));

            if (base64 != null) {
                byte[] bytes = Base64.getDecoder().decode(base64);
                ItemStack item = Serializers.bytes().deserialize(bytes);
                inventory.setItem(i, item);
            } else {
                inventory.setItem(i, null);
            }
        }
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
