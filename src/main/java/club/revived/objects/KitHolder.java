package club.revived.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor @Getter
public class KitHolder {

    private final UUID uuid;
    private final List<Kit> list;

    public static KitHolder newEmpty(UUID uuid){
        List<Kit> kits = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            kits.add(new Kit(uuid, i, new HashMap<>(), new HashMap<>()));
            kits.add(new Kit(uuid, i, new HashMap<>(), new HashMap<>()));
        }
        return new KitHolder(uuid, kits
        );
    }
}
