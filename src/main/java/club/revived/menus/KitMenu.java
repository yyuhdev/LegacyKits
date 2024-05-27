package club.revived.menus;

import club.revived.WeirdoKits;
import club.revived.menus.kitroom.Armor;
import club.revived.menus.kitroom.Arrows;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

public class KitMenu {

    private final Player player;
    private final MenuBase<?> menu;
    WeirdoKits kits  = WeirdoKits.getInstance();

    public KitMenu(Player player){
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<gold>Kits"), 6*9);
        init();
    }

    public void  init() {

        this.menu.border(Button.button(
                ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                        .name("")
                ).onClick(event -> {
                            event.setCancelled(true);
                        }),
                "X X X X X X X X X",
                "X . . . . . . . X",
                "X . . . . . . . X",
                "X . . . . . . . X",
                "X . . . . . . . X",
                "X . . . . . . . X"
        );

        for (int x = 10; x < 17; x++) {
            int i = x;
            this.menu.button(x, Button.button(
                            ItemBuilder.item(Material.CHEST)
                                    .name(TextStyle.style("<gold>Kit " + (x - 9)))
                                    .lore(TextStyle.style("<white>Click to edit")))
                    .onClick(event -> {
                        event.setCancelled(true);
                        for (Player global : Bukkit.getOnlinePlayers()) {
                            if (!kits.broadcast.contains(global.getUniqueId()))
                                global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has opened the kit editor.");
                            KitEditor editor = new KitEditor(player, i - 9);
                            editor.open();
                        }
                    })
            );
        }
        for(int x = 19; x < 26; x++){
            int i = x;
            this.menu.button(x, Button.button(
                    ItemBuilder.item(Material.ENDER_CHEST)
                            .name(TextStyle.style("<gold>Enderchest " + (x - 18)))
                            .lore(TextStyle.style("<white>Click to edit")))
                    .onClick(event -> {
                        event.setCancelled(true);
                        for(Player global : Bukkit.getOnlinePlayers()){
                            if (!kits.broadcast.contains(global.getUniqueId()))
                                global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has opened the kit editor.");
                            EnderchestEditor editor = new EnderchestEditor(player, i-18);
                            editor.open();
                        }
                    })
            );
            this.menu.button(38, Button.button(
                            ItemBuilder.item(Material.END_CRYSTAL)
                                    .name(TextStyle.style("<gold>Kit Room"))
                                    .lore(TextStyle.style("<white>Click to open"))
                    )
                    .onClick(event -> {
                        event.setCancelled(true);
                        Armor armor = new Armor(player);
                        armor.open();
                    }));
        }
    }
    public void open(){
        this.menu.open(this.player);
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
    }
}
