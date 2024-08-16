package club.revived.listener;

import club.revived.LegacyKits;
import club.revived.storage.kit.KitData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void handle(PlayerRespawnEvent event){
        if(!LegacyKits.getInstance().autoKitUsers.contains(event.getPlayer().getUniqueId())){
            int x = LegacyKits.getInstance().lastUsedKits.getOrDefault(event.getPlayer().getUniqueId(), 1);
            KitData.load(event.getPlayer(), x);
        }
    }
}
