package club.revived;

import java.io.*;
import java.lang.reflect.Member;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import club.revived.command.*;
import club.revived.command.kits.*;
import club.revived.config.ConfigHandler;
import club.revived.runables.Broadcast;
import club.revived.util.ConfigUtil;
import club.revived.util.KitLoading;
import club.revived.util.MessageUtil;
import dev.manere.utils.config.Config;
import dev.manere.utils.library.wrapper.PluginWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class WeirdoKits extends PluginWrapper implements Listener {


    public Map<UUID, Integer> LastUsedKit = new HashMap<>();

    private static WeirdoKits instance;
    public File messages;
    public File data;
    public File sounds;

    private ConfigUtil configUtil;
    private KitLoading loading;
    private Broadcast brc;

    @Override
    protected void start() {
        Config.init();
        instance = this;
        this.loading = new KitLoading();
        this.configUtil = new ConfigUtil();
        new KitCommand("k");
        new KitCommand("kit");
        new KitCommand("kits");
        new Kit1Command("k1");
        new Kit1Command("kit1");
        new Kit2Command("k2");
        new Kit2Command("kit2");
        new Kit3Command("k3");
        new Kit3Command("kit3");
        new Kit4Command("k4");
        new Kit4Command("kit4");
        new Kit5Command("k5");
        new Kit5Command("kit5");
        new Kit6Command("k6");
        new Kit6Command("kit6");
        new Kit7Command("k7");
        new Kit7Command("kit7");
        new EcCommand();
        new Broadcast(this);

        Bukkit.getPluginManager().registerEvents(this, this);

        this.data = new File("data.yml");
        if (!this.data.exists()) {
            saveResource("data.yml", false);
        }
        this.sounds = new File("sounds.yml");
        if (!this.sounds.exists()) {
            saveResource("sounds.yml", false);
        }
        this.messages = new File("messages.yml");
        if (!this.messages.exists()) {
            saveResource("messages.yml", false);
        }
    }

    public static WeirdoKits getInstance() {
        return instance;
    }

    public Broadcast getBrc() {
        return brc;
    }

    public ConfigUtil getConfigUtil() {
        return this.configUtil;
    }

    public KitLoading getKitLoader() {
        return loading;
    }
}
