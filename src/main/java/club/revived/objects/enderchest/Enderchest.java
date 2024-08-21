package club.revived.objects.enderchest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor @Getter
public class Enderchest {

    private final UUID owner;
    private final int ID;
    private final String name;
    private final Map<Integer, ItemStack> content;
}
