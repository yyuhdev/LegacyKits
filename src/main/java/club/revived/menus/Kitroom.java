package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.storage.room.KitRoomData;
import club.revived.util.enums.KitroomPage;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;

public class Kitroom
extends InventoryBuilder {

    private KitroomPage currentPage = KitroomPage.NETHERITE_CRYSTAL;

    @SuppressWarnings("deprecation")
    public Kitroom(Player player) {
        super(54, Component.text("<player>'s Kits"
                .replace("<player>", player.getName())
        ));

        KitRoomData.loadKitRoomPage(KitroomPage.NETHERITE_CRYSTAL).thenAccept(map -> {
            for(int x = 0; x<45; x++){
                int finalX = x;
                setItem(x, map.get(x), event -> {
                    if(LegacyKits.getInstance().getConfig().getBoolean("single_click")){
                        event.setCancelled(true);
                        player.getInventory().addItem(map.get(finalX));
                    }
                });
            }
        });

//        setItem(45, ItemBuilder.item(Material.NETHER_STAR)
//                .name(TextStyle.style("<#cdd6fa>✎ Refill Items")).build(), e -> {
//            e.setCancelled(true);
//            KitRoomData.loadKitRoomPage(currentPage).thenAccept(map -> {
//                for(int x = 0; x<45; x++){
//                    int finalX = x;
//                    setItem(x, map.get(x), event -> {
//                        if(LegacyKits.getInstance().getConfig().getBoolean("single_click")){
//                            event.setCancelled(true);
//                            player.getInventory().addItem(map.get(finalX));
//                        }
//                    });
//                }
//            });
//        });

        setItem(46, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                .name("").build(), event -> event.setCancelled(true));

        setItem(46, ItemBuilder.item(Material.NETHERITE_CHESTPLATE)
                .name(TextStyle.style("<#cdd6fa>\uD83D\uDDE1 Netherite Crystal"))
                .addEnchantment(Enchantment.PROTECTION, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES).build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.NETHERITE_CRYSTAL;
            KitRoomData.loadKitRoomPage(KitroomPage.NETHERITE_CRYSTAL).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    int finalX = x;
                    setItem(x, map.get(x), event -> {
                        if(LegacyKits.getInstance().getConfig().getBoolean("single_click")){
                            event.setCancelled(true);
                            player.getInventory().addItem(map.get(finalX));
                        }
                    });
                }
            });
        });

        setItem(47, ItemBuilder.item(Material.DIAMOND_CHESTPLATE)
                .name(TextStyle.style("<#cdd6fa>\uD83D\uDDE1 Diamond Crystal"))
                .addEnchantment(Enchantment.PROTECTION, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES).build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.DIAMOND_CRYSTAL;
            KitRoomData.loadKitRoomPage(KitroomPage.DIAMOND_CRYSTAL).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    int finalX = x;
                    setItem(x, map.get(x), event -> {
                        if(LegacyKits.getInstance().getConfig().getBoolean("single_click")){
                            event.setCancelled(true);
                            player.getInventory().addItem(map.get(finalX));
                        }
                    });
                }
            });
        });

        setItem(48, ItemBuilder.item(Material.ARROW)
                .name(TextStyle.style("<#cdd6fa>🏹 Arrows"))
                .addFlag(ItemFlag.HIDE_ITEM_SPECIFICS)
                .build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.ARROWS;
            KitRoomData.loadKitRoomPage(KitroomPage.ARROWS).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    int finalX = x;
                    setItem(x, map.get(x), event -> {
                        if(LegacyKits.getInstance().getConfig().getBoolean("single_click")){
                            event.setCancelled(true);
                            player.getInventory().addItem(map.get(finalX));
                        }
                    });
                }
            });
        });

        setItem(49, ItemBuilder.item(Material.SPLASH_POTION)
                .name(TextStyle.style("<#cdd6fa>⚗ Potions"))
                .addFlag(ItemFlag.HIDE_ITEM_SPECIFICS)
                .build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.POTIONS;
            KitRoomData.loadKitRoomPage(KitroomPage.POTIONS).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    int finalX = x;
                    setItem(x, map.get(x), event -> {
                        if(LegacyKits.getInstance().getConfig().getBoolean("single_click")){
                            event.setCancelled(true);
                            player.getInventory().addItem(map.get(finalX));
                        }
                    });
                }
            });
        });

        setItem(50, ItemBuilder.item(Material.NETHERITE_SWORD)
                .name(TextStyle.style("<#cdd6fa>\uD83C\uDFF9 Armory"))
                .addEnchantment(Enchantment.BANE_OF_ARTHROPODS, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.ARMORY;
            KitRoomData.loadKitRoomPage(KitroomPage.ARMORY).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    int finalX = x;
                    setItem(x, map.get(x), event -> {
                        if(LegacyKits.getInstance().getConfig().getBoolean("single_click")){
                            event.setCancelled(true);
                            player.getInventory().addItem(map.get(finalX));
                        }
                    });
                }
            });
        });

        setItem(51, ItemBuilder.item(Material.MACE)
                .name(TextStyle.style("<#cdd6fa>⚔ Special Items"))
                .addEnchantment(Enchantment.BANE_OF_ARTHROPODS, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.SPECIAL_ITEMS;
            KitRoomData.loadKitRoomPage(KitroomPage.SPECIAL_ITEMS).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    int finalX = x;
                    setItem(x, map.get(x), event -> {
                        if(LegacyKits.getInstance().getConfig().getBoolean("single_click")){
                            event.setCancelled(true);
                            player.getInventory().addItem(map.get(finalX));
                        }
                    });
                }
            });
        });

        setItem(52, ItemBuilder.item(Material.SHIELD)
                .name(TextStyle.style("<#cdd6fa>\uD83D\uDD31 Miscellaneous"))
                .addEnchantment(Enchantment.BANE_OF_ARTHROPODS, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = KitroomPage.MISC;
            KitRoomData.loadKitRoomPage(KitroomPage.MISC).thenAccept(map -> {
                for(int x = 0; x<45; x++){
                    int finalX = x;
                    setItem(x, map.get(x), event -> {
                        if(LegacyKits.getInstance().getConfig().getBoolean("single_click")){
                            event.setCancelled(true);
                            player.getInventory().addItem(map.get(finalX));
                        }
                    });
                }
            });
        });

        setItem(53, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));
        setItem(45, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));

//        setItem(53, ItemBuilder.item(Material.BARRIER)
//                .name(TextStyle.style("<red>Go Back")).build(), e -> {
//            e.setCancelled(true);
//            player.closeInventory();
//        });

        addCloseHandler(e -> Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new KitMenu(player).open(player),1));
    }
}