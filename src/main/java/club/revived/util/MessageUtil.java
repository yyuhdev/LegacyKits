package club.revived.util;

import club.revived.WeirdoKits;
import club.revived.config.Files;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Warden;
import org.jetbrains.annotations.NotNull;

public class MessageUtil {
    @NotNull
    public FileConfiguration messageConfig() {
        return Files.config(Files.create(Files.file("messages.yml")));
    }

    public void message(Player player, String key) {
        if (messageConfig().getBoolean(key + ".enabled")) {
            player.sendRichMessage(messageConfig().getString(key + ".message", "<red>Failed to find message!").replace("<prefix>", getPrefix() + "<reset>").replace("<player>", player.getName()));
        }
    }

    public void brcmessage(Player player, Player global, String key) {
        if (messageConfig().getBoolean(key + ".enabled")) {
            global.sendRichMessage(messageConfig().getString(key + ".message", "<red>Failed to find message!").replace("<prefix>", getPrefix() + "<reset>").replace("<player>", player.getName()));
        }
    }


    @NotNull
    public String getPrefix() {
        try {
            return messageConfig().getString("PREFIX");
        } catch (Exception ignored) {
            return messageConfig().getString("PREFIX", "<red>Failed to find prefix!");
        }
    }
}