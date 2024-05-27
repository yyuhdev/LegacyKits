package club.revived.command;

import club.revived.WeirdoKits;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GetEcCommand implements CommandExecutor, TabCompleter {

    private final Map<UUID, KitRequest> openRequest = new HashMap<>();

    public GetEcCommand(String name) {
        WeirdoKits.getInstance().getCommand(name).setExecutor(this);
        WeirdoKits.getInstance().getCommand(name).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length != 2) {
            player.sendMessage(ChatColor.RED + "Usage: /getec <player> <number>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        if (args[1].equalsIgnoreCase("accept")) {
            if (openRequest.containsKey(target.getUniqueId())) {
                KitRequest request = openRequest.get(target.getUniqueId());
                if (request.requester.equals(player)) {
                    openRequest.remove(target.getUniqueId());
                    WeirdoKits.getInstance().getKitLoader().ecloadothers(player, target, request.kitNumber);
                    player.sendMessage(ChatColor.GOLD + "Request accepted.");
                    target.sendMessage(ChatColor.GOLD + "Your request has been accepted by " + player.getName() + ".");
                } else {
                    player.sendMessage(ChatColor.RED + "You have no pending requests from this player.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "No request found to accept.");
            }
            return true;
        }

        if (isInt(args[1])) {
            int kitNumber = Integer.parseInt(args[1]);
            if (kitNumber < 1 || kitNumber > 7) {
                player.sendMessage(ChatColor.RED + "Usage: /getec <player> <number>");
                return true;
            }
            if (player != target) {

                openRequest.put(player.getUniqueId(), new KitRequest(target, args[1]));
                target.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
                target.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GRAY + " has requested to copy your Enderchest " + ChatColor.GOLD + args[1] + ChatColor.GRAY + ". Use " + ChatColor.GOLD + "/getec " + player.getName() + " accept " + ChatColor.GRAY + " to accept their request.");
                player.sendMessage(ChatColor.GOLD + "Kit request sent to " + target.getName() + ".");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Usage: /getec <player> <number>");
        }

        return true;
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
        if (strings.length == 1) {
            List<String> list = new ArrayList<>();
            for (Player global : Bukkit.getOnlinePlayers()) {
                list.add(global.getName());
            }
            return list;
        }
        if (strings.length == 2) {
            return List.of("1", "2", "3", "4", "5", "6", "7", "accept");
        }
        return null;
    }

    private static class KitRequest {
        Player requester;
        String kitNumber;

        KitRequest(Player requester, String kitNumber) {
            this.requester = requester;
            this.kitNumber = kitNumber;
        }
    }
}
