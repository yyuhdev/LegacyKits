package club.revived.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import club.revived.WeirdoKits;
import club.revived.util.ItemUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class InventoryClickListener implements Listener {
    private final ArrayList<ItemStack> kitRoomItems = new ArrayList<>();

    public InventoryClickListener() {
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.MULTISHOT, 1).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.ELYTRA, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.FIREWORK_ROCKET, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        ItemStack arrow = (new ItemUtil(Material.TIPPED_ARROW, 64)).toItemStack();
        PotionMeta potionMeta = (PotionMeta)arrow.getItemMeta();
        potionMeta.setBasePotionData(new PotionData(PotionType.SLOW_FALLING));
        arrow.setItemMeta((ItemMeta)potionMeta);
        this.kitRoomItems.add(arrow);
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.FIRE_ASPECT, 2).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.ENDER_PEARL, 16)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.ENDER_PEARL, 16)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.DIAMOND_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.DIAMOND_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.DIAMOND_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.DIAMOND_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.BOW)).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.CROSSBOW)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PIERCING, 4).addEnchantment(Enchantment.QUICK_CHARGE, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_PICKAXE)).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(PotionType.SPEED, 2, 1, true)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(PotionType.SPEED, 2, 1, true)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.OBSIDIAN, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.END_CRYSTAL, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.RESPAWN_ANCHOR, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.GLOWSTONE, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.BOW)).addEnchantment(Enchantment.ARROW_FIRE, 1).addEnchantment(Enchantment.ARROW_INFINITE, 1).addEnchantment(Enchantment.ARROW_DAMAGE, 5).addEnchantment(Enchantment.ARROW_KNOCKBACK, 2).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.YELLOW_SHULKER_BOX)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.NETHERITE_AXE)).addEnchantment(Enchantment.DAMAGE_ALL, 4).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(PotionType.STRENGTH, 2, 1, true)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(PotionType.STRENGTH, 2, 1, true)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.EXPERIENCE_BOTTLE, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.EXPERIENCE_BOTTLE, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.GOLDEN_APPLE, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.GOLDEN_APPLE, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.SHIELD)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.BLUE_SHULKER_BOX)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.ENDER_CHEST, 64)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.TOTEM_OF_UNDYING)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.TOTEM_OF_UNDYING)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.TOTEM_OF_UNDYING)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.TOTEM_OF_UNDYING)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.TOTEM_OF_UNDYING)).toItemStack());
        this.kitRoomItems.add((new ItemUtil(Material.TOTEM_OF_UNDYING)).toItemStack());
    }

    private void repairItem(ItemStack item) {
        if(item == null) return;
        item.setDurability((short) 0);
    }

    @EventHandler
    public void handle(InventoryClickEvent event) {
        Player player;
        HumanEntity humanEntity = event.getWhoClicked();
        if (humanEntity instanceof Player) {
            player = (Player) humanEntity;
        } else {
            return;
        }

        if (event.getInventory().getType() == InventoryType.PLAYER)
            return;
        if (event.getInventory().getType() == InventoryType.CREATIVE)
            return;
        if (event.getCurrentItem() == null)
            return;
        if (event.getCurrentItem().getItemMeta() == null)
            return;
        InventoryView view = event.getView();
        ItemStack itemStack = event.getCurrentItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Kit Preview")){
            event.setCancelled(true);
        }
        if(event.getView().getTitle().equalsIgnoreCase(ChatColor.GOLD + "Settings")) {
            event.setCancelled(true);
            if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Death Messages")) {
                WeirdoKits weirdoKits = WeirdoKits.getInstance();
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                if (weirdoKits.deathmessages.contains(player.getUniqueId())) {
                    weirdoKits.deathmessages.remove(player.getUniqueId());
                    player.sendRichMessage("<gold><bold>WK <reset><gray>Toggled Death Messages on");
                    player.sendActionBar(MiniMessage.miniMessage().deserialize("<gold><bold>ON"));
                } else {
                    weirdoKits.deathmessages.add(player.getUniqueId());
                    player.sendRichMessage("<gold><bold>WK <reset><gray>Toggled Death Messages off");
                    player.sendActionBar(MiniMessage.miniMessage().deserialize("<gold><bold>OFF"));
                }
            }
            if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Autokit")) {
                WeirdoKits weirdoKits = WeirdoKits.getInstance();
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                if (weirdoKits.autokit.contains(player.getUniqueId())) {
                    weirdoKits.autokit.remove(player.getUniqueId());
                    player.sendRichMessage("<gold><bold>WK <reset><gray>Toggled Autokit on");
                    player.sendActionBar(MiniMessage.miniMessage().deserialize("<gold><bold>ON"));
                } else {
                    weirdoKits.autokit.add(player.getUniqueId());
                    player.sendRichMessage("<gold><bold>WK <reset><gray>Toggled Autokit off");
                    player.sendActionBar(MiniMessage.miniMessage().deserialize("<gold><bold>OFF"));
                }
            }

            if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Re-Kit Messages")) {
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
                WeirdoKits weirdoKits = WeirdoKits.getInstance();
                if (weirdoKits.broadcast.contains(player.getUniqueId())) {
                    weirdoKits.broadcast.remove(player.getUniqueId());
                    player.sendRichMessage("<gold><bold>WK <reset><gray>Toggled broadcast messages on");
                    player.sendActionBar(MiniMessage.miniMessage().deserialize("<gold><bold>ON"));
                } else {
                    weirdoKits.broadcast.add(player.getUniqueId());
                    player.sendRichMessage("<gold><bold>WK <reset><gray>Toggled broadcast messages off");
                    player.sendActionBar(MiniMessage.miniMessage().deserialize("<gold><bold>OFF"));
                }
            }
        }

        if (event.getView().getTitle().equalsIgnoreCase("Kits")) {
            event.setCancelled(true);
            if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Repair Items" )) {
                for (ItemStack item : player.getInventory().getContents()) {
                    repairItem(item);
                }
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1,2);
                for(Player global : Bukkit.getOnlinePlayers()) {
                    if (!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                        global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " repaired their inventory.");
                }
                player.updateInventory();
            }

            if(itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Settings")){
                Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Settings");
                for(int x = 0; x < 9; x++){
                    inventory.setItem(x, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE).setName(" ").toItemStack()));
                }
                for(int x = 18; x < 27; x++){
                    inventory.setItem(x, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE).setName(" ").toItemStack()));
                }
                inventory.setItem(17, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE).setName(" ").toItemStack()));
                inventory.setItem(9, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE).setName(" ").toItemStack()));

                inventory.setItem(11, (new ItemUtil(Material.IRON_CHESTPLATE).setName(ChatColor.GOLD + "Autokit").setLore(ChatColor.WHITE + "Click to toggle").addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack()));
                inventory.setItem(13, (new ItemUtil(Material.SKELETON_SKULL).setName(ChatColor.GOLD + "Death Messages").setLore(ChatColor.WHITE + "Click to toggle").toItemStack()));
                inventory.setItem(15, (new ItemUtil(Material.SPRUCE_SIGN).setName(ChatColor.GOLD + "Re-Kit Messages").setLore(ChatColor.WHITE + "Click to toggle").toItemStack()));




                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
            }

            if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Clear Inventory" )) {
                if(event.getClick().isShiftClick()){
                    player.getInventory().clear();
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1,2);
                    for(Player global : Bukkit.getOnlinePlayers()) {
                        if (!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                            global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " cleared their inventory");
                    }
                }
            }

            if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Premade Kit" )) {
                for(int x = 0; x < player.getInventory().getSize(); x++){
                    player.getInventory().setItem(x, new ItemStack(Material.TOTEM_OF_UNDYING));
                }
                player.getInventory().setHelmet((new ItemUtil(Material.NETHERITE_HELMET)).addEnchantment(Enchantment.WATER_WORKER, 1).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.OXYGEN, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
                player.getInventory().setChestplate((new ItemUtil(Material.NETHERITE_CHESTPLATE)).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
                player.getInventory().setLeggings((new ItemUtil(Material.NETHERITE_LEGGINGS)).addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
                player.getInventory().setBoots((new ItemUtil(Material.NETHERITE_BOOTS)).addEnchantment(Enchantment.DEPTH_STRIDER, 3).addEnchantment(Enchantment.PROTECTION_FALL, 4).addEnchantment(Enchantment.MENDING, 1).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
                player.getInventory().setItem(0,(new ItemUtil(Material.NETHERITE_SWORD)).addEnchantment(Enchantment.KNOCKBACK, 1).addEnchantment(Enchantment.DAMAGE_ALL, 5).addEnchantment(Enchantment.SWEEPING_EDGE, 3).addEnchantment(Enchantment.DURABILITY, 3).toItemStack());
                player.getInventory().setItem(1, new ItemStack(Material.END_CRYSTAL, 64));
                player.getInventory().setItem(2, new ItemStack(Material.ENDER_PEARL, 16));
                player.getInventory().setItem(4, new ItemStack(Material.GOLDEN_APPLE, 64));
                player.getInventory().setItem(5, new ItemStack(Material.OBSIDIAN, 64));
                player.getInventory().setItem(6, (new ItemUtil(Material.NETHERITE_PICKAXE).addEnchantment(Enchantment.DIG_SPEED, 5).addEnchantment(Enchantment.DURABILITY, 3).addEnchantment(Enchantment.MENDING, 1).toItemStack()));
                player.getInventory().setItem(7, new ItemStack(Material.RESPAWN_ANCHOR, 64));
                player.getInventory().setItem(8, new ItemStack(Material.GLOWSTONE, 64));
                player.getInventory().setItem(28, new ItemStack(Material.END_CRYSTAL, 64));
                player.getInventory().setItem(29, new ItemStack(Material.ENDER_PEARL, 16));
                player.getInventory().setItem(30, new ItemStack(Material.ENDER_PEARL, 16));
                player.getInventory().setItem(32, new ItemStack(Material.OBSIDIAN, 64));
                player.getInventory().setItem(17, new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
                player.getInventory().setItem(26, new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
                player.getInventory().setItem(35, new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
                player.getInventory().setItem(20, new ItemStack(Material.ENDER_PEARL, 16));
                player.getInventory().setItem(21, new ItemStack(Material.ENDER_PEARL, 16));

                for(Player global : Bukkit.getOnlinePlayers()) {
                    if (!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                        global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has claimed the premade kit.");
                }
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1,2);



            }
                    for (int i = 1; i <= 9; i++) {
                        if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLDK + "Kit " + i)) {
                            for(Player global : Bukkit.getOnlinePlayers()) {
                                if (!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                                    global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has opened the kit editor.");
                            }
                            Inventory inventory = kitInventory(itemMeta.getDisplayName());
                            HashMap<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().load(player.getUniqueId(),  String.valueOf(i));
                            for (int slot = 0; slot < 41; slot++) {
                                inventory.setItem(slot, map.get(Integer.valueOf(slot)));
                            }
                            player.openInventory(inventory);
                            player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
                        }
                    }

            for (int i = 1; i <= 9; i++) {
                if (itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Enderchest " + i)) {
                    for(Player global : Bukkit.getOnlinePlayers()) {
                        if (!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                            global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has opened the enderchest editor.");
                    }
                    Inventory inventory = ecinventory(itemMeta.getDisplayName());
                    HashMap<Integer, ItemStack> map = WeirdoKits.getInstance().getConfigUtil().loadec(player.getUniqueId(), String.valueOf(i));
                    for (int slot = 0; slot < 27; slot++) {
                        inventory.setItem(slot, map.get(Integer.valueOf(slot)));
                    }
                    player.openInventory(inventory);
                    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
                }
            }



            if (itemStack.getType() == Material.NETHER_STAR) {
                Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Kit Room");
                int slot = 0;
                for (ItemStack kitRoomItem : this.kitRoomItems) {
                    inventory.setItem(slot, kitRoomItem);
                    slot++;
                }
                for(Player global : Bukkit.getOnlinePlayers()) {
                    if (!WeirdoKits.getInstance().broadcast.contains(global.getUniqueId()))
                        global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has opened the kit room.");
                }

                for (int j = 1; j < 8; j++)
                    inventory.setItem(j + 45, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
                inventory.setItem(47, (new ItemUtil(Material.NETHERITE_SWORD).setName(ChatColor.GOLD + "Armor").addEnchantment(Enchantment.FROST_WALKER, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack()));
                inventory.setItem(48, (new ItemUtil(Material.SPLASH_POTION).setName(ChatColor.GOLD + "Potions").toItemStack()));
                inventory.setItem(49, (new ItemUtil(Material.ENDER_PEARL).setName(ChatColor.GOLD + "Consumables").toItemStack()));
                inventory.setItem(50, (new ItemUtil(Material.ARROW).setName(ChatColor.GOLD + "Arrows").toItemStack()));
                inventory.setItem(51, (new ItemUtil(Material.END_CRYSTAL).setName(ChatColor.GOLD + "Explosives").toItemStack()));
                inventory.setItem(53, (new ItemUtil(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setName(ChatColor.GOLD + "Armor Trims").toItemStack()));
                inventory.setItem(45, (new ItemUtil(Material.BARRIER)).setName(ChatColor.RED + "Back").toItemStack());
                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
            }


        }
        if (view.getTitle().equalsIgnoreCase(ChatColor.GOLD + "Kit Room")) {
            if (itemStack.getType() == Material.BARRIER) {
                event.setCancelled(true);
                WeirdoKits.getInstance().openInventory(player);
            }
            if (itemStack.getType() == Material.NETHERITE_SWORD && itemStack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Armor")) {
                event.setCancelled(true);
                Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Kit Room");
                int slot = 0;
                for (ItemStack kitRoomItem : this.kitRoomItems) {
                    inventory.setItem(slot, kitRoomItem);
                    slot++;
                }

                for (int j = 1; j < 8; j++)
                    inventory.setItem(j + 45, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
                inventory.setItem(47, (new ItemUtil(Material.NETHERITE_SWORD).setName(ChatColor.GOLD + "Armor").addEnchantment(Enchantment.FROST_WALKER, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack()));
                inventory.setItem(48, (new ItemUtil(Material.SPLASH_POTION).setName(ChatColor.GOLD + "Potions").toItemStack()));
                inventory.setItem(49, (new ItemUtil(Material.ENDER_PEARL).setName(ChatColor.GOLD + "Consumables").toItemStack()));
                inventory.setItem(50, (new ItemUtil(Material.ARROW).setName(ChatColor.GOLD + "Arrows").toItemStack()));
                inventory.setItem(51, (new ItemUtil(Material.END_CRYSTAL).setName(ChatColor.GOLD + "Explosives").toItemStack()));
                inventory.setItem(53, (new ItemUtil(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setName(ChatColor.GOLD + "Armor Trims").toItemStack()));
                inventory.setItem(45, (new ItemUtil(Material.BARRIER)).setName(ChatColor.RED + "Back").toItemStack());
                player.openInventory(inventory);
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
            }

                if (itemStack.getType() == Material.SPLASH_POTION && itemStack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Potions")) {
                    event.setCancelled(true);
                    Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Kit Room");
                    ItemStack strenght = (new ItemUtil(Material.SPLASH_POTION, 1)).toItemStack();
                    PotionMeta potionMeta = (PotionMeta)strenght.getItemMeta();
                    potionMeta.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
                    strenght.setItemMeta((ItemMeta)potionMeta);

                    ItemStack speed = (new ItemUtil(Material.SPLASH_POTION, 1)).toItemStack();
                    PotionMeta sppedmeta = (PotionMeta)speed.getItemMeta();
                    sppedmeta.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
                    speed.setItemMeta((ItemMeta)sppedmeta);

                    ItemStack invis = (new ItemUtil(Material.SPLASH_POTION, 1)).toItemStack();
                    PotionMeta invismeta = (PotionMeta)invis.getItemMeta();
                    invismeta.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
                    invis.setItemMeta((ItemMeta)invismeta);

                    ItemStack regen = (new ItemUtil(Material.SPLASH_POTION, 1)).toItemStack();
                    PotionMeta regenm = (PotionMeta)regen.getItemMeta();
                    regenm.setBasePotionData(new PotionData(PotionType.REGEN, true, false));
                    regen.setItemMeta((ItemMeta)regenm);

                    ItemStack regen2 = (new ItemUtil(Material.SPLASH_POTION, 1)).toItemStack();
                    PotionMeta regenm2 = (PotionMeta)regen2.getItemMeta();
                    regenm2.setBasePotionData(new PotionData(PotionType.REGEN, false, true));
                    regen2.setItemMeta((ItemMeta)regenm2);

                    for(int x = 0; x < 9; x++)
                      inv.setItem(x, speed);
                    for(int x = 9; x < 18; x++)
                        inv.setItem(x, strenght);
                    for(int x = 18; x < 27; x++)
                        inv.setItem(x, invis);
                    for(int x = 27; x < 36; x++)
                        inv.setItem(x, regen);
                    for(int x = 36; x < 45; x++)
                        inv.setItem(x, regen2);

                    for (int j = 1; j < 8; j++)
                        inv.setItem(j + 45, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
                    inv.setItem(47, (new ItemUtil(Material.NETHERITE_SWORD).setName(ChatColor.GOLD + "Armor").addEnchantment(Enchantment.FROST_WALKER, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack()));
                    inv.setItem(48, (new ItemUtil(Material.SPLASH_POTION).setName(ChatColor.GOLD + "Potions").toItemStack()));
                    inv.setItem(49, (new ItemUtil(Material.ENDER_PEARL).setName(ChatColor.GOLD + "Consumables").toItemStack()));
                    inv.setItem(50, (new ItemUtil(Material.ARROW).setName(ChatColor.GOLD + "Arrows").toItemStack()));
                    inv.setItem(51, (new ItemUtil(Material.END_CRYSTAL).setName(ChatColor.GOLD + "Explosives").toItemStack()));
                    inv.setItem(53, (new ItemUtil(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setName(ChatColor.GOLD + "Armor Trims").toItemStack()));
                    inv.setItem(45, (new ItemUtil(Material.BARRIER)).setName(ChatColor.RED + "Back").toItemStack());
                    player.openInventory(inv);
                    player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
                }

            if (itemStack.getType() == Material.END_CRYSTAL && itemStack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Explosives")    ) {
                event.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Kit Room");

                for(int x = 0; x < 9; x++){
                    inv.setItem(x, new ItemStack(Material.END_CRYSTAL, 64));
                }
                for(int x = 9; x < 18; x++){
                    inv.setItem(x, new ItemStack(Material.RESPAWN_ANCHOR, 64));
                }
                for(int x = 18; x < 27; x++){
                    inv.setItem(x, new ItemStack(Material.GLOWSTONE, 64));
                }
                for(int x = 27; x < 36; x++){
                    inv.setItem(x, new ItemStack(Material.OBSIDIAN, 64));
                }
                for(int x = 36; x < 45; x++){
                    inv.setItem(x, new ItemStack(Material.BLACK_BED, 1));
                }


                for (int j = 1; j < 8; j++)
                    inv.setItem(j + 45, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
                inv.setItem(47, (new ItemUtil(Material.NETHERITE_SWORD).setName(ChatColor.GOLD + "Armor").addEnchantment(Enchantment.FROST_WALKER, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack()));
                inv.setItem(48, (new ItemUtil(Material.SPLASH_POTION).setName(ChatColor.GOLD + "Potions").toItemStack()));
                inv.setItem(49, (new ItemUtil(Material.ENDER_PEARL).setName(ChatColor.GOLD + "Consumables").toItemStack()));
                inv.setItem(50, (new ItemUtil(Material.ARROW).setName(ChatColor.GOLD + "Arrows").toItemStack()));
                inv.setItem(51, (new ItemUtil(Material.END_CRYSTAL).setName(ChatColor.GOLD + "Explosives").toItemStack()));
                inv.setItem(53, (new ItemUtil(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setName(ChatColor.GOLD + "Armor Trims").toItemStack()));
                inv.setItem(45, (new ItemUtil(Material.BARRIER)).setName(ChatColor.RED + "Back").toItemStack());
                player.openInventory(inv);
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
            }

            if (itemStack.getType() == Material.ENDER_PEARL && itemStack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Consumables")) {
                event.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Kit Room");

                ItemStack gap = new ItemStack(Material.GOLDEN_APPLE, 64);
                ItemStack pearl = new ItemStack(Material.ENDER_PEARL, 16);
                ItemStack xp = new ItemStack(Material.EXPERIENCE_BOTTLE, 64);
                ItemStack echest = new ItemStack(Material.ENDER_CHEST, 64);
                ItemStack glowstone = new ItemStack(Material.GLOWSTONE, 64);

                for(int x = 0; x < 9; x++)
                    inv.setItem(x, gap);
                for(int x = 9; x < 18; x++)
                    inv.setItem(x, pearl);
                for(int x = 18; x < 27; x++)
                    inv.setItem(x, xp);
                for(int x = 27; x < 36; x++)
                    inv.setItem(x, echest);
                for(int x = 36; x < 45; x++)
                    inv.setItem(x, glowstone);


                for (int j = 1; j < 8; j++)
                    inv.setItem(j + 45, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
                inv.setItem(47, (new ItemUtil(Material.NETHERITE_SWORD).setName(ChatColor.GOLD + "Armor").addEnchantment(Enchantment.FROST_WALKER, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack()));
                inv.setItem(48, (new ItemUtil(Material.SPLASH_POTION).setName(ChatColor.GOLD + "Potions").toItemStack()));
                inv.setItem(49, (new ItemUtil(Material.ENDER_PEARL).setName(ChatColor.GOLD + "Consumables").toItemStack()));
                inv.setItem(50, (new ItemUtil(Material.ARROW).setName(ChatColor.GOLD + "Arrows").toItemStack()));
                inv.setItem(51, (new ItemUtil(Material.END_CRYSTAL).setName(ChatColor.GOLD + "Explosives").toItemStack()));
                inv.setItem(53, (new ItemUtil(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setName(ChatColor.GOLD + "Armor Trims").toItemStack()));
                inv.setItem(45, (new ItemUtil(Material.BARRIER)).setName(ChatColor.RED + "Back").toItemStack());
                player.openInventory(inv);
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
            }

            if (itemStack.getType() == Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE && itemStack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Armor Trims")) {
                event.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Kit Room");


                for (int x = 0; x < 9; x++){
                    inv.setItem(x, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
                }

                for (int x = 45; x < 54; x++){
                    inv.setItem(x, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
                }

                inv.setItem(19, new ItemStack(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE, 64));
                inv.setItem(20, new ItemStack(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE, 64));
                inv.setItem(21, new ItemStack(Material.EYE_ARMOR_TRIM_SMITHING_TEMPLATE, 64));
                inv.setItem(22, new ItemStack(Material.HOST_ARMOR_TRIM_SMITHING_TEMPLATE, 64));
                inv.setItem(23, new ItemStack(Material.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE, 64));
                inv.setItem(24, new ItemStack(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE, 64));
                inv.setItem(25, new ItemStack(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE, 64));
                inv.setItem(19, new ItemStack(Material.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE, 64));


                inv.setItem(29, new ItemStack(Material.IRON_INGOT, 64));
                inv.setItem(30, new ItemStack(Material.GOLD_INGOT, 64));
                inv.setItem(31, new ItemStack(Material.EMERALD, 64));
                inv.setItem(32, new ItemStack(Material.DIAMOND, 64));
                inv.setItem(33, new ItemStack(Material.NETHERITE_INGOT, 64));



                player.openInventory(inv);
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
            }

            if (itemStack.getType() == Material.ARROW && itemStack.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Arrows")) {
                event.setCancelled(true);
                Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Kit Room");
                for(int x = 0; x <9; x++){
                    ItemStack arrow = (new ItemUtil(Material.TIPPED_ARROW, 64)).toItemStack();
                    PotionMeta potionMeta = (PotionMeta)arrow.getItemMeta();
                    potionMeta.setBasePotionData(new PotionData(PotionType.SLOW_FALLING));
                    arrow.setItemMeta((ItemMeta)potionMeta);
                    inv.setItem(x, arrow);
                }
                for(int x = 9; x <18; x++){
                    ItemStack arrow = (new ItemUtil(Material.TIPPED_ARROW, 64)).toItemStack();
                    PotionMeta potionMeta = (PotionMeta)arrow.getItemMeta();
                    potionMeta.setBasePotionData(new PotionData(PotionType.WEAKNESS));
                    arrow.setItemMeta((ItemMeta)potionMeta);
                    inv.setItem(x, arrow);
                }
                for(int x = 18; x <27; x++){
                    ItemStack arrow = (new ItemUtil(Material.TIPPED_ARROW, 64)).toItemStack();
                    PotionMeta potionMeta = (PotionMeta)arrow.getItemMeta();
                    potionMeta.setBasePotionData(new PotionData(PotionType.POISON));
                    arrow.setItemMeta((ItemMeta)potionMeta);
                    inv.setItem(x, arrow);
                }
                for(int x = 27; x <36; x++){
                    inv.setItem(x, new ItemStack(Material.SPECTRAL_ARROW, 64));
                }
                for(int x = 36; x <45; x++){
                    inv.setItem(x, new ItemStack(Material.ARROW, 64));
                }



                for (int j = 1; j < 8; j++)
                    inv.setItem(j + 45, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
                inv.setItem(47, (new ItemUtil(Material.NETHERITE_SWORD).setName(ChatColor.GOLD + "Armor").addEnchantment(Enchantment.FROST_WALKER, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack()));
                inv.setItem(48, (new ItemUtil(Material.SPLASH_POTION).setName(ChatColor.GOLD + "Potions").toItemStack()));
                inv.setItem(49, (new ItemUtil(Material.ENDER_PEARL).setName(ChatColor.GOLD + "Consumables").toItemStack()));
                inv.setItem(50, (new ItemUtil(Material.ARROW).setName(ChatColor.GOLD + "Arrows").toItemStack()));
                inv.setItem(51, (new ItemUtil(Material.END_CRYSTAL).setName(ChatColor.GOLD + "Explosives").toItemStack()));
                inv.setItem(53, (new ItemUtil(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE).setName(ChatColor.GOLD + "Armor Trims").toItemStack()));
                inv.setItem(45, (new ItemUtil(Material.BARRIER)).setName(ChatColor.RED + "Back").toItemStack());
                player.openInventory(inv);
                player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
            }

                if (itemStack.getType() == Material.GRAY_STAINED_GLASS_PANE)
                    event.setCancelled(true);
                return;
            }
        if (view.getTitle().startsWith(ChatColor.GOLD + "Enderchest ")) {
            if (itemStack.getType() == Material.DIAMOND_CHESTPLATE) {
                event.setCancelled(true);
                Inventory inventory = event.getView().getTopInventory();
                for (int slot = 0; slot < 27; slot++)
                    inventory.setItem(slot, player.getInventory().getItem(slot));
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1,2);
            }
            if (itemStack.getType() == Material.ENDER_CHEST) {
                event.setCancelled(true);
                Inventory inventory = event.getView().getTopInventory();
                for (int slot = 0; slot < 27; slot++)
                    inventory.setItem(slot, player.getEnderChest().getItem(slot));
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1,2);
            }
            if (itemStack.getType() == Material.GRAY_STAINED_GLASS_PANE)
                event.setCancelled(true);
        }
            if (view.getTitle().startsWith(ChatColor.GOLD + "Kit ")) {
                if (itemStack.getType() == Material.DIAMOND_CHESTPLATE) {
                    event.setCancelled(true);
                    if(player.getInventory().contains(Material.ENCHANTED_GOLDEN_APPLE)){
                        player.playSound(player, Sound.BLOCK_ANVIL_PLACE,1,2);
                        player.closeInventory();
                        return;
                    }
                    Inventory inventory = event.getView().getTopInventory();
                    for (int slot = 0; slot < 36; slot++)
                        inventory.setItem(slot, player.getInventory().getItem(slot));
                    inventory.setItem(36, player.getInventory().getHelmet());
                    inventory.setItem(37, player.getInventory().getChestplate());
                    inventory.setItem(38, player.getInventory().getLeggings());
                    inventory.setItem(39, player.getInventory().getBoots());
                    inventory.setItem(40, player.getInventory().getItemInOffHand());
                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1,2);
                }
                if (itemStack.getType() == Material.GRAY_STAINED_GLASS_PANE)
                    event.setCancelled(true);
            }
    }

private Inventory kitInventory(String name) {
    Inventory inventory = Bukkit.createInventory(null, 45, name);
    inventory.setItem(41, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName("").toItemStack());
    inventory.setItem(42, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
    inventory.setItem(43, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName(" ").toItemStack());
    inventory.setItem(44, (new ItemUtil(Material.DIAMOND_CHESTPLATE)).setName(ChatColor.AQUA + "Import from Inventory").addEnchantment(Enchantment.DAMAGE_ALL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());
    return inventory;
}

    private Inventory ecinventory(String name) {
        Inventory inventory = Bukkit.createInventory(null, 36, name);
        for (int x = 27; x < 36; x++) {
            inventory.setItem(x, (new ItemUtil(Material.GRAY_STAINED_GLASS_PANE)).setName("").toItemStack());
        }
        inventory.setItem(35, (new ItemUtil(Material.DIAMOND_CHESTPLATE)).setName(ChatColor.AQUA + "Import from Inventory").addEnchantment(Enchantment.DAMAGE_ALL, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).addItemFlag(ItemFlag.HIDE_ATTRIBUTES).toItemStack());
        inventory.setItem(34, (new ItemUtil(Material.ENDER_CHEST).setName(ChatColor.AQUA + "Import from Enderchest").toItemStack()));
        return inventory;
    }
}
