package club.revived.command;

import club.revived.WeirdoKits;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Ec7Command implements CommandExecutor {
    public Ec7Command(String name) {
        WeirdoKits.getInstance().getCommand(name).setExecutor(this);
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
        WeirdoKits.getInstance().ecload(player, "7");
        return false;
    }
}
