package club.revived.menus;

import club.revived.framework.inventory.InventoryBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class PublicKits
extends InventoryBuilder {

    public PublicKits(Player player) {
        super(54, Component.text("Public Kits"));
    }
}
