package club.revived.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class MessageHandler {

    @NotNull
    public static FileConfiguration config() {
        return Files.config(Files.file("messages.yml"));
    }


    public static String of(String s){
        return config().getString(s);
    }
}
