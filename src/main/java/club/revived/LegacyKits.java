package club.revived;

import club.revived.cache.EnderchestCache;
import club.revived.cache.KitCache;
import club.revived.cache.KitRoomCache;
import club.revived.cache.SettingsCache;
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
import java.util.*;
import java.util.stream.Stream;

public class LegacyKits extends JavaPlugin implements Listener {
    @Getter public static LegacyKits instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        setupFiles();
        loadCommands();
        InventoryManager.register(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        DatabaseManager.getInstance();
        KitRoomCache.update();
    }

    @Override
    public void onDisable() {
        DatabaseManager.getInstance().shutdown();
    }

    public static void log(String s) {
        getInstance().getComponentLogger().info(s);
    }
    public void setupFiles(){
        Stream.of(
                "messages",
                "sql"
        ).forEach(name -> {
            File file = new File(getDataFolder(), "<name>.yml"
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
            File file = new File(getDataFolder(), "kitroom/<name>.yml"
                    .replace("<name>", name)
            );
            if (!file.exists()) {
                saveResource(file.getPath(), false);
            }
        });
    }
    public void loadCommands(){
        for (int x = 1; x <= 18; x++) {
            CommandUtil.registerCommand("ec" + x, new EnderchestKit(x));
            CommandUtil.registerCommand("kit" + x, new KitLoad(x));
            CommandUtil.registerCommand("k" + x, new KitLoad(x));
        }
        Kit kit = new Kit();
        CommandUtil.registerCommand("kit", kit, kit);
        CommandUtil.registerCommand("k", kit, kit);
        CommandUtil.registerCommand("kits", kit, kit);
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
                        SettingsCache.setSettings(uuid, new Settings(uuid, false, 1));
                        return;
                    }
                    settings.ifPresent(holder -> SettingsCache.setSettings(uuid, holder));
                });
    }
}
