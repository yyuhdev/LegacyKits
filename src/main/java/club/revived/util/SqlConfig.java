package club.revived.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SqlConfig {

    @NotNull
    public static final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File("sql.yml"));

    public static String getString(String path){
        return configuration.getString(path);
    }
    public static int getInt(String path){
        return configuration.getInt(path);
    }
}
