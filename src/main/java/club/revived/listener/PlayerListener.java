package club.revived.listener;

import club.revived.LegacyKits;
import club.revived.cache.EnderchestCache;
import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.storage.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    public void onQuit(PlayerQuitEvent event){
        KitCache.invalidate(event.getPlayer().getUniqueId());
        EnderchestCache.invalidate(event.getPlayer().getUniqueId());
        SettingsCache.invalidate(event.getPlayer().getUniqueId());
    }
}
