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
        kits.compute(playerUUID, (uuid, items) -> {
            if (items == null) {
                items = new KitHolder(uuid, new ArrayList<>());
            }
//            Kit k = items.getList().get(kit.getID());
//            if (k.getID() == kit.getID() && k.getType() == kit.getType()) {
//                items.getList().remove(k);
//            }
            if(items.getList().isEmpty()){
                return KitHolder.newEmpty(playerUUID);
            }
            items.getList().get(kit.getID());
            return items;
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