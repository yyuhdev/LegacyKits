package club.revived.command;

import club.revived.AithonKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

public class EcCommand {

    AithonKits kits = AithonKits.getInstance();

    public EcCommand(){
        init();
    }

    private void init() {
        for (int x = 1; x < 10; x++) {
            int finalX = x;
            Commands.command("ec" + finalX)
                    .completes(context -> Suggestions.empty())
                    .executes(ctx -> {
                        kits.getKitLoader().loadEnderChest(ctx.player(), String.valueOf(finalX));
                        return CommandResult.success();
                    })
                    .build()
                    .register();
        }
    }
}
