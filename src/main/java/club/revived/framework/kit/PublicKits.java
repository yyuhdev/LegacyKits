package club.revived.framework.kit;

import club.revived.config.Files;
import dev.manere.utils.serializers.Serializers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PublicKits {

    // Todo: Loading mechanism
    // Todo: Own instance of public kits

    public CompletableFuture<Boolean> savePublicKit(UUID uuid, Inventory inventory){
        return CompletableFuture.supplyAsync(() -> {
            File file = Files.create(new File(Files.mkdirs(Files.file("public-kits")), uuid + ".yml"));
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
}
