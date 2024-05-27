package club.revived.menus.kitroom;

import club.revived.menus.KitEditor;
import club.revived.menus.KitMenu;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.checkerframework.checker.units.qual.A;

public class Consumables {
    private final Player player;
    private final MenuBase<?> menu;

    public Consumables(Player player){
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<gold>Kit Room"), 6*9);
        init();
    }

    private void  init(){

        this.menu.fill(ItemBuilder.item(Material.GOLDEN_APPLE).amount(64),
                "X X X X X X X X X",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . ."
        );
        this.menu.fill(ItemBuilder.item(Material.ENDER_PEARL).amount(16),
                ". . . . . . . . .",
                "X X X X X X X X X",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . ."
        );
        this.menu.fill(ItemBuilder.item(Material.EXPERIENCE_BOTTLE).amount(64),
                ". . . . . . . . .",
                ". . . . . . . . .",
                "X X X X X X X X X",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . ."
        );

        this.menu.fill(ItemBuilder.item(Material.ENDER_CHEST).amount(64),
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                "X X X X X X X X X",
                ". . . . . . . . .",
                ". . . . . . . . ."
        );
        this.menu.fill(ItemBuilder.item(Material.GLOWSTONE).amount(64),
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                "X X X X X X X X X",
                ". . . . . . . . ."
        );

        this.menu.border(Button.button(
                                ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE))
                        .onClick(event -> {
                            event.setCancelled(true);
                        }),
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". . . . . . . . .",
                ". X . . . . . X ."
        );

        this.menu.button(50, Button.button(
                        ItemBuilder.item(Material.ARROW)
                                .name(TextStyle.style("<gold>Arrows")))
                .onClick(event -> {
                    Arrows arrows = new Arrows(player);
                    arrows.open();
                }));

        this.menu.button(45, Button.button(
                        ItemBuilder.item(Material.BARRIER)
                                .name(TextStyle.style("<red>Back")))
                .onClick(event -> {
                    event.setCancelled(true);
                    KitMenu kitMenu = new KitMenu(player);
                    kitMenu.open();
                }));
        this.menu.button(51, Button.button(
                        ItemBuilder.item(Material.END_CRYSTAL).name(TextStyle.style("<gold>Explosives")))
                .onClick(event -> {
                    Explosives explosives = new Explosives(player);
                    explosives.open();
                })
        );

        this.menu.button(53, Button.button(
                        ItemBuilder.item(Material.STRUCTURE_VOID).name(TextStyle.style("<gold>Refill")))
                .onClick(event -> {
                    Consumables consumables = new Consumables(player);
                    consumables.open();
                })
        );

        this.menu.button(47, Button.button(
                        ItemBuilder.item(Material.NETHERITE_SWORD)
                                .name(TextStyle.style("<gold>Armory")).addFlag(ItemFlag.HIDE_ATTRIBUTES).addEnchantment(Enchantment.ARROW_DAMAGE,1).addFlag(ItemFlag.HIDE_ENCHANTS))
                .onClick(event -> {
                    event.setCancelled(true);
                    Armor armor = new Armor(player);
                    armor.open();
                }));

        this.menu.button(48, Button.button(
                        ItemBuilder.item(Material.SPLASH_POTION).name(TextStyle.style("<gold>Potions")))
                .onClick(event -> {
                    Potions potions = new Potions(player);
                    potions.open();
                })
        );
        this.menu.button(49, Button.button(
                        ItemBuilder.item(Material.ENDER_PEARL).name(TextStyle.style("<gold>Consumables")))
                .onClick(event -> {
                    Consumables consumables = new Consumables(player);
                    consumables.open();
                })
        );
    }

    public void open(){
        this.menu.open(player);
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
    }
}
