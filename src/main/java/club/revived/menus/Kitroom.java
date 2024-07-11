package club.revived.menus;

import club.revived.AithonKits;
import club.revived.items.Shulker;
import club.revived.menus.tabs.*;
import club.revived.menus.items.ScrollDownItem;
import club.revived.menus.items.ScrollUpItem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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


public class Kitroom  {

    private final Player player;
    private final Gui toOpen;

    public Kitroom(Player player) {
        this.player = player;
        Item border = new SimpleItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" "));

        ArrayList<Item> arrows = new ArrayList<>();
        ArrayList<Item> armory = new ArrayList<>();
        ArrayList<Item> consumables = new ArrayList<>();
        ArrayList<Item> explosives = new ArrayList<>();
        ArrayList<Item> shulkers = new ArrayList<>();
        ArrayList<Item> pvp = new ArrayList<>();
        ArrayList<Item> potions = new ArrayList<>();

        Registry.POTION.forEach(potionType -> {
            if(potionType == PotionType.UNCRAFTABLE) return;
            if(potionType == PotionType.MUNDANE) return;
            if(potionType == PotionType.THICK) return;
            if(potionType == PotionType.LUCK) return;
            if(potionType == PotionType.AWKWARD) return;
            if(potionType == PotionType.WATER_BREATHING) return;
            if(potionType == PotionType.LONG_WATER_BREATHING) return;
            if(potionType == PotionType.WATER) return;
            if(potionType == PotionType.INVISIBILITY) return;
            if(potionType == PotionType.LONG_INVISIBILITY) return;
            if(potionType == PotionType.NIGHT_VISION) return;
            if(potionType == PotionType.LONG_NIGHT_VISION) return;
            ItemStack defaultSplashPotion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta splashPotionMeta = (PotionMeta) defaultSplashPotion.getItemMeta();
            splashPotionMeta.setBasePotionType(potionType);
            defaultSplashPotion.setItemMeta(splashPotionMeta);
            potions.add(new SimpleItem(defaultSplashPotion, click -> {
                player.getInventory().addItem(defaultSplashPotion);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));
        });

        Registry.POTION.forEach(potionType -> {
            if(potionType == PotionType.UNCRAFTABLE) return;
            if(potionType == PotionType.MUNDANE) return;
            if(potionType == PotionType.THICK) return;
            if(potionType == PotionType.LUCK) return;
            if(potionType == PotionType.AWKWARD) return;
            if(potionType == PotionType.WATER_BREATHING) return;
            if(potionType == PotionType.LONG_WATER_BREATHING) return;
            if(potionType == PotionType.WATER) return;
            if(potionType == PotionType.INVISIBILITY) return;
            if(potionType == PotionType.LONG_INVISIBILITY) return;
            if(potionType == PotionType.NIGHT_VISION) return;
            if(potionType == PotionType.LONG_NIGHT_VISION) return;
            ItemStack defaultArrow = new ItemStack(Material.TIPPED_ARROW, 64);
            PotionMeta potionMeta = (PotionMeta) defaultArrow.getItemMeta();
            potionMeta.setBasePotionType(potionType);
            defaultArrow.setItemMeta(potionMeta);
            arrows.add(new SimpleItem(defaultArrow, click -> {
                player.getInventory().addItem(defaultArrow);
                player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
            }));
        });

        ItemStack arrow = new ItemStack(Material.ARROW, 64);
        ItemStack spectralArrow = new ItemStack(Material.SPECTRAL_ARROW, 64);

        arrows.add(new SimpleItem(arrow, click -> {
            player.getInventory().addItem(arrow);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
        }));

        arrows.add(new SimpleItem(spectralArrow, click -> {
            player.getInventory().addItem(spectralArrow);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
        }));

        cpvpItems().forEach(item -> pvp.add(new SimpleItem(
                item, click -> {
            player.getInventory().addItem(item);
            player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
        }
        )));


        for(ItemStack itemStack : shulkerPage()){
            shulkers.add(new SimpleItem(itemStack, click -> {
                player.getInventory().addItem(itemStack);
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
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
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
                .build();

        Gui trimGui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(shulkers)
                .build();

        toOpen = TabGui.normal()
                .setStructure(
                        "# 0 1 2 3 4 5 6 #",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "# # # # # # # # #"
                )
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('6', new ShulkerTab(6))
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

    @SuppressWarnings("deprecation")
    public void open() {
        Window window = Window.single()
                .setViewer(player)
                .setTitle(ChatColor.of("#FFD1A3") + "Kitroom")
                .setGui(toOpen)
                .addCloseHandler(closeTask())
                .build();

        window.open();
        player.playSound(player, Sound.ENTITY_CHICKEN_EGG, 5, 5);
    }

    private ArrayList<ItemStack> cpvpItems(){
        ArrayList<ItemStack> kitRoomItems = new ArrayList<>();
        kitRoomItems.add(dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.MULTISHOT, 1).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.ELYTRA, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.FIREWORK_ROCKET, 64)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        ItemStack arrow = (dev.manere.utils.item.ItemBuilder.item(Material.TIPPED_ARROW, 64)).build();
        PotionMeta potionMeta = (PotionMeta)arrow.getItemMeta();
        potionMeta.setBasePotionType(PotionType.LONG_SLOW_FALLING);
        arrow.setItemMeta(potionMeta);
        kitRoomItems.add(arrow);
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.ENDER_PEARL, 16)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.BOW)).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PIERCING, 4).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_PICKAXE)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.OBSIDIAN, 64)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.END_CRYSTAL, 64)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.RESPAWN_ANCHOR, 64)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.GLOWSTONE, 64)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.BOW)).addEnchantment(Enchantment.ARROW_FIRE, 1).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_AXE)).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.EXPERIENCE_BOTTLE, 64)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.GOLDEN_APPLE, 64)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.SHIELD)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.ENDER_CHEST, 64)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.TOTEM_OF_UNDYING)).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        kitRoomItems.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_LEGGINGS    )).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        return kitRoomItems;
    }

    private ArrayList<ItemStack> armoryList(){
        ArrayList<ItemStack> toReturn = new ArrayList<>();

        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_PICKAXE)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_AXE)).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.ELYTRA, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3)).build());
        ItemStack firework1 = new ItemStack(Material.FIREWORK_ROCKET, 64);
        FireworkMeta data = (FireworkMeta) firework1.getItemMeta();
        data.setPower(1);
        firework1.setItemMeta(data);
        toReturn.add(firework1);
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_PICKAXE)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_AXE)).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.BOW)).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PIERCING, 4).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.MULTISHOT, 1).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.BOW)).addEnchantment(Enchantment.ARROW_FIRE, 1).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.NETHERITE_SHOVEL)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.DIAMOND_SHOVEL)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add((dev.manere.utils.item.ItemBuilder.item(Material.SHIELD)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
        toReturn.add(dev.manere.utils.item.ItemBuilder.item(Material.TRIDENT).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.CHANNELING, 1).addEnchantment(Enchantment.LOYALTY, 3).addEnchantment(Enchantment.IMPALING, 5).build());
        toReturn.add(dev.manere.utils.item.ItemBuilder.item(Material.TRIDENT).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.RIPTIDE, 3).addEnchantment(Enchantment.IMPALING, 5).build());
        return toReturn;
    }

    public ArrayList<ItemStack> consumablesPage(){
        ArrayList<ItemStack> toReturn =  new ArrayList<>();
        toReturn.add(new ItemStack(Material.TOTEM_OF_UNDYING));
        toReturn.add(new ItemStack(Material.GOLDEN_APPLE, 64));
        toReturn.add(new ItemStack(Material.ENDER_PEARL, 16));
        toReturn.add(new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
        toReturn.add(new ItemStack(Material.ENDER_CHEST, 64));
        toReturn.add(new ItemStack(Material.ICE, 64));
        toReturn.add(new ItemStack(Material.BUCKET));
        toReturn.add(new ItemStack(Material.AXOLOTL_BUCKET));
        toReturn.add(new ItemStack(Material.PUFFERFISH_BUCKET));
        toReturn.add(new ItemStack(Material.WATER_BUCKET));
        toReturn.add(new ItemStack(Material.LAVA_BUCKET));
        toReturn.add(new ItemStack(Material.POWDER_SNOW_BUCKET));
        toReturn.add(new ItemStack(Material.MILK_BUCKET));
        toReturn.add(new ItemStack(Material.SHULKER_BOX));
        toReturn.add(new ItemStack(Material.COBWEB, 64));
        toReturn.add(new ItemStack(Material.SPONGE, 64));
        toReturn.add(new ItemStack(Material.HONEY_BOTTLE, 16));
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
        toReturn.add(new ItemStack(Material.TNT, 64));
        toReturn.add(new ItemStack(Material.PISTON, 64));
        toReturn.add(new ItemStack(Material.REDSTONE_BLOCK, 64));
        toReturn.add(new ItemStack(Material.IRON_DOOR, 64));
        toReturn.add(new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE, 64));
        toReturn.add(new ItemStack(Material.OAK_BOAT));
        return toReturn;
    }

    public ArrayList<ItemStack> shulkerPage(){
        ArrayList<ItemStack> toReturn =  new ArrayList<>();
        toReturn.add(Shulker.crystalShulker());
        toReturn.add(Shulker.obsidian());
        toReturn.add(Shulker.anchorShulker());
        toReturn.add(Shulker.glowstoneShulker());
        toReturn.add(Shulker.pearlShulker());
        toReturn.add(Shulker.xpShulker());
        toReturn.add(Shulker.totemShulker());
        toReturn.add(Shulker.gapShulker());
        toReturn.add(Shulker.strenghtShulker());
        toReturn.add(Shulker.speedShulker());
        toReturn.add(Shulker.invisShulker());
        toReturn.add(Shulker.mixedShulker());

        return toReturn;
    }
}
