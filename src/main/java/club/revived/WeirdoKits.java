package club.revived;

import java.io.*;
import java.util.*;

import club.revived.command.*;
import club.revived.command.kits.*;
import club.revived.listener.DeathListener;
import club.revived.listener.Eat;
import club.revived.util.ConfigUtil;
import club.revived.util.KitLoading;
import dev.manere.utils.library.wrapper.PluginWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WeirdoKits extends PluginWrapper implements Listener {

    private final File autokitfile;
    private final File broadcastfile;
    private final File deathmessagefile;

    public ArrayList<UUID> autokit = new ArrayList<>();
    public ArrayList<UUID> broadcast = new ArrayList<>();
    public ArrayList<UUID> deathmessages = new ArrayList<>();

    public Map<UUID, Integer> LastUsedKit = new HashMap<>();

    private static WeirdoKits instance;

    private ConfigUtil configUtil;
    private KitLoading loading;

    public WeirdoKits() {
        autokitfile = new File(this.getDataFolder(), "autokit.yml");
        if (!autokitfile.exists()) {
            try {
                autokitfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        broadcastfile = new File(this.getDataFolder(), "broadcast.yml");
        if (!broadcastfile.exists()) {
            try {
                broadcastfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        deathmessagefile = new File(this.getDataFolder(), "deathmessage.yml");
        if (!deathmessagefile.exists()) {
            try {
                deathmessagefile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loadData();
    }

    @Override
    protected void start() {
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

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new Eat(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
    }

    @EventHandler
    public void stop() {
        saveData();
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

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(autokitfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UUID uuid = UUID.fromString(line.trim());
                autokit.add(uuid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(broadcastfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UUID uuid = UUID.fromString(line.trim());
                broadcast.add(uuid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(broadcastfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UUID uuid = UUID.fromString(line.trim());
                broadcast.add(uuid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(autokitfile))) {
            for (UUID uuid : autokit) {
                writer.write(uuid.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(deathmessagefile))) {
            for (UUID uuid : deathmessages) {
                writer.write(uuid.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(broadcastfile))) {
            for (UUID uuid : broadcast) {
                writer.write(uuid.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
