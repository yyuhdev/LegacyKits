package club.revived;

import java.io.File;
import java.util.*;

import club.revived.command.*;
import club.revived.config.Files;
import club.revived.debugging.DebugCommand;
import club.revived.listener.RespawnListener;
import club.revived.miscellaneous.Itemlist.MenuOpeningReason;
import club.revived.requests.ShareKitImpl;
import club.revived.util.ConfigUtil;
import club.revived.util.KitLoading;
import club.revived.util.PublicKit;
import dev.manere.utils.config.Config;
import dev.manere.utils.elements.Elements;
import dev.manere.utils.library.wrapper.PluginWrapper;
import org.bukkit.event.Listener;

public class AithonKits extends PluginWrapper implements Listener {
    private Map<UUID, Integer> lastUsedKits = new HashMap<>();
    public static HashMap<UUID, Long> cooldowns = new HashMap<>();
    public ArrayList<UUID> autoKitUsers = new ArrayList<>();
    public MenuOpeningReason reason;
    public ArrayList<PublicKit> publicKits = new ArrayList<>();

    private static AithonKits instance;

    private ConfigUtil configUtil;
    private ShareKitImpl shareKit;
    private KitLoading loading;

    @Override
    protected void start() {
        Config.init();
        instance = this;
        this.loading = new KitLoading();
        this.configUtil = new ConfigUtil();
        this.shareKit = new ShareKitImpl();
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);


        Elements.of(
                "messages",
                "sounds",
                "data"
        ).forEach(name -> Files.save("<name>.yml"
            .replaceAll("<name>", name)
        ));

        new ItemListCommand();
        new KitCommand("k");
        new KitCommand("kit");
        new KitCommand("kits");
        new ViewKitCommand();
        new KitAdminCommand();
        new DebugCommand();
        new KitClearCommand();
        new AutokitCommand();
        new KitClaimCommand();
        new EcCommand();
    }

    public static AithonKits getInstance() {
        return instance;
    }

    public ConfigUtil getConfigUtil() {
        return this.configUtil;
    }

    public ShareKitImpl shareKit(){
        return this.shareKit;
    }

    public KitLoading getKitLoader() {
        return loading;
    }


    public Map<UUID, Integer> lastUsedKits() {
        return lastUsedKits;
    }
}
