package club.revived.menus;

import club.revived.WeirdoKits;
import club.revived.menus.kitroom.Armor;
import club.revived.menus.kitroom.Arrows;
import club.revived.util.MessageUtil;
import club.revived.util.PageSound;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.checkerframework.checker.units.qual.A;

public class KitMenu {

    private final Player player;
    private final MenuBase<?> menu;
    WeirdoKits kits = WeirdoKits.getInstance();

    public KitMenu(Player player) {
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<gold>Kits"), 6 * 9);
        init();
    }

    public void init() {

        this.menu.border(Button.button(
                        ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                                .name("")
                ).onClick(event -> {
                    event.setCancelled(true);
                }),
                "X X X X X X X X X",
                "X . . . . . . . X",
                "X . . . . . . . X",
                "X . . . . . . . X",
                "X . . . . . . . X",
                "X . . . . . . . X"
        );

        for (int x = 10; x < 17; x++) {
            int i = x;
            this.menu.button(x, Button.button(
                            ItemBuilder.item(Material.CHEST)
                                    .name(TextStyle.style("<gold>Kit " + (x - 9)))
                                    .lore(TextStyle.style("<white>Click to edit")))
                    .onClick(event -> {
                                event.setCancelled(true);
                                for (Player global : Bukkit.getOnlinePlayers()) {
                                        new MessageUtil().brcmessage(player,global, "broadcast_messages.kit_editor_open");
                                }
                                KitEditor editor = new KitEditor(player, i - 9);
                                editor.open();
                            }
                    ));

        }
        for (int x = 19; x < 26; x++) {
            int i = x;
            this.menu.button(x, Button.button(
                            ItemBuilder.item(Material.ENDER_CHEST)
                                    .name(TextStyle.style("<gold>Enderchest " + (x - 18)))
                                    .lore(TextStyle.style("<white>Click to edit")))
                    .onClick(event -> {
                        event.setCancelled(true);
                        for (Player global : Bukkit.getOnlinePlayers()) {
                                new MessageUtil().brcmessage(player, global, "broadcast_messages.enderchest_editor_open");
                        }
                        EnderchestEditor editor = new EnderchestEditor(player, i - 18);
                        editor.open();
                    })
            );
            this.menu.button(38, Button.button(
                            ItemBuilder.item(Material.END_CRYSTAL)
                                    .name(TextStyle.style("<gold>Kit Room"))
                                    .lore(TextStyle.style("<white>Click to open"))
                    )
                    .onClick(event -> {
                        event.setCancelled(true);
                        Armor armor = new Armor(player);
                        armor.open();
                        for (Player global : Bukkit.getOnlinePlayers()) {
                                new MessageUtil().brcmessage(player,global, "broadcast_messages.kit_room_open");
                        }
                    }));

            this.menu.button(37, Button.button(
                            ItemBuilder.item(Material.NETHERITE_HELMET).name(TextStyle.style("<gold>Premade Kit")).lore(TextStyle.style("<white>Click to claim")).addFlag(ItemFlag.HIDE_ATTRIBUTES))
                    .onClick(event -> {
                        event.setCancelled(true);
                        PremadeKits premadeKits = new PremadeKits(player);
                        premadeKits.open();
                    })
            );

            this.menu.button(40, Button.button(
                            ItemBuilder.item(Material.NETHER_STAR).name(TextStyle.style("<gold>Info")).lore(TextStyle.style("<white>yes")))
                    .onClick(event -> {
                        event.setCancelled(true);
                    })
            );
        }
    }

    public void open() {
        this.menu.open(this.player);
        new PageSound().playPageSound(player);
        for (Player global : Bukkit.getOnlinePlayers()) {
                new MessageUtil().message(global, "broadcast_messages.kit_menu_open");
        }
    }
}
