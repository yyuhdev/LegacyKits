package club.revived.listener;

import club.revived.WeirdoKits;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void handle(InventoryCloseEvent event) {
        Player player;
        HumanEntity humanEntity = event.getPlayer();
        if (humanEntity instanceof Player) {
            player = (Player) humanEntity;
        } else {
            return;
        }
        if (event.getInventory().getType() == InventoryType.PLAYER)
            return;
        if (event.getInventory().getType() == InventoryType.CREATIVE)
            return;
        InventoryView view = event.getView();
        for (int i = 1; i <= 9; i++) {
            if (view.getTitle().equalsIgnoreCase(ChatColor.GOLD + "Kit " + i))
                if (WeirdoKits.getInstance().getConfigUtil().save(player.getUniqueId(), String.valueOf(i), event.getInventory())) {
                    player.sendRichMessage("<green>Kit has been saved successfully");
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
                } else {
                    player.sendMessage("failed!");
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0F, 5.0F);
                }
        }
        for (int i = 1; i <= 9; i++) {
            if (view.getTitle().equalsIgnoreCase(ChatColor.GOLD + "Enderchest " + i))
                if (WeirdoKits.getInstance().getConfigUtil().saveec(player.getUniqueId(), String.valueOf(i), event.getInventory())) {
                    player.sendRichMessage("<green>Kit has been saved successfully");
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
                } else {
                    player.sendMessage("failed!");
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0F, 5.0F);
                }
        }
        if(event.getView().getTitle().equals(ChatColor.GOLD + "Kit 1")){
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                player.performCommand("kit");
            },1L);
        }
        if(event.getView().getTitle().equals(ChatColor.GOLD + "Kit 2")){
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                player.performCommand("kit");
            },1L);
        }

        if(event.getView().getTitle().equals(ChatColor.GOLD + "Kit 3")){
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                player.performCommand("kit");
            },1L);
        }

        if(event.getView().getTitle().equals(ChatColor.GOLD + "Kit 4")){
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                player.performCommand("kit");
            },1L);
        }

        if(event.getView().getTitle().equals(ChatColor.GOLD + "Kit 5")){
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                player.performCommand("kit");
            },1L);
        }

        if(event.getView().getTitle().equals(ChatColor.GOLD + "Kit 6")){
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                player.performCommand("kit");
            },1L);
        }

        if(event.getView().getTitle().equals(ChatColor.GOLD + "Kit 7")){
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                player.performCommand("kit");
            },1L);
        }

        if(event.getView().getTitle().equals(ChatColor.GOLD + "Settings")){
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                player.performCommand("kit");
            },1L);
        }


        if(event.getView().getTitle().startsWith(ChatColor.GOLD + "Enderchest ")){
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                player.performCommand("kit");
            },1L);
        }
    }
}
