package club.revived.menus.admin;

import club.revived.LegacyKits;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.storage.premade.PremadeKitData;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PresetEditor
        extends InventoryBuilder {

    private final LegacyKits kits;

    public PresetEditor(String toSave, Player player) {
        super(54, ColorUtil.of("Editing "
                + toSave));
        setItems(5,8, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));
        setItems(45,52, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), event -> event.setCancelled(true));

        this.kits = LegacyKits.getInstance();

        setItem(35, ItemBuilder.item(Material.CHEST).name(ColorUtil.of("<#cdd6fa>Import from Inventory")).build(), e -> {
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
        addCloseHandler(e -> PremadeKitData.savePremadeKit(toSave, e.getInventory()).thenAccept(aBoolean -> {
            if (aBoolean) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5.0f, 1.0f);
                player.sendRichMessage("KIT_SAVE");
                return;
            }
            player.sendRichMessage("<red>An error occurred while saving kit");
            this.kits.getComponentLogger().error(ColorUtil.of("<red>Could not save preset kit <kit>"
                    .replace("<kit>", toSave)
            ));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0f, 5.0f);
        }));

        PremadeKitData.loadPremadeKit(toSave).thenAccept(map -> {
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
