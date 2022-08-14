package de.iv.iutils.menus;

import de.iv.iutils.exceptions.MenuManagerException;
import de.iv.iutils.exceptions.MenuManagerNotSetupException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.StringJoiner;

public abstract class Menu implements InventoryHolder {

    protected Inventory inventory;
    protected InventoryMapper mapper;
    protected Player p;
    protected ItemStack FILLER_GLASS = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

    public Menu(InventoryMapper mapper) {
        this.mapper = mapper;
        this.p = mapper.getOwner();
    }

    public abstract String getMenuName();
    public abstract int getSlots();
    public abstract boolean cancelAllClicks();
    public abstract void handle(InventoryClickEvent e);
    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        mapper.getOwner().openInventory(inventory);
    }

    protected void reloadItems() {
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, null);
        }
        setMenuItems();
    }

    protected void reload() throws MenuManagerException, MenuManagerNotSetupException {
        p.closeInventory();
        MenuManager.openMenu(this.getClass(), p);
    }

}
