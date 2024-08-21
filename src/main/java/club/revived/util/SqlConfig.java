package club.revived.util;

import club.revived.LegacyKits;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SqlConfig {

    @NotNull
    public static FileConfiguration configuration() {
        return YamlConfiguration.loadConfiguration(new File(LegacyKits.getInstance().getDataFolder(), "sql.yml"));
    }

    public static String getString(String path){
        return configuration().getString(path);
    }
    public static int getInt(String path){
        return configuration().getInt(path);
    }
}
