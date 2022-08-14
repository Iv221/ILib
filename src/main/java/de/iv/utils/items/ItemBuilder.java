package de.iv.utils.items;


import de.iv.iutils.exceptions.ItemBuilderException;
import de.iv.iutils.exceptions.ManagerSetupException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;

public class ItemBuilder {

    private static boolean isSetup = false;

    private static Plugin plugin;

    private ItemStack is;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, byte durability) {
        is = new ItemStack(m, amount, durability);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(is);
    }

    public ItemBuilder setCustomModelData(int data) {
        ItemMeta im = is.getItemMeta();
        im.setCustomModelData(data);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    public static void setGlowing(ItemStack item, Inventory inventory, int index, boolean glow) {
        ItemMeta im = item.getItemMeta();
        if(!glow && im.hasEnchant(Enchantment.WATER_WORKER)) {
            im.removeEnchant(Enchantment.WATER_WORKER);
            im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            im.addEnchant(Enchantment.WATER_WORKER, 1, true);
            im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(im);
        inventory.setItem(index, item);
    }

    public ItemStack build() {
        return is;
    }

    public static void setup(Plugin plugin) throws NoSuchFieldException, IllegalAccessException, ManagerSetupException {
        if(!isSetup) {
            ItemBuilder.plugin = plugin;
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);

            NamespacedKey key = new NamespacedKey(plugin, plugin.getDescription().getName());
            Glow glow = new Glow(key);
            Enchantment.registerEnchantment(glow);
            isSetup = true;
        } else throw new ManagerSetupException();

    }

}
