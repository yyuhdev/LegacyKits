package club.revived.cache;

import club.revived.storage.room.KitRoomData;
import club.revived.util.enums.KitroomPage;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class KitRoomCache {

    private static final Map<KitroomPage, Map<Integer, ItemStack>> cache = new ConcurrentHashMap<>();

    public static CompletableFuture<Map<Integer, ItemStack>> getKitRoomPage(KitroomPage page) {
        if (cache.containsKey(page)) {
            return CompletableFuture.completedFuture(cache.get(page));
        }
        return KitRoomData.loadKitRoomPage(page).thenApply(data -> {
            cache.put(page, data);
            return data;
        });
    }

    public static CompletableFuture<Void> update() {
        return CompletableFuture.runAsync(() -> {
            Arrays.stream(KitroomPage.values()).forEach(page -> {
                Map<Integer, ItemStack> data = KitRoomData.loadKitRoomPage(page).join();
                cache.put(page, data);
            });
        });
    }

    public static void saveKitRoomPage(KitroomPage page, Inventory inventory) {
        Map<Integer, ItemStack> map = new ConcurrentHashMap<>();
        for (int slot = 0; slot < 45; slot++) {
            ItemStack stack = inventory.getItem(slot);
            if (stack != null) {
                map.put(slot, stack);
            } else {
                map.remove(slot);
            }
        }
        cache.put(page, map);
        KitRoomData.saveKitRoomPage(page, inventory);
    }

    public static void invalidateCache(KitroomPage page) {
        cache.remove(page);
    }

    public static void clearCache() {
        cache.clear();
    }
}
