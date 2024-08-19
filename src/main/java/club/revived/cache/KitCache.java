package club.revived.cache;

import club.revived.objects.Kit;
import club.revived.objects.KitHolder;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class KitCache {
    private final Map<UUID, KitHolder> kits = new ConcurrentHashMap<>();

    public void addKit(UUID playerUUID, Kit kit) {
        kits.computeIfAbsent(playerUUID, KitHolder::newEmpty);
        KitHolder holder = kits.get(playerUUID);
        holder.getList().remove(kit.getID());
        holder.getList().put(kit.getID(), kit);
    }

    public void removeKit(UUID playerUUID, Kit item) {
        kits.computeIfPresent(playerUUID, (uuid, items) -> {
            items.getList().remove(item.getID());
            return items;
        });
    }

    public void update(UUID playerUUID, KitHolder holder) {
        kits.put(playerUUID, holder);
    }

    public void invalidate(UUID playerUUID) {
        kits.remove(playerUUID);
    }

    public Map<Integer, Kit> getKits(UUID playerUUID) {
        if (kits.get(playerUUID) == null) {
            return new HashMap<>();
        }
        Map<Integer, Kit> ret = new HashMap<>();
        kits.computeIfPresent(playerUUID, (uuid, items) -> {
            ret.putAll(items.getList());
            return items;
        });
        return ret;
    }

    public boolean doesItemExist(UUID playerUUID, Kit e) {
        throw new UnsupportedOperationException();
    }
}