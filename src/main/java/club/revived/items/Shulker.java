package club.revived.items;

import dev.manere.utils.item.ItemBuilder;
import dev.manere.utils.text.color.TextStyle;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class Shulker {

    public static ItemStack crystalShulker(){
        ItemStack shulkerBoxItem = new ItemStack(Material.PINK_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(new ItemStack(Material.END_CRYSTAL, 64));
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<light_purple><bold>END CRYSTAL SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    public static ItemStack glowstoneShulker(){
        ItemStack shulkerBoxItem = new ItemStack(Material.YELLOW_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(new ItemStack(Material.GLOWSTONE, 64));
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<yellow><bold>GLOWSTONE SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    public static ItemStack anchorShulker(){
        ItemStack shulkerBoxItem = new ItemStack(Material.PURPLE_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(new ItemStack(Material.RESPAWN_ANCHOR, 64));
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<dark_purple><bold>RESPAWN ANCHOR SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    public static ItemStack pearlShulker(){
        ItemStack shulkerBoxItem = new ItemStack(Material.PINK_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 16));
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<light_purple><bold>ENDER PEARL SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    public static ItemStack gapShulker(){
        ItemStack shulkerBoxItem = new ItemStack(Material.YELLOW_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 64));
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<yellow><bold>GAPPLE SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    public static ItemStack totemShulker(){
        ItemStack shulkerBoxItem = new ItemStack(Material.YELLOW_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING));
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<yellow><bold>TOTEM SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    public static ItemStack xpShulker(){
        ItemStack shulkerBoxItem = new ItemStack(Material.LIME_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 64));
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<green><bold>XP SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    public static ItemStack obsidian(){
        ItemStack shulkerBoxItem = new ItemStack(Material.PURPLE_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(new ItemStack(Material.OBSIDIAN, 64));
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<dark_purple><bold>OBSIDIAN SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack strenghtShulker(){
        ItemStack strenght = ItemBuilder.item(Material.SPLASH_POTION, 1).build();
        PotionMeta potionMeta = (PotionMeta)strenght.getItemMeta();
        potionMeta.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
        strenght.setItemMeta(potionMeta);

        ItemStack shulkerBoxItem = new ItemStack(Material.ORANGE_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(strenght);
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<gold><bold>STRENGTH SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack speedShulker(){
        ItemStack speed = ItemBuilder.item(Material.SPLASH_POTION, 1).build();
        PotionMeta sppedmeta = (PotionMeta)speed.getItemMeta();
        sppedmeta.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        speed.setItemMeta(sppedmeta);

        ItemStack shulkerBoxItem = new ItemStack(Material.LIGHT_BLUE_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(speed);
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<AQUA><bold>SPEED SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack invisShulker(){
        ItemStack invis = ItemBuilder.item(Material.SPLASH_POTION, 1).build();
        PotionMeta invismeta = (PotionMeta)invis.getItemMeta();
        invismeta.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
        invis.setItemMeta(invismeta);

        ItemStack shulkerBoxItem = new ItemStack(Material.WHITE_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 27; x++){
            shulkerBox.getInventory().addItem(invis);
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<white><bold>INVIS SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack mixedShulker(){
        //hehe w
        ItemStack invis = ItemBuilder.item(Material.SPLASH_POTION, 1).build();
        PotionMeta invismeta = (PotionMeta)invis.getItemMeta();
        invismeta.setBasePotionData(new PotionData(PotionType.INVISIBILITY, true, false));
        invis.setItemMeta(invismeta);
        ItemStack speed = ItemBuilder.item(Material.SPLASH_POTION, 1).build();
        PotionMeta sppedmeta = (PotionMeta)speed.getItemMeta();
        sppedmeta.setBasePotionData(new PotionData(PotionType.SPEED, false, true));
        speed.setItemMeta(sppedmeta);
        ItemStack strenght = ItemBuilder.item(Material.SPLASH_POTION, 1).build();
        PotionMeta potionMeta = (PotionMeta)strenght.getItemMeta();
        potionMeta.setBasePotionData(new PotionData(PotionType.STRENGTH, false, true));
        strenght.setItemMeta(potionMeta);

        ItemStack shulkerBoxItem = new ItemStack(Material.YELLOW_SHULKER_BOX);
        BlockStateMeta blockStateMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        BlockState state = blockStateMeta.getBlockState();
        ShulkerBox shulkerBox = (ShulkerBox) state;
        for(int x = 0; x < 9; x++){
            shulkerBox.getInventory().addItem(invis);
        }
        for(int x = 9; x < 18; x++){
            shulkerBox.getInventory().addItem(strenght);
        }
        for(int x = 18; x < 27; x++){
            shulkerBox.getInventory().addItem(speed);
        }
        blockStateMeta.setBlockState(shulkerBox);
        blockStateMeta.displayName(TextStyle.style("<yellow><bold>MIXED SHULKER"));
        shulkerBoxItem.setItemMeta(blockStateMeta);
        return shulkerBoxItem;
    }
}
