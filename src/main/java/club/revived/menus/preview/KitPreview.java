package club.revived.menus.preview;

import club.revived.cache.KitCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.objects.kit.Kit;
import club.revived.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitPreview extends InventoryBuilder {

    public KitPreview(Player player, Kit kit){
        super(54, Component.text("<player>'s Kits"
                .replace("<player>", player.getName())
        ), true);
        setItems(5,8, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));
        setItems(45,53, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));
        Map<Integer, ItemStack> map = KitCache.getKits(player.getUniqueId()).get(kit.getID()).getContent();
        for (int slot = 36; slot < 41; ++slot) {
            setItem(slot - 36, map.getOrDefault(slot, new ItemStack(Material.AIR)));
        }
        for (int slot = 9; slot < 36; ++slot) {
            setItem(slot, map.getOrDefault(slot, new ItemStack(Material.AIR)));
        }
        for (int slot = 0; slot < 9; slot++) {
            setItem(slot + 36, map.getOrDefault(slot, new ItemStack(Material.AIR)));
        }
    }
}
