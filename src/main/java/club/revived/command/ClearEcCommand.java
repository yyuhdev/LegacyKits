package club.revived.command;

import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.text.color.TextStyle;

public class ClearEcCommand {

    public ClearEcCommand(){
        Commands.command("clear-ec")
                .executes(ctx -> {
                    ctx.player().getEnderChest().clear();
                    ctx.player().sendActionBar(TextStyle.style("<#72FF9D>Successfully cleared your enderchest"));
                    return CommandResult.success();
                }).register();
    }
}
