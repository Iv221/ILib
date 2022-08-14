package de.iv.iutils.menus;

import de.iv.iutils.exceptions.MenuManagerException;
import de.iv.iutils.exceptions.MenuManagerNotSetupException;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class MenuManager {

    private static HashMap<Player, InventoryMapper> mapperMap = new HashMap<>();
    private static boolean isSetup = false;

    public static void setup(Server server, Plugin plugin) {
        registerListener(server, plugin);
        System.out.println("MENU MANAGER WAS REGISTERED");
        isSetup = true;
    }

    /**
     * @param server - The server the plugin is running on
     * @param plugin - The plugin instance
     */
    private static void registerListener(Server server, Plugin plugin) {
        boolean isRegistered = false;
        for (RegisteredListener rl : InventoryClickEvent.getHandlerList().getRegisteredListeners()) {
            System.out.println(rl.getListener().getClass().getSimpleName());
            if(rl.getListener() instanceof MenuListener) {
                isRegistered = true;
                break;
            }
        }
        if(!isRegistered) {
            server.getPluginManager().registerEvents(new MenuListener(), plugin);
        }
    }


    /**
     *
     * @param menuClass - The class of the menu that will be opened
     * @param p - The player the menu is displayed to
     */
    public static void openMenu(Class<? extends Menu> menuClass, Player p) throws MenuManagerException, MenuManagerNotSetupException {
        try {
            menuClass.getConstructor(InventoryMapper.class).newInstance(getMapper(p)).open();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new MenuManagerException();
        }
    }


    public static InventoryMapper getMapper(Player p) throws MenuManagerException, MenuManagerNotSetupException{
        if(!isSetup) {
            throw new MenuManagerNotSetupException();
        }

        InventoryMapper mapper;
        if(!mapperMap.containsKey(p)) {
            mapper = new InventoryMapper(p);
            mapperMap.put(p, mapper);
            return mapper;
        } else return mapperMap.get(p);
    }

}
