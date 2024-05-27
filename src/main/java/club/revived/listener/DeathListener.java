package club.revived.listener;

import club.revived.WeirdoKits;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.deathMessage(null);

        for (Player global : Bukkit.getOnlinePlayers()){
            if(!WeirdoKits.getInstance().deathmessages.contains(global.getUniqueId())){
                global.sendRichMessage("<gold><bold>WK <reset><gold>" + event.getPlayer().getName() + " <gray>died.");
            }
        }

    }
}
