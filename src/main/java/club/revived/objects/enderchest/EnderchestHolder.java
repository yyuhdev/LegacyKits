package club.revived.objects.enderchest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor @Getter
public class EnderchestHolder {

    private final UUID uuid;
    private final Map<Integer, Enderchest> list;

    public static EnderchestHolder newEmpty(UUID uuid){
        Map<Integer, Enderchest> kits = new HashMap<>();
        for (int i = 1; i <= 16; i++) {
            kits.put(i, new Enderchest(uuid, i, String.valueOf(i), new HashMap<>()));
        }
        return new EnderchestHolder(uuid, kits);
    }
}
