package club.revived.util;

import club.revived.config.Files;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageUtil {
    @NotNull
    public static FileConfiguration messageConfig() {
        return Files.config(Files.create(Files.file("messages.yml")));
    }

    public static void send(Player player, String key) {
        if (!messageConfig().getBoolean(key + ".enabled")) return;

        player.sendRichMessage(
            messageConfig().getString(key + ".message", "<red>Failed to find message!")
                .replaceAll("<prefix>", getPrefix() + "<reset>")
                .replaceAll("<player>", player.getName())
        );
    }

    public static void broadcast(Player player, Player global, String key) {
        if (!messageConfig().getBoolean(key + ".enabled")) return;

        global.sendRichMessage(
            messageConfig().getString(key + ".message", "<red>Failed to find message!")
                .replace("<prefix>", getPrefix() + "<reset>")
                .replace("<player>", player.getName())
        );
    }


    @NotNull
    public static String getPrefix() {
        return messageConfig().getString("PREFIX", "");
    }
}