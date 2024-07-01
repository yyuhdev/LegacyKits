package club.revived.command;

import club.revived.AithonKits;
import club.revived.menus.KitMenu;
import club.revived.util.MessageUtil;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class KitCommand {
    private final String cmd;

    public KitCommand(String cmd) {
        this.cmd = cmd;
        init();
    }

    private void init(){
        Commands.command(this.cmd)
            .completes(context -> {
                if(context.argSize() == 1){
                    return Suggestions.of("auto", "messages", "menu", "load");
                }
                return Suggestions.empty();
            })
            .executes(context -> {
                if(context.argSize() == 1){
                    if(Objects.requireNonNull(context.rawArgAt(0)).equals("auto")){
                        ArrayList<UUID> list = AithonKits.getInstance().autoKitUsers;
                        if(list.contains(context.player().getUniqueId())){
                            list.remove(context.player().getUniqueId());
                            context.player().sendRichMessage("<gray>Auto-kit has been <red>disabled");
                        } else {
                            list.add(context.player().getUniqueId());
                            context.player().sendRichMessage("<gray>Auto-kit has been <green>enabled");
                        }
                    }
                    if(Objects.requireNonNull(context.rawArgAt(0)).equals("menu")){
                        KitMenu kitMenu = new KitMenu(context.player());
                        kitMenu.open();
                        for (Player global : Bukkit.getOnlinePlayers()) {
                            MessageUtil.broadcast(context.player(), global, "broadcast_messages.kit_menu_open");
                        }
                    }
                }
                if(context.argSize() == 0) {
                    KitMenu kitMenu = new KitMenu(context.player());
                    kitMenu.open();
                    for (Player global : Bukkit.getOnlinePlayers()) {
                        MessageUtil.broadcast(context.player(), global, "broadcast_messages.kit_menu_open");
                    }
                }
                return CommandResult.success();
            })
            .build()
            .register();
    }
}