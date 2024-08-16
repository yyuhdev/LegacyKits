package club.revived.command;

import club.revived.LegacyKits;
import club.revived.config.MessageHandler;
import club.revived.storage.kit.EnderchestData;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.entity.Player;

public class EnderchestKit {

    LegacyKits kits = LegacyKits.getInstance();

    public EnderchestKit(){
        init();
    }

    private void init() {
        for (int x = 1; x < 10; x++) {
            int finalX = x;
            Commands.command("ec" + finalX)
                    .completes(context -> Suggestions.empty())
                    .executes(ctx -> {
                        EnderchestData.load(ctx.player(), finalX);
                        ctx.player().sendRichMessage(MessageHandler.of("ENDERCHEST_LOAD")
                                .replace("<ec>", String.valueOf(finalX))
                        );
                        return CommandResult.success();
                    })
                    .build()
                    .register();
        }
    }
}
