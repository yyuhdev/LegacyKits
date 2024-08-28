package club.revived.menus;

import club.revived.cache.EnderchestCache;
import club.revived.config.MessageHandler;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.objects.enderchest.Enderchest;
import club.revived.objects.enderchest.EnderchestHolder;
import club.revived.storage.DatabaseManager;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnderchestEditor
extends InventoryBuilder {

    public EnderchestEditor(Player player, int id) {
        super(36, ColorUtil.of("<player>'s Enderchest "
                .replace("<player>", player.getName())
                + id));
        Map<Integer, ItemStack> items = EnderchestCache.getKits(player.getUniqueId()).get(id).getContent();
        for(int slot = 0; slot<27; slot++){
            setItem(slot, items.getOrDefault(slot, new ItemStack(Material.AIR)));
        }

        setItems(27, 35, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));

        setItem(35, ItemBuilder.item(Material.ENDER_CHEST)
                .name(ColorUtil.of("<#cdd6fa>Import"))
                        .lore(
                                ColorUtil.of(""),
                                ColorUtil.of("<grey>Import the items in your"),
                                ColorUtil.of("<grey>enderchest into the kit"),
                                ColorUtil.of(""),
                                ColorUtil.of("<#cdd6fa>Click to import"),
                                ColorUtil.of("")
                ).build(), e -> {
            e.setCancelled(true);
            for (int slot = 0; slot < 27; slot++) {
                setItem(slot, player.getEnderChest().getItem(slot));
            }

            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
        });

        addCloseHandler(e -> {
            Map<Integer, ItemStack> map = new HashMap<>();
            for(int slot = 0; slot<27; slot++){
                map.put(slot, Objects.requireNonNullElseGet(e.getInventory().getItem(slot), () -> new ItemStack(Material.AIR)));
            }
            EnderchestCache.addKit(player.getUniqueId(), new Enderchest(player.getUniqueId(), id, "test", map));
            DatabaseManager.getInstance().save(EnderchestHolder.class, new EnderchestHolder(player.getUniqueId(), EnderchestCache.getKits(player.getUniqueId())));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5.0f, 1.0f);
            player.sendRichMessage(MessageHandler.of("ENDERCHEST_SAVE"));
        });
    }
}
