package club.revived.listener;

import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.objects.Kit;
import club.revived.objects.KitHolder;
import club.revived.objects.Settings;
import club.revived.storage.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        DatabaseManager.getInstance().get(KitHolder.class, event.getPlayer().getUniqueId())
                .thenAccept(kitHolder -> {
                    if(kitHolder.isEmpty()){
                        KitCache.update(event.getPlayer().getUniqueId(), KitHolder.newEmpty(event.getPlayer().getUniqueId()));
                    }
                    KitCache.update(event.getPlayer().getUniqueId(), kitHolder.get());
                });
        DatabaseManager.getInstance().get(Settings.class, event.getPlayer().getUniqueId())
                .thenAccept(settings -> {
                    if(settings.isEmpty()) return;
                    SettingsCache.setSettings(event.getPlayer().getUniqueId(), settings.get());
                });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        KitCache.invalidate(event.getPlayer().getUniqueId());
        SettingsCache.invalidate(event.getPlayer().getUniqueId());
    }
}
