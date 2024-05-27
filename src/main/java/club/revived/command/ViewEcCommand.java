package club.revived.command;

import club.revived.WeirdoKits;
import club.revived.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewEcCommand implements CommandExecutor, TabCompleter {
    public ViewEcCommand(String name) {
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
            player = (Player) commandSender;
        } else {
            return false;
        }
        if (args.length != 2) {
            player.sendRichMessage("<red>Usage: /viewkit <player> <number>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            player.sendRichMessage("<red>Usage: /viewkit <player> <number>");
            return true;
        }

        if (isInt(args[1])) {

            if(Integer.valueOf(args[1]) < 1 &&  Integer.valueOf(args[1]) > 7){
                player.sendRichMessage("<red>Usage: /viewkit <player> <number>");
                return true;
            }

            Inventory inventory = ecinventory(ChatColor.GOLD + "Kit Preview");
            HashMap<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().loadec(player.getUniqueId(), args[1]);
            for (int slot = 0; slot < 27; slot++) {
                inventory.setItem(slot, map.get(Integer.valueOf(slot)));
            }
            player.openInventory(inventory);
            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);

        }
        return false;
    }

    private Inventory ecinventory(String name) {
        Inventory inventory = Bukkit.createInventory(null, 36, name);
        for (int x = 27; x < 36; x++) {
            inventory.setItem(x, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName("").toItemStack());
        }
        return inventory;
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1){
            List<String> list = new ArrayList<>();
            for(Player global : Bukkit.getOnlinePlayers()){
                list.add(global.getName());
            }
            return list;

        }
        if(strings.length == 2){
            return List.of("1","2","3","4","5", "6", "7");
        }
        return null;
    }
}
