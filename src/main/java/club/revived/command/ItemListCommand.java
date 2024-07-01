package club.revived.command;


import club.revived.miscellaneous.Itemlist.ItemListMenu;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

public class ItemListCommand {

    public ItemListCommand(){
        Commands.command("itemlist")
                .completes(ctx -> Suggestions.empty())
                .executes(ctx -> {
                    new ItemListMenu(ctx.player()).open();
                    return CommandResult.success();
                }).register();
    }
}
