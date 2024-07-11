package club.revived.debugging;

import club.revived.miscellaneous.Itemlist.BackItem;
import club.revived.miscellaneous.Itemlist.GuideItem;
import club.revived.miscellaneous.Itemlist.ScrollDownItem;
import club.revived.miscellaneous.Itemlist.ScrollUpItem;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.ScrollGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.List;

public class DebugCommand {

    public static List<ItemStack> ITEM_STACKS = new ArrayList<>();
    public static List<String> ITEM_NAMES = new ArrayList<>();

    static {
        for (Material material : Material.values()) {
            if (!material.isLegacy() && !material.isAir()) {
                ITEM_STACKS.add(new ItemStack(material));
                ITEM_NAMES.add(material.toString());
            }
        }
    }

    public DebugCommand(){
        Commands.command("search").completes(ctx -> {
            if(ctx.argSize() == 1){
                return Suggestions.of("[<text>]");
            }
            return Suggestions.empty();
        }).executes(ctx -> {
            List<Item> itemResults = new ArrayList<>();
            List<ExtractedResult> results = FuzzySearch.extractSorted(ctx.rawArgAt(0), ITEM_NAMES);
            for(ExtractedResult result : results){
                ItemStack itemStack = ITEM_STACKS.get(result.getIndex());
                if(itemStack == null || itemStack.getType().isAir() || itemStack.getType().isLegacy()) continue;
                itemResults.add(new SimpleItem(new ItemBuilder(itemStack), click -> ctx.player().getInventory().addItem(itemStack)));
            }
            Gui gui = ScrollGui.items()
                    .setStructure(
                            "x x x x x x x x u",
                            "x x x x x x x x #",
                            "x x x x x x x x i",
                            "x x x x x x x x s",
                            "x x x x x x x x #",
                            "x x x x x x x x d")
                    .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                    .addIngredient('s', new GuideItem())
                    .addIngredient('i', new BackItem())
                    .addIngredient('d', new ScrollDownItem())
                    .addIngredient('u', new ScrollUpItem())
                    .setContent(itemResults)
                    .build();

            Window window = Window.single()
                    .setGui(gui)
                    .setViewer(ctx.player())
                    .setTitle("ugh").build();
            window.open();
            return CommandResult.success();
        }).register();
    }
}
