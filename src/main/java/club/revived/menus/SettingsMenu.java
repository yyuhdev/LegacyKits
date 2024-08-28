package club.revived.menus;

import club.revived.cache.SettingsCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.objects.settings.Settings;
import club.revived.storage.DatabaseManager;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class SettingsMenu extends InventoryBuilder {

    public SettingsMenu(Player player) {
        super(54, Component.text("<player>'s Kits"
                .replace("<player>", player.getName())
        ));
        for (int i = 0; i < this.getInventory().getSize(); i++) {
            setItem(i, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), e ->
                    e.setCancelled(true));
        }
        AtomicBoolean messages = new AtomicBoolean(SettingsCache.getSettings(player.getUniqueId()).isBroadcastMessages());
        AtomicBoolean smartAutokit = new AtomicBoolean(SettingsCache.getSettings(player.getUniqueId()).isSmartAutokit());
        AtomicBoolean autokit = new AtomicBoolean(SettingsCache.getSettings(player.getUniqueId()).isAutokit());
        AtomicBoolean singleClickKitRoom = new AtomicBoolean(SettingsCache.getSettings(player.getUniqueId()).isSingleClickKitRoom());

        setSwapItem(10,
                ItemBuilder.item(Material.LIME_DYE).name("<#cdd6fa>Kit Messages")
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Toggle kit broadcast"),
                                ColorUtil.of("<grey>messages on and off"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Enabled: <green><bold>ENABLED"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to toggle")
                        ).build(),
                ItemBuilder.item(Material.GRAY_DYE).name("<#cdd6fa>Kit Messages")
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Toggle kit broadcast"),
                                ColorUtil.of("<grey>messages on and off"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Enabled: <red><bold>DISABLED"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to toggle")
                        ).build(), messages.get(),
                event -> {
                    messages.set(true);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK,1,1);
                },
                event -> {
                    messages.set(false);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK,1,1);
                }
        );

        setSwapItem(11,
                ItemBuilder.item(Material.LIME_DYE).name("<#cdd6fa>Smart Auto Kit")
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Load last loaded"),
                                ColorUtil.of("<grey>kit on respawn."),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Enabled: <green><bold>ENABLED"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to toggle")
                        ).build(),
                ItemBuilder.item(Material.GRAY_DYE).name("<#cdd6fa>Smart Auto Kit")
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Load last loaded"),
                                ColorUtil.of("<grey>kit on respawn."),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Enabled: <red><bold>DISABLED"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to toggle")
                        ).build(), smartAutokit.get(),
                event -> {
                    smartAutokit.set(true);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK,1,1);
                },
                event -> {
                    smartAutokit.set(false);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK,1,1);
                }
        );

        setSwapItem(12,
                ItemBuilder.item(Material.LIME_DYE).name("<#cdd6fa>Auto Kit")
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Get either you last"),
                                ColorUtil.of("<grey>loaded kit or your"),
                                ColorUtil.of("<grey>selected kit on respawn"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Enabled: <green><bold>ENABLED"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to toggle")
                        ).build(),
                ItemBuilder.item(Material.GRAY_DYE).name("<#cdd6fa>Auto Kit")
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Get either you last"),
                                ColorUtil.of("<grey>loaded kit or your"),
                                ColorUtil.of("<grey>selected kit on respawn"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Enabled: <red><bold>DISABLED"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to toggle")
                        ).build(), autokit.get(),
                event -> {
                    autokit.set(true);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK,1,1);
                },
                event -> {
                    autokit.set(false);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK,1,1);
                }
        );

        setSwapItem(19,
                ItemBuilder.item(Material.LIME_DYE).name("<#cdd6fa>Single Click Kitroom")
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Instantly get the item"),
                                ColorUtil.of("<grey>you clicked in the"),
                                ColorUtil.of("<grey>kitroom into your inventory"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Enabled: <green><bold>ENABLED"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to toggle")
                        ).build(),
                ItemBuilder.item(Material.GRAY_DYE).name("<#cdd6fa>Single Click Kitroom")
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Instantly get the item"),
                                ColorUtil.of("<grey>you clicked in the"),
                                ColorUtil.of("<grey>kitroom into your inventory"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Enabled: <red><bold>DISABLED"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to toggle")
                        ).build(), singleClickKitRoom.get(),
                event -> {
                    singleClickKitRoom.set(true);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK,1,1);
                },
                event -> {
                    singleClickKitRoom.set(false);
                    player.playSound(player, Sound.BLOCK_LEVER_CLICK,1,1);
                }
        );

        setItem(40, ItemBuilder.item(Material.CHERRY_DOOR).name("<#cdd6fa>Exit").lore(
                ColorUtil.of(""),
                ColorUtil.of("<grey>Close the menu and"),
                ColorUtil.of("<grey>return to the main menu"),
                ColorUtil.of(""),
                ColorUtil.of("<#cdd6fa>Click to close")
        ).build(), event -> {
            event.setCancelled(true);
            new KitMenu(player).open(player);
        });

        addCloseHandler(event -> {
            final int id = SettingsCache.getSettings(player.getUniqueId()).getSelectedKit();
            SettingsCache.setSettings(player.getUniqueId(), new Settings(player.getUniqueId(), smartAutokit.get(), id, messages.get(), singleClickKitRoom.get(), autokit.get()));
            DatabaseManager.getInstance().save(Settings.class, SettingsCache.getSettings(player.getUniqueId()));
        });
    }
}
