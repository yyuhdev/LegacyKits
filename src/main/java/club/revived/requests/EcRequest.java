package club.revived.requests;

import org.bukkit.entity.Player;

public class EcRequest {
    Player requester;
    String kitNumber;

    EcRequest(Player requester, String kitNumber) {
        this.requester = requester;
        this.kitNumber = kitNumber;
    }
}
 