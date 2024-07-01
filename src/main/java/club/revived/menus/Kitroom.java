package club.revived.menus;

import club.revived.AithonKits;
import club.revived.menus.TabItems.*;
import club.revived.miscellaneous.Itemlist.ScrollDownItem;
import club.revived.miscellaneous.Itemlist.ScrollUpItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.ScrollGui;
import xyz.xenondevs.invui.gui.TabGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Kitroom {

    private final Player player;
    private final Gui toOpen;

    public Kitroom(Player player) {
        this.player = player;
        Item border = new SimpleItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" "));

        ArrayList<Item> enchantments = new ArrayList<>();
        ArrayList<Item> potions = new ArrayList<>();
        ArrayList<Item> arrows = new ArrayList<>();
        ArrayList<Item> armory = new ArrayList<>();
        ArrayList<Item> consumables = new ArrayList<>();
        ArrayList<Item> explosives = new ArrayList<>();

        for(PotionType potion : Registry.POTION){
            ItemStack defaultArrow = new ItemStack(Material.TIPPED_ARROW, 64);
            PotionMeta potionMeta = (PotionMeta) defaultArrow.getItemMeta();
            potionMeta.setBasePotionType(potion);
            defaultArrow.setItemMeta(potionMeta);
            arrows.add(new SimpleItem(defaultArrow, click -> {
                player.getInventory().addItem(defaultArrow);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));
        }

        for(ItemStack itemStack : armoryList()){
            armory.add(new SimpleItem(itemStack, click -> {
                player.getInventory().addItem(itemStack);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));
        }
        for(ItemStack itemStack : consumablesPage()){
            consumables.add(new SimpleItem(itemStack, click -> {
                player.getInventory().addItem(itemStack);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));
        }
        for(ItemStack itemStack : explosivesPage()){
            explosives.add(new SimpleItem(itemStack, click -> {
                player.getInventory().addItem(itemStack);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));
        }


        for(PotionType potion : Registry.POTION){

            /*
             * Normale Potions
             */

            ItemStack defaultPotion = new ItemStack(Material.POTION);
            PotionMeta potionMeta = (PotionMeta) defaultPotion.getItemMeta();
            potionMeta.setBasePotionType(potion);
            defaultPotion.setItemMeta(potionMeta);
            potions.add(new SimpleItem(defaultPotion, click -> {
                player.getInventory().addItem(defaultPotion);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));

            /*
             * Splash Potions
             */

            ItemStack defaultSplashPotion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta splashPotionMeta = (PotionMeta) defaultPotion.getItemMeta();
            splashPotionMeta.setBasePotionType(potion);
            defaultSplashPotion.setItemMeta(splashPotionMeta);
            potions.add(new SimpleItem(defaultSplashPotion, click -> {
                player.getInventory().addItem(defaultSplashPotion);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));

            /*
             * Lingering Potions
             */

            ItemStack defaultLingeringPotion = new ItemStack(Material.LINGERING_POTION);
            PotionMeta lingeringPotionMeta = (PotionMeta) defaultPotion.getItemMeta();
            lingeringPotionMeta.setBasePotionType(potion);
            defaultLingeringPotion.setItemMeta(lingeringPotionMeta);
            potions.add(new SimpleItem(defaultLingeringPotion, click -> {
                player.getInventory().addItem(defaultLingeringPotion);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));
        }

        for (Enchantment enchantment : Registry.ENCHANTMENT) {
            ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
            meta.addStoredEnchant(enchantment, enchantment.getMaxLevel(), true);
            enchantedBook.setItemMeta(meta);
            enchantments.add(new SimpleItem(enchantedBook, click -> {
                player.getInventory().addItem(enchantedBook);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));
        }

        List<Item> toAddTrims = Arrays.stream(Material.values())
                .filter(material -> !material.isAir() && material.isItem()
                        && material.toString().contains("TRIM"))
                .map(material -> new SimpleItem(new ItemBuilder(material, 64), click -> {
                    player.getInventory().addItem(new ItemStack(material, material.getMaxStackSize()));
                    player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                }))
                .collect(Collectors.toList());

        Gui armoryGui = ScrollGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .setContent(armory)
                .build();

        Gui explosivesGui = ScrollGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .setContent(explosives)
                .build();

        Gui consumablesGui = ScrollGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .setContent(consumables)
                .build();


        Gui potionsGui = ScrollGui.items()
                .setStructure(
                        "x x x x x x x x u",
                        "x x x x x x x x #",
                        "x x x x x x x x #",
                        "x x x x x x x x #",
                        "x x x x x x x x d")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('d', new ScrollDownItem())
                .addIngredient('u', new ScrollUpItem())
                .setContent(potions)
                .build();

        Gui arrowsGui = ScrollGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .setContent(arrows)
                .build();

        Gui gui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(enchantments)
                .build();

        Gui trimGui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(toAddTrims)
                .build();

        toOpen = TabGui.normal()
                .setStructure(
                        "# 0 1 2 3 4 5 6 #",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('6', new TrimTab(6))
                .addIngredient('5', new EnchantsTab(5))
                .addIngredient('1', new PotionsTab(1))
                .addIngredient('2', new ArrowsTab(2))
                .addIngredient('0', new ArmoryTab(0))
                .addIngredient('3', new ConsumablesTab(3))
                .addIngredient('4', new ExplosivesTab(4))
                .setTabs(Arrays.asList(armoryGui, potionsGui, arrowsGui, consumablesGui, explosivesGui, gui, trimGui))
                .build();

    }

    public BukkitRunnable closeTask(){
        return new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().runTaskLater(AithonKits.getInstance(), () -> new KitMenu(player).open(), 1L);
            }
        };
    }

    public void open() {
        Window window = Window.single()
                .setViewer(player)
                .setTitle("§6Miscellaneous Items")
                .setGui(toOpen)
                .addCloseHandler(closeTask())
                .build();

        window.open();
        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 5, 5);
    }

    private ArrayList<ItemStack> armoryList(){
        ArrayList<ItemStack> armoryPage = new ArrayList<>();

        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_PICKAXE)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_AXE)).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.ELYTRA, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3)).build());
        ItemStack firework1 = new ItemStack(Material.FIREWORK_ROCKET, 64);
        FireworkMeta data = (FireworkMeta) firework1.getItemMeta();
        data.setPower(1);
        firework1.setItemMeta(data);
        armoryPage.add(firework1);
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_PICKAXE)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_AXE)).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.BOW)).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PIERCING, 4).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.MULTISHOT, 1).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.BOW)).addEnchantment(Enchantment.ARROW_FIRE, 1).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SHOVEL)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_HOE)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SHOVEL)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_HOE)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        armoryPage.add((dev.manere.utils.item.ItemBuilder.item(Material.SHIELD)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        return armoryPage;
    }

    public ArrayList<ItemStack> consumablesPage(){
        ArrayList<ItemStack> toReturn =  new ArrayList<>();
        toReturn.add(new ItemStack(Material.TOTEM_OF_UNDYING));
        toReturn.add(new ItemStack(Material.GOLDEN_APPLE, 64));
        toReturn.add(new ItemStack(Material.ENDER_PEARL, 16));
        toReturn.add(new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
        toReturn.add(new ItemStack(Material.ENDER_CHEST, 64));
        ItemStack firework1 = new ItemStack(Material.FIREWORK_ROCKET, 64);
        FireworkMeta data = (FireworkMeta) firework1.getItemMeta();
        data.setPower(1);
        firework1.setItemMeta(data);
        toReturn.add(firework1);
        ItemStack firework2 = new ItemStack(Material.FIREWORK_ROCKET, 64);
        FireworkMeta data2 = (FireworkMeta) firework2.getItemMeta();
        data2.setPower(2);
        firework2.setItemMeta(data2);
        toReturn.add(firework2);
        ItemStack firework3 = new ItemStack(Material.FIREWORK_ROCKET, 64);
        FireworkMeta data3 = (FireworkMeta) firework3.getItemMeta();
        data3.setPower(3);
        firework3.setItemMeta(data3);
        toReturn.add(firework3);

        return toReturn;
    }

    public ArrayList<ItemStack> explosivesPage(){
        ArrayList<ItemStack> toReturn =  new ArrayList<>();
        toReturn.add(new ItemStack(Material.END_CRYSTAL, 64));
        toReturn.add(new ItemStack(Material.OBSIDIAN, 64));
        toReturn.add(new ItemStack(Material.RESPAWN_ANCHOR, 64));
        toReturn.add(new ItemStack(Material.GLOWSTONE, 64));
        toReturn.add(new ItemStack(Material.RED_BED));
        toReturn.add(new ItemStack(Material.TNT_MINECART));
        toReturn.add(new ItemStack(Material.RAIL, 64));
        toReturn.add(new ItemStack(Material.ACTIVATOR_RAIL, 64));
        return toReturn;
    }
}
