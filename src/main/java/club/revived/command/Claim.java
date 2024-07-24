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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Claim implements CommandExecutor, TabCompleter {

    private final ConfigUtil configUtil = new ConfigUtil();

    public Claim(){
        AithonKits.getInstance().getCommand("claim").setExecutor(this);
        AithonKits.getInstance().getCommand("claim").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) return true;
        if(args.length == 1){
            try {
                if(!listFilesInDirectory().get().contains(new File(AithonKits.getInstance().getDataFolder(), File.separator + "premade-kits" + File.separator + args[0] + ".yml"))) {
                    player.sendRichMessage("<red>That kit does not exist");
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
            configUtil.loadPremadeKit(args[0]).thenAccept(map -> {
                for (int slot = 0; slot < 41; slot++) {
                    player.getInventory().setItem(slot, map.get(slot));
                }
                player.getInventory().setHelmet(map.get(36));
                player.getInventory().setChestplate(map.get(37));
                player.getInventory().setLeggings(map.get(38));
                player.getInventory().setBoots(map.get(39));
            });
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            List<String> sug = new ArrayList<>();
            try {
                List<File> files = listFilesInDirectory().get();
                for (File file : files) {
                    sug.add(file.getName().replace(".yml", ""));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ArrayList<String> toReturn = new ArrayList<>();
            sug.stream().map(String::toString).filter(string -> string.startsWith(args[0])).forEach(toReturn::add);
            return toReturn;
        }
        return List.of();
    }

    public CompletableFuture<List<File>> listFilesInDirectory() {
        return CompletableFuture.supplyAsync(() -> {
            File dir = new File(AithonKits.getInstance().getDataFolder() + File.separator + "premade-kits");
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                return Arrays.asList(files != null ? files : new File[0]);
            }
            return List.of();
        });
    }

}