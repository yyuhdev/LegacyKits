package club.revived.runables;

import club.revived.config.Files;
import club.revived.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Broadcast {

    private final Plugin plugin;

    @NotNull
    public FileConfiguration messageConfig() {
        return Files.config(Files.file("messages.yml"));
    }

    public Broadcast(Plugin plugin) {
        this.plugin = plugin;
        if (messageConfig().getBoolean("auto_broadcast.enabled")) {
            int timer = messageConfig().getInt("auto_broadcast.timer");
            Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                for (Player global : Bukkit.getOnlinePlayers()) {
                    new MessageUtil().message(global, "auto_broadcast");
                }
            }, 0, timer);

        }
    }
}
