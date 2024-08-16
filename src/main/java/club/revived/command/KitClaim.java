package club.revived.command;

import club.revived.LegacyKits;
import club.revived.storage.kit.KitData;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.entity.Player;

public class KitClaim {

    public KitClaim() {
        init();
    }

    private void init() {
        for(int x = 1; x < 10; x++) {
            int finalX = x;
            Commands.command("k" + finalX)
                    .completes(context -> Suggestions.empty())
                    .executes(ctx -> {
                        KitData.load(ctx.player(), finalX);
                        LegacyKits.cooldowns.put(ctx.player().getUniqueId(), 30l);
                        return CommandResult.success();
                    })
                    .build()
                    .register();

            Commands.command("kit" + finalX)
                    .completes(ctx -> Suggestions.empty())
                    .executes(ctx -> {
                        KitData.load(ctx.player(), finalX);
                        LegacyKits.cooldowns.put(ctx.player().getUniqueId(), 30l);
                        return CommandResult.success();
                   })
                    .build()
                    .register();
        }
    }
}
