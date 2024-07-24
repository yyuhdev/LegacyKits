package club.revived.command;

import club.revived.AithonKits;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.ScrollGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.item.impl.controlitem.ScrollItem;
import xyz.xenondevs.invui.window.Window;
import java.util.ArrayList;
import java.util.List;

public class Search
        implements CommandExecutor, TabCompleter {

    public static List<ItemStack> ITEM_STACKS = new ArrayList<>();
    public static List<String> ITEM_NAMES = new ArrayList<>();
    public static List<Material> FILTERED = List.of(Material.BEDROCK,
            Material.COMMAND_BLOCK,
            Material.CHAIN_COMMAND_BLOCK,
            Material.REPEATING_COMMAND_BLOCK,
            Material.COMMAND_BLOCK_MINECART,
            Material.ENCHANTED_GOLDEN_APPLE,
            Material.SPAWNER,
            Material.REINFORCED_DEEPSLATE,
            Material.END_PORTAL,
            Material.END_PORTAL_FRAME,
            Material.DRAGON_EGG,
            Material.LIGHT,
            Material.STRUCTURE_BLOCK,
            Material.JIGSAW,
            Material.KNOWLEDGE_BOOK,
            Material.BARRIER
    );

    static {
        for (Material material : Material.values()) {
            if (material.isAir()) continue;
            if (FILTERED.contains(material)) continue;
            if (material.toString().contains("SPAWN_EGG")) continue;
            if (material.toString().contains("GRATE")) continue;
            if (material.toString().contains("BULB")) continue;
            if (material.toString().contains("LEGACY")) continue;
            if (material.toString().contains("CHISELED_COPPER")) continue;
            if (material.toString().contains("TUFF")) continue;
            if (material.toString().contains("TRIAL")) continue;
            if (material.isLegacy()) continue;

            ITEM_STACKS.add(new ItemStack(material));
            ITEM_NAMES.add(material.toString());

        }
    }

    public Search(){
        AithonKits.getInstance().getCommand("search").setExecutor(this);
        AithonKits.getInstance().getCommand("search").setTabCompleter(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage("This command can only be executed by a player.");
            return true;
        }
        if (args.length != 1) {
            player.sendRichMessage("<red>Usage: /search <value>");
            return true;
        }

        List<Item> resultItems = new ArrayList<>();
        List<ExtractedResult> results = FuzzySearch.extractSorted(args[0], ITEM_NAMES);
        for (ExtractedResult result : results) {
            ItemStack stack = ITEM_STACKS.get(result.getIndex());
            resultItems.add(new SimpleItem(new ItemBuilder(stack), click -> player.getInventory().addItem(stack.add(stack.getMaxStackSize() - 1))));
        }
        Gui gui = ScrollGui.items()
                .setStructure(
                        "x x x x x x x x u",
                        "x x x x x x x x #",
                        "x x x x x x x x #",
                        "x x x x x x x x #",
                        "x x x x x x x x #",
                        "x x x x x x x x d"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('d', new ScrollDownItem())
                .addIngredient('u', new ScrollUpItem())
                .setContent(resultItems)
                .addIngredient('#', new SimpleItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("")))
                .build();

        Window window = Window.single()
                .setGui(gui)
                .setTitle("Results for: " + args[0])
                .setViewer(player)
                .build();
        window.open();

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> toReturn = new ArrayList<>();
            ITEM_NAMES.stream().map(String::toString).filter(string -> string.startsWith(args[0])).forEach(toReturn::add);
            return toReturn;
        }
        return List.of();
    }

    public static class ScrollDownItem extends ScrollItem {

        public ScrollDownItem() {
            super(1);
        }

        @Override
        public ItemProvider getItemProvider(ScrollGui<?> gui) {
            ItemBuilder builder = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE);
            builder.setDisplayName("§7Scroll down");
            if (!gui.canScroll(1))
                builder.addLoreLines("§cYou can't scroll further down");

            return builder;
        }
    }

    public static class ScrollUpItem extends ScrollItem {

        public ScrollUpItem() {
            super(-1);
        }

        @Override
        public ItemProvider getItemProvider(ScrollGui<?> gui) {
            ItemBuilder builder = new ItemBuilder(Material.RED_STAINED_GLASS_PANE);
            builder.setDisplayName("§7Scroll up");
            if (!gui.canScroll(-1))
                builder.addLoreLines("§cYou've reached the top");

            return builder;
        }
    }
}

