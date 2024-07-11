package club.revived.menus.admin;

import club.revived.AithonKits;
import club.revived.util.ConfigUtil;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PremadeKitEditor {

    private final Player player;
    private final MenuBase<?> menu;

    public PremadeKitEditor(Player player, String toSave){
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<#FFD1A3>Edit " + toSave), 5*9);
        this.menu.border(Button.button(
                ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name(""))
                .onClick(event -> event.setCancelled(true)),
                "",
                "",
                "",
                "",
                ". . . . . X X X .");

        this.menu.button(44, Button.button(ItemBuilder.item(Material.CHEST)
                .name(TextStyle.style("<#FFD1A3>Import from Inventory"))
        ).onClick(event -> {
            event.setCancelled(true);

            for (int slot = 0; slot < 41; slot++) {
                this.menu.inventory().setItem(slot, this.player.getInventory().getItem(slot));
            }

            Inventory inventory = this.menu.inventory();

            inventory.setItem(36, player.getInventory().getHelmet());
            inventory.setItem(37, player.getInventory().getChestplate());
            inventory.setItem(38, player.getInventory().getLeggings());
            inventory.setItem(39, player.getInventory().getBoots());
            inventory.setItem(40, player.getInventory().getItemInOffHand());

            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
        }));

        this.menu.onClose(event -> {
            if(new ConfigUtil().savePremadeKit(toSave, this.menu.inventory())){
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5.0F, 1.0F);
                return;
            }

            player.sendRichMessage("<dark_red><bold>FAILED");
            AithonKits.getInstance().getComponentLogger().error(TextStyle.style("<dark_red>SEVERE ERROR WHILST KIT SAVING <reset><purple>uwu"));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0F, 5.0F);
        });

    }

    public void open(){
        this.menu.open(this.player);
    }
}
