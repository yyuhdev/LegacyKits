package club.revived.command.kits;

import club.revived.WeirdoKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

public class Kit1Command {
    WeirdoKits kits = WeirdoKits.getInstance();
    private final String cmd;

    public Kit1Command(String cmd) {
        this.cmd = cmd;
        init();
    }

    private void init() {

        Commands.command(cmd)
                .completes(context -> {
                    return Suggestions.empty();
                })
                .executes(ctx -> {
                    kits.getKitLoader().load(ctx.player(),"1");
                    return CommandResult.success();
                })
                .build()
                .register();
    }
}
