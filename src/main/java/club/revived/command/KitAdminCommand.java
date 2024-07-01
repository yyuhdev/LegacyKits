package club.revived.command;

import club.revived.AithonKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Sound;

import java.util.Objects;

public class KitAdminCommand {

    public KitAdminCommand(){
        Commands.command("kitadmin")
                .permission("club.revived.admin")
                .completes(ctx -> {
                    if(ctx.argSize() == 1){
                        return Suggestions.of("savepremadekit", "savepremadeec", "editkitroom");
                    }
                    if(ctx.argSize() == 2){
                        return Suggestions.of("armory, consumables, explosives");
                    }
                    return Suggestions.empty();
                })
                .executes(ctx -> {
                    if(ctx.argSize() == 1 && Objects.equals(ctx.rawArgAt(0), "savepremadekit")) {
                        if (AithonKits.getInstance().getConfigUtil().savePremadeKit(ctx.player().getInventory())) {
                            ctx.player().sendRichMessage("<red>Successfully saved the premade kit");
                            ctx.player().playSound(ctx.player().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
                            return CommandResult.success();
                        }
                        ctx.player().sendRichMessage("<dark_red><bold>FAILED");
                        AithonKits.getInstance().getComponentLogger().error(TextStyle.style("<dark_red>SEVERE ERROR WHILST KIT SAVING <reset><purple>uwu"));
                        ctx.player().playSound(ctx.player().getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0F, 5.0F);
                    }
                    if(ctx.argSize() == 1 && Objects.equals(ctx.rawArgAt(0), "savepremadeec")) {
                        if (AithonKits.getInstance().getConfigUtil().savePremadeEnderchest(ctx.player().getEnderChest())) {
                            ctx.player().sendRichMessage("<red>Successfully saved the premade enderchest");
                            ctx.player().playSound(ctx.player().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
                            return CommandResult.success();
                        }
                        ctx.player().sendRichMessage("<dark_red><bold>FAILED");
                        AithonKits.getInstance().getComponentLogger().error(TextStyle.style("<dark_red>SEVERE ERROR WHILST ENDERCHEST SAVING <reset><purple>uwu"));
                        ctx.player().playSound(ctx.player().getLocation(), Sound.BLOCK_ANVIL_LAND, 5.0F, 5.0F);
                    }
                    return CommandResult.success();
                }).register();

    }
}
