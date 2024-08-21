package club.revived.menus.preview;

import club.revived.LegacyKits;
import club.revived.config.MessageHandler;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.menus.PremadeKits;
import club.revived.storage.premade.PremadeKitData;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EnderchestPreview extends InventoryBuilder {

    private boolean isEditing = false;

    public EnderchestPreview(Player player) {
        super(36, Component.text("<player>'s Kits"
                .replace("<player>", player.getName())), true);

        setItems(27, 35, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));
        PremadeKitData.loadPremadeKit("enderchest").thenAccept(map -> {
            for (int slot = 0; slot < 27; ++slot) {
                setItem(slot, map.get(slot), event -> {
                    if (!isEditing) {
                        event.setCancelled(true);
                    }
                });
            }
        });
        if (player.hasPermission("legacykits.edit.presetkits")) {
            setItem(35, ItemBuilder.item(Material.WRITABLE_BOOK).name(ColorUtil.of("<#ffe3dc>Edit Kit")).build(),
                    event -> {
                        event.setCancelled(true);
                        isEditing = true;
                        setSafety(false);
                        player.sendRichMessage(MessageHandler.of("EDITING_PRESET_KIT"));
                    }
            );
        }
        addCloseHandler(event -> {
            if(isEditing){
                PremadeKitData.savePresetEnderchest("enderchest", event.getInventory()).thenAccept(aBoolean -> {
                    if(aBoolean){
                        player.sendRichMessage(MessageHandler.of("KIT_SAVE"));
                        return;
                    }
                    player.sendRichMessage("<red>An error occurred while trying to save kit");
                });
            }
            Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new PremadeKits(player).open(player),1);
        });
    }
}
