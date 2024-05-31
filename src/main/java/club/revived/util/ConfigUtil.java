package club.revived.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import club.revived.WeirdoKits;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class ConfigUtil {
    public boolean saveec(UUID uuid, String kitNumber, Inventory inventory) {
            try {
                File folder = new File(WeirdoKits.getInstance().getDataFolder() + File.separator + "Userdata");
                CompletableFuture.runAsync(() -> {
                    if (!folder.exists()) {
                        folder.mkdirs();}
                });

                File file = new File(folder, uuid.toString() + ".yml");
                FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                for (int i = 0; i < 27; i++) {
                    ItemStack item = inventory.getItem(i);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
                    bukkitObjectOutputStream.writeObject(item);
                    bukkitObjectOutputStream.close();
                    String base64 = Base64Coder.encodeLines(byteArrayOutputStream.toByteArray());
                    configuration.set("ec." + kitNumber + "." + i, base64);
                }
                configuration.save(file);
                return true;
            } catch (Exception exception) {
                exception.printStackTrace();
                return true;
            }
    }

    public boolean save(UUID uuid, String kitNumber, Inventory inventory) {
            try {
                File folder = new File(WeirdoKits.getInstance().getDataFolder() + File.separator + "Userdata");
                CompletableFuture.runAsync(() -> {
                if (!folder.exists()) {
                    folder.mkdirs();}
                });

                File file = new File(folder, uuid.toString() + ".yml");
                FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
                for (int i = 0; i < 41; i++) {
                    ItemStack item = inventory.getItem(i);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
                    bukkitObjectOutputStream.writeObject(item);
                    bukkitObjectOutputStream.close();
                    String base64 = Base64Coder.encodeLines(byteArrayOutputStream.toByteArray());
                    configuration.set("Kit." + kitNumber + "." + i, base64);
                }
                configuration.save(file);
                return true;
            } catch (Exception exception) {
                exception.printStackTrace();
                return true;
        }
    }

    public HashMap<Integer, ItemStack> load(UUID uuid, String kitNumber) {
            HashMap<Integer, ItemStack> map = new HashMap<>();
            File folder = new File(WeirdoKits.getInstance().getDataFolder() + File.separator + "Userdata");
            File file = new File(folder, uuid.toString() + ".yml");
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            String basePath = "Kit." + kitNumber;
            for (int i = 0; i < 41; i++) {
                String path = basePath + "." + i;
                if (configuration.isSet(path)) {
                    try {
                        String base64 = configuration.getString(path);
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
                        BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);

                        map.put(i, (ItemStack) bukkitObjectInputStream.readObject());

                        bukkitObjectInputStream.close();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }

            return map;
    }

    public HashMap<Integer, ItemStack> loadec(UUID uuid, String kitNumber) {
        HashMap<Integer, ItemStack> map = new HashMap<>();
        File folder = new File(WeirdoKits.getInstance().getDataFolder() + File.separator + "Userdata");
        File file = new File(folder, uuid.toString() + ".yml");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        for (int i = 0; i < 27; i++) {
            String path = "ec." + kitNumber + "." + i;
            if (configuration.isSet(path)) {
                try {
                    String base64 = configuration.getString(path);
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
                    BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);

                    map.put(i, (ItemStack) bukkitObjectInputStream.readObject());

                    bukkitObjectInputStream.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        return map;
    }

}
