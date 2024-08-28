package club.revived.storage.room;

import club.revived.LegacyKits;
import club.revived.menus.kitroom.KitroomPage;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class KitRoomData {

    public static void saveKitRoomPage(KitroomPage page, Map<Integer, ItemStack> content) {
        Bukkit.getAsyncScheduler().runNow(LegacyKits.getInstance(), scheduledTask -> {
            File file = new File(LegacyKits.getInstance().getDataFolder(), "kitroom/" + page.toString().toLowerCase() + ".yml");
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (int x : content.keySet()) {
                ItemStack stack = content.get(x);
                if(stack == null || stack.isEmpty()){
                    configuration.set(String.valueOf(x), null);
                    break;
                }
                configuration.set(String.valueOf(x), Base64.getEncoder().encodeToString(stack.serializeAsBytes()));
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
            for(int slot = 0; slot < 54; slot++){
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
