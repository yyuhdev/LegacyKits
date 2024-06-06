package club.revived;

import java.util.*;

import club.revived.command.*;
import club.revived.config.Files;
import club.revived.runables.Broadcast;
import club.revived.util.ConfigUtil;
import club.revived.util.KitLoading;
import dev.manere.utils.config.Config;
import dev.manere.utils.elements.Elements;
import dev.manere.utils.library.wrapper.PluginWrapper;
import org.bukkit.event.Listener;

public class AithonKits extends PluginWrapper implements Listener {
    private Map<UUID, Integer> lastUsedKits = new HashMap<>();

    private static AithonKits instance;

    private ConfigUtil configUtil;
    private KitLoading loading;
    private Broadcast broadcast;

    @Override
    protected void start() {
        Config.init();
        instance = this;
        this.loading = new KitLoading();
        this.configUtil = new ConfigUtil();

        Elements.of("messages", "sounds", "data").forEach(name -> Files.save("<name>.yml"
            .replaceAll("<name>", name)
        ));

        this.broadcast = new Broadcast();
        this.broadcast.startTask();
        new KitCommand("k");
        new KitCommand("kit");
        new KitCommand("kits");
        new KitClaimCommand();
        new EcCommand();
    }

    public static AithonKits getInstance() {
        return instance;
    }

    public ConfigUtil getConfigUtil() {
        return this.configUtil;
    }

    public KitLoading getKitLoader() {
        return loading;
    }

    public  Broadcast getBroadcast(){
        return broadcast;
    }

    public Map<UUID, Integer> lastUsedKits() {
        return lastUsedKits;
    }
}
