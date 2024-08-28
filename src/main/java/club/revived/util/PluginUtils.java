package club.revived.util;

import club.revived.cache.SettingsCache;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class PluginUtils {

    public static boolean canSeeBroadcast(Player player){
        return !SettingsCache.getSettings(player.getUniqueId()).isBroadcastMessages();
    }

    public static boolean usesSingleClickKR(Player player){
        return SettingsCache.getSettings(player.getUniqueId()).isSingleClickKitRoom();
    }

    public static boolean hasAutokit(Player player){
        return SettingsCache.getSettings(player.getUniqueId()).isAutokit();
    }

    public static @NotNull List<Player> inRadius(@NotNull Location loc, final double radius){
        List<Player> rtn = new ArrayList<>();
        for(Player p : Bukkit.getOnlinePlayers()){
            Location location = p.getLocation();
            if(loc.getWorld() == location.getWorld()
                && loc.distance(location) <= radius
            ){
                rtn.add(p);
            }
        }
        return rtn;
    }

    public static String serializeItemStacks(Map<Integer, ItemStack> itemStacks) {
        ItemStack[] items = itemStacks.values().toArray(new ItemStack[0]);

        List<String> serializedItems = new ArrayList<>();
        for (ItemStack item : items) {
            String serialized = serializeItemStack(item);
            serializedItems.add(serialized);
        }

        return String.join(";", serializedItems);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static String serializeItemStack(ItemStack itemStack) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream bukkitObjectOutputStream;

        try {
            bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            bukkitObjectOutputStream.writeObject(itemStack);
            bukkitObjectOutputStream.close();

            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Map<Integer, ItemStack> deserializeItemStackMap(String data) {
        Map<Integer, ItemStack> kitContents = new HashMap<>();
        String[] serializedItems = data.split(";");

        for (int i = 0; i < serializedItems.length; i++) {
            ItemStack item = deserialize(serializedItems[i]);
            kitContents.put(i, item);
        }

        return kitContents;
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static ItemStack deserialize(String data) {
        byte[] bytes = Base64.getDecoder().decode(data);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);
            ItemStack itemStack = (ItemStack) bukkitObjectInputStream.readObject();
            bukkitObjectInputStream.close();

            return itemStack;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
