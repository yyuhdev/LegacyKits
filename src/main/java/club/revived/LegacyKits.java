package club.revived;

import club.revived.cache.*;
import club.revived.command.EnderchestKit;
import club.revived.command.Kit;
import club.revived.command.KitLoad;
import club.revived.framework.inventory.InventoryManager;
import club.revived.listener.PlayerListener;
import club.revived.objects.enderchest.EnderchestHolder;
import club.revived.objects.kit.KitHolder;
import club.revived.objects.settings.Settings;
import club.revived.storage.DatabaseManager;
import club.revived.util.CommandUtil;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

public class LegacyKits extends JavaPlugin implements Listener {
    @Getter public static LegacyKits instance;
    @Getter public static Map<UUID, Integer> lastUsedKit = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        setupFiles();
        loadCommands();
        InventoryManager.register(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        DatabaseManager.getInstance();
        KitRoomCache.update();
        PremadeKitCache.preloadCache("evaluation", "drain");
    }

    @Override
    public void onDisable() {
        DatabaseManager.getInstance().shutdown();
    }

    public static void log(String s) {
        getInstance().getComponentLogger().info(s);
    }
    public void setupFiles(){
        log("Loading and setting up files...");
        Stream.of(
                "messages",
                "sql"
        ).forEach(name -> {
            File file = new File("<name>.yml"
                    .replace("<name>", name)
            );
            if (!file.exists()) {
                saveResource(file.getPath(), false);
            }
        });

        Stream.of(
                "armory",
                "diamond_crystal",
                "misc",
                "arrows",
                "special_items",
                "netherite_crystal",
                "potions"
        ).forEach(name -> {
            File file = new File("kitroom/<name>.yml"
                    .replace("<name>", name)
            );
            if (!file.exists()) {
                saveResource(file.getPath(), false);
            }
        });
        log("Files have been loaded!");
    }
    public void loadCommands(){
        for (int x = 1; x <= 16; x++) {
            CommandUtil.registerCommand("ec" + x, new EnderchestKit(x));
            CommandUtil.registerCommand("kit" + x, new KitLoad(x));
            CommandUtil.registerCommand("k" + x, new KitLoad(x));
        }
        Kit kit = new Kit();
        CommandUtil.registerCommand("kit", kit, kit);
        CommandUtil.registerCommand("k", kit, kit);
        CommandUtil.registerCommand("kits", kit, kit);
        log("Registered commands!");
    }
    public void loadPlayerData(UUID uuid) {
        DatabaseManager.getInstance().get(EnderchestHolder.class, uuid)
                .thenAccept(enderchestHolder -> {
                    if (enderchestHolder.isEmpty()) {
                        EnderchestCache.update(uuid, EnderchestHolder.newEmpty(uuid));
                        return;
                    }
                    enderchestHolder.ifPresent(enderchestHolder1 -> EnderchestCache.update(uuid, enderchestHolder1));
                });
        DatabaseManager.getInstance().get(KitHolder.class, uuid)
                .thenAccept(kitHolder -> {
                    if (kitHolder.isEmpty()) {
                        KitCache.update(uuid, KitHolder.newEmpty(uuid));
                        return;
                    }
                    kitHolder.ifPresent(holder -> KitCache.update(uuid, holder));
                });
        DatabaseManager.getInstance().get(Settings.class, uuid)
                .thenAccept(settings -> {
                    if (settings.isEmpty()) {
                        SettingsCache.setSettings(uuid, new Settings(uuid, false, 1, true));
                        return;
                    }
                    settings.ifPresent(holder -> SettingsCache.setSettings(uuid, holder));
                });
    }
}
