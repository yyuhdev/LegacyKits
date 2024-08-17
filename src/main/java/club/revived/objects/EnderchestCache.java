package club.revived.objects;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnderchestCache {
    private final Map<String, Map<Integer, Map<Integer, ItemStack>>> cache = new HashMap<>();

    public Map<Integer, ItemStack> get(UUID playerUUID, int kitNumber) {
        return cache.getOrDefault(playerUUID.toString(), new HashMap<>()).get(kitNumber);
    }

    public void put(UUID playerUUID, int kitNumber, Map<Integer, ItemStack> kitContents) {
        cache.computeIfAbsent(playerUUID.toString(), k -> new HashMap<>()).put(kitNumber, kitContents);
    }

    public void invalidate(UUID playerUUID, int kitNumber) {
        Map<Integer, Map<Integer, ItemStack>> playerKits = cache.get(playerUUID.toString());
        if (playerKits != null) {
            playerKits.remove(kitNumber);
            if (playerKits.isEmpty()) {
                cache.remove(playerUUID.toString());
            }
        }
    }

    public void invalidateAll(UUID playerUUID) {
        cache.remove(playerUUID.toString());
    }
}
