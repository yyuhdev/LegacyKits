package club.revived.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor @Getter
public class KitHolder {

    private final UUID uuid;
    private final List<Kit> list;

    public static KitHolder newEmpty(UUID uuid){
        return new KitHolder(uuid, List.of(
                new Kit(uuid, 1, new HashMap<>(), KitType.INVENTORY),
                new Kit(uuid, 2, new HashMap<>(), KitType.INVENTORY),
                new Kit(uuid, 3, new HashMap<>(), KitType.INVENTORY),
                new Kit(uuid, 4, new HashMap<>(), KitType.INVENTORY),
                new Kit(uuid, 5, new HashMap<>(), KitType.INVENTORY),
                new Kit(uuid, 6, new HashMap<>(), KitType.INVENTORY),
                new Kit(uuid, 7, new HashMap<>(), KitType.INVENTORY),
                new Kit(uuid, 1, new HashMap<>(), KitType.ENDERCHEST),
                new Kit(uuid, 2, new HashMap<>(), KitType.ENDERCHEST),
                new Kit(uuid, 3, new HashMap<>(), KitType.ENDERCHEST),
                new Kit(uuid, 4, new HashMap<>(), KitType.ENDERCHEST),
                new Kit(uuid, 5, new HashMap<>(), KitType.ENDERCHEST),
                new Kit(uuid, 6, new HashMap<>(), KitType.ENDERCHEST),
                new Kit(uuid, 7, new HashMap<>(), KitType.ENDERCHEST)
        ));
    }
}
