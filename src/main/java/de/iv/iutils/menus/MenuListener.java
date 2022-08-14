package de.iv.iutils.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuListener implements Listener {

    @EventHandler
    public static void onClick(InventoryClickEvent e) {
        if(e.getInventory().getHolder() instanceof Menu) {
            if(e.getCurrentItem() == null) return;
            Menu menu = (Menu) e.getInventory().getHolder();
            if(menu.cancelAllClicks()) {
                e.setCancelled(true);
            }

            menu.handle(e);

        }
    }

}
