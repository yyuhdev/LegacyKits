package club.revived.command;

import club.revived.AithonKits;
import club.revived.util.ConfigUtil;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ClaimCommand implements CommandExecutor, TabCompleter {

    private final ConfigUtil configUtil = new ConfigUtil();

    public ClaimCommand(){
        AithonKits.getInstance().getCommand("claim").setExecutor(this);
        AithonKits.getInstance().getCommand("claim").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return true;
        if(args.length == 1) {
            if (args[0].equals("drain") || args[0].equals("evaluation") || args[0].equals("enderchest")) {
                if(args[0].equals("enderchest")){
                    if(configUtil.loadPremadeEnderchest(args[0], player.getEnderChest())) {
                        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        player.sendActionBar(TextStyle.style("<#72FF9D>Enderchest loaded successfully"));
                        return true;
                    }
                    player.sendRichMessage("<red>An Error occurred whilst trying to claim kit");
                }
                if (configUtil.loadPremadeKit(args[0], player.getInventory())) {
                    player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    player.sendActionBar(TextStyle.style("<#72FF9D>Kit loaded successfully"));
                    return true;
                }
                player.sendRichMessage("<red>An Error occurred whilst trying to claim kit");
            }
            else {
                player.sendActionBar(TextStyle.style("<red>Wrong Usage"));
                player.sendRichMessage("<red>Usage: /claim <kit>");
            }
        }
        else {
            player.sendActionBar(TextStyle.style("<red>Wrong Usage"));
            player.sendRichMessage("<red>Usage: /claim <kit>");
        }
        return true;

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> sug = new ArrayList<>();
        sug.add("drain");
        sug.add("evaluation");
        sug.add("enderchest");
        ArrayList<String> toReturn = new ArrayList<>();
        sug.stream().map(String::toString).filter(string -> string.startsWith(args[0])).forEach(toReturn::add);
        return toReturn;
    }
}