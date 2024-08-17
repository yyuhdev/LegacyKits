package club.revived.menus.preview;

import club.revived.LegacyKits;
import club.revived.config.MessageHandler;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.menus.PremadeKits;
import club.revived.storage.premade.PremadeKitData;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PremadePreview extends InventoryBuilder {

    private boolean isEditing = false;

    public PremadePreview(Player player, String kit){
        super(54, Component.text("<player>'s Kits"
                .replace("<player>", player.getName())
        ), true);
        setItems(5,8, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));
        setItems(45,53, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));
        if (player.hasPermission("legacykits.edit.presetkits")) {
            setItem(53, ItemBuilder.item(Material.WRITABLE_BOOK).name(TextStyle.style("<#ffe3dc>Edit Kit")).build(),
                    event -> {
                        event.setCancelled(true);
                        isEditing = true;
                        setSafety(false);
                        player.sendRichMessage(MessageHandler.of("EDITING_PRESET_KIT"));
                        setItem(52, ItemBuilder.item(Material.CHEST).name(TextStyle.style("<#ffe3dc>Import from Inventory")).build(), e -> {
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
                    }
            );
        }
        addCloseHandler(event -> {
            if(isEditing){
                PremadeKitData.savePremadeKit(kit, event.getInventory()).thenAccept(aBoolean -> {
                    if(aBoolean){
                        player.sendRichMessage(MessageHandler.of("KIT_SAVE"));
                        return;
                    }
                    player.sendRichMessage("<red>An error occurred while trying to save kit");
                });
            }
            Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new PremadeKits(player).open(player),1);
        });
        PremadeKitData.loadPremadeKit(kit).thenAccept(map -> {
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
