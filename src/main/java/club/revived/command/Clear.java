package club.revived.command;

import club.revived.config.MessageHandler;
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
                    ctx.player().sendRichMessage(MessageHandler.of("INVENTORY_CLEARED"));
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(ctx.player()))
                            global.sendRichMessage(MessageHandler.of("INVENTORY_CLEARED_BROADCAST")
                                    .replace("<player>", ctx.player().getName()));
                    }
                    return CommandResult.success();
                }).register();

        Commands.command("clearec")
                .executes(ctx -> {
                    ctx.player().getEnderChest().clear();
                    ctx.player().sendRichMessage(MessageHandler.of("ENDERCHEST_CLEARED"));
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(ctx.player()))
                            global.sendRichMessage(MessageHandler.of("ENDERCHEST_CLEARED_BROADCAST")
                                    .replace("<player>", ctx.player().getName()));
                    }
                    return CommandResult.success();
                }).register();

        Commands.command("cleareffects")
                .executes(ctx -> {
                    ctx.player().clearActivePotionEffects();
                    ctx.player().sendRichMessage(MessageHandler.of("EFFECTS_CLEARED"));
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(ctx.player()))
                            global.sendRichMessage(MessageHandler.of("EFFECTS_CLEARED_BROADCAST")
                                    .replace("<player>", ctx.player().getName()));
                    }
                    return CommandResult.success();
                }).register();
    }
}
