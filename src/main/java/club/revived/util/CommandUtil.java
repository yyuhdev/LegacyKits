package club.revived.util;

import club.revived.LegacyKits;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

public class CommandUtil {

    public static void registerCommand(String name, CommandExecutor executor) {
        try {
            Field commandMapField = getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(getServer());
            BukkitCommand command = new BukkitCommand(name) {
                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
                    if (!getPlugin(LegacyKits.class).isEnabled()) {
                        return false;
                    }
                    return executor.onCommand(sender, this, commandLabel, args);
                }
            };
            commandMap.register("kits", command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerCommand(String name, CommandExecutor executor, TabCompleter completer) {
        try {
            Field commandMapField = getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(getServer());
            BukkitCommand command = new BukkitCommand(name) {
                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
                    if (!getPlugin(LegacyKits.class).isEnabled()) {
                        return false;
                    }
                    return executor.onCommand(sender, this, commandLabel, args);
                }
                @Override
                public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) throws IllegalArgumentException {
                    if (completer != null) {
                        return Objects.requireNonNull(completer.onTabComplete(sender, this, alias, args));
                    }
                    return super.tabComplete(sender, alias, args);
                }
            };
            commandMap.register("kits", command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
