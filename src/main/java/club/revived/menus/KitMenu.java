package club.revived.menus;

import club.revived.AithonKits;
import club.revived.util.MessageUtil;
import club.revived.util.PageSound;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.List;

public class KitMenu {
    private final Player player;
    private final MenuBase<?> menu;
    private final AithonKits kits = AithonKits.getInstance();
    public Inventory currentPreview;

    public KitMenu(Player player) {
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<#FFD1A3>Kits"), 6 * 9);
        AithonKits.getInstance().getServer().getPluginManager().registerEvents(new menuListener(), AithonKits.getInstance());
        init();
    }

    public void init() {
        this.menu.border(Button.button(
                        ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE)
                                .name("")
                ).onClick(event -> event.setCancelled(true)),
                "X X X X X X X X X",
                "X . . . . . . . X",
                "X . . . . . . . X",
                "X X X X X X X X X",
                "X . . X . X . . X",
                "X X X X X X X X X"
        );
        this.menu.button(43, Button.button(
                        ItemBuilder.item(Material.NETHERITE_HELMET).name(TextStyle.style("<#FFD1A3>\uD83D\uDD31 Preset Kits"))
                                .lore(List.of(MiniMessage.miniMessage().deserialize("<gray><i>● Click to open")))
                                .addFlag(ItemFlag.HIDE_ATTRIBUTES))
                .onClick(event -> {
                    event.setCancelled(true);
                    new PremadeKitsMenu(player).open();
                })
        );

        this.menu.button(42, Button.button(
                        ItemBuilder.item(Material.RED_DYE).name(TextStyle.style("<red>✂ Clear Inventory")).lore(
                                MiniMessage.miniMessage().deserialize("<gray><i>● Shift click")))
                .onClick(event -> {
                    event.setCancelled(true);
                    if(event.getClick().isShiftClick())
                        player.getInventory().clear();
                }));


        for (int x = 10; x < 17; x++) {
            int i = x;
            this.menu.button(x, Button.button(ItemBuilder.item(Material.CHEST)
                    .name(TextStyle.style("<#FFD1A3>\uD83C\uDFF9 Kit " + (x - 9)))
                    .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Left click to load"), MiniMessage.miniMessage().deserialize("<gray><i>● Right click to edit"))
            ).onClick(event -> {
                event.setCancelled(true);
                if (event.getClick().isRightClick()) {

                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(player))
                            MessageUtil.broadcast(player, global, "broadcast_messages.kit_editor_open");
                    }

                    KitEditor editor = new KitEditor(player, i - 9);
                    editor.open();
                    return;
                }
                kits.getKitLoader().load(player, String.valueOf(i-9));
            }));
        }

        for (int x = 19; x < 26; x++) {
            int i = x;
            this.menu.button(x, Button.button(ItemBuilder.item(Material.ENDER_CHEST)
                    .name(TextStyle.style("<#FFD1A3>\uD83D\uDDE1 Enderchest " + (x - 18)))
                    .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Left click to load"), MiniMessage.miniMessage().deserialize("<gray><i>● Right click to edit"))
            ).onClick(event -> {
                event.setCancelled(true);

                if(event.getClick().isRightClick()) {

                    for (Player global : Bukkit.getOnlinePlayers()) {
                        if (global.getLocation().getNearbyPlayers(250).contains(player))
                            MessageUtil.broadcast(player, global, "broadcast_messages.enderchest_editor_open");
                    }

                    EnderchestEditor editor = new EnderchestEditor(player, i - 18);
                    editor.open();
                    return;
                }

                kits.getKitLoader().loadEnderChest(player, String.valueOf(i-18));
            }));

            this.menu.button(38, Button.button(ItemBuilder.item(Material.END_CRYSTAL)
                    .name(TextStyle.style("<#FFD1A3>\uD83E\uDE93 Kit Room"))
                    .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Get Items for"),
                            MiniMessage.miniMessage().deserialize("<gray><i>your kits here"))
            ).onClick(event -> {
                event.setCancelled(true);
                new Kitroom(player).open();

                for (Player global : Bukkit.getOnlinePlayers()) {
                    if (global.getLocation().getNearbyPlayers(250).contains(player))
                        MessageUtil.broadcast(player, global, "broadcast_messages.kit_room_open");
                }
            }));

            this.menu.button(40, Button.button(ItemBuilder.item(Material.ENDER_CHEST)
                    .name(TextStyle.style("<#FFD1A3>\uD83D\uDDE1 Enderchest Preview"))
                    .lore(MiniMessage.miniMessage().deserialize("<gray><i>● Click to look at your enderchest"))
            ).onClick(event -> {
                event.setCancelled(true);
                Bukkit.getScheduler().runTaskAsynchronously(AithonKits.getInstance(), () -> {
                    currentPreview = previewEnderchestMenu(TextStyle.style("<#FFD1A3><player>'s Enderchest"
                            .replace("<player>", player.getName())
                    ));
                    for(int y = 0; y < 27; y++){
                        currentPreview.setItem(y, player.getEnderChest().getItem(y));
                    }
                    Bukkit.getScheduler().runTask(AithonKits.getInstance(), () -> player.openInventory(currentPreview));
                });
            }));
        }
    }

    public Inventory previewEnderchestMenu(Component name){
        Inventory inventory = Bukkit.createInventory(null, 36, name);
        for(int x = 27; x < 36; x++){
            inventory.setItem(x, ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        }
        return inventory;
    }

    public void open() {
        this.menu.open(this.player);
        new PageSound().play(player);
    }

    public class menuListener implements Listener {
        @EventHandler
        public void onClick(InventoryClickEvent event){
            if(event.getInventory() == currentPreview){
                event.setCancelled(true);
            }
        }
        @EventHandler
        public void onClose(InventoryCloseEvent event){
            if(event.getInventory() == currentPreview){
                Bukkit.getScheduler().runTaskLater(AithonKits.getInstance(), KitMenu.this::open,1L);
            }
        }
    }
}
