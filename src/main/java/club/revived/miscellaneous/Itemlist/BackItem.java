package club.revived.miscellaneous.Itemlist;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class BackItem extends AbstractItem {
    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.BARRIER).setDisplayName("§6Search").addLoreLines("§7Click to search for an item");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        event.setCancelled(true);
        player.closeInventory();

    }
}