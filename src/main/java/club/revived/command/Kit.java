package club.revived.command;

import club.revived.config.MessageHandler;
import club.revived.menus.KitMenu;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Kit {

    public Kit(){
        Commands.command("kit")
                .aliases("k", "kits")
                .completes(ctx -> Suggestions.empty())
                .executes(ctx -> {
                    new KitMenu(ctx.player()).open(ctx.player());
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if(global.getLocation().getNearbyPlayers(250).contains(ctx.player())){
                            global.sendRichMessage(MessageHandler.of("KIT_MENU_OPEN_BROADCAST")
                                    .replace("<player>", ctx.player().getName())
                            );
                        }
                    }
                    return CommandResult.success();
                })
                .build()
                .register();
    }
}