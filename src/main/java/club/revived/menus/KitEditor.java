package club.revived.menus;

import club.revived.AithonKits;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.util.ConfigUtil;
import club.revived.util.MessageUtil;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class KitEditor
        extends InventoryBuilder {

    private final AithonKits kits;
    private final ConfigUtil configUtil;

    public KitEditor(int kit, Player player) {
        super(54, TextStyle.style("<player>'s Kit "
                .replace("<player>", player.getName())
                + kit));
        setItems(5,8, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));
        setItems(45,50, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));

        this.kits = AithonKits.getInstance();
        this.configUtil = AithonKits.getInstance().getConfigUtil();

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
            configUtil.save(player.getUniqueId(), String.valueOf(kit), e.getInventory()).thenAccept(aBoolean -> {
                if (aBoolean) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5.0f, 1.0f);
                    MessageUtil.send(player, "messages.kit_save");
                    return;
                }
                player.sendRichMessage("<red>An error occurred while saving kit");
                this.kits.getComponentLogger().error(TextStyle.style("<red>Could not save <player>'s kit <kit>"
                        .replace("<player>", player.getName())
                        .replace("<kit>", String.valueOf(kit))
                ));
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0f, 5.0f);
            });
        });

        setItem(53, ItemBuilder.item(Material.CHEST).name(TextStyle.style("<#ffe3dc>Import from Inventory")).build(), e -> {
            e.setCancelled(true);
            for (int slot = 9; slot < 36; ++slot) {
                setItem(slot, player.getInventory().getItem(slot));
            }
            for(int slot = 0; slot<9; slot++){
                setItem(slot+36, player.getInventory().getItem(slot));
            }
            setItem(0, player.getInventory().getHelmet());
            setItem(1, player.getInventory().getChestplate());
            setItem(2, player.getInventory().getLeggings());
            setItem(3, player.getInventory().getBoots());
            setItem(4, player.getInventory().getItemInOffHand());
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
        });
        addCloseHandler(e -> {
            configUtil.save(player.getUniqueId(), String.valueOf(kit), e.getInventory()).thenAccept(aBoolean -> {
                if (aBoolean) {
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5.0f, 1.0f);
                    MessageUtil.send(player, "messages.kit_save");
                    return;
                }
                player.sendRichMessage("<red>An error occurred while saving kit");
                this.kits.getComponentLogger().error(TextStyle.style("<red>Could not save <player>'s kit <kit>"
                        .replace("<player>", player.getName())
                        .replace("<kit>", String.valueOf(kit))
                ));
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0f, 5.0f);
            });
            Bukkit.getScheduler().runTaskLater(AithonKits.getInstance(), () -> new KitMenu(player).open(player),1);
        });

        AithonKits.getInstance().getConfigUtil().load(player.getUniqueId(), String.valueOf(kit)).thenAccept(map -> {
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
 