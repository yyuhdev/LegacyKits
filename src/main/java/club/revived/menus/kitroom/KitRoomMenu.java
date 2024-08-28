package club.revived.menus.kitroom;

import club.revived.framework.inventory.InventoryBuilder;
import club.revived.menus.KitMenu;
import club.revived.menus.kitroom.pages.*;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;

public class KitRoomMenu extends InventoryBuilder {

    public KitRoomMenu(Player player) {
        super(54, ColorUtil.of("<player>'s Kits"
                .replace("<player>", player.getName())
        ), true);

        AtomicBoolean editing = new AtomicBoolean(false);

        for(int x = 0; x<this.getInventory().getSize(); x++){
            setItem(x, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                    .name("").build());
        }

        setItem(11, ItemBuilder.item(Material.NETHERITE_CHESTPLATE)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .name("<#cdd6fa>Netherite PvP")
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Get default crystal"),
                        ColorUtil.of("<grey>pvp items in here"),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to open")
                )
                .build(), event -> {
            event.setCancelled(true);
            new NetheritePvPPage(player, editing.get()).open(player);
        });

        setItem(13, ItemBuilder.item(Material.DIAMOND_CHESTPLATE)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .name("<#cdd6fa>Diamond PvP")
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Get diamond crystal"),
                        ColorUtil.of("<grey>pvp items in here"),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to open")
                )
                .build(), event -> {
            event.setCancelled(true);
            new DiamondPvPPage(player, editing.get()).open(player);
        });

        setItem(15, ItemBuilder.item(Material.NETHERITE_SWORD)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .name("<#cdd6fa>Armory")
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Get tools & crystal"),
                        ColorUtil.of("<grey>pvp items in here"),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to open")
                )
                .build(), event -> {
            event.setCancelled(true);
            new ArmoryPage(player, editing.get()).open(player);
        });

        setItem(29, ItemBuilder.item(Material.TOTEM_OF_UNDYING)
                .name("<#cdd6fa>Consumables")
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Get items like totems &"),
                        ColorUtil.of("<grey>other consumable items in here"),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to open")
                )
                .build(), event -> {
            event.setCancelled(true);
            new ConsumablesPage(player, editing.get()).open(player);
        });

        setItem(31, ItemBuilder.item(Material.SHULKER_BOX)
                .name("<#cdd6fa>Shulkers")
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Get shulkers"),
                        ColorUtil.of("<grey>for drain in here"),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to open")
                )
                .build(), event -> {
            event.setCancelled(true);
            new ShulkersPage(player, editing.get()).open(player);
        });

        setItem(33, ItemBuilder.item(Material.SHIELD)
                .name("<#cdd6fa>Miscellaneous")
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Get other items"),
                        ColorUtil.of("<grey>in here"),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to open")
                )
                .build(), event -> {
            event.setCancelled(true);
            new MiscellaneousPage(player, editing.get()).open(player);
        });

        setItem(53, ItemBuilder.item(Material.CHERRY_DOOR).name("<#cdd6fa>Exit").lore(
                ColorUtil.of(""),
                ColorUtil.of("<grey>Close the menu and"),
                ColorUtil.of("<grey>return to the main menu"),
                ColorUtil.of(""),
                ColorUtil.of("<#cdd6fa>Click to close")
        ).build(), event -> {
            event.setCancelled(true);
            new KitMenu(player).open(player);
        });

        setItem(51, ItemBuilder.item(Material.TNT).name("<#cdd6fa>Clear Inventory")
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Click to clear"),
                        ColorUtil.of("<grey>your inventory")
                ).build(), event -> {
                    event.setCancelled(true);
                    player.getInventory().clear();
                }
        );

        setItem(52, ItemBuilder.item(Material.TOTEM_OF_UNDYING)
                .name("<#cdd6fa>Totem Filler")
                .lore(
                        ColorUtil.of(""),
                        ColorUtil.of("<grey>Fill your entire"),
                        ColorUtil.of("<grey>inventory with totems"),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to fill")
                ).build(), event -> {
                    event.setCancelled(true);
                    for (int i = 0; i < player.getInventory().getSize(); i++) {
                        player.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING));
                    }
                }
        );

        if(player.hasPermission("legacykits.edit.kitroom")) {
            setSwapItem(44,
                    ItemBuilder.item(Material.LEVER)
                            .name("<#cdd6fa>Editing Mode")
                            .lore(
                                    ColorUtil.of(""),
                                    ColorUtil.of("<grey>Edit any kit room"),
                                    ColorUtil.of("<grey>page while being in this"),
                                    ColorUtil.of("<grey>mode. This is admin only!"),
                                    ColorUtil.of(""),
                                    ColorUtil.of("<grey>Enabled: <green><bold>ENABLED"),
                                    ColorUtil.of(""),
                                    ColorUtil.of("<#cdd6fa>Click to toggle")
                            ).build(),
                    ItemBuilder.item(Material.LEVER).name("<#cdd6fa>Editing Mode")
                            .lore(
                                    ColorUtil.of(""),
                                    ColorUtil.of("<grey>Edit any kit room"),
                                    ColorUtil.of("<grey>page while being in this"),
                                    ColorUtil.of("<grey>mode. This is admin only!"),
                                    ColorUtil.of(""),
                                    ColorUtil.of("<grey>Enabled: <red><bold>DISABLED"),
                                    ColorUtil.of(""),
                                    ColorUtil.of("<#cdd6fa>Click to toggle")
                            ).build(), editing.get(),
                    event -> {
                        editing.set(true);
                        player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1,1);
                    }, event -> {
                        editing.set(false);
                        player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1,1);
                    });
        }
    }
}
