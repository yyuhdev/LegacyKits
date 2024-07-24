package club.revived.command;

import club.revived.menus.KitMenu;
import club.revived.util.MessageUtil;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Kit {
    private final String cmd;

    public Kit(String cmd) {
        this.cmd = cmd;
        init();
    }

    private void init() {
        Commands.command(this.cmd)
                .executes(context -> {
                    new KitMenu(context.player()).open(context.player());
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        MessageUtil.broadcast(context.player(), global, "broadcast_messages.kit_menu_open");
                    }
                    return CommandResult.success();
                })
                .build()
                .register();
    }
}