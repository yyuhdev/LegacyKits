package club.revived.debugging;

import club.revived.AithonKits;
import club.revived.util.PublicKit;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class DebugCommand {

    ArrayList<PublicKit> publicKits = new ArrayList<>();

    public DebugCommand(){

        Commands.command("debug")
                .completes(ctx -> Suggestions.of("createpubkit", "pubkits"))
                .executes(ctx -> {
                    if(ctx.rawArgAt(0) == "createpubkit"){
                        publicKits.add(new PublicKit((HashMap<Integer, ItemStack>) AithonKits.getInstance().getConfigUtil().load(ctx.player().getUniqueId(), "1")));
                        return CommandResult.success();
                    }
                    if(ctx.rawArgAt(0) == "pubkits"){
                        ItemStack itemStack = new ItemStack(Material.SHULKER_BOX);
                        BlockStateMeta im = (BlockStateMeta) itemStack.getItemMeta();
                        ShulkerBox box = (ShulkerBox) im.getBlockState();
                        Inventory inventory = box.getInventory();
                        inventory.setItem(0, new ItemStack(Material.NETHERITE_BLOCK));
                        ctx.player().getInventory().setItem(0, itemStack);
                        return CommandResult.success();
                    }
                    return CommandResult.success();
                }).register();


    }

}
