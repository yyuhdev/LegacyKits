package club.revived.command;

import club.revived.cache.KitCache;
import club.revived.config.MessageHandler;
import club.revived.util.PluginUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@RequiredArgsConstructor
public class KitLoad implements CommandExecutor {

    private final int id;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendRichMessage(MessageHandler.of("SENDER_IS_CONSOLE"));
            return true;
        }
        Map<Integer, ItemStack> map =KitCache.getKits(player.getUniqueId()).get(id).getContent();
        player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
        player.setFoodLevel(20);
        player.getActivePotionEffects().clear();
        player.setSaturation(20);
        player.sendRichMessage(MessageHandler.of("KIT_LOAD").replace("<kit>", String.valueOf(id)));
        for(Player g : PluginUtils.inRadius(player.getLocation(), 50)){
            g.sendRichMessage(MessageHandler.of("KIT_LOAD_BROADCAST")
                    .replace("<player>", player.getName())
                    .replace("<kit>", String.valueOf(id))
            );
        }
        return false;
    }
}
