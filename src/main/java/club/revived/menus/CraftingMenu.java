package club.revived.menus;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class CraftingMenu {

    private final Player player;
    private final MenuBase<?> menu;

    public CraftingMenu(Player player){
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<#FFD1A3>Crafting Menu"), 27);

        this.menu.border(Button.button(
                ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name(""))
                .onClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true)),
                "X X X X X X X X X",
                "X . X . X . X . X",
                "X X X X X X X X X"
        );
        this.menu.button(10, Button.button(
                ItemBuilder.item(Material.ENCHANTING_TABLE).name(TextStyle.style("<#FFD1A3>Enchantment Table")))
                .onClick(event -> {
                    event.setCancelled(true);
                    player.openInventory(Bukkit.createInventory(null, InventoryType.ENCHANTING));
                }));

        this.menu.button(12, Button.button(
                        ItemBuilder.item(Material.ANVIL).name(TextStyle.style("<#FFD1A3>Anvil")))
                .onClick(event -> {
                    event.setCancelled(true);
                    player.openInventory(Bukkit.createInventory(null, InventoryType.ANVIL));
                }));

        this.menu.button(14, Button.button(
                        ItemBuilder.item(Material.SMITHING_TABLE).name(TextStyle.style("<#FFD1A3>Smithing Table")))
                .onClick(event -> {
                    event.setCancelled(true);
                    player.openInventory(Bukkit.createInventory(null, InventoryType.SMITHING));
                }));

        this.menu.button(16, Button.button(
                        ItemBuilder.item(Material.CRAFTING_TABLE).name(TextStyle.style("<#FFD1A3>Crafting Table")))
                .onClick(event -> {
                    event.setCancelled(true);
                    player.openInventory(Bukkit.createInventory(null, InventoryType.CRAFTING));
                }));
    }
    public void open(){
        this.menu.open(player);
    }
}
