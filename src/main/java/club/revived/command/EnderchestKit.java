package club.revived.command;

import club.revived.cache.EnderchestCache;
import club.revived.config.MessageHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@RequiredArgsConstructor
public class EnderchestKit implements CommandExecutor {

    private final int id;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendRichMessage(MessageHandler.of("SENDER_IS_CONSOLE"));
            return true;
        }
        Map<Integer, ItemStack> map = EnderchestCache.getKits(player.getUniqueId()).get(id).getContent();
        player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
        player.sendRichMessage(MessageHandler.of("ENDERCHEST_LOAD")
                .replace("<ec>", String.valueOf(id))
        );
        return false;
    }
}
