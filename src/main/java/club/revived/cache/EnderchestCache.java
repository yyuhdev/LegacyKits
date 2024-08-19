package club.revived.cache;

import club.revived.objects.Enderchest;
import club.revived.objects.EnderchestHolder;
import club.revived.objects.Kit;
import club.revived.objects.KitHolder;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class EnderchestCache {
    private final Map<UUID, EnderchestHolder> kits = new ConcurrentHashMap<>();

    public void addKit(UUID playerUUID, Enderchest kit) {
        kits.computeIfAbsent(playerUUID, EnderchestHolder::newEmpty);
        EnderchestHolder holder = kits.get(playerUUID);
        holder.getList().removeIf(k ->k.getID() == kit.getID());
        holder.getList().add(kit);
    }

    public void removeKit(UUID playerUUID, Enderchest item) {
        kits.computeIfPresent(playerUUID, (uuid, items) -> {
            items.getList().remove(item);
            return items;
        });
    }

    public void update(UUID playerUUID, EnderchestHolder holder) {
        kits.put(playerUUID, holder);
    }

    public void invalidate(UUID playerUUID) {
        kits.remove(playerUUID);
    }

    public List<Enderchest> getKits(UUID playerUUID) {
        if (kits.get(playerUUID) == null) {
            return List.of();
        }
        List<Enderchest> ret = new ArrayList<>();
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