
package club.revived.menus;

import club.revived.AithonKits;
import club.revived.util.PageSound;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.List;

public class GuideMenu {

    private final Player player;
    private final MenuBase<?> menu;

    public GuideMenu(Player player){
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<gold>Kit Guide"), 45);
        init();
    }

    private void init(){
        this.menu.border(Button.button(
                ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name(""))
                .onClick(event -> event.setCancelled(true)),
                "X X X X X X X X X",
                "X X . X . X . X X",
                "X X X X X X X X X",
                "X X . X . X . X X",
                "X X X X X X X X X");

        this.menu.button(11, Button.button(
                ItemBuilder.item(Material.CHEST)
                        .name(TextStyle.style("<gold>Kit System"))
                        .lore(List.of(
                                MiniMessage.miniMessage().deserialize("<em><gray>Create Custom Kits to train & embrace"),
                                MiniMessage.miniMessage().deserialize("<em><gray>in battles against your biggest enemies"),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>Get Items in the Kit Room to create your Kits,"),
                                TextStyle.style("<#FFF6A8>after that you can just Left-Click on any of the"),
                                TextStyle.style("<#FFF6A8>Kits to edit it."),
                                MiniMessage.miniMessage().deserialize("<gray>You can also claim one of the pre-made Kits and use it."),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>Press the Diamond Chestplate to import your inventory"),
                                TextStyle.style("<#FFF6A8>into the kit editor. You can also manually edit the kit.")
                        ))).onClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true))


        );

        this.menu.button(13, Button.button(
                ItemBuilder.item(Material.ENDER_CHEST)
                        .name(TextStyle.style("<gold>Enderchest System"))
                        .lore(List.of(
                                MiniMessage.miniMessage().deserialize("<em><gray>Create Custom Enderchest to train & embrace"),
                                MiniMessage.miniMessage().deserialize("<em><gray>in battles against your biggest enemies"),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>Get Items in the Kit Room to create your Enderchest,"),
                                TextStyle.style("<#FFF6A8>after that you can just Left-Click on any of the"),
                                TextStyle.style("<#FFF6A8>Enderchests to edit it."),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>Press the Diamond Chestplate to import your inventory"),
                                TextStyle.style("<#FFF6A8>into the kit editor or press the Enderchest to import"),
                                TextStyle.style("<#FFF6A8>your current enderchest into the kit editor")
                        ))).onClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true))


        );

        this.menu.button(15, Button.button(
                ItemBuilder.item(Material.DIAMOND_SWORD)
                        .name(TextStyle.style("<gold>Kit Sharing"))
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .lore(List.of(
                                MiniMessage.miniMessage().deserialize("<em><gray>Share your Kits and Enderchest"),
                                MiniMessage.miniMessage().deserialize("<em><gray>to make your Kits public"),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>You can easily share your kits by using"),
                                TextStyle.style("<#FFF6A8>/kit share <player>."),
                                MiniMessage.miniMessage().deserialize("<em><gray>You can accept a share request by using"),
                                MiniMessage.miniMessage().deserialize("<em><gray>/kit share accept <player> <kit_num>")
                        ))).onClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true))
        );

        this.menu.button(29, Button.button(
                ItemBuilder.item(Material.WRITABLE_BOOK)
                        .name(TextStyle.style("<gold>Kit Messages"))
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .lore(List.of(
                                MiniMessage.miniMessage().deserialize("<em><gray>Kit Messages let you and others see"),
                                MiniMessage.miniMessage().deserialize("<em><gray>if someone might be re-kitting"),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>As soon as you claim a kit, this message appears: "),
                                TextStyle.style("<gray>\u2694 <player> has loaded a kit".replace("<player>", player.getName())),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>As soon as you claim an enderchest, this message appears: "),
                                TextStyle.style("<gray>\u2694 <player> has loaded an enderchest".replace("<player>", player.getName())),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>You can also toggle kit messages on and off by using /kit messages")
                        ))).onClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true))
        );

        this.menu.button(31, Button.button(
                ItemBuilder.item(Material.NETHERITE_CHESTPLATE)
                        .name(TextStyle.style("<gold>Auto-Kit"))
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .lore(List.of(
                                MiniMessage.miniMessage().deserialize("<em><gray>Auto-Kit automatically gives you a kit"),
                                MiniMessage.miniMessage().deserialize("<em><gray>as soon as you respawn"),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>You can easily skip the step of needing to claim a kit once"),
                                TextStyle.style("<#FFF6A8>when you respawn by enabling autokit using /autokit."),
                                TextStyle.style(""),
                                TextStyle.style("<#FFF6A8>Autokit automatically gives you your last claimed kit"),
                                TextStyle.style("<#FFF6A8>when you respawn.")
                        ))).onClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true))
        );

        this.menu.button(33, Button.button(
                ItemBuilder.item(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                        .name(TextStyle.style("<gold>Empty Guide Slot"))
                        .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                        .lore(List.of(
                                TextStyle.style("<gray>This will be filled soon")
                        ))).onClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true))
        );

        this.menu.onClose(event -> Bukkit.getScheduler().runTaskLater(AithonKits.getInstance(), () -> {
            KitMenu kitMenu = new KitMenu(player);
            kitMenu.open();
        }, 1L));


    }

    public void open(){
        this.menu.open(this.player);
        new PageSound().play(player);
    }
}
