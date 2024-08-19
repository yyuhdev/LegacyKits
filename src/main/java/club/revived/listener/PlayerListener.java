package club.revived.listener;

import club.revived.LegacyKits;
import club.revived.cache.EnderchestCache;
import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.storage.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(!DatabaseManager.getInstance().isConnected()){
            event.getPlayer().kick();
            return;
        }
        LegacyKits.getInstance().loadPlayerData(event.getPlayer().getUniqueId() );
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        KitCache.invalidate(event.getPlayer().getUniqueId());
        EnderchestCache.invalidate(event.getPlayer().getUniqueId());
        SettingsCache.invalidate(event.getPlayer().getUniqueId());
    }
}
