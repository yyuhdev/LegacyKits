package club.revived.menus;

import club.revived.LegacyKits;
import club.revived.cache.KitCache;
import club.revived.config.MessageHandler;
import club.revived.framework.inventory.InventoryBuilder;
import club.revived.objects.Kit;
import club.revived.objects.KitHolder;
import club.revived.objects.KitType;
import club.revived.storage.DatabaseManager;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnderchestEditor
extends InventoryBuilder {

    public EnderchestEditor(Player player, int id) {
        super(36, TextStyle.style("<player>'s Enderchest "
                .replace("<player>", player.getName())
                + id));
        setItems(27, 33, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build(), e -> e.setCancelled(true));
        setItem(35, ItemBuilder.item(Material.CHEST).name(TextStyle.style("<#cdd6fa>Import from Inventory")).build(), e -> {
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

        setItem(33, ItemBuilder.item(Material.RED_DYE).name(TextStyle.style("<red>Clear Editor")).build(), e -> {
            e.setCancelled(true);
            for(int x = 0; x<27; x++){
                setItem(x, null);
            }
        });

        setItem(34, ItemBuilder.item(Material.ENDER_CHEST)
                .name(TextStyle.style("<#cdd6fa>Import from Enderchest")).build(), e -> {
            e.setCancelled(true);

            if (player.getEnderChest().contains(Material.ENCHANTED_GOLDEN_APPLE)) for (int i = 0; i < 27; i++) {
                ItemStack item = player.getEnderChest().getContents()[i];
                if (item == null) continue;

                if (item.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
                    player.getEnderChest().setItem(i, new ItemStack(Material.AIR));
                }
            }

            for (int slot = 0; slot < 27; slot++) {
                setItem(slot, player.getEnderChest().getItem(slot));
            }

            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
        });

        addCloseHandler(e -> {
            Map<Integer, ItemStack> map = new HashMap<>();
            for(int slot = 0; slot<27; slot++){
                map.put(slot, Objects.requireNonNullElseGet(e.getInventory().getItem(slot), () -> new ItemStack(Material.AIR)));
            }
            KitCache.addKit(player.getUniqueId(), new Kit(player.getUniqueId(), id, map, KitType.ENDERCHEST));
            DatabaseManager.getInstance().save(KitHolder.class, new KitHolder(player.getUniqueId(), KitCache.getKits(player.getUniqueId())));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5.0f, 1.0f);
            player.sendRichMessage(MessageHandler.of("ENDERCHEST_SAVE"));
            Bukkit.getScheduler().runTaskLater(LegacyKits.getInstance(), () -> new KitMenu(player).open(player),1);
        });

        for(Kit kit : KitCache.getKits(player.getUniqueId())){
            if(kit.getType() != KitType.ENDERCHEST) continue;
            if(kit.getID() == id){
                Map<Integer, ItemStack> map =  kit.getContent();
                for(int slot = 0; slot<27; slot++){
                    setItem(slot, map.get(slot));
                }
            }
        }
    }
}
