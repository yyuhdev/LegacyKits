package club.revived;

import java.util.*;

import club.revived.command.*;
import club.revived.config.Files;
import club.revived.debugging.DebugCommand;
import club.revived.listener.RespawnListener;
import club.revived.menus.MenuOpeningReason;
import club.revived.util.ConfigUtil;
import club.revived.util.KitLoading;
import club.revived.util.PublicKit;
import dev.manere.utils.elements.Elements;
import dev.manere.utils.library.wrapper.PluginWrapper;
import org.bukkit.event.Listener;

public class AithonKits extends PluginWrapper implements Listener {
    private final Map<UUID, Integer> lastUsedKits = new HashMap<>();
    public static HashMap<UUID, Long> cooldowns = new HashMap<>();
    public ArrayList<UUID> autoKitUsers = new ArrayList<>();
    public MenuOpeningReason reason;
    public ArrayList<PublicKit> publicKits = new ArrayList<>();

    private static AithonKits instance;

    private ConfigUtil configUtil;
    private KitLoading loading;

    @Override
    protected void start() {
        instance = this;
        this.loading = new KitLoading();
        this.configUtil = new ConfigUtil();
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);


        Elements.of(
                "messages",
                "sounds"
        ).forEach(name -> Files.save("<name>.yml"
            .replaceAll("<name>", name)
        ));

        new KitCommand("k");
        new KitCommand("kit");
        new KitCommand("kits");
        new ViewKitCommand();
        new KitAdminCommand();
        new DebugCommand();
        new KitClearCommand();
        new AutokitCommand();
        new KitClaimCommand();
        new ClearCommand();
        new ClearEcCommand();
        new ClaimCommand();
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


    public Map<UUID, Integer> lastUsedKits() {
        return lastUsedKits;
    }
}
