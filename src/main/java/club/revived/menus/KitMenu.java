package club.revived.menus;

import club.revived.AithonKits;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.util.ConfigUtil;
import club.revived.util.MessageUtil;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        setItem(38, ItemBuilder.item(Material.CHERRY_SIGN).name(TextStyle.style("<#cdd6fa><bold>INFORMATION"))
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
            AithonKits.getInstance().getConfigUtil().loadPremadeKit("evaluation").thenAccept(map -> {
                for (int slot = 0; slot < 41; slot++) {
                    player.getInventory().setItem(slot, map.get(slot));
                }
                player.getInventory().setHelmet(map.get(36));
                player.getInventory().setChestplate(map.get(37));
                player.getInventory().setLeggings(map.get(38));
                player.getInventory().setBoots(map.get(39));
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
                    .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Left click to load"),
                            MiniMessage.miniMessage().deserialize("<gray><i>● Right click to edit")).build(), e -> {
                e.setCancelled(true);
                if (e.getClick().isRightClick()) {

                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(player))
                            MessageUtil.broadcast(player, global, "broadcast_messages.kit_editor_open");
                    }

                    KitEditor editor = new KitEditor(i - 9, player);
                    editor.open(player);
                    return;
                }
                AithonKits.getInstance().getLoading().load(player, String.valueOf(i - 9));

            });
        }

        for (int x = 19; x < 26; x++) {
            int i = x;
            setItem(i, ItemBuilder.item(Material.ENDER_CHEST)
                    .name(TextStyle.style("<#cdd6fa>\uD83D\uDDE1 Enderchest " + (x - 18)))
                    .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Left click to load"),
                            MiniMessage.miniMessage().deserialize("<gray><i>● Right click to edit")).build(), e -> {
                e.setCancelled(true);
                if (e.getClick().isRightClick()) {
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(player))
                            MessageUtil.broadcast(player, global, "broadcast_messages.enderchest_editor_open");
                    }
                    new EnderchestEditor(player, i - 18).open(player);
                    return;
                }
                AithonKits.getInstance().getLoading().loadEnderChest(player, String.valueOf(i - 18));
            });
        }

        setItem(37, ItemBuilder.item(Material.END_CRYSTAL)
                .name(TextStyle.style("<#cdd6fa>\uD83E\uDE93 Kit Room"))
                .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Get Items for"),
                        MiniMessage.miniMessage().deserialize("<gray><i>your kits here")).build(), e -> {
            e.setCancelled(true);
            new Kitroom(player).open(player);
            for (Player global : Bukkit.getOnlinePlayers()) {
                if (global.getLocation().getNearbyPlayers(250).contains(player))
                    MessageUtil.broadcast(player, global, "broadcast_messages.kit_room_open");
            }
        });

        setItem(41, ItemBuilder.item(Material.EXPERIENCE_BOTTLE)
                .name(TextStyle.style("<#cdd6fa>\uD83E\uDDEA Repair Items"))
                .build(), e -> {
            e.setCancelled(true);
            for (ItemStack stack : player.getInventory().getContents()) {
                if (stack == null) continue;
                stack.setDurability((short) 0);
            }

        });

        setItem(42, ItemBuilder.item(Material.RED_DYE)
                .name(TextStyle.style("<red>Clear Inventory")).build(), e -> {
            e.setCancelled(true);
            player.getInventory().clear();
        });
    }

    public Inventory previewEnderchestMenu(Component name) {
        Inventory inventory = Bukkit.createInventory(null, 36, name);
        for (int x = 27; x < 36; x++) {
            inventory.setItem(x, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        }
        return inventory;
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
                Bukkit.getScheduler().runTaskLater(AithonKits.getInstance(), () -> open(player),1L);
            }
        }
    }
}
