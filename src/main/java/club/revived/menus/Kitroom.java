package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.storage.kit_room.KitRoomData;
import club.revived.util.enums.Page;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class Kitroom
extends InventoryBuilder {

    private Page currentPage = Page.NETHERITE_CRYSTAL;

    @SuppressWarnings("deprecation")
    public Kitroom(Player player) {
        super(54, Component.text("<player>'s Kits"
                .replace("<player>", player.getName())
        ));

        KitRoomData.loadKitRoomPage(Page.NETHERITE_CRYSTAL).thenAccept(map -> {
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

        setItem(45, ItemBuilder.item(Material.NETHER_STAR)
                .name(TextStyle.style("<#cdd6fa>✎ Refill Items")).build(), e -> {
            e.setCancelled(true);
            KitRoomData.loadKitRoomPage(currentPage).thenAccept(map -> {
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

        setItem(46, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                .name("").build(), event -> event.setCancelled(true));

        setItem(47, ItemBuilder.item(Material.NETHERITE_CHESTPLATE)
                .name(TextStyle.style("<#cdd6fa>\uD83D\uDDE1 Netherite Crystal"))
                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES).build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = Page.NETHERITE_CRYSTAL;
            KitRoomData.loadKitRoomPage(Page.NETHERITE_CRYSTAL).thenAccept(map -> {
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

        setItem(48, ItemBuilder.item(Material.DIAMOND_CHESTPLATE)
                .name(TextStyle.style("<#cdd6fa>\uD83D\uDDE1 Diamond Crystal"))
                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES).build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = Page.DIAMOND_CRYSTAL;
            KitRoomData.loadKitRoomPage(Page.DIAMOND_CRYSTAL).thenAccept(map -> {
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
                .addFlag(ItemFlag.HIDE_POTION_EFFECTS)
                .build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = Page.POTIONS;
            KitRoomData.loadKitRoomPage(Page.POTIONS).thenAccept(map -> {
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
                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = Page.ARMORY;
            KitRoomData.loadKitRoomPage(Page.ARMORY).thenAccept(map -> {
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

        setItem(51, ItemBuilder.item(Material.SHIELD)
                .name(TextStyle.style("<#cdd6fa>\uD83D\uDD31 Miscellaneous"))
                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build(), e -> {
            e.setCancelled(true);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
            currentPage = Page.MISC;
            KitRoomData.loadKitRoomPage(Page.MISC).thenAccept(map -> {
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

        setItem(52, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                .name("").build(), event -> event.setCancelled(true));

        setItem(53, ItemBuilder.item(Material.BARRIER)
                .name(TextStyle.style("<red>Go Back")).build(), e -> {
            e.setCancelled(true);
            player.closeInventory();
        });

        addCloseHandler(e -> Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new KitMenu(player).open(player),1));
    }
}