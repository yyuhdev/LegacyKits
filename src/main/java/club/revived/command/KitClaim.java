package club.revived.command;

import club.revived.LegacyKits;
import club.revived.cache.KitCache;
import club.revived.config.MessageHandler;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class KitClaim {

    public KitClaim() {
        init();
    }

    private void init() {
        for(int x = 1; x < 8; x++) {
            int finalX = x;
            Commands.command("k" + finalX)
                    .completes(context -> Suggestions.empty())
                    .executes(ctx -> {
                        Player player = ctx.player();
                        Map<Integer, ItemStack> map =KitCache.getKits(player.getUniqueId()).get(finalX).getContent();
                        ctx.player().getInventory().setContents(map.values().toArray(new ItemStack[0]));
                        player.setFoodLevel(20);
                        player.getActivePotionEffects().clear();
                        player.setSaturation(20);
                        player.sendRichMessage(MessageHandler.of("KIT_LOAD").replace("<kit>", String.valueOf(finalX)));
                        LegacyKits.getInstance().lastUsedKits.put(player.getUniqueId(), finalX);
                        for (Player global : Bukkit.getOnlinePlayers()) {
                            if (global.getLocation().getNearbyPlayers(250).contains(player)) {
                                global.sendRichMessage(MessageHandler.of("KIT_LOAD_BROADCAST")
                                        .replace("<player>", player.getName())
                                        .replace("<kit>", String.valueOf(finalX))
                                );
                            }
                        }
                        return CommandResult.success();
                    })
                    .build()
                    .register();

            Commands.command("kit" + finalX)
                    .completes(ctx -> Suggestions.empty())
                    .executes(ctx -> {
                        Player player = ctx.player();
                        Map<Integer, ItemStack> map =KitCache.getKits(player.getUniqueId()).get(finalX).getContent();
                        ctx.player().getInventory().setContents(map.values().toArray(new ItemStack[0]));
                        player.setFoodLevel(20);
                        player.getActivePotionEffects().clear();
                        player.setSaturation(20);
                        player.sendRichMessage(MessageHandler.of("KIT_LOAD").replace("<kit>", String.valueOf(finalX)));
                        LegacyKits.getInstance().lastUsedKits.put(player.getUniqueId(), finalX);
                        for (Player global : Bukkit.getOnlinePlayers()) {
                            if (global.getLocation().getNearbyPlayers(250).contains(player)) {
                                global.sendRichMessage(MessageHandler.of("KIT_LOAD_BROADCAST")
                                        .replace("<player>", player.getName())
                                        .replace("<kit>", String.valueOf(finalX))
                                );
                            }
                        }
                        return CommandResult.success();
                   })
                    .build()
                    .register();
        }
    }
}
