package club.revived.cache;

import club.revived.objects.kit.Kit;
import club.revived.objects.settings.Settings;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.UUID;
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