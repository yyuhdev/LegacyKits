package club.revived.menus;

import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.config.MessageHandler;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.util.ColorUtil;
import club.revived.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitMenu
extends InventoryBuilder {

    private final Player player;

    public KitMenu(Player player) {
        super(54, ColorUtil.of("<player>'s Kits"
                .replace("<player>", player.getName())
        ), true);
        this.player = player;

        for (int x = 10; x < 17; x++) {
            int i = x;
            setItem(x, getSelectedState(x-9), e -> {
                e.setCancelled(true);
                if (e.getClick().isRightClick()) {
                    KitEditor editor = new KitEditor(i - 9, player);
                    editor.open(player);
                    return;
                }
                player.closeInventory();
                Map<Integer, ItemStack> map =KitCache.getKits(player.getUniqueId()).get(i-9).getContent();
                player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
                player.setFoodLevel(20);
                player.getActivePotionEffects().clear();
                player.setSaturation(20);
                player.sendRichMessage(MessageHandler.of("KIT_LOAD").replace("<kit>", String.valueOf(i-9)));
                for (Player global : Bukkit.getOnlinePlayers()) {
                    if (global.getLocation().getNearbyPlayers(250).contains(player)) {
                        global.sendRichMessage(MessageHandler.of("KIT_LOAD_BROADCAST")
                                .replace("<player>", player.getName())
                                .replace("<kit>", String.valueOf(i-9))
                        );
                    }
                }
            });
        }

        for (int x = 19; x < 26; x++) {
            int i = x;
            setItem(x, getSelectedState(x - 9), e -> {
                e.setCancelled(true);
                if (e.getClick().isRightClick()) {
                    KitEditor editor = new KitEditor(i - 9, player);
                    editor.open(player);
                    return;
                }
                player.closeInventory();
                Map<Integer, ItemStack> map = KitCache.getKits(player.getUniqueId()).get(i - 9).getContent();
                player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
                player.setFoodLevel(20);
                player.getActivePotionEffects().clear();
                player.setSaturation(20);
                player.sendRichMessage(MessageHandler.of("KIT_LOAD").replace("<kit>", String.valueOf(i - 9)));
                for (Player global : Bukkit.getOnlinePlayers()) {
                    if (global.getLocation().getNearbyPlayers(250).contains(player)) {
                        global.sendRichMessage(MessageHandler.of("KIT_LOAD_BROADCAST")
                                .replace("<player>", player.getName())
                                .replace("<kit>", String.valueOf(i - 9))
                        );
                    }
                }
            });
        }

        setItem(40, ItemBuilder.item(Material.END_CRYSTAL)
                .name(ColorUtil.of("<#cdd6fa>\uD83E\uDE93 Kit Room"))
                .lore(ColorUtil.of(""),
                        ColorUtil.of("<grey>Get items and gear in the"),
                        ColorUtil.of("<grey>kit room to create your"),
                        ColorUtil.of("<grey>custom kits."),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Click to open")).build(), e -> {
            e.setCancelled(true);
            new Kitroom(player).open(player);
        });
    }

    private ItemStack getSelectedState(int kit){
        if(SettingsCache.getSettings(player.getUniqueId()).getSelectedKit() == kit){
            return ItemBuilder.item(Material.KNOWLEDGE_BOOK).name(ColorUtil.of("<#cdd6fa>\uD83C\uDFF9 Custom Kit <kit>"
                            .replace("<kit>", String.valueOf(kit))
                    ))
                    .lore(ColorUtil.of(""),
                            ColorUtil.of("<grey>Custom kits allow you to"),
                            ColorUtil.of("<grey>create preset kits which you"),
                            ColorUtil.of("<grey>can  claim at any time."),
                            ColorUtil.of(""),
                            ColorUtil.of("<#cdd6fa>Selected: <selected>"
                                    .replace("<selected>", "<green><bold>SELECTED")
                            ),
                            ColorUtil.of(""),
                            ColorUtil.of("<#cdd6fa>Left click to load"),
                            ColorUtil.of("<#cdd6fa>Right click to edit")).build();
        }
        return ItemBuilder.item(Material.BOOK).name(ColorUtil.of("<gray>\uD83C\uDFF9 Custom Kit <kit>"
                        .replace("<kit>", String.valueOf(kit))
                ))
                .lore(ColorUtil.of(""),
                        ColorUtil.of("<grey>Custom kits allow you to"),
                        ColorUtil.of("<grey>create preset kits which you"),
                        ColorUtil.of("<grey>can  claim at any time."),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Selected: <selected>"
                                .replace("<selected>", "<red><bold>NOT SELECTED")
                        ),
                        ColorUtil.of(""),
                        ColorUtil.of("<#cdd6fa>Left click to load"),
                        ColorUtil.of("<#cdd6fa>Right click to edit")).build();
    }
}
