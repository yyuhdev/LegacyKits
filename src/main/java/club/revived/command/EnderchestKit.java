package club.revived.command;

import club.revived.cache.EnderchestCache;
import club.revived.config.MessageHandler;
import club.revived.objects.Enderchest;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnderchestKit {

    public EnderchestKit(){
        init();
    }

    private void init() {
        for (int x = 1; x < 10; x++) {
            int finalX = x;
            Commands.command("ec" + finalX)
                    .completes(context -> Suggestions.empty())
                    .executes(ctx -> {
                        for(Enderchest kit : EnderchestCache.getKits(ctx.player().getUniqueId())){
                            if(kit.getID() == finalX){
                                Map<Integer, ItemStack> map =  kit.getContent();
                                ctx.player().getEnderChest().setContents(map.values().toArray(new ItemStack[0]));
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
