package club.revived.menus;

import club.revived.AithonKits;
import club.revived.util.ConfigUtil;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

public class PremadeKitsMenu {

    private final Player player;
    private final MenuBase<?> menu;
    public ConfigUtil configUtil = new ConfigUtil();
    public Inventory currentPreview;

    public PremadeKitsMenu(Player player){
        AithonKits.getInstance().getServer().getPluginManager().registerEvents(new premadeKitListener(), AithonKits.getInstance());
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<#FFD1A3>Preset Kits"), 27);
        this.menu.border(Button.button(
                ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name(""))
                        .onClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true)),
                "X X X X X X X X X",
                "X X X . . . X X X",
                "X X X X X X X X X"
        );
        this.menu.button(12, Button.button(
                ItemBuilder.item(Material.END_CRYSTAL).name(TextStyle.style("<#FFD1A3>Preset Evaluation Kit"))
                        .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Click to claim")))
                .onClick(event -> {
                    event.setCancelled(true);
                    if(event.getClick().isRightClick()){
                        Bukkit.getScheduler().runTaskAsynchronously(AithonKits.getInstance(), () -> {
                            currentPreview = previewKitInventory(TextStyle.style("<#FFD1A3>Preset Eval Kit Preview"));
                            configUtil.loadPremade("evaluation", currentPreview);
                            Bukkit.getScheduler().runTask(AithonKits.getInstance(), () -> player.openInventory(currentPreview));
                        });
                        return;
                    }
                    if (configUtil.loadPremadeKit("evaluation", player.getInventory())) {
                        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        player.sendActionBar(TextStyle.style("<#72FF9D>Kit loaded successfully"));
                        return;
                    }
                    player.sendRichMessage("<red>An error occurred.");
                }));

        this.menu.button(13, Button.button(
                        ItemBuilder.item(Material.POTION).name(TextStyle.style("<#FFD1A3>Preset Drain Kit")).addFlag(ItemFlag.HIDE_POTION_EFFECTS)
                                .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Click to claim")))
                .onClick(event -> {
                    event.setCancelled(true);
                    if(event.getClick().isRightClick()){
                        Bukkit.getScheduler().runTaskAsynchronously(AithonKits.getInstance(), () -> {
                            currentPreview = previewKitInventory(TextStyle.style("<#FFD1A3>Preset Drain Kit Preview"));
                            configUtil.loadPremade("drain", currentPreview);
                            Bukkit.getScheduler().runTask(AithonKits.getInstance(), () -> player.openInventory(currentPreview));
                        });
                        return;
                    }
                    if (configUtil.loadPremadeKit("drain", player.getInventory())) {
                        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        player.sendActionBar(TextStyle.style("<#72FF9D>Kit loaded successfully"));
                        return;
                    }
                    player.sendRichMessage("<red>An error occurred.");
                }));

        this.menu.button(14, Button.button(
                        ItemBuilder.item(Material.ENDER_CHEST).name(TextStyle.style("<#FFD1A3>Preset Enderchest"))
                                .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Click to claim"),
                                        MiniMessage.miniMessage().deserialize("<red>⚠ The Kit gets loaded into your enderchest")
                                        ))
                .onClick(event -> {
                    event.setCancelled(true);
                    if(event.getClick().isRightClick()){
                        Bukkit.getScheduler().runTaskAsynchronously(AithonKits.getInstance(), () -> {
                            currentPreview = previewEnderchestMenu(TextStyle.style("<#FFD1A3>Preset Enderchest Preview"));
                            configUtil.loadPremadeEnderchest("enderchest", currentPreview);
                            Bukkit.getScheduler().runTask(AithonKits.getInstance(), () -> player.openInventory(currentPreview));
                        });
                        return;
                    }
                    if(configUtil.loadPremadeEnderchest("enderchest", player.getEnderChest())) {
                        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                        player.sendActionBar(TextStyle.style("<#72FF9D>Enderchest loaded successfully"));
                        return;
                    }
                    player.sendRichMessage("<red>An error occurred.");
                }));
    }

    public Inventory previewEnderchestMenu(Component name){
        Inventory inventory = Bukkit.createInventory(null, 36, name);
        for(int x = 27; x < 36; x++){
            inventory.setItem(x, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        }
        return inventory;
    }
    public Inventory previewKitInventory(Component name){
        Inventory inventory = Bukkit.createInventory(null, 45, name);
        inventory.setItem(41, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        inventory.setItem(42, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        inventory.setItem(43, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        inventory.setItem(44, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        return inventory;
    }

    public void open(){
        this.menu.open(player);
    }



    public class premadeKitListener implements Listener {
        @EventHandler
        public void onClick(InventoryClickEvent event){
            if(event.getInventory() == currentPreview){
                event.setCancelled(true);
            }
        }
        @EventHandler
        public void onClose(InventoryCloseEvent event){
            if(event.getInventory() == currentPreview){
                Bukkit.getScheduler().runTaskLater(AithonKits.getInstance(), PremadeKitsMenu.this::open, 1L);
            }
        }
    }
}