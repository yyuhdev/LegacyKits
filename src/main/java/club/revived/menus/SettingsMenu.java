package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.cache.SettingsCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.objects.settings.Settings;
import club.revived.storage.DatabaseManager;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class SettingsMenu extends InventoryBuilder {

    private final Player player;

    public SettingsMenu(Player player) {
        super(27, Component.text("<player>'s Kits"
                .replace("<player>", player.getName())
        ));
        this.player = player;

        AtomicBoolean messages = new AtomicBoolean(SettingsCache.getSettings(player.getUniqueId()).isBroadcastMessages());
        AtomicBoolean smartAutokit = new AtomicBoolean(SettingsCache.getSettings(player.getUniqueId()).isSmartAutokit());

        setSwapItem(11,
                ItemBuilder.item(Material.CHERRY_SIGN).name("<#cdd6fa>Kit Messages")
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Toggle kit broadcast"),
                                ColorUtil.of("<grey>messages on and off"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Enabled: <green><bold>ENABLED"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to toggle")
                        ).build(),
                ItemBuilder.item(Material.CHERRY_SIGN).name("<#cdd6fa>Kit Messages")
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
                },
                event -> {
                    messages.set(false);
                }
        );

        setSwapItem(13,
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
                },
                event -> {
                    smartAutokit.set(false);
                }
        );

        addCloseHandler(event -> {
            final int id = SettingsCache.getSettings(player.getUniqueId()).getSelectedKit();
            SettingsCache.setSettings(player.getUniqueId(), new Settings(player.getUniqueId(), smartAutokit.get(), id, messages.get()));
            DatabaseManager.getInstance().save(Settings.class, SettingsCache.getSettings(player.getUniqueId()));
            Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new KitMenu(player).open(player), 1);
        });
    }
}
