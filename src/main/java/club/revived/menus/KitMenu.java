package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.config.MessageHandler;
import club.revived.framework.head.HeadBuilder;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.storage.kit.EnderchestData;
import club.revived.storage.kit.KitData;
import club.revived.storage.premade.PremadeKitData;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class KitMenu
extends InventoryBuilder {

    public Inventory currentPreview;
    List<Integer> pinkGlass = List.of(28,29,30,31,32,33,34,43);
    List<Integer> purpleGlass = List.of(0,1,2,3,4,5,6,7,8,9,10,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53, 40);

    public KitMenu(Player player) {
        super(54, TextStyle.style("<player>'s Kits"
                .replace("<player>", player.getName())
        ));
        setItem(38, ItemBuilder.item(Material.CHERRY_SIGN).name(TextStyle.style("<#cdd6fa>☃ Information"))
                        .lore(TextStyle.style("<gray>Click a kit slot to claim your kit"),
                                TextStyle.style("<gray>Right click to kit slot to edit the kit"),
                                TextStyle.style("<gray>Get items in the kit room")
                        )
                .build(), e ->
                e.setCancelled(true));

        setItem(39, ItemBuilder.item(Material.NETHERITE_CHESTPLATE)
                .addFlag(ItemFlag.HIDE_ATTRIBUTES)
                .addFlag(ItemFlag.HIDE_ENCHANTS)
                .addEnchantment(Enchantment.ARROW_DAMAGE, 1)
                .name(TextStyle.style("<#cdd6fa>⛄ Preset Kit"))
                .lore(TextStyle.style("<gray>You can claim other kits"), TextStyle.style("<gray>using /claim <kit>"))
                .build(), e -> {
            e.setCancelled(true);
            PremadeKitData.loadPremadeKit("evaluation").thenAccept(map -> {
                for (int slot = 0; slot < 41; slot++) {
                    player.getInventory().setItem(slot, map.get(slot));
                }
                player.getInventory().setHelmet(map.get(36));
                player.getInventory().setChestplate(map.get(37));
                player.getInventory().setLeggings(map.get(38));
                player.getInventory().setBoots(map.get(39));
                player.playSound(player, Sound.ITEM_ARMOR_EQUIP_NETHERITE,1,1);
            });
        });

        for (int x : pinkGlass) {
            setItem(x, ItemBuilder.item(Material.MAGENTA_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));
        }
        for (int x : purpleGlass) {
            setItem(x, ItemBuilder.item(Material.PURPLE_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));
        }

        for (int x = 10; x < 17; x++) {
            int i = x;
            setItem(x, ItemBuilder.item(Material.CHEST).name(TextStyle.style("<#cdd6fa>\uD83C\uDFF9 Kit " + (x - 9)))
                    .lore(TextStyle.style("<gray>Left click to load"),
                            TextStyle.style("<gray>Right click to edit")).build(), e -> {
                e.setCancelled(true);
                if (e.getClick().isRightClick()) {
                    KitEditor editor = new KitEditor(i - 9, player);
                    editor.open(player);
                    return;
                }
                KitData.load(player, (i-9));
            });
        }

        for (int x = 19; x < 26; x++) {
            int i = x;
            setItem(i, ItemBuilder.item(Material.ENDER_CHEST)
                    .name(TextStyle.style("<#cdd6fa>\uD83D\uDDE1 Enderchest " + (x - 18)))
                    .lore(TextStyle.style("<gray>Left click to load"),
                            TextStyle.style("<gray>Right click to edit")).build(), e -> {
                e.setCancelled(true);
                if (e.getClick().isRightClick()) {
                    new EnderchestEditor(player, i - 18).open(player);
                    return;
                }
                EnderchestData.load(player, (i - 18));
            });
        }

        setItem(37, ItemBuilder.item(Material.END_CRYSTAL)
                .name(TextStyle.style("<#cdd6fa>\uD83E\uDE93 Kit Room"))
                .lore(TextStyle.style("<gray>Get Items for"),
                        TextStyle.style ("<gray>your kits here")).build(), e -> {
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
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP,1,1);

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


    public class menuListener implements Listener {
        @EventHandler
        public void onClick(InventoryClickEvent event){
            if(event.getInventory() == currentPreview){
                event.setCancelled(true);
            }
        }
        @EventHandler
        public void onClose(InventoryCloseEvent event){
            if(event.getInventory() == currentPreview){
                Player player = (Player) event.getPlayer();
                Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> open(player),1L);
            }
        }
    }
}
