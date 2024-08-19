package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.menus.misc.KitCopySelector;
import club.revived.objects.Kit;
import club.revived.objects.KitHolder;
import club.revived.objects.Settings;
import club.revived.storage.DatabaseManager;
import club.revived.util.enums.CloseReason;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KitEditor
        extends InventoryBuilder {

    private final Player player;
    private CloseReason reason;

    public KitEditor(int id, Player player) {
        super(54, TextStyle.style("<player>'s Kit "
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
        setItems(5, 8, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));
        setItems(45, 50, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));

        setItem(51, ItemBuilder.item(Material.WRITABLE_BOOK).name(TextStyle.style("<#ffe3dc>Copy Kit"))
                .lore(
                        TextStyle.style(""),
                        TextStyle.style("<grey>Copy the contents of"),
                        TextStyle.style("<grey>your kit into other kits"),
                        TextStyle.style("")
                )
                .build(), e -> {
            e.setCancelled(true);
            reason = CloseReason.PLUGIN;
            new KitCopySelector(player, id).open(player);
        });

        setItem(52, ItemBuilder.item(Material.ENDER_CHEST).name(TextStyle.style("<#ffe3dc>Custom Enderchest"))
                .lore(
                        TextStyle.style(""),
                        TextStyle.style("<grey>Modify the enderchest which"),
                        TextStyle.style("<grey>corresponds to <#ffe3dc>kit " + id),
                        TextStyle.style("")
                )
        .build(), e -> {
            e.setCancelled(true);
            reason = CloseReason.PLUGIN;
            new EnderchestEditor(player, id).open(player);
        });
        setItem(50, statusItem(player.getUniqueId(), id), event -> {
            event.setCancelled(true);
            SettingsCache.setSettings(player.getUniqueId(), new Settings(player.getUniqueId(), false, id));
            DatabaseManager.getInstance().save(Settings.class, SettingsCache.getSettings(player.getUniqueId()));
            setItem(50, statusItem(player.getUniqueId(), id), event1 -> event1.setCancelled(true));
        });

        setItem(53, ItemBuilder.item(Material.CHEST).name(TextStyle.style("<#ffe3dc>Import from Inventory")).build(), e -> {
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
            if(reason == CloseReason.PLUGIN) return;
            Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new KitMenu(player).open(player), 1);
        });
    }

    private ItemStack statusItem(UUID uuid, int id) {
        if (SettingsCache.getSettings(uuid).getSelectedKit() == id) {
            return ItemBuilder.item(Material.KNOWLEDGE_BOOK).name("<#ffe3dc>Standard Kit")
                    .lore(
                            TextStyle.style(""),
                            TextStyle.style("<grey>This allows you to make"),
                            TextStyle.style("<grey>kit <kit> your standard"
                                    .replace("<kit>", String.valueOf(id))
                            ),
                            TextStyle.style("<grey>kit."),
                            TextStyle.style(""),
                            TextStyle.style("<#ffe3dc>Selected Kit: <selected>"
                                    .replace("<selected>", String.valueOf(SettingsCache.getSettings(player.getUniqueId()).getSelectedKit()))
                            ),
                            TextStyle.style("")
                    ).build();
        }
        return ItemBuilder.item(Material.BOOK).name("<#ffe3dc>Standard Kit")
                .lore(
                        TextStyle.style(""),
                        TextStyle.style("<grey>This allows you to make"),
                        TextStyle.style("<grey>kit <kit> your standard"
                                .replace("<kit>", String.valueOf(id))
                        ),
                        TextStyle.style("<grey>kit."),
                        TextStyle.style(""),
                        TextStyle.style("<#ffe3dc>Selected Kit: <selected>"
                                .replace("<selected>", String.valueOf(SettingsCache.getSettings(player.getUniqueId()).getSelectedKit()))
                        ),
                        TextStyle.style("")
                ).build();
    }
}
