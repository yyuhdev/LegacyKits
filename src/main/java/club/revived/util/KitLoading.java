package club.revived.util;

import club.revived.WeirdoKits;
import club.revived.config.Files;
import club.revived.config.SoundConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class KitLoading {
    @NotNull
    public FileConfiguration soundConfig() {
        return Files.config(Files.create(Files.file("sounds.yml")));
    }

    public void loadEnderChest(Player player, String name) {
        Map<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().loadEnderChest(player.getUniqueId(), name);
        player.getEnderChest().clear();

        for (int slot = 0; slot < 27; slot++) {
            player.getEnderChest().setItem(slot, map.get(slot));
        }

        MessageUtil.send(player, "messages.enderchest_load");

        for (Player global : Bukkit.getOnlinePlayers()) {
            MessageUtil.broadcast(player, global, "broadcast_messages.enderchest_load");
        }

        SoundConfig.play(
            soundConfig().getString("enderchest_claim.sound"),
            soundConfig().getInt("enderchest_claim.pitch"),
            soundConfig().getInt("enderchest_claim.volume"),
            player
        );
    }

    public void load(Player player, String name) {
        Map<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().load(player.getUniqueId(), name);
        WeirdoKits.getInstance().lastUsedKits().put(player.getUniqueId(), Integer.valueOf(name));

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        WeirdoKits.getInstance().lastUsedKits().put(player.getUniqueId(), Integer.valueOf(name));

        for (int slot = 0; slot < 41; slot++) {
            player.getInventory().setItem(slot, map.get(slot));
        }

        for (Player global : Bukkit.getOnlinePlayers()) {
            MessageUtil.broadcast(player, global, "broadcast_messages.kit_load");
        }

        MessageUtil.send(player, "messages.kit_load");

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);

        SoundConfig.play(
            soundConfig().getString("kit_claim.sound"),
            soundConfig().getInt("kit_claim.pitch"),
            soundConfig().getInt("kit_claim.volume"),
            player
        );

        player.setFireTicks(0);
    }
}
