package club.revived.menus.items;

import org.bukkit.Material;
import xyz.xenondevs.invui.gui.ScrollGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.ScrollItem;

public class ScrollUpItem extends ScrollItem {

    public ScrollUpItem() {
        super(-1);
    }

    @Override
    public ItemProvider getItemProvider(ScrollGui<?> gui) {
        ItemBuilder builder = new ItemBuilder(Material.RED_STAINED_GLASS_PANE);
        builder.setDisplayName("§7Scroll up");
        if (!gui.canScroll(-1))
            builder.addLoreLines("§cYou've reached the top");

        return builder;
    }

}
