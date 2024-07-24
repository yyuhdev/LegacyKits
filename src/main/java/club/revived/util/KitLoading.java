package club.revived.util;

import club.revived.AithonKits;
import club.revived.config.Files;
import club.revived.config.SoundConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KitLoading {
    @NotNull
    public FileConfiguration soundConfig() {
        return Files.config(Files.create(Files.file("sounds.yml")));
    }

    public void loadEnderChest(Player player, String name) {
        AithonKits.getInstance().getConfigUtil().loadEnderChest(player.getUniqueId(),name).thenAccept(map -> {
            for (int slot = 0; slot < 27; ++slot) {
                player.getEnderChest().setItem(slot, map.get(slot));
            }
        });
        MessageUtil.send(player, "messages.enderchest_load");
        for (Player global : Bukkit.getOnlinePlayers()) {
            if(global.getLocation().getNearbyPlayers(250).contains(player))
                MessageUtil.broadcast(player, global, "broadcast_messages.enderchest_load");
        }
        if(soundConfig().getBoolean("enderchest_claim.enabled")) {
            SoundConfig.play(
                    soundConfig().getString("enderchest_claim.sound"),
                    soundConfig().getInt("enderchest_claim.pitch"),
                    soundConfig().getInt("enderchest_claim.volume"),
                    player
            );
        }
    }

    public void load(Player player, String name) {
        AithonKits.getInstance().getConfigUtil().loadEnderChest(player.getUniqueId(),name).thenAccept(map -> {
            for (int slot = 0; slot < 27; ++slot) {
                player.getEnderChest().setItem(slot, map.get(slot));
            }
        });
        AithonKits.getInstance().getConfigUtil().load(player.getUniqueId(), name).thenAccept(map -> {
            for (int slot = 0; slot < 41; slot++) {
                player.getInventory().setItem(slot, map.get(slot));
            }
            player.getInventory().setHelmet(map.get(36));
            player.getInventory().setChestplate(map.get(37));
            player.getInventory().setLeggings(map.get(38));
            player.getInventory().setBoots(map.get(39));
        });
        AithonKits.getInstance().lastUsedKits.put(player.getUniqueId(), Integer.valueOf(name));
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        AithonKits.getInstance().lastUsedKits.put(player.getUniqueId(), Integer.valueOf(name));
        for (Player global : Bukkit.getOnlinePlayers()) {
            if(global.getLocation().getNearbyPlayers(250).contains(player))
                MessageUtil.broadcast(player, global, "broadcast_messages.kit_load");
        }

        MessageUtil.send(player, "messages.kit_load");

        player.setHealth(20);
        player.setFoodLevel(20);
        player.getActivePotionEffects().clear();
        player.setSaturation(20);

        if(soundConfig().getBoolean("kit_claim.enabled")) {
            SoundConfig.play(
                    soundConfig().getString("kit_claim.sound"),
                    soundConfig().getInt("kit_claim.pitch"),
                    soundConfig().getInt("kit_claim.volume"),
                    player
            );
        }

        player.setFireTicks(0);
    }

//    public void loadothers(Player requester, Player toKit, String name) {
//        Map<Integer, ItemStack> map = AithonKits.getInstance().getConfigUtil().load(toKit.getUniqueId(), name);
//        AithonKits.getInstance().lastUsedKits().put(requester.getUniqueId(), Integer.valueOf(name));
//        toKit.getInventory().clear();
//        toKit.getInventory().setArmorContents(null);
//
//        for (int slot = 0; slot < 41; slot++) {
//            toKit.getInventory().setItem(slot, map.get(slot));
//        }
//
//        toKit.getInventory().setHelmet(map.get(36));
//        toKit.getInventory().setChestplate(map.get(37));
//        toKit.getInventory().setLeggings(map.get(38));
//        toKit.getInventory().setBoots(map.get(39));
//
//        for (Player global : Bukkit.getOnlinePlayers()) {
//            MessageUtil.broadcast(toKit, global, "broadcast_messages.kit_load");
//        }
//
//        MessageUtil.send(toKit, "messages.kit_load");
//
//        toKit.setHealth(20);
//        toKit.setFoodLevel(20);
//        toKit.setSaturation(20);
//
//        if(soundConfig().getBoolean("kit_claim.enabled")) {
//            SoundConfig.play(
//                    soundConfig().getString("kit_claim.sound"),
//                    soundConfig().getInt("kit_claim.pitch"),
//                    soundConfig().getInt("kit_claim.volume"),
//                    toKit
//            );
//        }
//
//        toKit.setFireTicks(0);
//    }
}
