package club.revived.command;

import club.revived.AithonKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.entity.Player;

public class KitClaim {
    private final AithonKits kits = AithonKits.getInstance();

    public KitClaim() {
        init();
    }

    private void init() {
        for(int x = 1; x < 10; x++) {
            int finalX = x;
            Commands.command("k" + finalX)
                    .completes(context -> Suggestions.empty())
                    .executes(ctx -> {
                        if(isOnCooldown(ctx.player())) return CommandResult.success();
                        kits.getLoading().load(ctx.player(), String.valueOf(finalX));
                        AithonKits.cooldowns.put(ctx.player().getUniqueId(), 30l);
                        return CommandResult.success();
                    })
                    .build()
                    .register();

            Commands.command("kit" + finalX)
                    .completes(ctx -> Suggestions.empty())
                    .executes(ctx -> {
                        if(isOnCooldown(ctx.player())) return CommandResult.success();
                        kits.getLoading().load(ctx.player(), String.valueOf(finalX));
                        AithonKits.cooldowns.put(ctx.player().getUniqueId(), 30l);
                        return CommandResult.success();
                   })
                    .build()
                    .register();
        }
    }

    private boolean isOnCooldown(Player player) {
        if (AithonKits.cooldowns.containsKey(player.getUniqueId())) {
            long cooldownTime = (AithonKits.cooldowns.get(player.getUniqueId()));
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - cooldownTime;
            long cooldownDuration = 30;
            return (elapsedTime < cooldownDuration);
        }
        return false;
    }
}
