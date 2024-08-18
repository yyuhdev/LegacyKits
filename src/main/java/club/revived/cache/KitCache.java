package club.revived.cache;

import club.revived.objects.Kit;
import club.revived.objects.KitHolder;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class KitCache {
    private final Map<UUID, KitHolder> kits = new ConcurrentHashMap<>();

    public void addKit(UUID playerUUID, Kit kit) {
        kits.compute(playerUUID, (uuid, holder) -> {
            if (holder == null) {
                holder = KitHolder.newEmpty(playerUUID);
            }
            List<Kit> newList = new ArrayList<>();
            for (Kit k : holder.getList()) {
                if (!(k.getID() == kit.getID() && k.getType() == kit.getType())) {
                    newList.add(k);
                }
            }
            newList.add(kit);
            return new KitHolder(uuid, newList);
        });
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
        kits.computeIfPresent(playerUUID, (uuid, holder) -> {
            ret.addAll(holder.getList());
            return holder;
        });
        return ret;
    }

    public boolean doesItemExist(UUID playerUUID, Kit e) {
        throw new UnsupportedOperationException();
    }
}