package club.revived.cache;

import club.revived.storage.premade.PremadeKitData;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class PremadeKitCache {

    private static final Map<String, Map<Integer, ItemStack>> cache = new ConcurrentHashMap<>();

    public static CompletableFuture<Map<Integer, ItemStack>> getPremadeKit(String kitName) {
        if (cache.containsKey(kitName)) {
            return CompletableFuture.completedFuture(cache.get(kitName));
        }
        return PremadeKitData.loadPremadeKit(kitName).thenApply(data -> {
            cache.put(kitName, data);
            return data;
        });
    }

    public static CompletableFuture<Boolean> savePremadeKit(String kitName, Inventory inventory) {
        return PremadeKitData.savePremadeKit(kitName, inventory).thenApply(success -> {
            if (success) {
                Map<Integer, ItemStack> map = new ConcurrentHashMap<>();
                for (int slot = 0; slot < 5; ++slot) {
                    ItemStack item = inventory.getItem(slot);
                    map.put(slot + 36, item);
                }
                for (int slot = 9; slot < 36; ++slot) {
                    ItemStack item = inventory.getItem(slot);
                    map.put(slot, item);
                }
                for (int slot = 36; slot < 45; ++slot) {
                    ItemStack item = inventory.getItem(slot);
                    map.put(slot - 36, item);
                }
                cache.put(kitName, map);
            }
            return success;
        });
    }

    public static void invalidateCache(String kitName) {
        cache.remove(kitName);
    }

    public static void clearCache() {
        cache.clear();
    }

    public static CompletableFuture<Void> preloadCache(String... kitNames) {
        return CompletableFuture.runAsync(() -> {
            for (String kitName : kitNames) {
                if (!cache.containsKey(kitName)) {
                    PremadeKitData.loadPremadeKit(kitName).thenAccept(data -> cache.put(kitName, data)).join();
                }
            }
        });
    }
}
