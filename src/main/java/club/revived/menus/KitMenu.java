package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.config.MessageHandler;
import club.revived.framework.inventory.InventoryBuilder;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitMenu
extends InventoryBuilder {

    private final Player player;

    public KitMenu(Player player) {
        super(54, TextStyle.style("<player>'s Kits"
                .replace("<player>", player.getName())
        ));
        this.player = player;

        setItem(39, ItemBuilder.item(Material.NETHERITE_CHESTPLATE)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addEnchantment(Enchantment.BANE_OF_ARTHROPODS, 1)
                .name(TextStyle.style("<#cdd6fa>⛄ Preset Kits"))
                .lore(TextStyle.style(""),
                        TextStyle.style("<grey>Too lazy to create your"),
                        TextStyle.style("<grey>own kits? No problem!"),
                        TextStyle.style("<grey>Just claim one here."),
                        TextStyle.style(""),
                        TextStyle.style("<#cdd6fa>Click to open")).build(), e -> {
            e.setCancelled(true);
            new PremadeKits(player).open(player);
        });

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
                LegacyKits.getInstance().lastUsedKits.put(player.getUniqueId(), i-9);
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
                LegacyKits.getInstance().lastUsedKits.put(player.getUniqueId(), i - 9);
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

//        for (int x = 19; x < 26; x++) {
//            int i = x;
//            setItem(i, ItemBuilder.item(Material.ENDER_CHEST)
//                    .name(TextStyle.style("<#cdd6fa>\uD83D\uDDE1 Custom Enderchest " + (x - 18)))
//                    .lore(TextStyle.style(""),
//                            TextStyle.style("<grey>Custom enderchests allow you to"),
//                            TextStyle.style("<grey>create preset enderchest which you"),
//                            TextStyle.style("<grey>can claim at any time."),
//                            TextStyle.style(""),
//                            TextStyle.style("<#cdd6fa>Left click to load"),
//                            TextStyle.style("<#cdd6fa>Right click to edit")).build(), e -> {
//                e.setCancelled(true);
//                if (e.getClick().isRightClick()) {
//                    new EnderchestEditor(player, i - 18).open(player);
//                    return;
//                }
//                for(Kit kit : KitCache.getKits(player.getUniqueId())){
//                    if(kit.getType() != KitType.ENDERCHEST) continue;
//                    if(kit.getID() == i-9){
//                        Map<Integer, ItemStack> map =  kit.getContent();
//                        player.getEnderChest().setContents(map.values().toArray(new ItemStack[0]));
//                    }
//                }
//            });
//        }

        setItem(41, ItemBuilder.item(Material.END_CRYSTAL)
                .name(TextStyle.style("<#cdd6fa>\uD83E\uDE93 Kit Room"))
                .lore(TextStyle.style(""),
                        TextStyle.style("<grey>Get items and gear in the"),
                        TextStyle.style("<grey>kit room to create your"),
                        TextStyle.style("<grey>custom kits."),
                        TextStyle.style(""),
                        TextStyle.style("<#cdd6fa>Click to open")).build(), e -> {
            e.setCancelled(true);
            new Kitroom(player).open(player);
        });
    }

    private ItemStack getSelectedState(int kit){
        String name = String.valueOf(kit);
        try {
            name = KitCache.getKits(player.getUniqueId()).get(kit).getName();
        } catch (Exception e){
            //
        }
        if(SettingsCache.getSettings(player.getUniqueId()).getSelectedKit() == kit){
            return ItemBuilder.item(Material.KNOWLEDGE_BOOK).name(TextStyle.style("<#cdd6fa>\uD83C\uDFF9 Custom Kit <kit>"
                            .replace("<kit>", String.valueOf(kit))
                    ))
                    .lore(TextStyle.style(""),
                            TextStyle.style("<grey>Custom kits allow you to"),
                            TextStyle.style("<grey>create preset kits which you"),
                            TextStyle.style("<grey>can  claim at any time."),
                            TextStyle.style(""),
                            TextStyle.style("<#cdd6fa>Selected: <selected>"
                                    .replace("<selected>", "<green><bold>SELECTED")
                            ),
                            TextStyle.style(""),
                            TextStyle.style("<#cdd6fa>Left click to load"),
                            TextStyle.style("<#cdd6fa>Right click to edit")).build();
        }
        return ItemBuilder.item(Material.BOOK).name(TextStyle.style("<gray>\uD83C\uDFF9 Custom Kit <kit>"
                        .replace("<kit>", String.valueOf(kit))
                ))
                .lore(TextStyle.style(""),
                        TextStyle.style("<grey>Custom kits allow you to"),
                        TextStyle.style("<grey>create preset kits which you"),
                        TextStyle.style("<grey>can  claim at any time."),
                        TextStyle.style(""),
                        TextStyle.style("<#cdd6fa>Selected: <selected>"
                                .replace("<selected>", "<red><bold>NOT SELECTED")
                        ),
                        TextStyle.style(""),
                        TextStyle.style("<#cdd6fa>Left click to load"),
                        TextStyle.style("<#cdd6fa>Right click to edit")).build();
    }
}
