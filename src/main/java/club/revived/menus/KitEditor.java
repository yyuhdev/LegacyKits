package club.revived.menus;

import club.revived.AithonKits;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitEditor {
    private final Player player;
    private final MenuBase<?> menu;
    private final Integer kit;
    private final AithonKits kits;
    private final ConfigUtil configUtil;

    public KitEditor(Player player, Integer kit) {
        this.kit = kit;
        this.player = player;
        this.kits = AithonKits.getInstance();
        this.configUtil = AithonKits.getInstance().getConfigUtil();
        this.menu = Menu.menu(TextStyle.style("<#FFD1A3>Kit " + kit), 5 * 9);
        init();
    }

    private void init() {
        this.menu.border(Button.button(
                ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                    .name(""),
                event -> event.setCancelled(true)
            ),
            ". . . . . . . . .",
            ". . . . . . . . .",
            ". . . . . . . . .",
            ". . . . . . . . .",
            ". . . . . X X X ."
        );

        Map<Integer, ItemStack> map = AithonKits.getInstance().getConfigUtil().load(player.getUniqueId(), String.valueOf(kit));

        for (int slot = 0; slot < 41; slot++) {
            menu.inventory().setItem(slot, map.get(slot));
        }

        this.menu.button(43, Button.button(
                ItemBuilder.item(Material.LIME_DYE).name(TextStyle.style("<green>Make Public")))
                .onClick(event -> {
                    event.setCancelled(true);
                    if(configUtil.savePublicKit(player.getUniqueId(), event.getInventory()))
                        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP,1,2);
                    else
                        player.sendRichMessage("<red>Something went wrong whilst trying to make your kit public");
                })
        );

        this.menu.button(44, Button.button(ItemBuilder.item(Material.CHEST)
            .name(TextStyle.style("<#FFD1A3>Import from Inventory"))
        ).onClick(event -> {
            event.setCancelled(true);

            if (player.getInventory().contains(Material.ENCHANTED_GOLDEN_APPLE)) for (int i = 0; i < 41; i++) {
                ItemStack item = player.getInventory().getContents()[i];
                if (item == null) continue;

                if (item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                }
            }

            for (int slot = 0; slot < 41; slot++) {
                this.menu.inventory().setItem(slot, this.player.getInventory().getItem(slot));
            }

            Inventory inventory = this.menu.inventory();

            inventory.setItem(36, player.getInventory().getHelmet());
            inventory.setItem(37, player.getInventory().getChestplate());
            inventory.setItem(38, player.getInventory().getLeggings());
            inventory.setItem(39, player.getInventory().getBoots());
            inventory.setItem(40, player.getInventory().getItemInOffHand());

            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
        }));

        this.menu.onClose(event -> {
            if (configUtil.save(player.getUniqueId(), String.valueOf(kit), event.getInventory())) {
                MessageUtil.send(player, "messages.kit_save");
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);

                configUtil.save(player.getUniqueId(), String.valueOf(kit), event.getInventory());

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
