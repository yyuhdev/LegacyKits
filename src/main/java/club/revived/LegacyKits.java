package club.revived;

import club.revived.cache.EnderchestCache;
import club.revived.cache.KitCache;
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
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class LegacyKits extends JavaPlugin implements Listener {
    public final Map<UUID, Integer> lastUsedKits = new HashMap<>();

    @Getter
    public static LegacyKits instance;
    @Getter
    public static List<UUID> renamingPlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
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

//        Files.save("kitroom/<name>.yml"
//                .replaceAll("<name>", name)
//        ));

        for (int x = 1; x <= 18; x++) {
            registerCommand("ec" + x, new EnderchestKit(x));
            registerCommand("kit" + x, new KitLoad(x));
            registerCommand("k" + x, new KitLoad(x));
        }
        Kit kit = new Kit();
        registerCommand("kit", kit, kit);
        registerCommand("k", kit, kit);
        registerCommand("kits", kit, kit);

        InventoryManager.register(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        DatabaseManager.getInstance();
        for(Player player : Bukkit.getOnlinePlayers()){
            loadPlayerData(player.getUniqueId());
        }
    }

    @Override
    public void onDisable() {
        DatabaseManager.getInstance().shutdown();
    }

    public static void log(String s) {
        getInstance().getComponentLogger().info(s);
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

    private void registerCommand(String name, CommandExecutor executor) {
        try {
            Field commandMapField = getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(getServer());
            BukkitCommand command = new BukkitCommand(name) {
                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
                    if (!getPlugin(LegacyKits.class).isEnabled()) {
                        return false;
                    }
                    return executor.onCommand(sender, this, commandLabel, args);
                }
            };
            commandMap.register("kits", command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerCommand(String name, CommandExecutor executor, TabCompleter completer) {
        try {
            Field commandMapField = getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(getServer());
            BukkitCommand command = new BukkitCommand(name) {
                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
                    if (!getPlugin(LegacyKits.class).isEnabled()) {
                        return false;
                    }
                    return executor.onCommand(sender, this, commandLabel, args);
                }
                @Override
                public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) throws IllegalArgumentException {
                    if (completer != null) {
                        return Objects.requireNonNull(completer.onTabComplete(sender, this, alias, args));
                    }
                    return super.tabComplete(sender, alias, args);
                }
            };
            commandMap.register("kits", command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
