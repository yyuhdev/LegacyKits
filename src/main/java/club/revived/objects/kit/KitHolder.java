package club.revived.objects.kit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor @Getter
public class  KitHolder {

    private final UUID uuid;
    private final Map<Integer, Kit> list;

    public static KitHolder newEmpty(UUID uuid){
        Map<Integer, Kit> kits = new HashMap<>();
        for (int i = 1; i <= 16; i++) {
            kits.put(i, new Kit(uuid, i, String.valueOf(i), new HashMap<>()));
        }
        return new KitHolder(uuid, kits
        );
    }
}
