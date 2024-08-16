package club.revived.command;

import club.revived.LegacyKits;
import club.revived.storage.kit.EnderchestData;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.entity.Player;

public class EnderchestKit {

    LegacyKits kits = LegacyKits.getInstance();

    public EnderchestKit(){
        init();
    }

    private void init() {
        for (int x = 1; x < 10; x++) {
            int finalX = x;
            Commands.command("ec" + finalX)
                    .completes(context -> Suggestions.empty())
                    .executes(ctx -> {
                        if (isOnCooldown(ctx.player())) return CommandResult.success();
                        EnderchestData.load(ctx.player(), finalX);
                        return CommandResult.success();
                    })
                    .build()
                    .register();
        }
    }

    private boolean isOnCooldown(Player player) {
        if (LegacyKits.cooldowns.containsKey(player.getUniqueId())) {
            long cooldownTime = LegacyKits.cooldowns.get(player.getUniqueId());
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - cooldownTime;
            long cooldownDuration = 30000L;
            return (elapsedTime < cooldownDuration);
        }
        return false;
    }
}
