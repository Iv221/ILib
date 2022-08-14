package de.iv;

import de.iv.iutils.exceptions.ManagerSetupException;
import de.iv.iutils.menus.MenuManager;
import de.iv.iutils.sqlite.SQLite;
import de.iv.utils.items.ItemBuilder;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class ILib {

    public static void setup(Server server, Plugin plugin) throws NoSuchFieldException, IllegalAccessException, ManagerSetupException {
        MenuManager.setup(server, plugin);
        //ItemBuilder.setup(plugin);
    }

    public static void useSQLite(Plugin plugin) {
        SQLite.setup(plugin);
    }

}
