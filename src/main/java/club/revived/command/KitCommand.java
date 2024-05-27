package club.revived.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import club.revived.WeirdoKits;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KitCommand implements CommandExecutor, TabCompleter {
    public KitCommand(String name) {
        WeirdoKits.getInstance().getCommand(name).setExecutor(this);
        WeirdoKits.getInstance().getCommand(name).setTabCompleter(this);
    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player;
        if (commandSender == null)
            return true;
        if (command == null)
            return true;
        if (s == null)
            return true;
        if (args == null)
            return true;
        if (commandSender instanceof Player) {
            player = (Player)commandSender;
        } else {
            return false;
        }
        if (args.length == 0) {
            WeirdoKits.getInstance().openInventory(player);
            return false;
        }
        if (args.length == 1) {
            if (WeirdoKits.getInstance().getConfig().isSet(player.getUniqueId().toString()) &&
                    WeirdoKits.getInstance().getConfig().getConfigurationSection(player.getUniqueId().toString()).getKeys(false).contains(args[0])) {
                WeirdoKits.getInstance().load(player, args[0]);
                return false;
            }
            player.sendMessage("was not found!");
            return false;
        }
        player.sendMessage("");
                player.sendMessage("use: <name>");
        return false;
    }

    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender == null)
            return null;
        if (command == null)
            return null;
        if (s == null)
            return null;
        if (args == null)
            return null;
        if (args.length == 1)
            return Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7",});
        return new ArrayList<>();
    }
}
