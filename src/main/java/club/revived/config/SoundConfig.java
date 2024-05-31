package club.revived.config;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoundConfig {

    private Sound sound = null;
    private double pitch = -1;
    private double volume = -1;

    public void sound(final @NotNull String rawSound, final double pitch, final double volume) {
        this.sound = Sound.valueOf(rawSound.replaceAll("minecraft:", ""));
        this.pitch = pitch();
        this.volume = volume();
    }

    @Nullable
    public Sound sound() {
        return sound;
    }

    public double pitch() {
        return pitch;
    }

    public double volume() {
        return volume;
    }

    public static void playCSound(String value, Integer volume, Integer pitch, Player player){
        Sound sound = Sound.valueOf(value);
        int p = pitch;
        int v = volume;
        player.playSound(player.getLocation(), sound, p, v);
    }
}
