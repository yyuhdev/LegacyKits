package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.config.MessageHandler;
import club.revived.framework.head.HeadBuilder;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.objects.Kit;
import club.revived.objects.KitType;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;

public class KitMenu
extends InventoryBuilder {

    private final Player player;

    List<Integer> pinkGlass = List.of(28,29,30,31,32,33,34,43);
    List<Integer> purpleGlass = List.of(0,1,2,3,4,5,6,7,8,9,10,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53, 40);

    public KitMenu(Player player) {
        super(54, TextStyle.style("<player>'s Kits"
                .replace("<player>", player.getName())
        ));
        this.player = player;
        setItem(38, ItemBuilder.item(Material.CHERRY_SIGN).name(TextStyle.style("<#cdd6fa>☃ Information"))
                .lore(TextStyle.style(""),
                        TextStyle.style("<grey>Use <#cdd6fa><underlined>Right click<reset><grey> to edit kits"),
                        TextStyle.style("<grey>Use <#cdd6fa><underlined>Left click<reset><grey> to claim kits"),
                        TextStyle.style(""))
                .build(), e ->
                e.setCancelled(true));

        setItem(39, ItemBuilder.item(Material.NETHERITE_CHESTPLATE)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
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

        for (int x : pinkGlass) {
            setItem(x, ItemBuilder.item(Material.MAGENTA_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));
        }
        for (int x : purpleGlass) {
            setItem(x, ItemBuilder.item(Material.PURPLE_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));
        }

        for (int x = 10; x < 17; x++) {
            int i = x;
            setItem(x, ItemBuilder.item(Material.CHEST).name(TextStyle.style("<#cdd6fa>\uD83C\uDFF9 Custom Kit " + (x - 9)))
                    .lore(TextStyle.style(""),
                            TextStyle.style("<grey>Custom kits allow you to"),
                            TextStyle.style("<grey>create preset kits which you"),
                            TextStyle.style("<grey>can  claim at any time."),
                            TextStyle.style(""),
                            TextStyle.style("<#cdd6fa>Selected: <selected>"
                                    .replace("<selected>", getSelectedState(x-9))
                            ),
                            TextStyle.style(""),
                            TextStyle.style("<#cdd6fa>Left click to load"),
                            TextStyle.style("<#cdd6fa>Right click to edit")).build(), e -> {
                e.setCancelled(true);
                if (e.getClick().isRightClick()) {
                    KitEditor editor = new KitEditor(i - 9, player);
                    editor.open(player);
                    return;
                }
                player.closeInventory();
                for(Kit kit : KitCache.getKits(player.getUniqueId())){
                    if(kit.getType() != KitType.INVENTORY) continue;
                    if(kit.getID() == i-9){
                        player.getInventory().setContents(kit.getContent().values().toArray(new ItemStack[0]));
                    }
                }
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
            setItem(i, ItemBuilder.item(Material.ENDER_CHEST)
                    .name(TextStyle.style("<#cdd6fa>\uD83D\uDDE1 Custom Enderchest " + (x - 18)))
                    .lore(TextStyle.style(""),
                            TextStyle.style("<grey>Custom enderchests allow you to"),
                            TextStyle.style("<grey>create preset enderchest which you"),
                            TextStyle.style("<grey>can claim at any time."),
                            TextStyle.style(""),
                            TextStyle.style("<#cdd6fa>Left click to load"),
                            TextStyle.style("<#cdd6fa>Right click to edit")).build(), e -> {
                e.setCancelled(true);
                if (e.getClick().isRightClick()) {
                    new EnderchestEditor(player, i - 18).open(player);
                    return;
                }
                for(Kit kit : KitCache.getKits(player.getUniqueId())){
                    if(kit.getType() != KitType.ENDERCHEST) continue;
                    if(kit.getID() == i-9){
                        Map<Integer, ItemStack> map =  kit.getContent();
                        player.getEnderChest().setContents(map.values().toArray(new ItemStack[0]));
                    }
                }
            });
        }

        setItem(37, ItemBuilder.item(Material.END_CRYSTAL)
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

        setItem(41, ItemBuilder.item(Material.EXPERIENCE_BOTTLE)
                .name(TextStyle.style("<#cdd6fa>\uD83E\uDDEA Repair Items"))
                .build(), e -> {
            e.setCancelled(true);
            for (ItemStack stack : player.getInventory().getContents()) {
                if (stack == null) continue;
                stack.setDurability((short) 0);
            }
            player.sendRichMessage(MessageHandler.of("ITEMS_REPAIRED"));
        });

        setItem(42, ItemBuilder.item(Material.RED_DYE)
                .name(TextStyle.style("<red>✂ Clear Inventory")).build(), e -> {
            e.setCancelled(true);
            player.getInventory().clear();
        });

        setItem(43, ItemBuilder.item(HeadBuilder.byUrl("https://textures.minecraft.net/texture/879e54cbe87867d14b2fbdf3f1870894352048dfecd962846dea893b2154c85"))
                .name(TextStyle.style("<#cdd6fa>☆ Public Kits"))
                .lore(TextStyle.style("<gray>COMING SOON"))
                .build(), e -> e.setCancelled(true)
        );
    }

    private String getSelectedState(int kit){
        if(SettingsCache.getSettings(player.getUniqueId()).getSelectedKit() == kit){
            return "<green><bold>SELECTED";
        }
        return "<red><bold>NOT SELECTED";
    }
}
