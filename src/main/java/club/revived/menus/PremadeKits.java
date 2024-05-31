package club.revived.menus;

import club.revived.WeirdoKits;
import club.revived.config.Files;
import club.revived.config.SoundConfig;
import club.revived.util.MessageUtil;
import club.revived.util.PageSound;
import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.menu.Button;
import dev.manere.utils.menu.MenuBase;
import dev.manere.utils.menu.normal.Menu;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

public class PremadeKits {

    private final Player player;
    private final MenuBase<?> menu;

    public PremadeKits(Player player){
        this.player = player;
        this.menu = Menu.menu(TextStyle.style("<gold>Premade Kits"), 27);
        init();
    }

    @NotNull
    public FileConfiguration soundConfig() {
        return Files.config(Files.create(Files.file("sounds.yml")));
    }

    private void init(){
        this.menu.border(Button.button(
                ItemBuilder.item(Material.GRAY_STAINED_GLASS_PANE).name(""))
                .onClick(event -> {
                    event.setCancelled(true);
                }),
                "X X X X X X X X X",
                "",
                "X X X X X X X X X"
        );

        this.menu.onClose(event -> {
            Bukkit.getScheduler().runTaskLater(WeirdoKits.getInstance(), () -> {
                KitMenu kitMenu = new KitMenu(player);
                kitMenu.open();
            },1L);
        });

        this.menu.button(10, Button.button(
                ItemBuilder.item(Material.LAVA_BUCKET).name(TextStyle.style("<gold>UHC")))
                .onClick(event -> {
                    event.setCancelled(true);
                    player.getInventory().clear();
                    player.getInventory().setHelmet(ItemBuilder.item(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                    player.getInventory().setChestplate(ItemBuilder.item(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                    player.getInventory().setLeggings(ItemBuilder.item(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                    player.getInventory().setBoots(ItemBuilder.item(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build());
                    player.getInventory().setItem(0, ItemBuilder.item(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 4).build());
                    player.getInventory().setItem(1, ItemBuilder.item(Material.DIAMOND_AXE).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
                    player.getInventory().setItem(2, new ItemStack(Material.LAVA_BUCKET));
                    player.getInventory().setItem(3, new ItemStack(Material.WATER_BUCKET));
                    player.getInventory().setItem(4, new ItemStack(Material.COBWEB, 16));
                    player.getInventory().setItem(5, ItemBuilder.item(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 1).build());
                    player.getInventory().setItem(6, ItemBuilder.item(Material.CROSSBOW).addEnchantment(Enchantment.PIERCING, 2).build());
                    player.getInventory().setItem(7, ItemBuilder.item(Material.GOLDEN_APPLE).build());
                    player.getInventory().setItem(8, new ItemStack(Material.COBBLESTONE, 64));
                    player.getInventory().setItem(17, new ItemStack(Material.ARROW, 17));
                    player.getInventory().setItem(21, new ItemStack(Material.WATER_BUCKET));
                    player.getInventory().setItem(26, new ItemStack(Material.SHIELD));
                    player.getInventory().setItem(28, ItemBuilder.item(Material.DIAMOND_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 3).build());
                    player.getInventory().setItem(29, new ItemStack(Material.LAVA_BUCKET));
                    player.getInventory().setItem(30, new ItemStack(Material.WATER_BUCKET));
                    player.getInventory().setItem(35, new ItemStack(Material.OAK_PLANKS, 64));
                    SoundConfig.playCSound(soundConfig().getString("premade_kit_claim.sound"), soundConfig().getInt("premade_kit_claim.pitch"),soundConfig().getInt("premade_kit_claim.volume"), player);
                    new MessageUtil().message(player, "messages.premade_kit_claim");
                    for(Player global : Bukkit.getOnlinePlayers()) {
                            new MessageUtil().brcmessage(player,global, "broadcast_messages.premade_kit_claim");
                    }

                })
        );
        this.menu.button(12, Button.button(
                ItemBuilder.item(Material.DIAMOND_SWORD).name(TextStyle.style("<gold>Sword")).addFlag(ItemFlag.HIDE_ATTRIBUTES))
                .onClick(event -> {
                    event.setCancelled(true);
                    player.getInventory().clear();
                    player.getInventory().setHelmet(ItemBuilder.item(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
                    player.getInventory().setChestplate(ItemBuilder.item(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
                    player.getInventory().setLeggings(ItemBuilder.item(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
                    player.getInventory().setBoots(ItemBuilder.item(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
                    player.getInventory().setItem(0, ItemBuilder.item(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 4).build());
                    SoundConfig.playCSound(soundConfig().getString("premade_kit_claim.sound"), soundConfig().getInt("premade_kit_claim.pitch"),soundConfig().getInt("premade_kit_claim.volume"), player);
                    new MessageUtil().message(player, "messages.premade_kit_claim");
                    for(Player global : Bukkit.getOnlinePlayers()) {
                            new MessageUtil().brcmessage(player,global, "broadcast_messages.premade_kit_claim");
                    }
                })
        );

        this.menu.button(14, Button.button(
                ItemBuilder.item(Material.CROSSBOW).name(TextStyle.style("<gold>Drain PvP")).addFlag(ItemFlag.HIDE_ENCHANTS).addEnchantment(Enchantment.ARROW_DAMAGE,1))
                .onClick(event -> {
                    event.setCancelled(true);
                    player.getInventory().clear();
                    player.getInventory().setHelmet(ItemBuilder.item(Material.NETHERITE_HELMET).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setChestplate(ItemBuilder.item(Material.NETHERITE_CHESTPLATE).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setLeggings(ItemBuilder.item(Material.NETHERITE_LEGGINGS).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setBoots(ItemBuilder.item(Material.NETHERITE_BOOTS).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(0, ItemBuilder.item(Material.NETHERITE_SWORD).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setItem(1, new ItemStack(Material.END_CRYSTAL, 64));
                    player.getInventory().setItem(2, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(3, new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(4, new ItemStack(Material.GOLDEN_APPLE,64));
                    player.getInventory().setItem(5, new ItemStack(Material.OBSIDIAN, 64));
                    player.getInventory().setItem(6, ItemBuilder.item(Material.SHIELD).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setItem(7, new ItemStack(Material.RESPAWN_ANCHOR, 64));
                    player.getInventory().setItem(8, new ItemStack(Material.GLOWSTONE, 64));
                    player.getInventory().setItem(27, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(18, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(29, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(20, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(19, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(9, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(10, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(11, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(28, new ItemStack(Material.END_CRYSTAL, 64));
                    player.getInventory().setItem(30, new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(31, new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(22, new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(20, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(21, new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(12, new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(13, new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(32, new ItemStack(Material.OBSIDIAN, 64));
                    player.getInventory().setItem(34, ItemBuilder.item(Material.ELYTRA).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setItem(35, ItemBuilder.item(Material.FIREWORK_ROCKET, 64).amount(64).build());
                    player.getInventory().setItem(33, ItemBuilder.item(Material.NETHERITE_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setItem(23, new ItemStack(Material.ENDER_CHEST, 64));
                    player.getInventory().setItem(14, ItemBuilder.item(Material.NETHERITE_AXE).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setItem(24, ItemBuilder.item(Material.CROSSBOW).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.MULTISHOT, 1).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                    ItemStack arrow = (ItemBuilder.item(Material.TIPPED_ARROW, 64)).build();
                    PotionMeta potionMeta = (PotionMeta)arrow.getItemMeta();
                    potionMeta.setBasePotionData(new PotionData(PotionType.SLOW_FALLING));
                    arrow.setItemMeta((ItemMeta)potionMeta);
                    player.getInventory().setItem(15, arrow);

                    ItemStack invis = (ItemBuilder.item(Material.SPLASH_POTION).build());
                    PotionMeta invism = (PotionMeta)invis.getItemMeta();
                    invism.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true,false));
                    invis.setItemMeta((ItemMeta)invism);
                    player.getInventory().setItem(16, invis);
                    player.getInventory().setItem(17, new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
                    ItemStack strenght = (ItemBuilder.item(Material.SPLASH_POTION).build());
                    PotionMeta strnghtm = (PotionMeta)strenght.getItemMeta();
                    strnghtm.setBasePotionData(new PotionData(PotionType.STRENGTH, false,true));
                    strenght.setItemMeta((ItemMeta)strnghtm);
                    player.getInventory().setItem(26, strenght);
                    ItemStack speed = (ItemBuilder.item(Material.SPLASH_POTION).build());
                    PotionMeta speedm = (PotionMeta)strenght.getItemMeta();
                    speedm.setBasePotionData(new PotionData(PotionType.SPEED, false,true));
                    speed.setItemMeta((ItemMeta)speedm);
                    player.getInventory().setItem(25, speed);
                    SoundConfig.playCSound(soundConfig().getString("premade_kit_claim.sound"), soundConfig().getInt("premade_kit_claim.pitch"),soundConfig().getInt("premade_kit_claim.volume"), player);
                    new MessageUtil().message(player, "messages.premade_kit_claim");
                    for(Player global : Bukkit.getOnlinePlayers()) {
                            new MessageUtil().brcmessage(player, global, "broadcast_messages.premade_kit_claim");
                    }




                })
        );

        this.menu.button(16, Button.button(
                        ItemBuilder.item(Material.END_CRYSTAL).name(TextStyle.style("<gold>Crystal PvP")))
                .onClick(event -> {
                    event.setCancelled(true);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
                    player.getInventory().clear();
                    for(int i = 9; i < 36; i++){
                        player.getInventory().setItem(i, new ItemStack(Material.TOTEM_OF_UNDYING));
                    }
                    player.getInventory().setHelmet(ItemBuilder.item(Material.NETHERITE_HELMET).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setChestplate(ItemBuilder.item(Material.NETHERITE_CHESTPLATE).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setLeggings(ItemBuilder.item(Material.NETHERITE_LEGGINGS).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setBoots(ItemBuilder.item(Material.NETHERITE_BOOTS).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setItemInOffHand(new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(0, ItemBuilder.item(Material.NETHERITE_SWORD).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setItem(1, new ItemStack(Material.END_CRYSTAL, 64));
                    player.getInventory().setItem(2, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(28, new ItemStack(Material.END_CRYSTAL, 64));
                    player.getInventory().setItem(3, new ItemStack(Material.TOTEM_OF_UNDYING));
                    player.getInventory().setItem(4, new ItemStack(Material.GOLDEN_APPLE,64));
                    player.getInventory().setItem(5, new ItemStack(Material.OBSIDIAN, 64));
                    player.getInventory().setItem(7, new ItemStack(Material.RESPAWN_ANCHOR, 64));
                    player.getInventory().setItem(8, new ItemStack(Material.GLOWSTONE, 64));
                    player.getInventory().setItem(29, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(30, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(29, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(20, new ItemStack(Material.ENDER_PEARL    , 64));
                    player.getInventory().setItem(21, new ItemStack(Material.ENDER_PEARL, 16));
                    player.getInventory().setItem(32, new ItemStack(Material.OBSIDIAN, 64));
                    player.getInventory().setItem(16, new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
                    player.getInventory().setItem(17, new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
                    player.getInventory().setItem(26, new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
                    player.getInventory().setItem(34, ItemBuilder.item(Material.ELYTRA).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).build());
                    player.getInventory().setItem(35, ItemBuilder.item(Material.FIREWORK_ROCKET, 64).amount(64).build());
                    player.getInventory().setItem(6, ItemBuilder.item(Material.NETHERITE_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).build());
                    SoundConfig.playCSound(soundConfig().getString("premade_kit_claim.sound"), soundConfig().getInt("premade_kit_claim.pitch"),soundConfig().getInt("premade_kit_claim.volume"), player);
                    new MessageUtil().message(player, "messages.premade_kit_claim");
                    for(Player global : Bukkit.getOnlinePlayers()) {
                            new MessageUtil().brcmessage(player,global, "broadcast_messages.premade_kit_claim");
                    }
                })
        );
    }
    public void open(){
        this.menu.open(this.player);
        new PageSound().playPageSound(player);
    }
}
