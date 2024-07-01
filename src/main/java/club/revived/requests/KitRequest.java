package club.revived.requests;

import org.bukkit.entity.Player;

public class KitRequest {
    Player requested;
    String kitNumber;

    KitRequest(Player requester, String kitNumber) {
        this.requested = requester;
        this.kitNumber = kitNumber;
    }
}
