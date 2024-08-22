package club.revived.menus.misc;

import club.revived.LegacyKits;
import club.revived.cache.KitCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.menus.KitEditor;
import club.revived.objects.kit.Kit;
import club.revived.objects.kit.KitHolder;
import club.revived.storage.DatabaseManager;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KitCopySelector extends InventoryBuilder {

    public KitCopySelector(Player player, int id) {
        super(36, Component.text(player.getName() + "'s Kits"), true);
        int idd = id;
        for (int x = 10; x < 17; x++) {
            if (x-9 == id){
                continue;
            }
            int kitId = x-9;
            setItem(x, ItemBuilder.item(Material.BOOK)
                    .name(ColorUtil.of("<#cdd6fa>\uD83C\uDFF9 Custom Kit <kit>"
                            .replace("<kit>", String.valueOf(kitId))))
                    .lore(ColorUtil.of(""),
                            ColorUtil.of("<grey>Custom kits allow you to"),
                            ColorUtil.of("<grey>create preset kits which you"),
                            ColorUtil.of("<grey>can claim at any time."),
                            ColorUtil.of(""),
                            ColorUtil.of("<green>Click to paste"),
                            ColorUtil.of("")
                    )
                    .build(), e -> {
                e.setCancelled(true);
                KitCache.addKit(player.getUniqueId(), new Kit(player.getUniqueId(), kitId, "name", KitCache.getKits(player.getUniqueId()).get(id).getContent()));
                DatabaseManager.getInstance().save(KitHolder.class, new KitHolder(player.getUniqueId(), KitCache.getKits(player.getUniqueId())));
                player.closeInventory();
            });
        }
        for (int x = 19; x < 26; x++) {
            if (x-9 == id){
                continue;
            }
            int kitId = x-9;
            setItem(x, ItemBuilder.item(Material.BOOK)
                    .name(ColorUtil.of("<#cdd6fa>\uD83C\uDFF9 Custom Kit <kit>"
                            .replace("<kit>", String.valueOf(kitId))))
                    .lore(ColorUtil.of(""),
                            ColorUtil.of("<grey>Custom kits allow you to"),
                            ColorUtil.of("<grey>create preset kits which you"),
                            ColorUtil.of("<grey>can claim at any time."),
                            ColorUtil.of(""),
                            ColorUtil.of("<green>Click to paste"),
                            ColorUtil.of("")
                    )
                    .build(), e -> {
                e.setCancelled(true);
                KitCache.addKit(player.getUniqueId(), new Kit(player.getUniqueId(), kitId, "name", KitCache.getKits(player.getUniqueId()).get(id).getContent()));
                DatabaseManager.getInstance().save(KitHolder.class, new KitHolder(player.getUniqueId(), KitCache.getKits(player.getUniqueId())));
                player.closeInventory();
            });
        }
        addCloseHandler(event -> {
            Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new KitEditor(idd, player).open(player), 1L);
        });
    }
}
