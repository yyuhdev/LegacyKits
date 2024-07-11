package club.revived.command;

import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.text.color.TextStyle;

public class ClearCommand {

    public ClearCommand(){
        Commands.command("clear")
                .executes(ctx -> {
                    ctx.player().getInventory().clear();
                    ctx.player().sendActionBar(TextStyle.style("<#72FF9D>Successfully cleared your inventory"));
                    return CommandResult.success();
                }).register();
    }
}
