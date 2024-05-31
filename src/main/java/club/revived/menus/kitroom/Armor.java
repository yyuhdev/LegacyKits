package club.revived.menus.kitroom;

import club.revived.menus.KitMenu;
import club.revived.util.PageSound;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Armor{

    private final Player player;
    private final MenuBase<?> menu;

    public Armor(Player player){
        this.player  = player;
        this.menu = Menu.menu(TextStyle.style("<gold>Kit Room"), 6*9);
        init();

    }

    private void init(){
        ArrayList<ItemStack> kitRoomItems = new ArrayList<>();
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.MULTISHOT, 1).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.ELYTRA, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3)).build());
        kitRoomItems.add((ItemBuilder.item(Material.FIREWORK_ROCKET, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        ItemStack arrow = (ItemBuilder.item(Material.TIPPED_ARROW, 64)).build();
        PotionMeta potionMeta = (PotionMeta)arrow.getItemMeta();
        potionMeta.setBasePotionData(new PotionData(PotionType.SLOW_FALLING));
        arrow.setItemMeta((ItemMeta)potionMeta);
        kitRoomItems.add(arrow);
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.ENDER_PEARL, 16)).build());
        kitRoomItems.add((ItemBuilder.item(Material.ENDER_PEARL, 16)).build());
        kitRoomItems.add((ItemBuilder.item(Material.DIAMOND_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.DIAMOND_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.DIAMOND_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.DIAMOND_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.BOW)).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PIERCING, 4).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_PICKAXE)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.OBSIDIAN, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.END_CRYSTAL, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.RESPAWN_ANCHOR, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.GLOWSTONE, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.BOW)).addEnchantment(Enchantment.ARROW_FIRE, 1).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_AXE)).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.EXPERIENCE_BOTTLE, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.EXPERIENCE_BOTTLE, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.GOLDEN_APPLE, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.GOLDEN_APPLE, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.SHIELD)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.ENDER_CHEST, 64)).build());
        kitRoomItems.add((ItemBuilder.item(Material.TOTEM_OF_UNDYING)).build());
        kitRoomItems.add((ItemBuilder.item(Material.TOTEM_OF_UNDYING)).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.DIAMOND_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((ItemBuilder.item(Material.DIAMOND_LEGGINGS    )).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());

        int slot = 0;
        for (ItemStack kitRoomItem : kitRoomItems) {
            this.menu.inventory().setItem(slot, kitRoomItem);
            slot++;
        }

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

        this.menu.button(45, Button.button(
                        ItemBuilder.item(Material.BARRIER)
                                .name(TextStyle.style("<red>Back")))
                .onClick(event -> {
                    KitMenu kitMenu = new KitMenu(player);
                    kitMenu.open();
                }));

        this.menu.button(47, Button.button(
                        ItemBuilder.item(Material.NETHERITE_SWORD)
                                .name(TextStyle.style("<gold>Armory")).addFlag(ItemFlag.HIDE_ATTRIBUTES).addEnchantment(Enchantment.ARROW_DAMAGE,1).addFlag(ItemFlag.HIDE_ENCHANTS))
                .onClick(event -> {
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
                    Armor armor = new Armor(player);
                    armor.open();
                })
        );

        this.menu.button(50, Button.button(
                        ItemBuilder.item(Material.ARROW)
                                .name(TextStyle.style("<gold>Arrows")))
                .onClick(event -> {
                    Arrows arrows = new Arrows(player);
                    arrows.open();
                }));
    }

    public void open(){
        this.menu.open(player);
        new PageSound().playPageSound(player);
    }
}