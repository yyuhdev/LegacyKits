package club.revived.config;

import club.revived.LegacyKits;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MessageHandler {

    @NotNull
    public static FileConfiguration config() {
        return YamlConfiguration.loadConfiguration(new File(LegacyKits.getInstance().getDataFolder(), "messages.yml"));
    }


    public static String of(String s){
        return config().getString(s);
    }
}
