package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.config.MessageHandler;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.storage.kit.KitData;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitEditor
        extends InventoryBuilder {

    public KitEditor(int kit, Player player) {
        super(54, TextStyle.style("<player>'s Kit "
                .replace("<player>", player.getName())
                + kit));
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
            KitData.saveAsync(player.getUniqueId().toString(), kit, e.getInventory());
        });

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
            KitData.saveAsync(player.getUniqueId().toString(), kit, e.getInventory());
            Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new KitMenu(player).open(player),1);
        });

        if(KitData.cachedContent(player.getUniqueId(), kit) != null){
            Map<Integer, ItemStack> map = KitData.cachedContent(player.getUniqueId(), kit);
            for (int slot = 36; slot < 41; ++slot) {
                setItem(slot-36, map.get(slot));
            }
            for (int slot = 9; slot < 36; ++slot) {
                setItem(slot, map.get(slot));
            }
            for(int slot = 0; slot<9; slot++){
                setItem(slot+36, map.get(slot));
            }
            return;
        }

        KitData.contentsAsync(player, kit, map -> {
            for (int slot = 36; slot < 41; ++slot) {
                setItem(slot-36, map.get(slot));
            }
            for (int slot = 9; slot < 36; ++slot) {
                setItem(slot, map.get(slot));
            }
            for(int slot = 0; slot<9; slot++){
                setItem(slot+36, map.get(slot));
            }
        });
    }
}
