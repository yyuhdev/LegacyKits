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

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class KitClear {

    Inventory inventory;
    HashMap<Player, UUID> toConfirm = new HashMap<>();
    String uuidString = "";
    UUID uuid;

    public KitClear(){
        AithonKits.getInstance().getServer().getPluginManager().registerEvents(new clickListener(), AithonKits.getInstance());
        Commands.command("clearkits")
                .permission("club.revived.admin")
                .completes(ctx -> {
                    if(ctx.argSize() == 1){
                        return Suggestions.players();
                    }
                    return Suggestions.empty();
                })
                .executes(ctx -> {
                    if(ctx.argSize() == 1) {
                        String targetUsername = Objects.requireNonNull(ctx.rawArgAt(0));
                        if (Bukkit.getPlayer(targetUsername) == null) {
                            ctx.player().sendRichMessage("<gray>Fetching uuid...");
                            long startTime = System.currentTimeMillis();
                            Schedulers.async().execute(task -> {
                                try {
                                    URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + targetUsername);
                                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                                    connection.setRequestMethod("GET");
                                    connection.connect();

                                    int response = connection.getResponseCode();
                                    if (response != 200) {
                                        Bukkit.getConsoleSender().sendRichMessage("<player> tried to delete the kits and enderchests from an invalid player (Response-Code: <code> | Target Player <target>)"
                                                .replace("<player>", ctx.player().getName())
                                                .replace("<code>", String.valueOf(response))
                                                .replace("<target>", Objects.requireNonNull(ctx.rawArgAt(0)))
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

                                    inventory = confirmationMenu();

                                    Schedulers.sync().execute(() -> {
                                        long endTime = System.currentTimeMillis();
                                        long duration = endTime - startTime;
                                        ctx.player().sendRichMessage("<green>Successfully fetched your targets uuid! Took " + duration + "ms");
                                        ctx.player().openInventory(inventory);
                                        ctx.player().playSound(ctx.player(), Sound.ENTITY_CHICKEN_EGG,5,5);
                                        toConfirm.put(ctx.player(), uuid);
                                    });
                                } catch (IOException e) {
                                    // empty catch block
                                }
                            });
                            return CommandResult.success();
                        }
                        Player target = Bukkit.getPlayer(targetUsername);
                        assert target != null;
                        toConfirm.put(ctx.player(), target.getUniqueId());
                        inventory = confirmationMenu();
                        ctx.player().openInventory(inventory);
                        ctx.player().playSound(ctx.player(), Sound.ENTITY_CHICKEN_EGG,5,5);
                    }


                    return CommandResult.success();
                }).register();
    }

    public Inventory confirmationMenu(){
        Inventory toReturn = Bukkit.createInventory(null, 27, Component.text("Confirmation Menu"));
        toReturn.setItem(12, ItemBuilder.item(Material.GREEN_CANDLE).name(TextStyle.style("<green>Confirm")).build());
        toReturn.setItem(14, ItemBuilder.item(Material.RED_CANDLE).name(TextStyle.style("<red>Cancel")).build());
        return toReturn;
    }

    public class clickListener implements Listener{
        @EventHandler
        public void onInventoryClick(InventoryClickEvent event){
            event.getInventory();
            if(event.getInventory() != inventory){
                return;
            }
            if(event.getCurrentItem() == null){
                return;
            }
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if(toConfirm.containsKey(player)){
                if(event.getCurrentItem().getType() == Material.GREEN_CANDLE){
                    long startTime = System.currentTimeMillis();
                    UUID uuidToClear = toConfirm.remove(player);
                    AithonKits.getInstance().getConfigUtil().clear(uuidToClear);
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    player.sendRichMessage("<green>Successfully deleted <uuid>'s kits! Took <duration> ms"
                            .replace("<uuid>", uuidToClear.toString())
                            .replace("<duration>", String.valueOf(duration))
                    );
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP,1,2);
                    player.closeInventory();
                }
                if(event.getCurrentItem().getType() == Material.RED_CANDLE){
                    toConfirm.remove(player);
                    player.closeInventory();
                }
                return;
            }
            player.sendRichMessage("<red>I dont fucking know how the nigger fuck you ended in here");

        }
    }
}
