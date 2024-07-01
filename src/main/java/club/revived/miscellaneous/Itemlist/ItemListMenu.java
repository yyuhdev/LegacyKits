package club.revived.miscellaneous;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.ScrollGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemListMenu {

    private final Player player;
    private final Gui gui;

    public ItemListMenu(Player player){
        this.player = player;
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" "));

        List<Item> items = Arrays.stream(Material.values())
                .filter(material -> !material.isAir() && material.isItem()
                        && material != Material.BEDROCK
                        && material != Material.COMMAND_BLOCK
                        && material != Material.CHAIN_COMMAND_BLOCK
                        && material != Material.REPEATING_COMMAND_BLOCK
                        && !material.toString().contains("SPAWN_EGG")
                        && material != Material.COMMAND_BLOCK_MINECART
                        && material != Material.ENCHANTED_GOLDEN_APPLE
                        && material != Material.SPAWNER
                        && material != Material.REINFORCED_DEEPSLATE
                        && material != Material.END_PORTAL_FRAME
                        && material != Material.DRAGON_EGG
                        && material != Material.LIGHT
                        && material != Material.STRUCTURE_BLOCK
                        && material != Material.STRUCTURE_VOID
                        && material != Material.JIGSAW
                        && material != Material.KNOWLEDGE_BOOK
                        && material != Material.BARRIER
                        && material != Material.ENCHANTED_BOOK
                        && !material.toString().contains("GRATE")
                        && !material.toString().contains("BULB")
                        && !material.toString().contains("TUFF")
                        && !material.toString().contains("TRIAL"))


                .map(material -> new SimpleItem(new ItemBuilder(material), click -> {
                    if(click.getClickType() == ClickType.RIGHT) {
                        player.getInventory().addItem(new ItemStack(material));
                        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                    if(click.getClickType() == ClickType.LEFT || click.getClickType() == ClickType.SHIFT_LEFT) {
                        player.getInventory().addItem(new ItemStack(material, material.getMaxStackSize()));
                        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }

                }))
                .collect(Collectors.toList());

        gui = ScrollGui.items()
                .setStructure(
                        "x x x x x x x x u",
                        "x x x x x x x x #",
                        "x x x x x x x x i",
                        "x x x x x x x x #",
                        "x x x x x x x x #",
                        "x x x x x x x x d")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('i', new InfoItem())
                .addIngredient('d', new ScrollDownItem())
                .addIngredient('u', new ScrollUpItem())
                .setContent(items)
                .build();


    }

    public void open(){
        Window window = Window.single()
                .setViewer(player)
                .setTitle("§6Itemlist")
                .setGui(gui)
                .build();

        window.open();
        player.playSound(player, Sound.ENTITY_CHICKEN_EGG,5,5);
    }
}
