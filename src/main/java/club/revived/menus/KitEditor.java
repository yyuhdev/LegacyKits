package club.revived.menus;

import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.menus.misc.KitCopySelector;
import club.revived.objects.kit.Kit;
import club.revived.objects.kit.KitHolder;
import club.revived.objects.settings.Settings;
import club.revived.storage.DatabaseManager;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class KitEditor
        extends InventoryBuilder {

    private final Player player;
    private CloseReason reason;

    public KitEditor(int id, Player player) {
        super(54, ColorUtil.of("<player>'s Kit "
                .replace("<player>", player.getName())
                + id));
        Map<Integer, ItemStack> map = KitCache.getKits(player.getUniqueId()).get(id).getContent();
        for (int slot = 36; slot < 41; ++slot) {
            setItem(slot - 36, map.getOrDefault(slot, new ItemStack(Material.AIR)));
        }
        for (int slot = 9; slot < 36; ++slot) {
            setItem(slot, map.getOrDefault(slot, new ItemStack(Material.AIR)));
        }
        for (int slot = 0; slot < 9; slot++) {
            setItem(slot + 36, map.getOrDefault(slot, new ItemStack(Material.AIR)));
        }
        this.player = player;
        setItems(5, 7, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));
        setItem(8, ItemBuilder.item(Material.CHERRY_DOOR).name("<#cdd6fa>Exit").lore(
                ColorUtil.of(""),
                ColorUtil.of("<grey>Close the menu and"),
                ColorUtil.of("<grey>return to the main menu"),
                ColorUtil.of(""),
                ColorUtil.of("<#cdd6fa>Click to close")
        ).build(), event -> {
            event.setCancelled(true);
            new KitMenu(player).open(player);
        });
        setItems(45, 50, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));

        setItem(51, ItemBuilder.item(Material.WRITABLE_BOOK).name(ColorUtil.of("<#cdd6fa>Copy Kit"))
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Copy the contents of"),
                        ColorUtil.of("<grey>your kit into other kits"),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to open"),
                        ColorUtil.of("")
                )
                .build(), e -> {
            e.setCancelled(true);
            reason = CloseReason.PLUGIN;
            new KitCopySelector(player, id).open(player);
        });

        setItem(52, ItemBuilder.item(Material.ENDER_CHEST).name(ColorUtil.of("<#cdd6fa>Custom Enderchest"))
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Modify the enderchest which"),
                        ColorUtil.of("<grey>corresponds to <#cdd6fa>kit " + id),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to open"),
                        ColorUtil.of("")
                )
        .build(), e -> {
            e.setCancelled(true);
            reason = CloseReason.PLUGIN;
            new EnderchestEditor(player, id).open(player);
        });
        setItem(50, statusItem(player.getUniqueId(), id), event -> {
            event.setCancelled(true);
            final boolean singleClick = SettingsCache.getSettings(player.getUniqueId()).isSingleClickKitRoom();
            SettingsCache.setSettings(player.getUniqueId(), new Settings(player.getUniqueId(),
                    SettingsCache.getSettings(player.getUniqueId()).isSmartAutokit(),
                    id,
                    SettingsCache.getSettings(player.getUniqueId()).isBroadcastMessages(),
                    singleClick,
                    SettingsCache.getSettings(player.getUniqueId()).isAutokit()
                    ));
            DatabaseManager.getInstance().save(Settings.class, SettingsCache.getSettings(player.getUniqueId()));
            player.playSound(player, Sound.ITEM_SPYGLASS_USE,1,1);
            setItem(50, statusItem(player.getUniqueId(), id), event1 -> event1.setCancelled(true));
        });

        setItem(53, ItemBuilder.item(Material.CHEST).name(ColorUtil.of("<#cdd6fa>Import"))
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Import the items in your"),
                        ColorUtil.of("<grey>inventory into the kit"),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to import"),
                        ColorUtil.of("")
                )
        .build(), e -> {
            e.setCancelled(true);
            for (int slot = 9; slot < 36; ++slot) {
                setItem(slot, player.getInventory().getItem(slot));
            }
            for (int slot = 0; slot < 9; slot++) {
                setItem(slot + 36, player.getInventory().getItem(slot));
            }
            setItem(3, player.getInventory().getHelmet());
            setItem(2, player.getInventory().getChestplate());
            setItem(1, player.getInventory().getLeggings());
            setItem(0, player.getInventory().getBoots());
            setItem(4, player.getInventory().getItemInOffHand());
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
        });
        addCloseHandler(e -> {
            Map<Integer, ItemStack> contents = new ConcurrentHashMap<>();
            for (int slot = 0; slot < 5; ++slot) {
                ItemStack item = e.getInventory().getItem(slot);
                contents.put(slot + 36, Objects.requireNonNullElseGet(item, () -> new ItemStack(Material.AIR)));
            }
            for (int slot = 9; slot < 36; ++slot) {
                ItemStack item = e.getInventory().getItem(slot);
                contents.put(slot, Objects.requireNonNullElseGet(item, () -> new ItemStack(Material.AIR)));
            }
            for (int slot = 36; slot < 45; ++slot) {
                ItemStack item = e.getInventory().getItem(slot);
                contents.put(slot - 36, Objects.requireNonNullElseGet(item, () -> new ItemStack(Material.AIR)));
            }
            KitCache.addKit(player.getUniqueId(), new Kit(player.getUniqueId(), id, "name", contents));
            DatabaseManager.getInstance().save(KitHolder.class, new KitHolder(player.getUniqueId(), KitCache.getKits(player.getUniqueId())));
        });
    }

    private ItemStack statusItem(UUID uuid, int id) {
        if (SettingsCache.getSettings(uuid).getSelectedKit() == id) {
            return ItemBuilder.item(Material.KNOWLEDGE_BOOK).name("<#ffe3dc>Standard Kit")
                    .lore(
                            ColorUtil.of(""),
                            ColorUtil.of("<grey>This allows you to make"),
                            ColorUtil.of("<grey>kit <kit> your standard"
                                    .replace("<kit>", String.valueOf(id))
                            ),
                            ColorUtil.of("<grey>kit."),
                            ColorUtil.of(""),
                            ColorUtil.of("<#ffe3dc>Selected Kit: <selected>"
                                    .replace("<selected>", String.valueOf(SettingsCache.getSettings(player.getUniqueId()).getSelectedKit()))
                            ),
                            ColorUtil.of("")
                    ).build();
        }
        return ItemBuilder.item(Material.BOOK).name("<#ffe3dc>Standard Kit")
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>This allows you to make"),
                        ColorUtil.of("<grey>kit <kit> your standard"
                                .replace("<kit>", String.valueOf(id))
                        ),
                        ColorUtil.of("<grey>kit."),
                        ColorUtil.of(""),
                        ColorUtil.of("<#ffe3dc>Selected Kit: <selected>"
                                .replace("<selected>", String.valueOf(SettingsCache.getSettings(player.getUniqueId()).getSelectedKit()))
                        ),
                        ColorUtil.of("")
                ).build();
    }
}
