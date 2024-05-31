package club.revived.util;

import club.revived.config.Files;
import club.revived.config.SoundConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PageSound {

    @NotNull
    public FileConfiguration soundConfig() {
        return Files.config(Files.create(Files.file("sounds.yml")));
    }

    public void playPageSound(Player player){
        SoundConfig.playCSound(soundConfig().getString("page_open.sound"), soundConfig().getInt("page_open.pitch"),soundConfig().getInt("page_open.volume"), player);
    }
}
