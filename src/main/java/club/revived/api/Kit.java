package club.revived.api;

import club.revived.cache.EnderchestCache;
import club.revived.cache.KitCache;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Kit {

    public static void loadKit(Player player, int id){
        Map<Integer, ItemStack> map = KitCache.getKits(player.getUniqueId()).get(id).getContent();
        player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
    }

    public static void loadEnderchest(Player player, int id){
        Map<Integer, ItemStack> map = EnderchestCache.getKits(player.getUniqueId()).get(id).getContent();
        player.getEnderChest().setContents(map.values().toArray(new ItemStack[0]));
    }
}
