package club.revived.runables;

import club.revived.config.Files;
import club.revived.util.MessageUtil;
import dev.manere.utils.scheduler.Schedulers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Broadcast {
    @NotNull
    public static FileConfiguration messageConfig() {
        return Files.config(Files.create(Files.file("messages.yml")));
    }

    public static void startTask() {
        if (!messageConfig().getBoolean("auto_broadcast.enabled")) return;
        int timer = messageConfig().getInt("auto_broadcast.timer");

        Schedulers.sync().execute(() -> {
            for (Player global : Bukkit.getOnlinePlayers()) {
                MessageUtil.send(global, "auto_broadcast");
            }
        }, 0, timer);
    }
}
