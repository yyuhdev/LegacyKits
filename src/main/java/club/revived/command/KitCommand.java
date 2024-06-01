package club.revived.command;

import club.revived.menus.KitMenu;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

public class KitCommand {
    private final String cmd;

    public KitCommand(String cmd) {
        this.cmd = cmd;
        init();
    }

    private void init(){
        Commands.command(this.cmd)
            .completes(context -> Suggestions.empty())
            .executes(context -> {
                KitMenu kitMenu = new KitMenu(context.player());
                kitMenu.open();

                return CommandResult.success();
            })
            .build()
            .register();
    }
}