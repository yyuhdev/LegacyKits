package club.revived.command;

import club.revived.LegacyKits;
import club.revived.cache.EnderchestCache;
import club.revived.cache.KitCache;
import club.revived.cache.PremadeKitCache;
import club.revived.config.MessageHandler;
import club.revived.menus.KitMenu;
import club.revived.menus.SettingsMenu;
import club.revived.menus.kitroom.KitRoomMenu;
import club.revived.menus.preview.KitPreview;
import club.revived.menus.preview.PremadePreview;
import club.revived.objects.enderchest.Enderchest;
import club.revived.objects.enderchest.EnderchestHolder;
import club.revived.objects.kit.KitHolder;
import club.revived.storage.DatabaseManager;
import club.revived.util.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Kit implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)){
            sender.sendRichMessage(MessageHandler.of("SENDER_IS_CONSOLE"));
            return true;
        }
        if(args.length == 1){
            switch (args[0]){
                case "kitroom" -> new KitRoomMenu(player).open(player);
                case "settings" -> new SettingsMenu(player).open(player);
            }
            return true;
        }
        if(args.length == 3){
            if (args[0].equals("view")) {
                try {
                    if (Integer.parseInt(args[2]) >= 17 || Integer.parseInt(args[2]) < 1) {
                        player.sendRichMessage("<red>Usage: /kit view <player> <1-16>");
                        return true;
                    }
                    Player t = Bukkit.getPlayer(args[1]);
                    if (t == null) {
                        player.sendRichMessage("<red>Target player is not online!");
                        return true;
                    }
                    new KitPreview(player, KitCache.getKits(t.getUniqueId()).get(Integer.parseInt(args[2]))).open(player);
                } catch (Exception e) {
                    player.sendRichMessage("<red>Usage: /kit view <player> <1-16>");
                }
            }
            return true;
        }
        if(args.length == 2){
            switch (args[0]){
                case "save-preset" -> {
                    if(!(args[1].equals("drain") || args[1].equals("evaluation"))) return true;
                    if(!player.hasPermission("legacykits.edit.presetkits")) return true;
                    new PremadePreview(player, args[1]).open(player);
                }
                case "preset" -> {
                    if(!(args[1].equals("drain") || args[1].equals("evaluation"))) return true;
                    PremadeKitCache.getPremadeKit(args[1]).thenAccept(map -> {
                        player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
                        player.setFoodLevel(20);
                        player.getActivePotionEffects().clear();
                        player.setSaturation(20);
                        player.sendRichMessage(MessageHandler.of("KIT_LOAD").replace("<kit>", args[1]));
                        for(Player g : PluginUtils.inRadius(player.getLocation(), 50)){
                            if(PluginUtils.canSeeBroadcast(player)) continue;
                            g.sendRichMessage(MessageHandler.of("KIT_LOAD_BROADCAST")
                                    .replace("<player>", player.getName())
                                    .replace("<kit>", args[1])
                            );
                        }
                    });
                }
                case "save-ec" -> {
                    try {
                        if(Integer.parseInt(args[1]) >= 17 || Integer.parseInt(args[1]) < 1){
                            player.sendRichMessage("<red>Usage: /kit save-ec <1-16>");
                            return true;
                        }
                        Map<Integer, ItemStack> map = new HashMap<>();
                        for(int x = 0; x<=27; x++){
                            map.put(x, player.getEnderChest().getItem(x));
                        }
                        EnderchestCache.addKit(player.getUniqueId(), new Enderchest(player.getUniqueId(), Integer.parseInt(args[1]), "test", map));
                        DatabaseManager.getInstance().save(EnderchestHolder.class, new EnderchestHolder(player.getUniqueId(), EnderchestCache.getKits(player.getUniqueId())));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5.0f, 1.0f);
                        player.sendRichMessage(MessageHandler.of("ENDERCHEST_SAVE"));
                    } catch (Exception e){
                        player.sendRichMessage("<red>Usage: /kit save-ec <1-16>");
                    }
                }
                case "enderchest" -> {
                    try {
                        if(Integer.parseInt(args[1]) >= 17 || Integer.parseInt(args[1]) < 1){
                            player.sendRichMessage("<red>Usage: /kit inventory <1-16>");
                            return true;
                        }
                        Map<Integer, ItemStack> map = EnderchestCache.getKits(player.getUniqueId()).get(Integer.parseInt(args[1])).getContent();
                        player.getEnderChest().setContents(map.values().toArray(new ItemStack[0]));
                        player.sendRichMessage(MessageHandler.of("ENDERCHEST_LOAD")
                                .replace("<ec>", String.valueOf(Integer.parseInt(args[1])))
                        );
                    } catch (Exception e){
                        player.sendRichMessage("<red>Usage: /kit inventory <1-16>");
                    }
                }
                case "inventory" -> {
                    try {
                        if(Integer.parseInt(args[1]) >= 17 || Integer.parseInt(args[1]) < 1){
                            player.sendRichMessage("<red>Usage: /kit enderchest <1-16>");
                            return true;
                        }
                        Map<Integer, ItemStack> map =KitCache.getKits(player.getUniqueId()).get(Integer.parseInt(args[1])).getContent();
                        player.getInventory().setContents(map.values().toArray(new ItemStack[0]));
                        player.setFoodLevel(20);
                        player.getActivePotionEffects().clear();
                        player.setSaturation(20);
                        player.sendRichMessage(MessageHandler.of("KIT_LOAD").replace("<kit>", args[1]));
                        for(Player g : PluginUtils.inRadius(player.getLocation(), 50)){
                            if(PluginUtils.canSeeBroadcast(player)) continue;
                            g.sendRichMessage(MessageHandler.of("KIT_LOAD_BROADCAST")
                                    .replace("<player>", player.getName())
                                    .replace("<kit>", args[1])
                            );
                        }
                        LegacyKits.getLastUsedKit().put(player.getUniqueId(), Integer.parseInt(args[1]));
                    } catch (Exception e){
                        player.sendRichMessage("<red>Usage: /kit enderchest <1-16>");
                    }
                }
                case "save-kit" -> {
                    try {
                        if(Integer.parseInt(args[1]) >= 17 || Integer.parseInt(args[1]) < 1){
                            player.sendRichMessage("<red>Usage: /kit save-kit <1-16>");
                            return true;
                        }
                        Map<Integer, ItemStack> map = new HashMap<>();
                        for(int x = 0; x<=41; x++){
                            map.put(x, player.getInventory().getItem(x));
                        }
                        KitCache.addKit(player.getUniqueId(), new club.revived.objects.kit.Kit(player.getUniqueId(), Integer.parseInt(args[1]), "name", map));
                        DatabaseManager.getInstance().save(KitHolder.class, new KitHolder(player.getUniqueId(), KitCache.getKits(player.getUniqueId())));
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 5.0f, 1.0f);
                        player.sendRichMessage(MessageHandler.of("KIT_SAVE"));
                    } catch (Exception e){
                        player.sendRichMessage("<red>Usage: /kit save-kit <1-16>");
                    }
                }
            }
            return true;
        }
        new KitMenu(player).open(player);
        for(Player g : PluginUtils.inRadius(player.getLocation(), 50)){
            if(PluginUtils.canSeeBroadcast(player)) continue;
            g.sendRichMessage(MessageHandler.of("KIT_MENU_OPEN_BROADCAST")
                    .replace("<player>", player.getName())
            );
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            List<String> toReturn = new ArrayList<>();
            if(sender.hasPermission("legacykits.edit.presetkits")){
                Stream.of(
                        "inventory",
                        "save-preset",
                        "enderchest",
                        "kitroom",
                        "settings",
                        "preset",
                        "view",
                        "save-kit",
                        "save-ec"
                ).map(String::toString).filter(string -> string.startsWith(args[0])).forEach(toReturn::add);
                return toReturn;
            }
            Stream.of(
                    "inventory",
                    "enderchest",
                    "kitroom",
                    "settings",
                    "preset",
                    "view",
                    "save-kit",
                    "save-ec"
            ).map(String::toString).filter(string -> string.startsWith(args[0])).forEach(toReturn::add);
            return toReturn;
        }
        if(args.length == 2){
            switch (args[0]){
                case "save-preset" -> {
                    if(!sender.hasPermission("legacykits.edit.presetkits")) return null;
                    List<String> toReturn = new ArrayList<>();
                    List<String> rt = List.of("drain", "evaluation");
                    rt.stream().map(String::toString).filter(string -> string.startsWith(args[1])).forEach(toReturn::add);
                    return toReturn;
                }
                case "preset" -> {
                    List<String> toReturn = new ArrayList<>();
                    List<String> rt = List.of("drain", "evaluation");
                    rt.stream().map(String::toString).filter(string -> string.startsWith(args[1])).forEach(toReturn::add);
                    return toReturn;
                }
                case "inventory", "enderchest", "save-kit", "save-ec", "delete" -> {
                    List<String> toReturn = new ArrayList<>();
                    List<String> rt = new ArrayList<>();
                    for(int x = 1; x<=16; x++){
                        rt.add(String.valueOf(x));
                    }
                    rt.stream().map(String::toString).filter(string -> string.startsWith(args[1])).forEach(toReturn::add);
                    return toReturn;
                }
                case "view" -> {
                    List<String> toReturn = new ArrayList<>();
                    List<String> rt = new ArrayList<>();
                    for(Player global : Bukkit.getOnlinePlayers()){
                        rt.add(global.getName());
                    }
                    rt.stream().map(String::toString).filter(string -> string.startsWith(args[1])).forEach(toReturn::add);
                    return toReturn;
                }
            }
        }
        if(args.length == 3){
            if (args[0].equals("view")) {
                List<String> toReturn = new ArrayList<>();
                List<String> rt = new ArrayList<>();
                for (int x = 1; x <= 16; x++) {
                    rt.add(String.valueOf(x));
                }
                rt.stream().map(String::toString).filter(string -> string.startsWith(args[2])).forEach(toReturn::add);
                return toReturn;
            }
        }
        return List.of();
    }
}