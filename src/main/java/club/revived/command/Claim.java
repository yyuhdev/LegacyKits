package club.revived.command;

import club.revived.LegacyKits;
import club.revived.storage.premade.PremadeKitData;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Claim {

    public Claim(){
        Commands.command("claim")
                .completes(ctx -> {
                    if(ctx.argSize() == 1){
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
                        sug.stream().map(String::toString).filter(string -> string.startsWith(ctx.rawArgAt(0))).forEach(toReturn::add);
                        return Suggestions.wrap(toReturn);
                    }
                    return Suggestions.empty();
                })
                .executes(ctx -> {
                    if(ctx.argSize() == 1){
                        Player player = ctx.player();
                        try {
                            if(!listFilesInDirectory().get().contains(new File(LegacyKits.getInstance().getDataFolder(), File.separator + "premade-kits" + File.separator + ctx.rawArgAt(0) + ".yml"))) {
                                player.sendRichMessage("<red>That kit does not exist");
                                return CommandResult.success();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return CommandResult.success();
                        }
                        PremadeKitData.loadPremadeKit(ctx.rawArgAt(0)).thenAccept(map -> {
                            for (int slot = 0; slot < 41; slot++) {
                                player.getInventory().setItem(slot, map.get(slot));
                            }
                            player.getInventory().setHelmet(map.get(36));
                            player.getInventory().setChestplate(map.get(37));
                            player.getInventory().setLeggings(map.get(38));
                            player.getInventory().setBoots(map.get(39));
                        });
                        return CommandResult.success();
                    }
                    ctx.player().sendRichMessage("<red>Usage: /claim <kit>");
                    return CommandResult.success();
                }).register();
    }

    public CompletableFuture<List<File>> listFilesInDirectory() {
        return CompletableFuture.supplyAsync(() -> {
            File dir = new File(LegacyKits.getInstance().getDataFolder() + File.separator + "premade-kits");
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                return Arrays.asList(files != null ? files : new File[0]);
            }
            return List.of();
        });
    }

}