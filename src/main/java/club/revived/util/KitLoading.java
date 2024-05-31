package club.revived.util;

import club.revived.WeirdoKits;
import club.revived.config.Files;
import club.revived.config.SoundConfig;
import club.revived.config.TextMessages;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class KitLoading {

    public KitLoading(){
    }

    public FileConfiguration soundConfig() {
        return Files.config(Files.file("sounds.yml"));
    }

    public void ecload(Player player, String name) {
        HashMap<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().loadec(player.getUniqueId(), name);
        player.getEnderChest().clear();
        for (int slot = 0; slot < 27; slot++) {
            player.getEnderChest().setItem(slot, map.get(Integer.valueOf(slot)));
        }
        new MessageUtil().message(player, "messages.enderchest_load");
        for (Player global : Bukkit.getOnlinePlayers()) {
            new MessageUtil().brcmessage(player, global, "broadcast_messages.enderchest_load");
        }
        SoundConfig.playCSound(soundConfig().getString("enderchest_claim.sound"), soundConfig().getInt("enderchest_claim.pitch"),soundConfig().getInt("enderchest_claim.volume"), player);
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
                new MessageUtil().brcmessage(player, global,"broadcast_messages.kit_load");
        }

        new MessageUtil().message(player, "messages.kit_load");
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        SoundConfig.playCSound(soundConfig().getString("kit_claim.sound"), soundConfig().getInt("kit_claim.pitch"),soundConfig().getInt("kit_claim.volume"), player);
        player.setFireTicks(0);
    }
}
