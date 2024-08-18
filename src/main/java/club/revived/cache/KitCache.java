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
        Bukkit.broadcastMessage("Before adding: " + holder.getList().size() + " kits.");
        holder.getList().removeIf(k ->k.getID() == kit.getID());
        holder.getList().add(kit);
        Bukkit.broadcastMessage("After adding: " + holder.getList().size() + " kits.");
    }



    public void removeKit(UUID playerUUID, Kit item) {
        kits.computeIfPresent(playerUUID, (uuid, items) -> {
            items.getList().remove(item);
            return items;
        });
    }

    public void update(UUID playerUUID, KitHolder holder) {
        kits.put(playerUUID, holder);
    }

    public void invalidate(UUID playerUUID) {
        kits.remove(playerUUID);
    }

    public List<Kit> getKits(UUID playerUUID) {
        if (kits.get(playerUUID) == null) {
            return List.of();
        }
        List<Kit> ret = new ArrayList<>();
        kits.computeIfPresent(playerUUID, (uuid, items) -> {
            ret.addAll(items.getList());
            return items;
        });
        return ret;
    }

    public boolean doesItemExist(UUID playerUUID, Kit e) {
        throw new UnsupportedOperationException();
    }
}