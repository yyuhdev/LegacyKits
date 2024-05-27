package club.revived.menus;

import club.revived.WeirdoKits;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KitMenu {

    private final Player player;
    private final MenuBase<?> menu;
    WeirdoKits kits  = WeirdoKits.getInstance();

    public KitMenu(Player player){
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<gold>Kits"), 4*6);
        init();
    }

    public void  init(){
        this.menu.button(10, Button.button(
                ItemBuilder.item(Material.CHEST)
                        .name(TextStyle.style("<gold>Kit 1"))
                        .lore(TextStyle.style("<white>Click to edit")))
                .onClick(event -> {
                    event.setCancelled(true);
                    for(Player global : Bukkit.getOnlinePlayers()){
                        if(!kits.broadcast.contains(global.getUniqueId()))
                            global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has opened the kit editor.");
                        KitEditor editor = new KitEditor(player, 1);
                        editor.open();
                    }
                })
        );
    }
    public void open(){
        this.menu.open(this.player);
    }
}
