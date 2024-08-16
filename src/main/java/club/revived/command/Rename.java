package club.revived.command;

import club.revived.LegacyKits;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rename implements CommandExecutor {

    public Rename(){
        LegacyKits.getInstance().getCommand("rename").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)){
            commandSender.sendRichMessage("<red>You can only use this as a player");
            return true;
        }
        ItemStack stack = player.getInventory().getItemInMainHand().clone();
        ItemMeta meta = stack.getItemMeta();
        StringBuilder value = new StringBuilder();
        for (String arg : strings) {
            if(arg.equals(strings[0])){
                value.append(arg);
                continue;
            }
            value.append(" ").append(arg);
        }
        meta.setDisplayName(translateColorCodes((String.valueOf(value))));
        stack.setItemMeta(meta);
        player.getInventory().setItemInMainHand(stack);
        return false;
    }

    public String translateColorCodes(String message) {
        Pattern pattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of("#" + matcher.group(1)).toString());
        }
        matcher.appendTail(buffer);
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}
