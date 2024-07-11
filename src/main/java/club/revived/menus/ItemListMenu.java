package club.revived.menus;

import club.revived.AithonKits;
import club.revived.menus.items.BackItem;
import club.revived.menus.items.GuideItem;
import club.revived.menus.items.ScrollDownItem;
import club.revived.menus.items.ScrollUpItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
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

    public ItemListMenu(Player player) {
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
                        && material !=  Material.DEBUG_STICK
                        && !material.toString().contains("GRATE")
                        && !material.toString().contains("BULB")
                        && !material.toString().contains("CHISELED_COPPER")
                        && !material.toString().contains("TUFF")
                        && !material.toString().contains("TRIAL"))


                .map(material -> new SimpleItem(new ItemBuilder(material), click -> {
                    if (click.getClickType() == ClickType.RIGHT) {
                        player.getInventory().addItem(new ItemStack(material));
                        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                    }
                    if (click.getClickType() == ClickType.LEFT || click.getClickType() == ClickType.SHIFT_LEFT) {
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
                        "x x x x x x x x s",
                        "x x x x x x x x #",
                        "x x x x x x x x d")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('s', new GuideItem())
                .addIngredient('i', new BackItem())
                .addIngredient('d', new ScrollDownItem())
                .addIngredient('u', new ScrollUpItem())
                .setContent(items)
                .build();


    }

    public BukkitRunnable closeTask(){
        return new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().runTaskLater(AithonKits.getInstance(), () -> new KitMenu(player).open(), 1L);
            }
        };
    }

    public void open() {
        Window window = Window.single()
                .setViewer(player)
                .setTitle(ChatColor.of("#FFD1A3") + "Itemlist")
                .setGui(gui)
                .addCloseHandler(closeTask())
                .build();

        window.open();
        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 5, 5);
    }
}
