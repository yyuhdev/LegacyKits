package club.revived.records;

import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.UUID;

public record Kit(
        int num,
        UUID owner,
        HashMap<Integer, ItemStack> items
) {

    public static Kit of(UUID owner, int num){
        return new Kit(num, owner, new HashMap<>());
    }
}
