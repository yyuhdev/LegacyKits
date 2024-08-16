package club.revived.command.admin;

import club.revived.LegacyKits;
import club.revived.menus.admin.PresetEditor;
import club.revived.storage.room.KitRoomData;
import club.revived.util.enums.Page;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class KitAdmin {

    public Inventory editor;
    public Page editing;

    public KitAdmin(){
        LegacyKits.getInstance().getServer().getPluginManager().registerEvents(new AdminListener(), LegacyKits.getInstance());
        Commands.command("kitadmin")
                .permission("club.revived.admin")
                .completes(ctx -> {
                    if(ctx.argSize() == 1){
                        List<String> toReturn = new ArrayList<>();
                        Stream.of("preset", "reload", "kitroom").map(String::toString).filter(string -> string.startsWith(ctx.rawArgAt(0))).forEach(toReturn::add);
                        return Suggestions.wrap(toReturn);
                    }
                    if(ctx.argSize() == 2){
                        if(ctx.rawArgAt(0).equals("preset")){
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
                            sug.stream().map(String::toString).filter(string -> string.startsWith(ctx.rawArgAt(1))).forEach(toReturn::add);
                            return Suggestions.wrap(toReturn);
                        }
                        if(ctx.rawArgAt(0).equals("kitroom")){
                            List<String> toReturn = new ArrayList<>();
                            Stream.of("netherite", "diamond", "potions", "armory", "misc").map(String::toString).filter(string -> string.startsWith(ctx.rawArgAt(1))).forEach(toReturn::add);
                            return Suggestions.wrap(toReturn);
                        }
                        return Suggestions.of("[<text>]");
                    }
                    return Suggestions.empty();
                })
                .executes(ctx -> {
                    if(ctx.argSize() == 1){
                        if(ctx.rawArgAt(0).equals("reload")){
                            LegacyKits.getInstance().reloadConfig();
                            ctx.player().sendRichMessage("<green>Successfully reloaded the config");
                        }
                    }
                    if(ctx.argSize() == 2) {
                        if (Objects.equals(ctx.rawArgAt(0), "preset")) {
                            new PresetEditor(ctx.rawArgAt(1), ctx.player()).open(ctx.player());
                        }
                        if(ctx.rawArgAt(0).equals("kitroom")){
                            if(ctx.rawArgAt(1).equals("netherite")){
                                editor = getKitRoomEditor(Page.NETHERITE_CRYSTAL);
                                editing = Page.NETHERITE_CRYSTAL;
                                ctx.player().openInventory(editor);
                            }
                            if(ctx.rawArgAt(1).equals("diamond")){
                                editor = getKitRoomEditor(Page.DIAMOND_CRYSTAL);
                                editing = Page.DIAMOND_CRYSTAL;
                                ctx.player().openInventory(editor);
                            }
                            if(ctx.rawArgAt(1).equals("potions")){
                                editor = getKitRoomEditor(Page.POTIONS);
                                editing = Page.POTIONS;
                                ctx.player().openInventory(editor);
                            }
                            if(ctx.rawArgAt(1).equals("armory")){
                                editor = getKitRoomEditor(Page.ARMORY);
                                editing = Page.ARMORY;
                                ctx.player().openInventory(editor);
                            }
                            if(ctx.rawArgAt(1).equals("misc")){
                                editor = getKitRoomEditor(Page.MISC);
                                editing = Page.MISC;
                                ctx.player().openInventory(editor);
                            }

                        }
                    }
                    return CommandResult.success();
                }).register();

    }

    public Inventory getKitRoomEditor(Page page){
        Inventory inventory = Bukkit.createInventory(null, 45, Component.text("Edit"));
        KitRoomData.loadKitRoomPage(page).thenAccept(map -> {
            for(int x = 0; x<45; x++){
                inventory.setItem(x, map.get(x));
            }
        });
        return inventory;
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

    private boolean isNumber(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public class AdminListener implements Listener {

        @EventHandler
        public void onClose(InventoryCloseEvent event){
            if(event.getInventory() == editor){
                KitRoomData.saveKitRoomPage(editing, event.getInventory());
                event.getPlayer().sendRichMessage("yh");
            }
        }
    }
}
