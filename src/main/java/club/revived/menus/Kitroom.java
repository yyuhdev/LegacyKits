package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.cache.KitRoomCache;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Kitroom
extends InventoryBuilder {

    private KitroomPage currentPage = KitroomPage.NETHERITE_CRYSTAL;
    private boolean isEditing = false;

    @SuppressWarnings("deprecation")
    public Kitroom(Player player) {
        super(54, Component.text("<player>'s Kits"
                .replace("<player>", player.getName())
        ));

        KitRoomCache.getKitRoomPage(KitroomPage.NETHERITE_CRYSTAL).thenAccept(map -> {
            for(int x = 0; x<45; x++){
                setItem(x, map.get(x));
            }
        });

        setItem(46, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                .name("").build(), event -> event.setCancelled(true));

        setItem(46, ItemBuilder.item(Material.NETHERITE_CHESTPLATE)
                .name(ColorUtil.of("<#cdd6fa>\uD83D\uDDE1 Netherite Crystal"))
                .addEnchantment(Enchantment.PROTECTION, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES).build(), e -> {
            e.setCancelled(true);
            if(isEditing) return;
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.NETHERITE_CRYSTAL;
            KitRoomCache.getKitRoomPage(KitroomPage.NETHERITE_CRYSTAL).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    setItem(x, map.get(x));
                }
            });
        });

        setItem(47, ItemBuilder.item(Material.DIAMOND_CHESTPLATE)
                .name(ColorUtil.of("<#cdd6fa>\uD83D\uDDE1 Diamond Crystal"))
                .addEnchantment(Enchantment.PROTECTION, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES).build(), e -> {
            e.setCancelled(true);
            if(isEditing) return;
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.DIAMOND_CRYSTAL;
            KitRoomCache.getKitRoomPage(KitroomPage.DIAMOND_CRYSTAL).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    setItem(x, map.get(x));
                }
            });
        });

        setItem(48, ItemBuilder.item(Material.ARROW)
                .name(ColorUtil.of("<#cdd6fa>🏹 Arrows"))
                .addFlag(ItemFlag.HIDE_ITEM_SPECIFICS)
                .build(), e -> {
            e.setCancelled(true);
            if(isEditing) return;
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.ARROWS;
            KitRoomCache.getKitRoomPage(KitroomPage.ARROWS).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    setItem(x, map.get(x));
                }
            });
        });

        setItem(49, ItemBuilder.item(Material.SPLASH_POTION)
                .name(ColorUtil.of("<#cdd6fa>⚗ Potions"))
                .addFlag(ItemFlag.HIDE_ITEM_SPECIFICS)
                .build(), e -> {
            e.setCancelled(true);
            if(isEditing) return;
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.POTIONS;
            KitRoomCache.getKitRoomPage(KitroomPage.POTIONS).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    setItem(x, map.get(x));
                }
            });
        });

        setItem(50, ItemBuilder.item(Material.NETHERITE_SWORD)
                .name(ColorUtil.of("<#cdd6fa>\uD83C\uDFF9 Armory"))
                .addEnchantment(Enchantment.BANE_OF_ARTHROPODS, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), e -> {
            e.setCancelled(true);
            if(isEditing) return;
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.ARMORY;
            KitRoomCache.getKitRoomPage(KitroomPage.ARMORY).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    setItem(x, map.get(x));
                }
            });
        });

        setItem(51, ItemBuilder.item(Material.MACE)
                .name(ColorUtil.of("<#cdd6fa>⚔ Special Items"))
                .addEnchantment(Enchantment.BANE_OF_ARTHROPODS, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), e -> {
            e.setCancelled(true);
            if(isEditing) return;
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.SPECIAL_ITEMS;
            KitRoomCache.getKitRoomPage(KitroomPage.SPECIAL_ITEMS).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    setItem(x, map.get(x));
                }
            });
        });

        setItem(52, ItemBuilder.item(Material.SHIELD)
                .name(ColorUtil.of("<#cdd6fa>\uD83D\uDD31 Miscellaneous"))
                .addEnchantment(Enchantment.BANE_OF_ARTHROPODS, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            if(isEditing) return;
            currentPage = KitroomPage.MISC;
            KitRoomCache.getKitRoomPage(KitroomPage.MISC).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    setItem(x, map.get(x));
                }
            });
        });

        if(player.hasPermission("legacykits.edit.kitroom")){
            setItem(53, ItemBuilder.item(Material.WRITABLE_BOOK).name(ColorUtil.of("<#ffe3dc>Edit Kit")).build(), e -> {
                e.setCancelled(true);
                isEditing = true;
                Map<Integer, ItemStack> content = new HashMap<>();
                for(int x = 0; x<45; x++){
                    content.put(x, this.getInventory().getItem(x));
                }
                addCloseHandler(event -> KitRoomCache.saveKitRoomPage(currentPage, content));
            });
        }
        else {
            setItem(53, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));
        }

        setItem(45, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));

//        setItem(53, ItemBuilder.item(Material.BARRIER)
//                .name(ColorUtil.of("<red>Go Back")).build(), e -> {
//            e.setCancelled(true);
//            player.closeInventory();
//        });

        addCloseHandler(e -> Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new KitMenu(player).open(player),1));
    }
}