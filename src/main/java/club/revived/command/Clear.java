package club.revived.command;

import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Clear {

    public Clear(){
        Commands.command("clear")
                .executes(ctx -> {
                    ctx.player().getInventory().clear();
                    ctx.player().sendActionBar(TextStyle.style("<#72FF9D>Successfully cleared your inventory"));
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(ctx.player()))
                            global.sendRichMessage("<dark_gray> ℹ <player> has cleared their inventory"
                                    .replace("<player>", ctx.player().getName()));
                    }
                    return CommandResult.success();
                }).register();

        Commands.command("clearec")
                .executes(ctx -> {
                    ctx.player().getEnderChest().clear();
                    ctx.player().sendActionBar(TextStyle.style("<#72FF9D>Successfully cleared your enderchest"));
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(ctx.player()))
                            global.sendRichMessage("<dark_gray> ℹ <player> has cleared their enderchest"
                                    .replace("<player>", ctx.player().getName()));
                    }
                    return CommandResult.success();
                }).register();

        Commands.command("cleareffects")
                .executes(ctx -> {
                    ctx.player().clearActivePotionEffects();
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(ctx.player()))
                            global.sendRichMessage(" <dark_gray>\uD83E\uDDEA <player> has cleared their potion effects"
                                    .replace("<player>", ctx.player().getName()));
                    }
                    return CommandResult.success();
                }).register();

        Commands.command("clearall")
                .executes(ctx -> {
                    ctx.player().clearActivePotionEffects();
                    ctx.player().getEnderChest().clear();
                    ctx.player().getInventory().clear();
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(ctx.player()))
                            global.sendRichMessage(" <dark_gray>\uD83E\uDDEA <player> has cleared themselves"
                                    .replace("<player>", ctx.player().getName()));
                    }
                    return CommandResult.success();
                }).register();


    }
}
