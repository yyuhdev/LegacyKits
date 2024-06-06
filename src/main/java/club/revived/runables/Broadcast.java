package club.revived.runables;

import club.revived.AithonKits;
import club.revived.config.Files;
import club.revived.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class Broadcast {

    public Boolean stopTask = false;

    @NotNull
    public static FileConfiguration messageConfig() {
        return Files.config(Files.create(Files.file("messages.yml")));
    }

    public void startTask() {
        if (!messageConfig().getBoolean("auto_broadcast.enabled")) return;
        int timer = messageConfig().getInt("auto_broadcast.timer");


        BukkitTask task = new BukkitRunnable(){

            @Override
            public void run(){
                if(stopTask){
                    cancel();
                    return;
                }
                for (Player global : Bukkit.getOnlinePlayers()) {
                    MessageUtil.send(global, "auto_broadcast");
                }
            }
        }.runTaskTimer(AithonKits.getInstance(), 0L, timer);
    }
}
