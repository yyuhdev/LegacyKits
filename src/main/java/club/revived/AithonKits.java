package club.revived;

import club.revived.command.*;
import club.revived.command.admin.KitAdmin;
import club.revived.config.Files;
import club.revived.framework.inventory.InventoryManager;
import club.revived.listener.RespawnListener;
import club.revived.util.ConfigUtil;
import club.revived.util.KitLoading;
import dev.manere.utils.elements.Elements;
import dev.manere.utils.library.wrapper.PluginWrapper;
import lombok.Getter;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AithonKits extends PluginWrapper implements Listener {
    public final Map<UUID, Integer> lastUsedKits = new HashMap<>();
    public static HashMap<UUID, Long> cooldowns = new HashMap<>();
    public ArrayList<UUID> autoKitUsers = new ArrayList<>();

    @Getter
    public static AithonKits instance;
    @Getter
    public ConfigUtil configUtil;
    @Getter
    public KitLoading loading;

    @Override
    protected void start() {
        instance = this;
        InventoryManager.register(this);
        this.loading = new KitLoading();
        this.configUtil = new ConfigUtil();
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);


        Elements.of(
                "messages",
                "config",
                "sounds"
        ).forEach(name -> Files.save("<name>.yml"
            .replaceAll("<name>", name)
        ));

        new Kit("k");
        new Kit("kit");
        new Kit("kits");
        new ViewKit();
        new KitAdmin();
        new Search();
        new KitClear();
        new Autokit();
        new KitClaim();
        new Clear();
        new Search();
        new Claim();
        new EnderchestKit();
    }
}
