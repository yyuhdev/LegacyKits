package club.revived.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class Eat implements Listener {

    @EventHandler
    public void onEat(PlayerItemConsumeEvent event){
        if(event.getItem().getType() == Material.ENCHANTED_GOLDEN_APPLE){
            event.setCancelled(true);
            event.getPlayer().getInventory().clear();
            event.getPlayer().kick();
        }
    }
}
