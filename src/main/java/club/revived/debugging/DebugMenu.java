package club.revived.debugging;

import club.revived.AithonKits;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class DebugMenu {

    private final Player player;
    private final Inventory inv;

    public DebugMenu(Player player){
        this.player = player;
        inv = Bukkit.createInventory(null, 54, "yurrr");
        AithonKits.getInstance().getConfigUtil().loadPremadeKit(inv);
        AithonKits.getInstance().getConfigUtil().loadPremadeKit(player.getInventory());
        player.openInventory(inv);
    }
}
