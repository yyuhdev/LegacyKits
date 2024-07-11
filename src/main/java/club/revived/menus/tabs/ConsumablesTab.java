package club.revived.menus.tabs;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.TabItem;

public class ConsumablesTab extends TabItem {

    private final int tab;

    public ConsumablesTab(int tab) {
        super(tab);
        this.tab = tab;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
        if (clickType == ClickType.LEFT) getGui().setTab(tab);
        player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN,1,1);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemProvider getItemProvider(TabGui gui) {
        if (!(gui.getCurrentTab() == tab)) {
            return new ItemBuilder(Material.GOLDEN_APPLE)
                    .addAllItemFlags()
                    .setDisplayName(ChatColor.of("#FFD1A3") + "Consumables")
                    .addLoreLines("§7Not Selected");
        } else {
            return new ItemBuilder(Material.GOLDEN_APPLE)
                    .addAllItemFlags()
                    .setDisplayName(ChatColor.of("#FFD1A3") + "Consumables")
                    .addLoreLines("§aSelected")
                    .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        }
    }

}
