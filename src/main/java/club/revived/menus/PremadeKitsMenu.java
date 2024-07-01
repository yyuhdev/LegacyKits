package club.revived.menus;

import club.revived.AithonKits;
import club.revived.config.Files;
import club.revived.menus.tabs.EnderchestTab;
import club.revived.menus.tabs.KitTab;
import dev.manere.utils.scheduler.Schedulers;
import dev.manere.utils.serializers.Serializers;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.io.File;
import java.util.Arrays;
import java.util.Base64;

public class PremadeKitsMenu {

    private final Gui gui;
    private final Player player;

    public PremadeKitsMenu(Player player){
        this.player = player;
        Gui enderchestGui = Gui.empty(9,4);
        Schedulers.async().execute(() -> {
            File file = Files.create(new File(AithonKits.getInstance().getDataFolder(),"premade-enderchest.yml"));
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (int i = 0; i < 27; i++) {
                String base64 = configuration.getString(String.valueOf(i));

                if (base64 != null) {
                    byte[] bytes = Base64.getDecoder().decode(base64);
                    ItemStack item = Serializers.bytes().deserialize(bytes);
                    enderchestGui.setItem(i, new SimpleItem(item));
                } else {
                    enderchestGui.setItem(i, null);
                }
            }
        });

        Gui kitGui = Gui.empty(9,4);
        Schedulers.async().execute(() -> {
            File file = Files.create(new File(AithonKits.getInstance().getDataFolder(),"premade-kit.yml"));
            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            for (int i = 0; i < 4*9; i++) {
                String base64 = configuration.getString(String.valueOf(i));

                if (base64 != null) {
                    byte[] bytes = Base64.getDecoder().decode(base64);
                    ItemStack item = Serializers.bytes().deserialize(bytes);
                    kitGui.setItem(i, new SimpleItem(item));
                } else {
                    kitGui.setItem(i, null);
                }
            }
        });

        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"));

        gui = TabGui.normal()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "# # # 0 # 1 # # #")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('0', new KitTab(0))
                .addIngredient('1', new EnderchestTab(1))
                .setTabs(Arrays.asList(kitGui, enderchestGui))
                .build();
    }

    public void open(){
        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle(ChatColor.of("#FFD1A3") + "Premade Kit")
                .build();

        window.open();
    }
}
