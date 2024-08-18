package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.objects.Kit;
import club.revived.objects.KitHolder;
import club.revived.objects.KitType;
import club.revived.objects.Settings;
import club.revived.storage.DatabaseManager;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class KitEditor
        extends InventoryBuilder {

    public KitEditor(int id, Player player) {
        super(54, TextStyle.style("<player>'s Kit "
                .replace("<player>", player.getName())
                + id));
        setItems(5,8, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));
        setItems(45,50, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));

        setItem(51, ItemBuilder.item(Material.RED_DYE).name(TextStyle.style("<red>Clear Kit")).build(), e -> {
            e.setCancelled(true);
            if (e.getClick().isShiftClick()) {
                for (int slot = 36; slot < 41; ++slot) {
                    setItem(slot, null);
                }
                for (int slot = 9; slot < 36; ++slot) {
                    setItem(slot, null);
                }
                for(int slot = 0; slot<9; slot++){
                    setItem(slot, null);
                }
            }
        });

        setItem(52, ItemBuilder.item(Material.LIME_CANDLE).name(TextStyle.style("<green>Save")).build(), e -> {
            e.setCancelled(true);
            Map<Integer, ItemStack> contents = new HashMap<>();
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
            KitCache.addKit(player.getUniqueId(), new Kit(player.getUniqueId(), id, contents, KitType.INVENTORY));
            DatabaseManager.getInstance().save(KitHolder.class, new KitHolder(player.getUniqueId(), KitCache.getKits(player.getUniqueId())));
        });

        if(LegacyKits.autokitKit.getOrDefault(player.getUniqueId(), 1) == id){
            setItem(50, ItemBuilder.item(Material.KNOWLEDGE_BOOK).name("<#ffe3dc>Standard Kit")
                            .lore(
                                    TextStyle.style(""),
                                    TextStyle.style("<grey>This allows you to make"),
                                    TextStyle.style("<grey>kit <kit> your standard"
                                            .replace("<kit>", String.valueOf(id))
                                    ),
                                    TextStyle.style("<grey>kit."),
                                    TextStyle.style(""),
                                    TextStyle.style("<#ffe3dc>Selected Kit: <selected>"
                                            .replace("<selected>", String.valueOf(LegacyKits.autokitKit.getOrDefault(player.getUniqueId(), 1)))
                                    ),
                                    TextStyle.style("")
                            ).build(), e -> {
                        e.setCancelled(true);
                        LegacyKits.autokitKit.put(player.getUniqueId(), id);
                    }
            );
        }
        else {
            setItem(50, ItemBuilder.item(Material.BOOK).name("<#ffe3dc>Standard Kit")
                            .lore(
                                    TextStyle.style(""),
                                    TextStyle.style("<grey>This allows you to make"),
                                    TextStyle.style("<grey>kit <kit> your standard"
                                            .replace("<kit>", String.valueOf(id))
                                    ),
                                    TextStyle.style("<grey>kit."),
                                    TextStyle.style(""),
                                    TextStyle.style("<#ffe3dc>Selected Kit: <selected>"
                                            .replace("<selected>", String.valueOf(LegacyKits.autokitKit.getOrDefault(player.getUniqueId(), 1)))
                                    ),
                                    TextStyle.style("")
                            ).build(), e -> {
                        e.setCancelled(true);
                        SettingsCache.setSettings(player.getUniqueId(), new Settings(player.getUniqueId(), false, id));
                        DatabaseManager.getInstance().save(Settings.class, SettingsCache.getSettings(player.getUniqueId()));
                        LegacyKits.autokitKit.put(player.getUniqueId(), id);
                        setItem(50, ItemBuilder.item(Material.KNOWLEDGE_BOOK).name("<#ffe3dc>Standard Kit")
                                        .lore(
                                                TextStyle.style(""),
                                                TextStyle.style("<grey>This allows you to make"),
                                                TextStyle.style("<grey>kit <kit> your standard"
                                                        .replace("<kit>", String.valueOf(id))
                                                ),
                                                TextStyle.style("<grey>kit."),
                                                TextStyle.style(""),
                                                TextStyle.style("<#ffe3dc>Selected Kit: <selected>"
                                                        .replace("<selected>", String.valueOf(LegacyKits.autokitKit.getOrDefault(player.getUniqueId(), 1)))
                                                ),
                                                TextStyle.style("")
                                        ).build(), event -> {
                                    event.setCancelled(true);
                                }
                        );
                    }
            );
        }

        setItem(53, ItemBuilder.item(Material.CHEST).name(TextStyle.style("<#ffe3dc>Import from Inventory")).build(), e -> {
            e.setCancelled(true);
            for (int slot = 9; slot < 36; ++slot) {
                setItem(slot, player.getInventory().getItem(slot));
            }
            for(int slot = 0; slot<9; slot++){
                setItem(slot+36, player.getInventory().getItem(slot));
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
            KitCache.addKit(player.getUniqueId(), new Kit(player.getUniqueId(), id, contents, KitType.INVENTORY));
            DatabaseManager.getInstance().save(KitHolder.class, new KitHolder(player.getUniqueId(), KitCache.getKits(player.getUniqueId())));
            Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new KitMenu(player).open(player),1);
        });

        for(Kit kit : KitCache.getKits(player.getUniqueId())){
            if(kit.getType() != KitType.INVENTORY) continue;
            if(kit.getID() == id){
                Map<Integer, ItemStack> map =  kit.getContent();
                for (int slot = 36; slot < 41; ++slot) {
                    setItem(slot-36, map.get(slot));
                }
                for (int slot = 9; slot < 36; ++slot) {
                    setItem(slot, map.get(slot));
                }
                for(int slot = 0; slot<9; slot++){
                    setItem(slot+36, map.get(slot));
                }
            }
        }
    }
}
