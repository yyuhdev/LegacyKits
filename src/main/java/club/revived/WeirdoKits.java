package club.revived;

import java.io.*;
import java.util.*;

import club.revived.command.*;
import club.revived.listener.DeathListener;
import club.revived.listener.Eat;
import club.revived.listener.InventoryClickListener;
import club.revived.listener.InventoryCloseListener;
import club.revived.util.ConfigUtil;
import club.revived.util.ItemUtil;
import club.revived.command.Kit2Command;
import club.revived.command.Kit5Command;
import dev.manere.utils.library.wrapper.PluginWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class WeirdoKits extends PluginWrapper implements Listener {

    private final File autokitfile;
    private final File broadcastfile;
    private final File deathmessagefile;

    public ArrayList<UUID> autokit = new ArrayList<>();
    public ArrayList<UUID> broadcast = new ArrayList<>();
    public ArrayList<UUID> deathmessages = new ArrayList<>();

    public Map<UUID, Integer> LastUsedKit = new HashMap<>();

    public static final String PREFIX = "";

    private static WeirdoKits instance;

    private ConfigUtil configUtil;

    public WeirdoKits(){
        autokitfile = new File(this.getDataFolder(), "autokit.yml");
        if (!autokitfile.exists()) {
            try {
                autokitfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        broadcastfile = new File(this.getDataFolder(), "broadcast.yml");
        if (!broadcastfile.exists()) {
            try {
                broadcastfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        deathmessagefile = new File(this.getDataFolder(), "deathmessage.yml");
        if(!deathmessagefile.exists()){
            try {
                deathmessagefile.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        loadData();
    }

    @Override
    protected void start() {
        instance = this;
        this.configUtil = new ConfigUtil();
        new KitCommand("kit");
        new KitCommand("k");
        new KitCommand("kits");
        new Kit1Command("kit1");
        new Kit2Command("kit2");
        new Kit3Command("kit3");
        new Kit4Command("kit4");
        new Kit5Command("kit5");
        new Kit6Command("kit6");
        new Kit7Command("kit7");
        new Kit1Command("k1");
        new Kit2Command("k2");
        new Kit3Command("k3");
        new Kit4Command("k4");
        new Kit5Command("k5");
        new Kit6Command("k6");
        new Kit7Command("k7");
        new Ec1Command("ec1");
        new Ec2Command("ec2");
        new Ec3Command("ec3");
        new Ec4Command("ec4");
        new Ec5Command("ec5");
        new Ec6Command("ec6");
        new Ec7Command("ec7");
        new ViewKitCommand("viewkit");
        new ViewEcCommand("viewec");
        new GetKitCommand("getkit");
        new GetEcCommand("getec");

        Bukkit.getPluginManager().registerEvents((Listener) new InventoryClickListener(), (Plugin) instance);
        Bukkit.getPluginManager().registerEvents((Listener) new InventoryCloseListener(), (Plugin) instance);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new Eat(), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
    }

    @EventHandler
    public void stop(){
        saveData();
    }

    public static WeirdoKits getInstance() {
        return instance;
    }

    public ConfigUtil getConfigUtil() {
        return this.configUtil;
    }

    @EventHandler
    public void onDeath(PlayerRespawnEvent event) {
        WeirdoKits weirdoKits = WeirdoKits.getInstance();
        if (!autokit.contains(event.getPlayer().getUniqueId())) {
            Integer kit = weirdoKits.LastUsedKit.get(event.getPlayer().getUniqueId());
            if (kit == null) {
                return;
            }
            load(event.getPlayer(), String.valueOf(kit));
        }
    }

    public void openInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Kits");
        for (int i =0; i < inventory.getSize(); i++) {
            inventory.setItem(i, new ItemUtil(Material.GRAY_STAINED_GLASS_PANE).setName("").toItemStack());
        }
        for (int i =0; i < 9; i++) {

            inventory.setItem(40, (new ItemUtil(Material.ACACIA_SIGN).setName(ChatColor.GOLD + "Settings").setLore(List.of(ChatColor.WHITE + "Click to config", ChatColor.WHITE  + "messages & more")).toItemStack()));


            inventory.setItem(37, (new ItemUtil(Material.END_CRYSTAL).setName(ChatColor.GOLD + "Premade Kit").setLore(ChatColor.WHITE + "Click to claim").toItemStack()));

            inventory.setItem(42, (new ItemUtil(Material.EXPERIENCE_BOTTLE).setName(ChatColor.GOLD + "Repair Items").setLore(List.of(ChatColor.WHITE + "Click to repair", ChatColor.WHITE + "all items in you inventory")).toItemStack()));
            inventory.setItem(43, (new ItemUtil(Material.RED_DYE).setName(ChatColor.GOLD + "Clear Inventory").setLore(List.of(ChatColor.WHITE + "Shift click to clear", ChatColor.WHITE + "your inventory")).toItemStack()));

            inventory.setItem(10, (new ItemUtil(Material.CHEST)).setName(ChatColor.GOLD + "Kit " + 1).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(11, (new ItemUtil(Material.CHEST)).setName(ChatColor.GOLD + "Kit " + 2).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(12, (new ItemUtil(Material.CHEST)).setName(ChatColor.GOLD + "Kit " + 3).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(13, (new ItemUtil(Material.CHEST)).setName(ChatColor.GOLD + "Kit " + 4).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(14, (new ItemUtil(Material.CHEST)).setName(ChatColor.GOLD + "Kit " + 5).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(15, (new ItemUtil(Material.CHEST)).setName(ChatColor.GOLD + "Kit " + 6).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(16, (new ItemUtil(Material.CHEST)).setName(ChatColor.GOLD + "Kit " + 7).setLore(ChatColor.WHITE + "Click to edit").toItemStack());

            inventory.setItem(19, (new ItemUtil(Material.ENDER_CHEST)).setName(ChatColor.GOLD + "Enderchest " + 1).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(20, (new ItemUtil(Material.ENDER_CHEST)).setName(ChatColor.GOLD + "Enderchest " + 2).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(21, (new ItemUtil(Material.ENDER_CHEST)).setName(ChatColor.GOLD + "Enderchest " + 3).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(22, (new ItemUtil(Material.ENDER_CHEST)).setName(ChatColor.GOLD + "Enderchest " + 4).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(23, (new ItemUtil(Material.ENDER_CHEST)).setName(ChatColor.GOLD + "Enderchest " + 5).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(24, (new ItemUtil(Material.ENDER_CHEST)).setName(ChatColor.GOLD + "Enderchest " + 6).setLore(ChatColor.WHITE + "Click to edit").toItemStack());
            inventory.setItem(25, (new ItemUtil(Material.ENDER_CHEST)).setName(ChatColor.GOLD + "Enderchest " + 7).setLore(ChatColor.WHITE + "Click to edit").toItemStack());

            inventory.setItem(38, (new ItemUtil(Material.NETHER_STAR)).setName(ChatColor.GOLD + "Kitroom").setLore(ChatColor.WHITE + "Click to open").toItemStack());

        }

        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 5.0F, 5.0F);
    }

    public void ecload(Player player, String name) {
        HashMap<Integer, ItemStack> map = getInstance().getConfigUtil().loadec(player.getUniqueId(), name);
        player.getEnderChest().clear();
        for (int slot = 0; slot < 27; slot++) {
            player.getEnderChest().setItem(slot, map.get(Integer.valueOf(slot)));
        }
        player.sendRichMessage("<green>Enderchest has been loaded successfully");
        for(Player global : Bukkit.getOnlinePlayers()) {
            if (!broadcast.contains(global.getUniqueId()))
                global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has loaded an Enderchest.");
        }
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
        player.closeInventory();
    }

    public void ecloadothers(Player player, Player target, String name) {
        HashMap<Integer, ItemStack> map = getInstance().getConfigUtil().loadec(player.getUniqueId(), name);
        target.getEnderChest().clear();
        for (int slot = 0; slot < 27; slot++) {
            target.getEnderChest().setItem(slot, map.get(Integer.valueOf(slot)));
        }
        target.sendRichMessage("<green>Enderchest has been loaded successfully");
        for(Player global : Bukkit.getOnlinePlayers()) {
            if (!broadcast.contains(global.getUniqueId()))
                global.sendRichMessage("<gold><bold>WK <reset><gray>" + target.getName() + " has loaded an Enderchest.");
        }
        target.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
    }

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(autokitfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UUID uuid = UUID.fromString(line.trim());
                autokit.add(uuid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(broadcastfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UUID uuid = UUID.fromString(line.trim());
                broadcast.add(uuid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(broadcastfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UUID uuid = UUID.fromString(line.trim());
                broadcast.add(uuid);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(autokitfile))) {
            for (UUID uuid : autokit) {
                writer.write(uuid.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(deathmessagefile))) {
            for (UUID uuid : deathmessages) {
                writer.write(uuid.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(broadcastfile))) {
            for (UUID uuid : broadcast) {
                writer.write(uuid.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadothers(Player player, Player target, String name) {
        HashMap<Integer, ItemStack> map = getInstance().getConfigUtil().load(player.getUniqueId(), name);
        target.getInventory().clear();
        target.getInventory().setArmorContents(null);
        for (int slot = 0; slot < 36; slot++)
            target.getInventory().setItem(slot, map.get(Integer.valueOf(slot)));
        for (ItemStack itemStack : Arrays.<ItemStack>asList(new ItemStack[]{map.get(Integer.valueOf(36)), map.get(Integer.valueOf(37)), map.get(Integer.valueOf(38)), map.get(Integer.valueOf(39)), map.get(Integer.valueOf(40))})) {
            if (itemStack != null) {
                if (itemStack.getType().name().endsWith("_HELMET")) {
                    target.getInventory().setHelmet(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_CHESTPLATE")) {
                    target.getInventory().setChestplate(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_LEGGINGS")) {
                    target.getInventory().setLeggings(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_BOOTS")) {
                    target.getInventory().setBoots(itemStack);
                    continue;
                }
                target.getInventory().setItemInOffHand(itemStack);
            }
        }
        for (Player global : Bukkit.getOnlinePlayers()) {
            if (!broadcast.contains(global.getUniqueId()))
                global.sendRichMessage("<gold><bold>WK <reset><gray>" + target.getName() + " has loaded a kit.");
        }
    }

    public void load(Player player, String name) {
        HashMap<Integer, ItemStack> map = getInstance().getConfigUtil().load(player.getUniqueId(), name);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        LastUsedKit.put(player.getUniqueId(), Integer.valueOf(name));
        for (int slot = 0; slot < 36; slot++)
            player.getInventory().setItem(slot, map.get(Integer.valueOf(slot)));
        for (ItemStack itemStack : Arrays.<ItemStack>asList(new ItemStack[] { map.get(Integer.valueOf(36)), map.get(Integer.valueOf(37)), map.get(Integer.valueOf(38)), map.get(Integer.valueOf(39)), map.get(Integer.valueOf(40)) })) {
            if (itemStack != null) {
                if (itemStack.getType().name().endsWith("_HELMET")) {
                    player.getInventory().setHelmet(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_CHESTPLATE")) {
                    player.getInventory().setChestplate(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_LEGGINGS")) {
                    player.getInventory().setLeggings(itemStack);
                    continue;
                }
                if (itemStack.getType().name().endsWith("_BOOTS")) {
                    player.getInventory().setBoots(itemStack);
                    continue;
                }
                player.getInventory().setItemInOffHand(itemStack);
            }
        }
        for(Player global : Bukkit.getOnlinePlayers()) {
            if(!broadcast.contains(global.getUniqueId()))
                global.sendRichMessage("<gold><bold>WK <reset><gray>" + player.getName() + " has loaded a kit.");
        }

        player.sendRichMessage("<green>Kit has been loaded successfully");
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 5.0F, 5.0F);
        player.setFireTicks(0);
        player.closeInventory();
    }
}
