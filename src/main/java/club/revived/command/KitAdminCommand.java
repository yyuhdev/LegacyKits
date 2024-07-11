package club.revived.command;

import club.revived.menus.admin.PremadeEnderchestEditor;
import club.revived.menus.admin.PremadeKitEditor;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

import java.util.Objects;

public class KitAdminCommand {

    public KitAdminCommand(){
        Commands.command("kitadmin")
                .permission("club.revived.admin")
                .completes(ctx -> {
                    if(ctx.argSize() == 1){
                        return Suggestions.of("savepremadekit", "savepremadeec");
                    }
                    if(ctx.argSize() == 2){
                        return Suggestions.of("[<text>]");
                    }
                    return Suggestions.empty();
                })
                .executes(ctx -> {
                    if(ctx.argSize() == 2) {
                        if (Objects.equals(ctx.rawArgAt(0), "savepremadekit")) {
                            new PremadeKitEditor(ctx.player(), ctx.rawArgAt(1)).open();
                        }
                        if(Objects.equals(ctx.rawArgAt(0), "savepremadeec")){
                            new PremadeEnderchestEditor(ctx.player(), ctx.rawArgAt(1)).open();
                        }
                    }
                    return CommandResult.success();
                }).register();

    }
}
