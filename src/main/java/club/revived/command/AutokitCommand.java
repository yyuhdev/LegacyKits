package club.revived.command;

import club.revived.AithonKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import java.util.ArrayList;
import java.util.UUID;

public class AutokitCommand {

    public AutokitCommand(){
        Commands.command("autokit")
                .completes(ctx -> Suggestions.empty())
                .executes(ctx -> {
                    ArrayList<UUID> list = AithonKits.getInstance().autoKitUsers;
                    if(list.contains(ctx.player().getUniqueId())){
                        list.remove(ctx.player().getUniqueId());
                        ctx.player().sendRichMessage("<gray>Auto-kit has been <red>disabled");
                    } else {
                        list.add(ctx.player().getUniqueId());
                        ctx.player().sendRichMessage("<gray>Auto-kit has been <green>enabled");
                    }
                    return CommandResult.success();
                }).register();
    }
}
