package club.revived.menus;

import club.revived.WeirdoKits;
import club.revived.util.ConfigUtil;
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
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnderchestEditor {

    private final Player player;
    private final MenuBase<?> menu;
    private final Integer i;
    private final WeirdoKits kits;
    private final ConfigUtil configUtil;

    public EnderchestEditor(Player player, Integer kit) {
        this.i = kit;
        this.player = player;
        this.kits = WeirdoKits.getInstance();
        this.configUtil = WeirdoKits.getInstance().getConfigUtil();
        this.menu = Menu.menu(TextStyle.style("<gold>Enderchest " + kit), 4 * 9);
        init();
    }

    private void init() {
        this.menu.border(Button.button(
                    ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                        .name(""))
                .onClick(event -> {
                    event.setCancelled(true);
                }),
            ". . . . . . . . .",
            ". . . . . . . . .",
            ". . . . . . . . .",
            "X X X X X X X . ."
        );

        Map<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().loadEnderChest(player.getUniqueId(), String.valueOf(i));
        for (int slot = 0; slot < 27; slot++)
            this.menu.inventory().setItem(slot, map.get(slot));

        this.menu.button(35, Button.button(
                ItemBuilder.item(Material.DIAMOND_CHESTPLATE)
                    .name(TextStyle.style("<aqua>Import from Inventory")))
            .onClick(event -> {
                event.setCancelled(true);

                if (player.getInventory().contains(Material.ENCHANTED_GOLDEN_APPLE)) for (int i = 0; i < 27; i++) {
                    ItemStack item = player.getInventory().getContents()[i];
                    if (item == null) continue;

                    if (item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                        player.getInventory().setItem(i, new ItemStack(Material.AIR));
                    }
                }

                for (int slot = 0; slot < 27; slot++) {
                    this.menu.inventory().setItem(slot, this.player.getInventory().getItem(slot));
                }

                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
            })
        );

        this.menu.button(34, Button.button(
                ItemBuilder.item(Material.ENDER_CHEST)
                    .name(TextStyle.style("<aqua>Import from Enderchest")))
            .onClick(event -> {
                event.setCancelled(true);

                if (player.getEnderChest().contains(Material.ENCHANTED_GOLDEN_APPLE)) for (int i = 0; i < 27; i++) {
                    ItemStack item = player.getEnderChest().getContents()[i];
                    if (item == null) continue;

                    if (item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                        player.getEnderChest().setItem(i, new ItemStack(Material.AIR));
                    }
                }

                for (int slot = 0; slot < 27; slot++) {
                    this.menu.inventory().setItem(slot, this.player.getEnderChest().getItem(slot));
                }

                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
            })
        );

        this.menu.onClose(event -> {
            if (configUtil.saveEnderChest(player.getUniqueId(), String.valueOf(i), event.getInventory())) {
                MessageUtil.send(player, "messages.enderchest_save");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);

                configUtil.saveEnderChest(player.getUniqueId(), String.valueOf(i), event.getInventory());

                Bukkit.getScheduler().runTaskLater(kits, () -> {
                    KitMenu kitMenu = new KitMenu(player);
                    kitMenu.open();
                }, 1L);
                return;
            }

            player.sendRichMessage("<dark_red><bold>FAILED");
            kits.getComponentLogger().error(TextStyle.style("<dark_red>SEVERE ERROR WHILST KIT SAVING <reset><purple>uwu"));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0F, 5.0F);
        });
    }

    public void open() {
        this.menu.open(this.player);
        new PageSound().play(player);
    }
}
