package club.revived.command;

import club.revived.AithonKits;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.scheduler.Schedulers;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class ViewKit {

    String uuidString = "";
    UUID uuid;

    public ViewKit(){
        AithonKits.getInstance().getServer().getPluginManager().registerEvents(new clickListener(), AithonKits.getInstance());
        Commands.command("viewkit")
                .aliases("vk")
                .completes(ctx -> {
                    if(ctx.argSize() == 1){
                        return Suggestions.of("enderchest", "kit");
                    }
                    if(ctx.argSize() == 2){
                        if(Objects.requireNonNull(ctx.rawArgAt(0)).equals("enderchest") || Objects.requireNonNull(ctx.rawArgAt(0)).equals("kit"))
                            return Suggestions.players();
                    }
                    if(ctx.argSize() == 3){
                        return Suggestions.of("1", "2", "3", "4", "5", "6", "7");
                    }
                    return Suggestions.empty();
                })
                .executes(ctx -> {
                    if(ctx.argSize() == 3){
                        if(Objects.requireNonNull(ctx.rawArgAt(0)).equals("kit")){
                            String targetUsername = Objects.requireNonNull(ctx.rawArgAt(1));
                            if(Bukkit.getPlayer(targetUsername) == null) {
                                ctx.player().sendRichMessage("<gray>Loading offline player's kit from the database...");
                                long startTime = System.currentTimeMillis();
                                Schedulers.async().execute(task -> {
                                    try {
                                        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + targetUsername);
                                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                                        connection.setRequestMethod("GET");
                                        connection.connect();

                                        int response = connection.getResponseCode();
                                        if (response != 200) {
                                            Bukkit.getConsoleSender().sendRichMessage("<player> tried to get an enderchest from an invalid player (Response-Code: <code> | Target Player <target>)"
                                                    .replace("<player>", ctx.player().getName())
                                                    .replace("<code>", String.valueOf(response))
                                                    .replace("<target>", Objects.requireNonNull(ctx.rawArgAt(1)))
                                            );
                                            ctx.player().sendRichMessage("<red>Invalid username!");
                                            throw new IOException("User not found");
                                        }
                                        StringBuilder inlineBuilder = new StringBuilder();
                                        Scanner scanner = new Scanner(url.openStream());
                                        while (scanner.hasNext()) {
                                            inlineBuilder.append(scanner.nextLine());
                                        }
                                        scanner.close();
                                        JsonObject profileData = (JsonObject) JsonParser.parseString(inlineBuilder.toString());
                                        uuidString = profileData.get("id").getAsString();
                                        StringBuilder formattedUuid = new StringBuilder(uuidString)
                                                .insert(20, '-')
                                                .insert(16, '-')
                                                .insert(12, '-')
                                                .insert(8, '-');
                                        uuid = UUID.fromString(formattedUuid.toString());

                                        if (isInt(ctx.rawArgAt(2))) {
                                            if (Integer.parseInt(Objects.requireNonNull(ctx.rawArgAt(2))) < 1 || Integer.parseInt(Objects.requireNonNull(ctx.rawArgAt(2))) > 7) {
                                                ctx.player().sendRichMessage("<red>Usage: /viewkit <player> <number>");
                                                return;
                                            }
                                            Inventory inventory = this.previewInventory(TextStyle.style("<gold>Kit Preview"));
                                            AithonKits.getInstance().getConfigUtil().load(uuid, ctx.rawArgAt(2)).thenAccept(map -> {
                                                for (int slot = 0; slot < 41; ++slot) {
                                                    inventory.setItem(slot, map.get(slot));
                                                }
                                            });

                                            Schedulers.sync().execute(() -> {
                                                long endTime = System.currentTimeMillis();
                                                long duration = endTime - startTime;
                                                ctx.player().sendRichMessage("<green>Successfully loaded the kit from the database! Took " + duration + "ms.");
                                                ctx.player().openInventory(inventory);
                                                ctx.player().playSound(ctx.player().getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0f, 5.0f);
                                            });
                                        }
                                    } catch (IOException e) {
                                        // empty catch block
                                    }
                                });
                                return CommandResult.success();
                            }
                            Player target = Bukkit.getPlayer(targetUsername);
                            Schedulers.async().execute(() -> {
                                if (isInt(ctx.rawArgAt(2))) {
                                    if (Integer.parseInt(Objects.requireNonNull(ctx.rawArgAt(2))) < 1 || Integer.parseInt(Objects.requireNonNull(ctx.rawArgAt(2))) > 7) {
                                        ctx.player().sendRichMessage("<red>Usage: /viewkit <player> <number>");
                                        return;
                                    }
                                    Inventory inventory = this.previewInventory(TextStyle.style("<gold>Enderchest Preview"));
                                    assert target != null;
                                    AithonKits.getInstance().getConfigUtil().load(uuid, ctx.rawArgAt(2)).thenAccept(map -> {
                                        for (int slot = 0; slot < 41; ++slot) {
                                            inventory.setItem(slot, map.get(slot));
                                        }
                                    });
                                    Schedulers.sync().execute(() -> {
                                        ctx.player().openInventory(inventory);
                                        ctx.player().playSound(ctx.player().getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0f, 5.0f);
                                    });
                                }
                            });

                        }
                    }
                        if(Objects.requireNonNull(ctx.rawArgAt(0)).equals("enderchest")){
                            String targetUsername = Objects.requireNonNull(ctx.rawArgAt(1));
                            if(Bukkit.getPlayer(targetUsername) == null) {
                                ctx.player().sendRichMessage("<gray>Loading offline player's enderchest from the database...");
                                long startTime = System.currentTimeMillis();
                                Schedulers.async().execute(task -> {
                                    try {
                                        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + targetUsername);
                                        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                                        connection.setRequestMethod("GET");
                                        connection.connect();

                                        int response = connection.getResponseCode();
                                        if (response != 200) {
                                            Bukkit.getConsoleSender().sendRichMessage("<player> tried to get an enderchest from an invalid player (Response-Code: <code> | Target Player <target>)"
                                                    .replace("<player>", ctx.player().getName())
                                                    .replace("<code>", String.valueOf(response))
                                                    .replace("<target>", Objects.requireNonNull(ctx.rawArgAt(1)))
                                            );
                                            ctx.player().sendRichMessage("<red>Invalid username!");
                                            throw new IOException("User not found");
                                        }
                                        StringBuilder inlineBuilder = new StringBuilder();
                                        Scanner scanner = new Scanner(url.openStream());
                                        while (scanner.hasNext()) {
                                            inlineBuilder.append(scanner.nextLine());
                                        }
                                        scanner.close();
                                        JsonObject profileData = (JsonObject) JsonParser.parseString(inlineBuilder.toString());
                                        uuidString = profileData.get("id").getAsString();
                                        StringBuilder formattedUuid = new StringBuilder(uuidString)
                                                .insert(20, '-')
                                                .insert(16, '-')
                                                .insert(12, '-')
                                                .insert(8, '-');
                                        uuid = UUID.fromString(formattedUuid.toString());

                                        if (isInt(ctx.rawArgAt(2))) {
                                            if (Integer.parseInt(Objects.requireNonNull(ctx.rawArgAt(2))) < 1 || Integer.parseInt(Objects.requireNonNull(ctx.rawArgAt(2))) > 7) {
                                                ctx.player().sendRichMessage("<red>Usage: /viewkit <player> <number>");
                                                return;
                                            }
                                            Inventory inventory = this.previewInventory2(TextStyle.style("<gold>Enderchest Preview"));
                                            AithonKits.getInstance().getConfigUtil().loadEnderChest(uuid, ctx.rawArgAt(2)).thenAccept(map -> {
                                                for (int slot = 0; slot < 27; ++slot) {
                                                    inventory.setItem(slot, map.get(slot));
                                                }
                                            });
                                            Schedulers.sync().execute(() -> {
                                                long endTime = System.currentTimeMillis();
                                                long duration = endTime - startTime;
                                                ctx.player().sendRichMessage("<green>Successfully loaded the enderchest from the database! Took " + duration + "ms.");
                                                ctx.player().openInventory(inventory);
                                                ctx.player().playSound(ctx.player().getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0f, 5.0f);
                                            });
                                        }
                                    } catch (IOException e) {
                                        // empty catch block
                                    }
                                });
                                return CommandResult.success();
                            }
                            Player target = Bukkit.getPlayer(targetUsername);
                            Schedulers.async().execute(() -> {
                                if (isInt(ctx.rawArgAt(2))) {
                                    if (Integer.parseInt(Objects.requireNonNull(ctx.rawArgAt(2))) < 1 || Integer.parseInt(Objects.requireNonNull(ctx.rawArgAt(2))) > 7) {
                                        ctx.player().sendRichMessage("<red>Usage: /viewkit <player> <number>");
                                        return;
                                    }
                                    Inventory inventory = this.previewInventory2(TextStyle.style("<gold>Enderchest Preview"));
                                    assert target != null;
                                    AithonKits.getInstance().getConfigUtil().loadEnderChest(uuid, ctx.rawArgAt(2)).thenAccept(map -> {
                                        for (int slot = 0; slot < 27; ++slot) {
                                            inventory.setItem(slot, map.get(slot));
                                        }
                                    });
                                    Schedulers.sync().execute(() -> {
                                        ctx.player().openInventory(inventory);
                                        ctx.player().playSound(ctx.player().getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0f, 5.0f);
                                    });
                                }
                            });

                    }
                    return CommandResult.success();
                }).register();
    }

    public Inventory previewInventory(Component name){
        Inventory inventory = Bukkit.createInventory(null, 45, name);
        inventory.setItem(41, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        inventory.setItem(42, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        inventory.setItem(43, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        inventory.setItem(44, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        return inventory;
    }
    public Inventory previewInventory2(Component name){
        Inventory inventory = Bukkit.createInventory(null, 36, name);
        for(int x = 27; x < 36; x++){
            inventory.setItem(x, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        }
        return inventory;
    }
    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static class clickListener implements Listener {
        @EventHandler
        public void onInvClick(InventoryClickEvent event){
            if(event.getView().title().equals(TextStyle.color("<gold>Kit Preview"))) {
                event.setCancelled(true);
            }
            if(event.getView().title().equals(TextStyle.color("<gold>Enderchest Preview"))) {
                event.setCancelled(true);
            }
        }
    }
}
