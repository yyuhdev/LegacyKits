package club.revived.util;

import club.revived.config.Files;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class SqlConfig {

    @NotNull
    public static FileConfiguration configuration() {
        return Files.config(Files.file("sql.yml"));
    }

    public static String getString(String path){
        return configuration().getString(path);
    }
    public static int getInt(String path){
        return configuration().getInt(path);
    }
}
