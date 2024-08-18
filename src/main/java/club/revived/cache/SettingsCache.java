package club.revived.cache;

import club.revived.objects.Kit;
import club.revived.objects.Settings;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class SettingsCache {
    private final Map<UUID, Settings> settings = new ConcurrentHashMap<>();

    public void setSettings(UUID playerUUID, Settings sett) {
        settings.put(playerUUID, sett);
    }

    public void invalidate(UUID playerUUID) {
        settings.remove(playerUUID);
    }

    public Settings getSettings(UUID playerUUID) {
        return settings.get(playerUUID);
    }

    public boolean doesItemExist(UUID playerUUID, Kit e) {
        throw new UnsupportedOperationException();
    }
}