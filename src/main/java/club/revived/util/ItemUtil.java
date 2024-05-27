package club.revived.util;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class ItemUtil {
    private final ItemStack item;

    public ItemUtil(Material material) {
        this.item = new ItemStack(material);
    }

    public ItemUtil(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public ItemUtil(PotionType potionType, int level, int amount, boolean splash) {
        this.item = (new Potion(potionType, level, splash)).toItemStack(amount);
    }

    public ItemUtil setName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil setLore(String... lore) {
        setLore(Arrays.asList(lore));
        return this;
    }

    public ItemUtil setLore(List<String> lore) {
        if (lore.isEmpty())
            return this;
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(lore);
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemUtil addEnchantment(Enchantment enchantment, int level) {
        if (this.item.getType() == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)this.item.getItemMeta();
            meta.addStoredEnchant(enchantment, level, true);
            this.item.setItemMeta((ItemMeta)meta);
        } else {
            ItemMeta meta = this.item.getItemMeta();
            meta.addEnchant(enchantment, level, true);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    public ItemUtil addItemFlag(ItemFlag flag) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(new ItemFlag[] { flag });
        this.item.setItemMeta(meta);
        return this;
    }

    public ItemStack toItemStack() {
        return this.item;
    }
}
