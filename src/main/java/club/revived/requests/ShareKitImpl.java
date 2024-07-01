package club.revived.requests;

import club.revived.AithonKits;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ShareKitImpl {

    private final ConcurrentHashMap<UUID, KitRequest> openRequest = new ConcurrentHashMap<>();

    public void request(Player player, Player target, String kitNum){
        if(isInt(kitNum)){
            int x = Integer.parseInt(kitNum);
            if (x < 1 || x > 7) {
                player.sendRichMessage("<red>Please try a valid kit (1-7)");
                return;
            }
            if(player == target) return;
            openRequest.put(player.getUniqueId(), new KitRequest(target, kitNum));
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1,2);
            target.sendRichMessage("<gold><player> <gray>has requested you to claim their kit <kit>"
                    .replace("<player>", player.getName())
                    .replace("<kit>", kitNum));
            player.sendRichMessage("<green>Request has been sent successfully");
            Bukkit.getScheduler().runTaskLaterAsynchronously(AithonKits.getInstance(), () -> {
                if(openRequest.containsKey(player.getUniqueId())){
                    openRequest.remove(player.getUniqueId());
                    player.sendRichMessage("<gray>Your current request expired");
                }
            },600);
        }
    }
    public void accept(Player requester, Player requested){
        if(openRequest.containsKey(requester.getUniqueId())){
            KitRequest request = openRequest.get(requester.getUniqueId());
            if(request.requested == requested){
                AithonKits.getInstance().getKitLoader().loadothers(requester, requested, request.kitNumber);
            }
        }
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
