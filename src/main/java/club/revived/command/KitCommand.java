package club.revived.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import club.revived.WeirdoKits;
import club.revived.menus.KitMenu;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class KitCommand {

    private final String cmd;

    public KitCommand(String cmd) {
        this.cmd = cmd;
        init();
    }

    private void init(){
        Commands.command(this.cmd)
                .completes(context -> {
                    return Suggestions.empty();
                })
                .executes(context -> {
                        KitMenu kitMenu = new KitMenu(context.player());
                        kitMenu.open();
                        return CommandResult.success();
                })
                .build()
                .register();


    }

}