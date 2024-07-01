package club.revived.command;

import club.revived.menus.Miscellaneous;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;

public class DebugCommand {

    public DebugCommand(){
        Commands.command("debug")
                .executes(ctx -> {
                    new Miscellaneous(ctx.player()).open();
                    return CommandResult.success();
                }).register();
    }

}
