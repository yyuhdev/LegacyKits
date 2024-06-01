package club.revived.command;

import club.revived.WeirdoKits;
import dev.manere.utils.command.CommandResult;
import dev.manere.utils.command.impl.Commands;
import dev.manere.utils.command.impl.suggestions.Suggestions;

public class EcCommand {

    WeirdoKits kits = WeirdoKits.getInstance();

    public EcCommand(){
        init();
    }

    private void init(){
        Commands.command("ec1")
                .completes(context -> Suggestions.empty())
                .executes(ctx -> {
                    kits.getKitLoader().loadEnderChest(ctx.player(), "1");
                    return CommandResult.success();
                })
                .build()
                .register();

        Commands.command("ec2")
                .completes(context -> Suggestions.empty())
                .executes(ctx -> {
                    kits.getKitLoader().loadEnderChest(ctx.player(), "2");
                    return CommandResult.success();
                })
                .build()
                .register();

        Commands.command("ec3")
                .completes(context -> Suggestions.empty())
                .executes(ctx -> {
                    kits.getKitLoader().loadEnderChest(ctx.player(), "3");
                    return CommandResult.success();
                })
                .build()
                .register();

        Commands.command("ec4")
                .completes(context -> Suggestions.empty())
                .executes(ctx -> {
                    kits.getKitLoader().loadEnderChest(ctx.player(), "4");
                    return CommandResult.success();
                })
                .build()
                .register();

        Commands.command("ec4")
                .completes(context -> Suggestions.empty())
                .executes(ctx -> {
                    kits.getKitLoader().loadEnderChest(ctx.player(), "4");
                    return CommandResult.success();
                })
                .build()
                .register();

        Commands.command("ec5")
                .completes(context -> Suggestions.empty())
                .executes(ctx -> {
                    kits.getKitLoader().loadEnderChest(ctx.player(), "5");
                    return CommandResult.success();
                })
                .build()
                .register();

        Commands.command("ec6")
                .completes(context -> Suggestions.empty())
                .executes(ctx -> {
                    kits.getKitLoader().loadEnderChest(ctx.player(), "6");
                    return CommandResult.success();
                })
                .build()
                .register();

        Commands.command("ec7")
                .completes(context -> Suggestions.empty())
                .executes(ctx -> {
                    kits.getKitLoader().loadEnderChest(ctx.player(), "7");
                    return CommandResult.success();
                })
                .build()
                .register();
    }
}
