package club.revived.util;

import club.revived.config.Files;
import dev.manere.utils.scheduler.Schedulers;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class MessageUtil {

    @NotNull
    public static FileConfiguration messageConfig() {
        return Files.config(Files.file("messages.yml"));
    }

    public static void send(Player player, String key) {
        if (!messageConfig().getBoolean(key + ".enabled")) return;

        CompletableFuture.supplyAsync(() -> messageConfig().getString(key + ".message", "<red>Failed to find message!")).thenAccept(string -> player.sendActionBar(MiniMessage.miniMessage().deserialize(string
                .replaceAll("<prefix>", getPrefix() + "<reset>")
                .replaceAll("<player>", player.getName())
        )));

    }

    public static void broadcast(Player player, Player global, String key) {
        if (!messageConfig().getBoolean(key + ".enabled")) return;

        CompletableFuture.supplyAsync(() -> messageConfig().getString(key + ".message", "<red>Failed to find message!")).thenAccept(string -> global.sendRichMessage(string
                .replace("<prefix>", getPrefix())
                .replace("<player>", player.getName())));
    }


    @NotNull
    public static String getPrefix(){
        String prefix = Schedulers.async().supply(() -> messageConfig().getString("PREFIX", ""));
        return Objects.requireNonNull(prefix);
    }
}