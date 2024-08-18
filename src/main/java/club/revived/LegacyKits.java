package club.revived;

import club.revived.cache.KitCache;
import club.revived.cache.SettingsCache;
import club.revived.command.*;
import club.revived.command.admin.KitAdmin;
import club.revived.config.Files;
import club.revived.framework.inventory.InventoryManager;
import club.revived.listener.PlayerListener;
import club.revived.objects.KitHolder;
import club.revived.objects.Settings;
import club.revived.storage.DatabaseManager;
import dev.manere.utils.library.wrapper.PluginWrapper;
import lombok.Getter;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public class LegacyKits extends PluginWrapper implements Listener {
    public final Map<UUID, Integer> lastUsedKits = new HashMap<>();
    public static HashMap<UUID, Integer> autokitKit = new HashMap<>();
    public ArrayList<UUID> autoKitUsers = new ArrayList<>();

    @Getter
    public static LegacyKits instance;

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

    public static void log(String s) {
        getInstance().getComponentLogger().info(s);
    }

    public void loadPlayerData(UUID uuid) {
        DatabaseManager.getInstance().get(KitHolder.class, uuid)
                .thenAccept(kitHolder -> {
                    kitHolder.ifPresent(holder -> KitCache.update(uuid, holder));
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
