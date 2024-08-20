package club.revived;

import club.revived.cache.EnderchestCache;
import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.command.*;
import club.revived.command.admin.KitAdmin;
import club.revived.config.Files;
import club.revived.framework.inventory.InventoryManager;
import club.revived.listener.PlayerListener;
import club.revived.objects.enderchest.EnderchestHolder;
import club.revived.objects.kit.KitHolder;
import club.revived.objects.settings.Settings;
import club.revived.storage.DatabaseManager;
import dev.manere.utils.library.wrapper.PluginWrapper;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.stream.Stream;

public class LegacyKits extends PluginWrapper implements Listener {
    public final Map<UUID, Integer> lastUsedKits = new HashMap<>();

    @Getter
    public static LegacyKits instance;
    @Getter
    public static List<UUID> renamingPlayers = new ArrayList<>();

    @Override
    protected void start() {
        instance = this;
        saveDefaultConfig();
        Stream.of(
                "messages",
                "sql"
        ).forEach(name -> Files.save("<name>.yml"
                .replaceAll("<name>", name)
        ));
        Stream.of(
                "armory",
                "diamond_crystal",
                "misc",
                "arrows",
                "special_items",
                "netherite_crystal",
                "potions"
        ).forEach(name -> Files.save("kitroom/<name>.yml"
                .replaceAll("<name>", name)
        ));

        InventoryManager.register(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        DatabaseManager.getInstance();
        new Kit();
        new KitAdmin();
        new KitClaim();
        new Clear();
        new Claim();
        new EnderchestKit();
    }

    @Override
    protected void stop(){
        DatabaseManager.getInstance().shutdown();
    }

    public static void log(String s) {
        getInstance().getComponentLogger().info(s);
    }

    public void loadPlayerData(UUID uuid) {
        Bukkit.broadcastMessage("Loadedggggggg eeeee");
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.broadcastMessage("Loaded eeeee");
            DatabaseManager.getInstance().get(EnderchestHolder.class, uuid)
                    .thenAccept(enderchestHolder -> {
                        Bukkit.broadcastMessage("Loaded eeeee");
                        if (enderchestHolder.isEmpty()) {
                            Bukkit.broadcastMessage("Loaded ec");
                            EnderchestCache.update(uuid, EnderchestHolder.newEmpty(uuid));
                            return;
                        }
                        Bukkit.broadcastMessage("Loaded ec1 with " + enderchestHolder.get().getList().size() + " items");
                        enderchestHolder.ifPresent(enderchestHolder1 -> EnderchestCache.update(uuid, enderchestHolder1));
                    });
        },1L);
        DatabaseManager.getInstance().get(KitHolder.class, uuid)
                .thenAccept(kitHolder -> {
                    if (kitHolder.isEmpty()) {
                        Bukkit.broadcastMessage("Loaded kits");
                        KitCache.update(uuid, KitHolder.newEmpty(uuid));
                        return;
                    }
                    kitHolder.ifPresent(holder -> {
                        KitCache.update(uuid, holder);
                        Bukkit.broadcastMessage("Updated kits");
                    });
                });
        DatabaseManager.getInstance().get(Settings.class, uuid)
                .thenAccept(settings -> {
                    if (settings.isEmpty()) {
                        SettingsCache.setSettings(uuid, new Settings(uuid, false, 1));
                        return;
                    }
                    settings.ifPresent(holder -> SettingsCache.setSettings(uuid, holder));
                });
    }
}
