package club.revived;

import club.revived.command.*;
import club.revived.command.admin.KitAdmin;
import club.revived.config.Files;
import club.revived.framework.inventory.InventoryManager;
import club.revived.listener.RespawnListener;
import club.revived.util.SqlConfig;
import club.revived.util.sql.SqlDataManager;
import dev.manere.utils.elements.Elements;
import dev.manere.utils.library.wrapper.PluginWrapper;
import lombok.Getter;
import org.bukkit.event.Listener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LegacyKits extends PluginWrapper implements Listener {
    public final Map<UUID, Integer> lastUsedKits = new HashMap<>();
    public static HashMap<UUID, Long> cooldowns = new HashMap<>();
    public ArrayList<UUID> autoKitUsers = new ArrayList<>();

    @Getter
    public static LegacyKits instance;
    @Getter
    public static SqlDataManager sql;

    @Override
    protected void start() {
        instance = this;
        saveDefaultConfig();
        Elements.of(
                "messages",
                "sql"
        ).forEach(name -> Files.save("<name>.yml"
                .replaceAll("<name>", name)
        ));
        Elements.of(
                "armory",
                "diamond",
                "misc",
                "netherite",
                "potions"
        ).forEach(name -> Files.save("kitroom/<name>.yml"
                .replaceAll("<name>", name)
        ));

        sql = new SqlDataManager(
                SqlConfig.getString("host"),
                SqlConfig.getString("database"),
                SqlConfig.getString("username"),
                SqlConfig.getString("password"),
                SqlConfig.getInt("port")
        );
        InventoryManager.register(this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);

        new Kit();
        new KitAdmin();
        new KitClaim();
        new Clear();
        new Claim();
        new EnderchestKit();
    }

    @Override
    protected void stop(){
        sql.close();
    }
}
