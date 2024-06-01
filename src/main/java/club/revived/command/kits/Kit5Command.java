package club.revived.command.kits;

import club.revived.WeirdoKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

public class Kit5Command {
    private final WeirdoKits kits = WeirdoKits.getInstance();
    private final String cmd;

    public Kit5Command(String cmd) {
        this.cmd = cmd;
        init();
    }

    private void init() {

        Commands.command(cmd)
                .completes(context -> Suggestions.empty())
                .executes(ctx -> {
                    kits.getKitLoader().load(ctx.player(), "5");
                    return CommandResult.success();
                })
                .build()
                .register();
    }
}
