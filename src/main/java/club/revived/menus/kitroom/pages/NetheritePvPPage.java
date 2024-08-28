package club.revived.menus.kitroom.pages;

import club.revived.LegacyKits;
import club.revived.cache.KitRoomCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.menus.kitroom.KitRoomMenu;
import club.revived.menus.kitroom.KitroomPage;
import club.revived.util.ColorUtil;
import club.revived.util.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class NetheritePvPPage extends InventoryBuilder {

    public NetheritePvPPage(Player player, boolean b){
        super(54, ColorUtil.of("<player>'s Kits"
                .replace("<player>", player.getName())
        ));
        KitRoomCache.getKitRoomPage(KitroomPage.NETHERITE_CRYSTAL).thenAccept(map -> {
            for(int x : map.keySet()){
                if(b) return;
                setItem(x, map.get(x), event -> {
                    if(PluginUtils.usesSingleClickKR(player)){
                        event.setCancelled(true);
                        player.getInventory().addItem(map.get(x));
                    }
                });
            }
        });
        addCloseHandler(event -> Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> {
            new KitRoomMenu(player).open(player);
            if(b){
                Map<Integer, ItemStack> content = new HashMap<>();
                for (int i = 0; i < this.getInventory().getSize(); i++) {
                    content.put(i, this.getInventory().getItem(i));
                }
                KitRoomCache.saveKitRoomPage(KitroomPage.NETHERITE_CRYSTAL, content);
            }
        }, 1));
    }
}
