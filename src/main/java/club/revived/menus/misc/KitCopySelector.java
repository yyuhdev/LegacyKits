package club.revived.menus.misc;

import club.revived.LegacyKits;
import club.revived.cache.KitCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.menus.KitEditor;
import club.revived.objects.kit.Kit;
import club.revived.objects.kit.KitHolder;
import club.revived.storage.DatabaseManager;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KitCopySelector extends InventoryBuilder {

    public KitCopySelector(Player player, int id) {
        super(36, Component.text(player.getName() + "'s Kits"));
        int idd = id;
        for (int x = 10; x < 17; x++) {
            if (x-9 == id){
                continue;
            }
            int kitId = x-9;
            setItem(x, ItemBuilder.item(Material.BOOK)
                    .name(TextStyle.style("<#cdd6fa>\uD83C\uDFF9 Custom Kit <kit>"
                            .replace("<kit>", String.valueOf(kitId))))
                    .lore(TextStyle.style(""),
                            TextStyle.style("<grey>Custom kits allow you to"),
                            TextStyle.style("<grey>create preset kits which you"),
                            TextStyle.style("<grey>can claim at any time."),
                            TextStyle.style(""),
                            TextStyle.style("<green>Click to paste"),
                            TextStyle.style("")
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
                    .name(TextStyle.style("<#cdd6fa>\uD83C\uDFF9 Custom Kit <kit>"
                            .replace("<kit>", String.valueOf(kitId))))
                    .lore(TextStyle.style(""),
                            TextStyle.style("<grey>Custom kits allow you to"),
                            TextStyle.style("<grey>create preset kits which you"),
                            TextStyle.style("<grey>can claim at any time."),
                            TextStyle.style(""),
                            TextStyle.style("<green>Click to paste"),
                            TextStyle.style("")
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
