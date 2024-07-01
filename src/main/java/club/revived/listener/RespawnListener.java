package club.revived.listener;

import club.revived.AithonKits;
import club.revived.util.KitLoading;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        if(AithonKits.getInstance().autoKitUsers.contains(event.getPlayer().getUniqueId())){
            int x = AithonKits.getInstance().lastUsedKits().get(event.getPlayer().getUniqueId());
            new KitLoading().load(event.getPlayer(), String.valueOf(x));
        }
    }
}
