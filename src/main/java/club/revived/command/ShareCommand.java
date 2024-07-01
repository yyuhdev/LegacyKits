package club.revived.command;

import club.revived.requests.KitRequest;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

import java.util.HashMap;
import java.util.UUID;

public class ShareCommand {

    private final HashMap<UUID, KitRequest> openRequest = new HashMap<>();

    public ShareCommand(){
        Commands.command("sharekit")
                .completes(ctx -> {
                    if(ctx.argSize() == 1){
                        return Suggestions.of("1", "2", "3", "4", "5", "6", "7", "accept");
                    }
                    if(ctx.argSize() == 2){

                    }
                    return Suggestions.of();
                });
    }
}
