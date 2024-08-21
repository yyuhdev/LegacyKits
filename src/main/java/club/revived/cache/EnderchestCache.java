package club.revived.cache;

import club.revived.objects.enderchest.Enderchest;
import club.revived.objects.enderchest.EnderchestHolder;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class EnderchestCache {
    private final Map<UUID, EnderchestHolder> kits = new ConcurrentHashMap<>();

    public void addKit(UUID playerUUID, Enderchest kit) {
        kits.computeIfAbsent(playerUUID, EnderchestHolder::newEmpty);
        EnderchestHolder holder = kits.get(playerUUID);
        holder.getList().remove(kit.getID());
        holder.getList().put(kit.getID(), kit);
    }

    public void removeKit(UUID playerUUID, Enderchest item) {
        kits.computeIfPresent(playerUUID, (uuid, items) -> {
            items.getList().remove(item.getID());
            return items;
        });
    }

    public void update(UUID playerUUID, EnderchestHolder holder) {
        kits.put(playerUUID, holder);
    }

    public void invalidate(UUID playerUUID) {
        kits.remove(playerUUID);
    }

    public Map<Integer, Enderchest> getKits(UUID playerUUID) {
        if (kits.get(playerUUID) == null) {
            return new HashMap<>();
        }
        Map<Integer, Enderchest> ret = new HashMap<>();
        kits.computeIfPresent(playerUUID, (uuid, items) -> {
            ret.putAll(items.getList());
            return items;
        });
        return ret;
    }
}