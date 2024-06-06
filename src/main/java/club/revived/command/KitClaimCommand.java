package club.revived.command;

import club.revived.AithonKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

public class KitClaimCommand {
    private final AithonKits kits = AithonKits.getInstance();

    public KitClaimCommand() {
        init();
    }

    private void init() {
        for(int x = 1; x < 10; x++) {
            int finalX = x;
            Commands.command("k" + finalX)
                    .completes(context -> Suggestions.empty())
                    .executes(ctx -> {
                        kits.getKitLoader().load(ctx.player(), String.valueOf(finalX));
                        return CommandResult.success();
                    })
                    .build()
                    .register();

            Commands.command("kit" + finalX)
                    .completes(ctx -> Suggestions.empty())
                    .executes(ctx -> {
                        kits.getKitLoader().load(ctx.player(), String.valueOf(finalX));
                        return CommandResult.success();
                   })
                    .build()
                    .register();
        }
    }
}
