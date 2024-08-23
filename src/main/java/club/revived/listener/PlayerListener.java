package club.revived.listener;

import club.revived.LegacyKits;
import club.revived.cache.EnderchestCache;
import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.storage.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event){
        if(!DatabaseManager.getInstance().isConnected()){
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            return;
        }
        LegacyKits.getInstance().loadPlayerData(event.getUniqueId());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        List<ItemStack> stacks = event.getDrops();
        List<Item> dropped = new ArrayList<>();
        for(ItemStack stack : stacks){
            dropped.add(event.getPlayer().getWorld().dropItemNaturally(event.getEntity().getLocation(),stack));
        }
        event.getDrops().clear();
        event.getPlayer().getInventory().clear();
        Bukkit.getScheduler().scheduleSyncDelayedTask(LegacyKits.getInstance(), () -> {
            event.getPlayer().spigot().respawn();
            for(Item item : dropped){
                if(item == null) continue;
                item.remove();
            }
        }, 20L);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        Map<Integer, ItemStack> map = KitCache.getKits(player.getUniqueId()).get(SettingsCache.getSettings(player.getUniqueId()).getSelectedKit()).getContent();
        player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        KitCache.invalidate(event.getPlayer().getUniqueId());
        EnderchestCache.invalidate(event.getPlayer().getUniqueId());
        SettingsCache.invalidate(event.getPlayer().getUniqueId());
    }
}
