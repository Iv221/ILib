package de.iv.iutils.menus;

import org.bukkit.entity.Player;

public class InventoryMapper {

    private Player owner;

    public InventoryMapper(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
