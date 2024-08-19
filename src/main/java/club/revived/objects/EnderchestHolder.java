package club.revived.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor @Getter
public class EnderchestHolder {

    private final UUID uuid;
    private final List<Enderchest> list;

    public static EnderchestHolder newEmpty(UUID uuid){
        List<Enderchest> kits = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            kits.add(new Enderchest(uuid, i, String.valueOf(i), new HashMap<>()));
        }
        return new EnderchestHolder(uuid, kits);
    }
}
