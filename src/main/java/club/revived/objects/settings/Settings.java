package club.revived.objects.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor @Getter
public class Settings {

    private final UUID owner;
    private final boolean smartAutokit;
    private final int selectedKit;
    private final boolean broadcastMessages;
    private final boolean singleClickKitRoom;
    private final boolean autokit;

}
