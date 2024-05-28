package club.revived.util;

import club.revived.WeirdoKits;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class KitLoading {

    public void ecload(Player player, String name) {
        HashMap<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().loadec(player.getUniqueId(), name);
        player.getEnderChest().clear();
        for (int slot = 0; slot < 27; slot++) {
            player.getEnderChest().setItem(slot, map.get(Integer.valueOf(slot)));
        }
        player.sendRichMessage("<green>Enderchest has been loaded successfully");
        for(Player global : Bukkit.getOnlinePlayers()) {
            if (!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has loaded an Enderchest.");
        }
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
    }

    public void ecloadothers(Player player, Player target, String name) {
        HashMap<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().loadec(player.getUniqueId(), name);
        target.getEnderChest().clear();
        for (int slot = 0; slot < 27; slot++) {
            target.getEnderChest().setItem(slot, map.get(Integer.valueOf(slot)));
        }
        target.sendRichMessage("<green>Enderchest has been loaded successfully");
        for(Player global : Bukkit.getOnlinePlayers()) {
            if (!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                global.sendRichMessage("<gold><bold>WK <reset><gray>" + target.getName() + " has loaded an Enderchest.");
        }
        target.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
    }


    public void loadothers(Player player, Player target, String name) {
        HashMap<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().load(player.getUniqueId(), name);
        target.getInventory().clear();
        target.getInventory().setArmorContents(null);
        for (int slot = 0; slot < 36; slot++)
            target.getInventory().setItem(slot, map.get(Integer.valueOf(slot)));
        for (ItemStack itemStack : Arrays.<ItemStack>asList(new ItemStack[]{map.get(Integer.valueOf(36)), map.get(Integer.valueOf(37)), map.get(Integer.valueOf(38)), map.get(Integer.valueOf(39)), map.get(Integer.valueOf(40))})) {
            if (itemStack != null) {
                if (itemStack.getType().name().endsWith("_HELMET")) {
                    target.getInventory().setHelmet(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_CHESTPLATE")) {
                    target.getInventory().setChestplate(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_LEGGINGS")) {
                    target.getInventory().setLeggings(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_BOOTS")) {
                    target.getInventory().setBoots(itemStack);
                    continue;
                }
                target.getInventory().setItemInOffHand(itemStack);
            }
        }
        for (Player global : Bukkit.getOnlinePlayers()) {
            if (!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                global.sendRichMessage("<gold><bold>WK <reset><gray>" + target.getName() + " has loaded a kit.");
        }
    }

    public void load(Player player, String name) {
        HashMap<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().load(player.getUniqueId(), name);
        WeirdoKits.getInstance().LastUsedKit.put(player.getUniqueId(), Integer.valueOf(name));
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        WeirdoKits.getInstance().LastUsedKit.put(player.getUniqueId(), Integer.valueOf(name));
        for (int slot = 0; slot < 36; slot++)
            player.getInventory().setItem(slot, map.get(Integer.valueOf(slot)));
        for (ItemStack itemStack : Arrays.<ItemStack>asList(new ItemStack[] { map.get(Integer.valueOf(36)), map.get(Integer.valueOf(37)), map.get(Integer.valueOf(38)), map.get(Integer.valueOf(39)), map.get(Integer.valueOf(40)) })) {
            if (itemStack != null) {
                if (itemStack.getType().name().endsWith("_HELMET")) {
                    player.getInventory().setHelmet(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_CHESTPLATE")) {
                    player.getInventory().setChestplate(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_LEGGINGS")) {
                    player.getInventory().setLeggings(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_BOOTS")) {
                    player.getInventory().setBoots(itemStack);
                    continue;
                }
                player.getInventory().setItemInOffHand(itemStack);
            }
        }
        for(Player global : Bukkit.getOnlinePlayers()) {
            if(!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has loaded a kit.");
        }

        player.sendRichMessage("<green>Kit has been loaded successfully");
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
        player.setFireTicks(0);
    }
}
