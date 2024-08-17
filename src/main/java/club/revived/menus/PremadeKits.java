package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.menus.preview.EnderchestPreview;
import club.revived.menus.preview.PremadePreview;
import club.revived.storage.premade.PremadeKitData;
import club.revived.util.enums.CloseReason;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PremadeKits extends InventoryBuilder {

    private CloseReason closeReason;

    public PremadeKits(Player player){
        super(45, Component.text("<player>'s  Kits"
                .replace("<player>", player.getName())
        ), true);

        List<Integer> glassPanes = List.of(0, 1, 2, 6, 7, 8, 9, 17, 27, 36, 37, 38, 42, 43, 44, 35);
        for(int x : glassPanes){
            setItem(x, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                    .name("").build(), e -> e.setCancelled(true));
        }

        setItem(22, ItemBuilder.item(Material.ENDER_CHEST).name(TextStyle.style("<#cdd6fa><bold>Preset Enderchest"))
                .lore(TextStyle.style(""),
                 TextStyle.style("<grey>You don't have an <#cdd6fa>Enderchest<grey>?"),
                        TextStyle.style("<grey>Use our <#cdd6fa><underlined>preset Enderchest"),
                        TextStyle.style("<grey>instead of creating your own. "),
                        TextStyle.style(""),
                        TextStyle.style("<#cdd6fa>Left click to load"),
                        TextStyle.style("<#cdd6fa>Right click to preview"))

                .build(), event -> {
                    event.setCancelled(true);
                    if (event.getClick().isRightClick()) {
                        closeReason = CloseReason.ANOTHER_MENU_OPENED;
                        new EnderchestPreview(player).open(player);
                        return;
                    }
                    PremadeKitData.loadPremadeKit("enderchest").thenAccept(map -> {
                        player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
                    });
                }
        );

        setItem(20, ItemBuilder.item(Material.END_CRYSTAL).name(TextStyle.style("<#cdd6fa><bold>Preset Evaluation Kit"))
                .lore(TextStyle.style(""),
                        TextStyle.style("<grey>You don't have an <#cdd6fa>Evaluation Kit<grey>?"),
                        TextStyle.style("<grey>Use our <#cdd6fa><underlined>preset Evaluation Kit"),
                        TextStyle.style("<grey>instead of creating your own. "),
                        TextStyle.style(""),
                        TextStyle.style("<#cdd6fa>Left click to load"),
                        TextStyle.style("<#cdd6fa>Right click to preview"))
                .build(), event -> {
                    event.setCancelled(true);
                    if (event.getClick().isRightClick()) {
                        closeReason = CloseReason.ANOTHER_MENU_OPENED;
                        new PremadePreview(player, "evaluation").open(player);
                        return;
                    }
                    PremadeKitData.loadPremadeKit("evaluation").thenAccept(map -> {
                        player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
                    });
                }
        );

        setItem(24, ItemBuilder.item(Material.CROSSBOW).name(TextStyle.style("<#cdd6fa><bold>Preset Drain Kit"))
                .lore(TextStyle.style(""),
                        TextStyle.style("<grey>You don't have a <#cdd6fa>Drain Kit<grey>?"),
                        TextStyle.style("<grey>Use our <#cdd6fa><underlined>preset Drain Kit"),
                        TextStyle.style("<grey>instead of creating your own. "),
                        TextStyle.style(""),
                        TextStyle.style("<#cdd6fa>Left click to load"),
                        TextStyle.style("<#cdd6fa>Right click to preview"))
                .build(), event -> {
            event.setCancelled(true);
            if (event.getClick().isRightClick()) {
                closeReason = CloseReason.ANOTHER_MENU_OPENED;
                new PremadePreview(player, "drain").open(player);
                return;
            }
            PremadeKitData.loadPremadeKit("drain").thenAccept(map -> {
                player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
            });
        });
        addCloseHandler(e -> Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () ->{
            if(closeReason == CloseReason.ANOTHER_MENU_OPENED) return;
            new KitMenu(player).open(player);
        },1L));
    }
}
