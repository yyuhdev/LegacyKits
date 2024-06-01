package club.revived;

import java.util.*;

import club.revived.command.*;
import club.revived.command.kits.*;
import club.revived.config.Files;
import club.revived.runables.Broadcast;
import club.revived.util.ConfigUtil;
import club.revived.util.KitLoading;
import dev.manere.utils.config.Config;
import dev.manere.utils.elements.Elements;
import dev.manere.utils.library.wrapper.PluginWrapper;
import org.bukkit.event.Listener;

public class WeirdoKits extends PluginWrapper implements Listener {
    private Map<UUID, Integer> lastUsedKits = new HashMap<>();

    private static WeirdoKits instance;

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
        Broadcast.startTask();

        Elements.of("data", "sounds", "messages").forEach(name -> Files.save("<name>.yml"
            .replaceAll("<name>", name)
        ));
    }

    public static WeirdoKits getInstance() {
        return instance;
    }

    public ConfigUtil getConfigUtil() {
        return this.configUtil;
    }

    public KitLoading getKitLoader() {
        return loading;
    }

    public Map<UUID, Integer> lastUsedKits() {
        return lastUsedKits;
    }
}
