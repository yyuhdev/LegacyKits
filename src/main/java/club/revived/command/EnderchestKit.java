package club.revived.command;

import club.revived.LegacyKits;
import club.revived.cache.KitCache;
import club.revived.config.MessageHandler;
import club.revived.objects.Kit;
import club.revived.objects.KitType;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnderchestKit {

    LegacyKits kits = LegacyKits.getInstance();

    public EnderchestKit(){
        init();
    }

    private void init() {
        for (int x = 1; x < 10; x++) {
            int finalX = x;
            Commands.command("ec" + finalX)
                    .completes(context -> Suggestions.empty())
                    .executes(ctx -> {
                        for(Kit kit : KitCache.getKits(ctx.player().getUniqueId())){
                            if(kit.getType() != KitType.ENDERCHEST) continue;
                            if(kit.getID() == finalX){
                                Map<Integer, ItemStack> map =  kit.getContent();
                                ctx.player().getInventory().setContents(map.values().toArray(new ItemStack[0]));
                            }
                        }
                        ctx.player().sendRichMessage(MessageHandler.of("ENDERCHEST_LOAD")
                                .replace("<ec>", String.valueOf(finalX))
                        );
                        return CommandResult.success();
                    })
                    .build()
                    .register();
        }
    }
}
