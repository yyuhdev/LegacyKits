package club.revived.menus;

import club.revived.AithonKits;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.util.MessageUtil;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnderchestEditor
extends InventoryBuilder {

    public EnderchestEditor(Player player, int kit) {
        super(36, TextStyle.style("<player>'s Enderchest "
                .replace("<player>", player.getName())
                + kit));
        setItems(27, 33, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));
        setItem(35, ItemBuilder.item(Material.CHEST).name(TextStyle.style("<#cdd6fa>Import from Inventory")).build(), e -> {
            e.setCancelled(true);
            if (player.getInventory().contains(Material.ENCHANTED_GOLDEN_APPLE)) for (int i = 0; i < 27; i++) {
                ItemStack item = player.getInventory().getContents()[i];
                if (item == null) continue;
                if (item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                }
            }
            for (int slot = 0; slot < 27; slot++) {
                setItem(slot, player.getInventory().getItem(slot));
            }

            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
        });

        setItem(33, ItemBuilder.item(Material.RED_DYE).name(TextStyle.style("<red>Clear Editor")).build(), e -> {
            e.setCancelled(true);
            for(int x = 0; x<27; x++){
                setItem(x, null);
            }
        });

        setItem(34, ItemBuilder.item(Material.ENDER_CHEST)
                .name(TextStyle.style("<#cdd6fa>Import from Enderchest")).build(), e -> {
            e.setCancelled(true);

            if (player.getEnderChest().contains(Material.ENCHANTED_GOLDEN_APPLE)) for (int i = 0; i < 27; i++) {
                ItemStack item = player.getEnderChest().getContents()[i];
                if (item == null) continue;

                if (item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                    player.getEnderChest().setItem(i, new ItemStack(Material.AIR));
                }
            }

            for (int slot = 0; slot < 27; slot++) {
                setItem(slot, player.getEnderChest().getItem(slot));
            }

            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
        });

        AithonKits.getInstance().getConfigUtil().loadEnderChest(player.getUniqueId(),String.valueOf(kit)).thenAccept(map -> {
            for (int slot = 0; slot < 27; ++slot) {
                setItem(slot, map.get(slot));
            }
        });

        addCloseHandler(e -> {
            AithonKits.getInstance().getConfigUtil().saveEnderChest(player.getUniqueId(), String.valueOf(kit), e.getInventory()).thenAccept(aBoolean -> {
                if (aBoolean) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5.0f, 1.0f);
                    MessageUtil.send(player, "messages.enderchest_save");
                    return;
                }
                player.sendRichMessage("<red>An error occurred while saving enderchest");
                AithonKits.getInstance().getComponentLogger().error(TextStyle.style("<red>Could not save <player>'s enderchest <kit>"
                        .replace("<player>", player.getName())
                        .replace("<kit>", String.valueOf(kit))
                ));
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0f, 5.0f);
            });
            Bukkit.getScheduler().runTaskLater(AithonKits.getInstance(), () -> new KitMenu(player).open(player),1);
        });
    }
}
