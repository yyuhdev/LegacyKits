package club.revived.command;

import club.revived.LegacyKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

import java.util.ArrayList;
import java.util.UUID;

public class Autokit {

    public Autokit(){
        Commands.command("autokit")
                .completes(ctx -> Suggestions.empty())
                .executes(ctx -> {
                    ArrayList<UUID> list = LegacyKits.getInstance().autoKitUsers;
                    if(list.contains(ctx.player().getUniqueId())){
                        list.remove(ctx.player().getUniqueId());
                        LegacyKits.getInstance().getConfig().getStringList("autokit.toggled_off").remove(ctx.player().getUniqueId().toString());
                        ctx.player().sendRichMessage("<gray>Auto-kit has been <green>enabled");
                    } else {
                        list.add(ctx.player().getUniqueId());
                        LegacyKits.getInstance().getConfig().getStringList("autokit.toggled_off").add(ctx.player().getUniqueId().toString());
                        ctx.player().sendRichMessage("<gray>Auto-kit has been <red>disabled");
                    }
                    return CommandResult.success();
                }).register();
    }
}
