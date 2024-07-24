package club.revived.command;

import club.revived.AithonKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.entity.Player;

public class EnderchestKit {

    AithonKits kits = AithonKits.getInstance();

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
                        kits.getConfigUtil().loadEnderChest(ctx.player().getUniqueId(), String.valueOf(finalX))
                                .thenAccept(map -> {
                                    for(int slot = 0; slot<27; slot++){
                                        ctx.player().getEnderChest().setItem(slot, map.get(slot));
                                    }
                                });
                        AithonKits.cooldowns.put(ctx.player().getUniqueId(), 30L);
                        return CommandResult.success();
                    })
                    .build()
                    .register();
        }
    }

    private boolean isOnCooldown(Player player) {
        if (AithonKits.cooldowns.containsKey(player.getUniqueId())) {
            long cooldownTime = AithonKits.cooldowns.get(player.getUniqueId());
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - cooldownTime;
            long cooldownDuration = 30000L;
            return (elapsedTime < cooldownDuration);
        }
        return false;
    }
}
